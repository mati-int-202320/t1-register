/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uniandes.arti;

import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.PredicateBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * A Camel route that calls the REST service using a timer
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class RestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		restConfiguration().component("servlet").bindingMode(RestBindingMode.auto);
		Predicate is2XX = PredicateBuilder.and(header(Exchange.HTTP_RESPONSE_CODE).isGreaterThanOrEqualTo(200),header(Exchange.HTTP_RESPONSE_CODE).isLessThan(300));

		rest().path("/crearCuenta").consumes("application/json").produces("application/json")
			.post().type(Ciudadano.class).outType(Ciudadano.class)
			.to("direct:validateCiudadano");

		// Validamos que el ciudadano exista en el servicio externo de la registraduria
		from("direct:validateCiudadano").routeId("validateCiudadano")
			.bean(Formateador.class) // formatea los datos del ciudadano
	        .setProperty("originalCiudadano", body()) // Almacena el objeto Ciudadano original
			.setHeader("CamelHttpMethod", constant("POST"))
			.setHeader("Content-Type", constant("application/json"))
			.marshal().json(JsonLibrary.Jackson) // Marshal a JSON message
			.to("{{uniandes.arti.urlpath}}/operador/verificar/registraduria?bridgeEndpoint=true")
			.choice()
				.when(is2XX)
					.log("Respuesta validacion: ${body}")
	                .setBody(exchangeProperty("originalCiudadano")) // Devuelve el objeto Ciudadano original
					.to("direct:almacenarCiudadano")
				.otherwise()
					.log("La respuesta no fue 2XX")
			.end();

		from("direct:almacenarCiudadano").routeId("almacenarCiudadano")
			.log("Almacenando en base de datos")
			.setProperty("originalCiudadano", body()) // Almacena el objeto Ciudadano original
			.bean(CiudadanoDAO.class) // formatea los datos del ciudadano
			.log("Respuesta: ${body}")
			.setBody(exchangeProperty("originalCiudadano")) // Devuelve el objeto Ciudadano original
			.to("direct:asociarOperador");

		from("direct:asociarOperador").routeId("asociarOperador")
	        .setProperty("originalCiudadano", body()) // Almacena el objeto Ciudadano original
			.setHeader("CamelHttpMethod", constant("POST"))
			.setHeader("Content-Type", constant("application/json"))
			.marshal().json(JsonLibrary.Jackson) // Marshal a JSON message
			.to("{{uniandes.arti.urlpath}}/operador/asociar?bridgeEndpoint=true")
			.choice()
            	.when(is2XX)
					.log("Respuesta asociacion: ${body}")
					.setBody(exchangeProperty("originalCiudadano")) // Devuelve el objeto Ciudadano original
					.log("Respuesta: ${body}")
				.otherwise()
	                .log("La respuesta no fue 2XX")
			.end();

	}
}
