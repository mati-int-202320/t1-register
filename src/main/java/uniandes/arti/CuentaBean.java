package uniandes.arti;
import org.springframework.stereotype.Component;
@Component
public class CuentaBean {

    public Ciudadano response(Ciudadano ciudadano) {
        ciudadano.setNombres(ciudadano.getNombres().toUpperCase());
        return ciudadano;
    }
    
   public String hello(String name) {
           return "Hello "+name;
} }