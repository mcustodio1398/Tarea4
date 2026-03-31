package Controlador;

import Modelo.Usuario;
import Modelo.UsuarioDAO;
import Vista.LoginFrame;
import Vista.RegistroFrame;
import Vista.PrincipalFrame;

public class LoginControlador {

    // ─── ATRIBUTOS ────────────────────────────────────────────────
    private LoginFrame vista;           // Vista del login
    private UsuarioDAO dao;             // Acceso a la base de datos

    // Constructor: recibe la vista y configura los eventos
    public LoginControlador(LoginFrame vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();
        inicializarEventos();
    }

    // Asigna las acciones a los botones de la vista
    private void inicializarEventos() {

        // Evento del botón Entrar
        vista.getBtnEntrar().addActionListener(e -> iniciarSesion());

        // Evento del botón Registrarse
        vista.getBtnRegistrarse().addActionListener(e -> abrirRegistro());
    }

    // Valida los campos e intenta iniciar sesión
    private void iniciarSesion() {
        String usuario = vista.getUsuario();
        String contrasena = vista.getContrasena();

        // Verifica que los campos no estén vacíos
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            vista.mostrarError("Debe ingresar su usuario y contraseña. " +
                               "Si no está registrado debe registrarse.");
            return;
        }

        // Intenta hacer login en la base de datos
        Usuario u = dao.login(usuario, contrasena);

        if (u != null) {
            // Login exitoso: cierra el login y abre la pantalla principal
            vista.dispose();
            PrincipalFrame principal = new PrincipalFrame();
            new PrincipalControlador(principal);
            principal.setVisible(true);
        } else {
            // Credenciales incorrectas
            vista.mostrarError("Usuario o contraseña incorrectos.");
            vista.limpiarCampos();
        }
    }

    // Abre la ventana de registro
    private void abrirRegistro() {
        RegistroFrame registro = new RegistroFrame();
        new RegistroControlador(registro, vista);
        registro.setVisible(true);
        vista.setVisible(false); // Oculta el login mientras se registra
    }
}