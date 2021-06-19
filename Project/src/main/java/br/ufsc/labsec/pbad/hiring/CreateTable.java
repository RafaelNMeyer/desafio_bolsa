package br.ufsc.labsec.pbad.hiring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void createNewTable() {
        // SQLite connection string
        String url = Constantes.caminhoDb;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS certificateInfos (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	validate TEXT,\n"
                + "	serial integer \n"
                + ");";

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
