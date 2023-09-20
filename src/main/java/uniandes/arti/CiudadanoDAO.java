package uniandes.arti;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CiudadanoDAO implements Processor {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CiudadanoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        // Aquí obtienes los datos del ciudadano desde la respuesta HTTP y los preparas para la inserción en la base de datos
        Ciudadano ciudadano = exchange.getIn().getBody(Ciudadano.class);
        // Procesa la respuesta y extrae los datos necesarios del ciudadano

        String sql = "INSERT INTO CIUDADANO (NUMEROIDENTIFICACION, TIPOIDENTIFICACION, NOMBRES, PRIMERAPELLIDO, SEGUNDOAPELLIDO, CUENTACORREO, CELULAR, IDOPERADOR, CORREOASOCIADO, FECHAEXPEDICION) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try{
            jdbcTemplate.update(sql, 
                ciudadano.getIdentificacion(),
                ciudadano.getTipoIdentificacion(),
                ciudadano.getNombres(),
                ciudadano.getPrimerApellido(),
                ciudadano.getSegundoApellido(),
                ciudadano.getCuentaCorreoPersonal(),
                ciudadano.getTelefono(),
                ciudadano.getIdOperador(),
                ciudadano.getCorreoAsociado(),
                ciudadano.getFechaExpedicion()
            );

            exchange.getIn().setBody("Creación del ciudadano "+ciudadano.getIdentificacion()+ " exitosa");

        } catch (DataAccessException e)
        {
            exchange.getIn().setBody("Error en la creación del ciudadano "+ciudadano.getIdentificacion()+ "\n" + e.getMessage());
        }

    }
    
}
