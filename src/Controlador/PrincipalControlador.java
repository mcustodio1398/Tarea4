package Controlador;

import Modelo.Usuario;
import Modelo.UsuarioDAO;
import Vista.PrincipalFrame;
import Vista.LoginFrame;
import java.util.List;

public class PrincipalControlador {

    // ─── ATRIBUTOS ────────────────────────────────────────────────
    private PrincipalFrame vista;       // Vista de la pantalla principal
    private UsuarioDAO dao;             // Acceso a la base de datos

    // Constructor: recibe la vista y configura los eventos
    public PrincipalControlador(PrincipalFrame vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();
        inicializarEventos();
        cargarUsuarios(); // Carga la tabla al abrir la ventana
    }

    // Asigna las acciones a los botones de la vista
    private void inicializarEventos() {

        // Evento del botón Nuevo — abre el registro
        vista.getBtnNuevo().addActionListener(e -> abrirRegistro());

        // Evento del botón Actualizar — actualiza el usuario seleccionado
        vista.getBtnActualizar().addActionListener(e -> actualizarUsuario());

        // Evento del botón Eliminar — elimina el usuario seleccionado
        vista.getBtnEliminar().addActionListener(e -> eliminarUsuario());

        // Evento del botón Cerrar Sesión — vuelve al login
        vista.getBtnCerrarSesion().addActionListener(e -> cerrarSesion());
    }

    // Carga todos los usuarios de la BD en la tabla
    public void cargarUsuarios() {
        javax.swing.table.DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0); // Limpia la tabla antes de cargar

        List<Usuario> lista = dao.listar();
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{
                u.getNombre(),
                u.getApellido(),
                u.getNumeroTelefonico(),
                u.getCorreoElectronico(),
                u.getNombreUsuario()
            });
        }
    }

    // Abre la ventana de registro para agregar un nuevo usuario
    private void abrirRegistro() {
        Vista.RegistroFrame registro = new Vista.RegistroFrame();
        new RegistroControlador(registro, null);
        registro.setVisible(true);
        // Al cerrar el registro recarga la tabla automáticamente
        registro.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                cargarUsuarios();
            }
        });
    }

    // Actualiza los datos del usuario seleccionado en la tabla
    private void actualizarUsuario() {
        int fila = vista.getFilaSeleccionada();

        // Verifica que haya una fila seleccionada
        if (fila == -1) {
            vista.mostrarError("Debe seleccionar un usuario para actualizar.");
            return;
        }

        // Obtiene los datos actuales de la fila seleccionada
        javax.swing.table.DefaultTableModel modelo = vista.getModeloTabla();
        String nombreActual  = modelo.getValueAt(fila, 0).toString();
        String apellidoActual = modelo.getValueAt(fila, 1).toString();
        String telefonoActual = modelo.getValueAt(fila, 2).toString();
        String correoActual  = modelo.getValueAt(fila, 3).toString();
        String usuarioActual = modelo.getValueAt(fila, 4).toString();

        // Solicita los nuevos datos mediante diálogos
        String nuevoNombre = javax.swing.JOptionPane.showInputDialog(vista,
            "Nombre:", nombreActual);
        if (nuevoNombre == null || nuevoNombre.isEmpty()) return;

        String nuevoApellido = javax.swing.JOptionPane.showInputDialog(vista,
            "Apellido:", apellidoActual);
        if (nuevoApellido == null || nuevoApellido.isEmpty()) return;

        String nuevoTelefono = javax.swing.JOptionPane.showInputDialog(vista,
            "Teléfono:", telefonoActual);
        if (nuevoTelefono == null || nuevoTelefono.isEmpty()) return;

        String nuevoCorreo = javax.swing.JOptionPane.showInputDialog(vista,
            "Correo Electrónico:", correoActual);
        if (nuevoCorreo == null || nuevoCorreo.isEmpty()) return;

        String nuevaContrasena = javax.swing.JOptionPane.showInputDialog(vista,
            "Nueva Contraseña (dejar vacío si no cambia):", "");
        if (nuevaContrasena == null) return;

        // Busca el usuario en la BD por nombre de usuario
        List<Usuario> lista = dao.listar();
        Usuario usuarioAActualizar = null;
        for (Usuario u : lista) {
            if (u.getNombreUsuario().equals(usuarioActual)) {
                usuarioAActualizar = u;
                break;
            }
        }

        if (usuarioAActualizar == null) {
            vista.mostrarError("No se encontró el usuario.");
            return;
        }

       // Actualiza los datos del objeto
        usuarioAActualizar.setNombre(nuevoNombre);
        usuarioAActualizar.setApellido(nuevoApellido);
        usuarioAActualizar.setNumeroTelefonico(nuevoTelefono); 
        usuarioAActualizar.setCorreoElectronico(nuevoCorreo);
        if (!nuevaContrasena.isEmpty()) {
            usuarioAActualizar.setContrasena(nuevaContrasena);
        }
        usuarioAActualizar.setCorreoElectronico(nuevoCorreo);
        if (!nuevaContrasena.isEmpty()) {
            usuarioAActualizar.setContrasena(nuevaContrasena);
        }

        // Guarda los cambios en la BD
        boolean exito = dao.actualizar(usuarioAActualizar);
        if (exito) {
            vista.mostrarExito("Usuario actualizado correctamente.");
            cargarUsuarios(); // Recarga la tabla
        } else {
            vista.mostrarError("Error al actualizar el usuario.");
        }
    }

    // Elimina el usuario seleccionado en la tabla
    private void eliminarUsuario() {
        int fila = vista.getFilaSeleccionada();

        // Verifica que haya una fila seleccionada
        if (fila == -1) {
            vista.mostrarError("Debe seleccionar un usuario para eliminar.");
            return;
        }

        // Confirma la eliminación
        boolean confirmar = vista.confirmar("¿Está seguro de eliminar este usuario?");
        if (!confirmar) return;

        // Obtiene el nombre de usuario de la fila seleccionada
        javax.swing.table.DefaultTableModel modelo = vista.getModeloTabla();
        String usuarioActual = modelo.getValueAt(fila, 4).toString();

        // Busca el id del usuario en la BD y lo elimina
        List<Usuario> lista = dao.listar();
        for (Usuario u : lista) {
            if (u.getNombreUsuario().equals(usuarioActual)) {
                boolean exito = dao.eliminar(u.getIdUsuario());
                if (exito) {
                    vista.mostrarExito("Usuario eliminado correctamente.");
                    cargarUsuarios(); // Recarga la tabla
                } else {
                    vista.mostrarError("Error al eliminar el usuario.");
                }
                return;
            }
        }
    }

    // Cierra la sesión y vuelve al login
    private void cerrarSesion() {
        vista.dispose();
        LoginFrame login = new LoginFrame();
        new LoginControlador(login);
        login.setVisible(true);
    }
}