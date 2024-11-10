package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQL {
    private static final String CONTROLADOR = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=bd_ventas;user=;password=1234456;loginTimeout=30"; // Cambié 'database' a 'databaseName'
    
    
    static {
        try {
            Class.forName(CONTROLADOR);
        } catch (ClassNotFoundException e) { 
            System.out.println("Error al cargar el controlador: " + e.getMessage());
        }
    }

    public Connection conectar() {
        Connection cnx = null;
        
        try {
            cnx = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        return cnx;
    }

    public Connection Conectar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}