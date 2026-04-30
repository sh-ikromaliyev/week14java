package org.example;
import java.sql.*;
import java.util.Scanner;
public class AuthorsBrowser {
    public static void main(String[] args) {
        try (Connection c = DatabaseUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Authors")) {
            while (rs.next())
                System.out.println("ID: "+rs.getInt("AuthorID")+" | "+rs.getString("FirstName")+" "+rs.getString("LastName"));
        } catch (SQLException e) {
            System.out.println("Database error: "+e.getMessage());
        }
        Scanner sc=new Scanner(System.in);
        String p=sc.nextLine();
        String sql="SELECT * FROM Authors WHERE LastName LIKE ?";
        try (Connection c = DatabaseUtil.getConnection();
             PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,p+"%");
            ResultSet rs=ps.executeQuery();
            boolean f=false;
            while(rs.next()){
                f=true;
                System.out.println("ID: "+rs.getInt("AuthorID")+" | "+rs.getString("FirstName")+" "+rs.getString("LastName"));
            }
            if(!f) System.out.println("No results found.");
        } catch(SQLException e){
            System.out.println("Database error: "+e.getMessage());
        }
    }
}
