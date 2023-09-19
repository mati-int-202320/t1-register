package uniandes.arti;
import org.springframework.stereotype.Component;
@Component
public class CuentaBean {

    public Ciudadano response(Ciudadano user) {
        user.setName(user.getName().toUpperCase());
        return user;
    }
    
   public String hello(String name) {
           return "Hello "+name;
} }