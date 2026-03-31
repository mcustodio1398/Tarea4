package tarea4;

public class MainTarea4 {
    public static void main(String[] args) {
        // Lanza la ventana de Login al iniciar la aplicación
        java.awt.EventQueue.invokeLater(() -> {
            Vista.LoginFrame login = new Vista.LoginFrame();
            new Controlador.LoginControlador(login);
            login.setVisible(true);
        });
    }
}