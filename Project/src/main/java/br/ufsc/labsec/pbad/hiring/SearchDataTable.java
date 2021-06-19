package br.ufsc.labsec.pbad.hiring;

import com.example.Project.UserData;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author sqlitetutorial.net
 */
public class SearchDataTable {

    /**
     * Connect to the test.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = Constantes.caminhoDb;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * select all rows in the warehouses table
     */
    public void selectAll(){
        String sql = "SELECT id, name, validate, serial FROM certificateInfos";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getInt("serial") + "\t" +
                        rs.getInt("validate"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public ArrayList<UserData> getData(String nameS, String inDate, String finalDate){
        String sql = "SELECT id, name, validate, serial "
                + "FROM certificateInfos WHERE name = ? AND validate >= ? AND validate <= ?";
        ArrayList<UserData> userList = new ArrayList<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1, nameS);
            pstmt.setString(2, inDate);
            pstmt.setString(3, finalDate);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set

            while (rs.next()) {
                UserData user = new UserData();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setvalidate(rs.getString("validate"));
                user.setSerial(rs.getInt("serial"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
    public ArrayList<UserData> onlyName(String nameS){
        String sql = "SELECT id, name, validate, serial "
                + "FROM certificateInfos WHERE name = ? ";
        ArrayList<UserData> userList = new ArrayList<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1, nameS);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                UserData user = new UserData();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setvalidate(rs.getString("validate"));
                user.setSerial(rs.getInt("serial"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
}
