package uniandes.arti;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
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

        // Inserta el objeto Ciudadano en la base de datos PostgreSQL
        Map<String, Object> params = new HashMap<>();
        params.put("NUMEROIDENTIFICACION", ciudadano.getIdentificacion());
        params.put("TIPOIDENTIFICACION", ciudadano.getTipoIdentificacion());
        params.put("NOMBRES", ciudadano.getNombres());
        params.put("PRIMERAPELLIDO", ciudadano.getPrimerApellido());
        params.put("SEGUNDOAPELLIDO", ciudadano.getSegundoApellido());
        params.put("CUENTACORREO", ciudadano.getCuentaCorreoPersonal());
        params.put("CELULAR", ciudadano.getTelefono());
        params.put("IDOPERADOR", ciudadano.getIdOperador());
        params.put("CORREOASOCIADO", ciudadano.getCorreoAsociado());
        params.put("FECHAEXPEDICION", ciudadano.getFechaExpedicion());

        String sql = "INSERT INTO tu_tabla (NUMEROIDENTIFICACION, TIPOIDENTIFICACION, NOMBRES, PRIMERAPELLIDO, SEGUNDOAPELLIDO, CUENTACORREO, CELULAR, IDOPERADOR, CORREOASOCIADO, FECHAEXPEDICION) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

    }
    
}
