package main.model;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class conn {

    private static Connection con;

    public static final void open() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_tokoe", "root", "");
            // here sonoo is database name, root is username and password
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static final boolean insert(String checkQuery, String mainQuery) {

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(checkQuery);

            if (!rs.next()) {
                stmt.executeUpdate(mainQuery);
                return true;
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static final ResultSet select(String query) {

        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            stmt = null;
            e.printStackTrace();
        }
        return rs;
    }
    
    public static final void update(String query) {
        
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static final void close() {
        try {
            con.close();  
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
