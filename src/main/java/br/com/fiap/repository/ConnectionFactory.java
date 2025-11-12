package br.com.fiap.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private String user = "rm565119";
    private String pwd = "201106";
    private String jdbc = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";

    public ConnectionFactory() {
    }

    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection(jdbc, user, pwd);
        }
        catch(SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return con;
    }
}