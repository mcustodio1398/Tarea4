package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.InputStream;
import java.util.Properties;

public class Conexion {

    private static Connection instancia = null;

    // Constructor privado: nadie puede hacer "new Conexion()" desde afuera
    private Conexion() {}

    // Método público para obtener la única instancia (Singleton)
    public static Connection getInstancia() {
        try {
            if (instancia == null || instancia.isClosed()) {

                // Leer credenciales desde config.properties. 
                //Esto funciona como un archivo env para proteger las credenciales
                Properties props = new Properties();
                InputStream input = Conexion.class
                        .getClassLoader()
                        .getResourceAsStream("config.properties");

                if (input == null) {
                    System.out.println("❌ No se encontró config.properties");
                    return null;
                }

                props.load(input);

                String url      = props.getProperty("db.url");
                String usuario  = props.getProperty("db.usuario");
                String password = props.getProperty("db.password");

                instancia = DriverManager.getConnection(url, usuario, password);
                System.out.println("✅ Conexión exitosa a la base de datos.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
        }

        return instancia;
    }
}