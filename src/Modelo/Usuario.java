package Modelo;

public class Usuario {

    //Atributos privados (Encapsulamiento)
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String numeroTelefonico; // Cambiado a String para soportar números largos
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasena;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo (sin id, para cuando se registra)
    public Usuario(String nombre, String apellido, String numeroTelefonico,
                   String correoElectronico, String nombreUsuario, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroTelefonico = numeroTelefonico;
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    // Constructor completo (con id, para cuando se recupera de la BD)
    public Usuario(int idUsuario, String nombre, String apellido, String numeroTelefonico,
                   String correoElectronico, String nombreUsuario, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroTelefonico = numeroTelefonico;
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    // ─── GETTERS Y SETTERS ───
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    // Retorna el número telefónico como String
    public String getNumeroTelefonico() { return numeroTelefonico; }
    // Permite asignar el número telefónico como String
    public void setNumeroTelefonico(String numeroTelefonico) { this.numeroTelefonico = numeroTelefonico; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    // ─── POLIMORFISMO: toString() ─────────────────────────────────
    // Sobrescribe el método de Object para mostrar el usuario como texto
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", usuario='" + nombreUsuario + '\'' +
                '}';
    }
}