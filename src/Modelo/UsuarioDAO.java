package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase que gestiona todas las operaciones de la BD para Usuario
public class UsuarioDAO {

    // Atributo que almacena la conexión activa a la BD
    private Connection conexion;

    // Constructor: obtiene la única instancia de conexión (Singleton)
    public UsuarioDAO() {
        this.conexion = Conexion.getInstancia();
    }

    // ─── REGISTRAR USUARIO ────────────────────────────────────────
    // Inserta un nuevo usuario en la BD, retorna true si fue exitoso
    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nombre, apellido, numero_telefonico, " +
                     "correo_electronico, nombre_usuario, contrasena) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            // PreparedStatement previene inyección SQL
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getNumeroTelefonico()); // Ahora es String
            ps.setString(4, usuario.getCorreoElectronico());
            ps.setString(5, usuario.getNombreUsuario());
            ps.setString(6, usuario.getContrasena());
            ps.executeUpdate(); // Ejecuta el INSERT
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar: " + e.getMessage());
            return false;
        }
    }

    // ─── LOGIN ────────────────────────────────────────────────────
    // Busca un usuario por nombre y contraseña, retorna el objeto Usuario o null
    public Usuario login(String nombreUsuario, String contrasena) {
        String sql = "SELECT * FROM Usuario WHERE nombre_usuario = ? AND contrasena = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery(); // Ejecuta el SELECT
            if (rs.next()) {
                // Si encontró el usuario, construye y retorna el objeto
                return new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("numero_telefonico"), // Ahora es String
                    rs.getString("correo_electronico"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contrasena")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al hacer login: " + e.getMessage());
        }
        return null; // Retorna null si no encontró coincidencia
    }

    // ─── LISTAR TODOS LOS USUARIOS ────────────────────────────────
    // Retorna una lista con todos los usuarios registrados en la BD
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>(); // Lista vacía para llenar
        String sql = "SELECT * FROM Usuario";
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql); // Ejecuta el SELECT
            while (rs.next()) {
                // Por cada fila, crea un objeto Usuario y lo agrega a la lista
                Usuario u = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("numero_telefonico"), // Ahora es String
                    rs.getString("correo_electronico"),
                    rs.getString("nombre_usuario"),
                    rs.getString("contrasena")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar: " + e.getMessage());
        }
        return lista; // Retorna la lista completa
    }

    // ─── ACTUALIZAR USUARIO ───────────────────────────────────────
    // Modifica los datos de un usuario existente según su id, retorna true si fue exitoso
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre=?, apellido=?, numero_telefonico=?, " +
                     "correo_electronico=?, nombre_usuario=?, contrasena=? " +
                     "WHERE id_usuario=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getNumeroTelefonico()); // Ahora es String
            ps.setString(4, usuario.getCorreoElectronico());
            ps.setString(5, usuario.getNombreUsuario());
            ps.setString(6, usuario.getContrasena());
            ps.setInt(7, usuario.getIdUsuario()); // Identifica qué usuario actualizar
            ps.executeUpdate(); // Ejecuta el UPDATE
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // ─── ELIMINAR USUARIO ─────────────────────────────────────────
    // Elimina un usuario de la BD según su id, retorna true si fue exitoso
    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE id_usuario=?";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idUsuario); // Identifica qué usuario eliminar
            ps.executeUpdate(); // Ejecuta el DELETE
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}