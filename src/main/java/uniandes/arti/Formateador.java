package uniandes.arti;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class Formateador implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Ciudadano ciudadano = exchange.getIn().getBody(Ciudadano.class);
        ciudadano.setNombres(ciudadano.getNombres().toUpperCase());
        ciudadano.setPrimerApellido(ciudadano.getPrimerApellido().toUpperCase());
        if(ciudadano.getSegundoApellido() != null)
            ciudadano.setSegundoApellido(ciudadano.getSegundoApellido().toUpperCase());
        else
            ciudadano.setSegundoApellido("");
        ciudadano.setCorreoAsociado(ciudadano.getTipoIdentificacion()+ciudadano.getIdentificacion()+"@carpeta.gov.co");
        exchange.getIn().setBody(ciudadano);
    }
    
}
