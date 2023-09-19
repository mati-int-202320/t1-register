package uniandes.arti;

public class Ciudadano {

    private long identificacion;
    private String tipoIdentificacion;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String cuentaCorreoPersonal;
    private long telefono;   
    private long idOperador;     

    public long getIdentificacion() {
        return identificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setIdentificacion(long identificacion) {
        this.identificacion = identificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public void setCuentaCorreoPersonal(String cuentaCorreoPersonal) {
        this.cuentaCorreoPersonal = cuentaCorreoPersonal;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public void setIdOperador(long idOperador) {
        this.idOperador = idOperador;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getCuentaCorreoPersonal() {
        return cuentaCorreoPersonal;
    }

    public long getTelefono() {
        return telefono;
    }

    public long getIdOperador() {
        return idOperador;
    }

    public Ciudadano() {
    }

    public Ciudadano(long id) {
        this.identificacion = id;
    }
   
}