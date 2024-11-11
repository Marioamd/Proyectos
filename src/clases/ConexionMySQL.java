package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/bd_gestion?useSSL=false";
    private static final String USER = "root";
    private static final String PWD = "kira3005/";

    static {
        try {
            Class.forName(CONTROLADOR);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador: " + e.getMessage());
        }
    }

    public Connection Conectar() {
        Connection cnx = null;
        
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        return cnx;
    }

    
}