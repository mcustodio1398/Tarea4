package Controlador;

import Modelo.Usuario;
import Modelo.UsuarioDAO;
import Vista.RegistroFrame;
import Vista.LoginFrame;

public class RegistroControlador {

    // ─── ATRIBUTOS ────────────────────────────────────────────────
    private RegistroFrame vista;        // Vista del registro
    private LoginFrame loginVista;      // Referencia al login para volver
    private UsuarioDAO dao;             // Acceso a la base de datos

    // Constructor: recibe la vista de registro y la del login
    public RegistroControlador(RegistroFrame vista, LoginFrame loginVista) {
        this.vista = vista;
        this.loginVista = loginVista;
        this.dao = new UsuarioDAO();
        inicializarEventos();
    }

    // Asigna las acciones a los botones de la vista
    private void inicializarEventos() {

        // Evento del botón Registrar
        vista.getBtnRegistrar().addActionListener(e -> registrar());

        // Evento del botón Volver
        vista.getBtnVolver().addActionListener(e -> volver());
    }

    // Valida los campos y registra el nuevo usuario
    private void registrar() {
        String nombre        = vista.getNombre();
        String apellido      = vista.getApellido();
        String nombreUsuario = vista.getNombreUsuario();
        String telefono      = vista.getTelefono();
        String correo        = vista.getCorreo();
        String contrasena    = vista.getContrasena();
        String confirmar     = vista.getConfirmarContrasena();

        // Verifica que todos los campos estén llenos
        if (nombre.isEmpty()) {
            vista.mostrarError("El campo Nombre es obligatorio.");
            return;
        }
        if (apellido.isEmpty()) {
            vista.mostrarError("El campo Apellido es obligatorio.");
            return;
        }
        if (nombreUsuario.isEmpty()) {
            vista.mostrarError("El campo Nombre de Usuario es obligatorio.");
            return;
        }
        if (telefono.isEmpty()) {
            vista.mostrarError("El campo Teléfono es obligatorio.");
            return;
        }
        if (correo.isEmpty()) {
            vista.mostrarError("El campo Correo Electrónico es obligatorio.");
            return;
        }
        if (contrasena.isEmpty()) {
            vista.mostrarError("El campo Contraseña es obligatorio.");
            return;
        }
        if (confirmar.isEmpty()) {
            vista.mostrarError("El campo Confirmar Contraseña es obligatorio.");
            return;
        }

        // Verifica que las contraseñas coincidan
        if (!contrasena.equals(confirmar)) {
            vista.mostrarError("Las contraseñas no coinciden.");
            return;
        }

        // Crea el objeto Usuario con teléfono como String y lo registra en la BD
        Usuario u = new Usuario(nombre, apellido, telefono, correo, nombreUsuario, contrasena);
        boolean exito = dao.registrar(u);

        if (exito) {
            vista.mostrarExito("Usuario registrado exitosamente.");
            volver(); // Regresa al login
        } else {
            vista.mostrarError("Error al registrar. El usuario ya puede existir.");
        }
    }

    // Cierra el registro y vuelve al login si existe
    private void volver() {
        vista.dispose();
        if (loginVista != null) {
            loginVista.setVisible(true);
            loginVista.limpiarCampos();
        }
    }
}