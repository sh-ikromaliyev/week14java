package org.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.*;

public class AuthorsManager extends Application {
    TableView<Author> table=new TableView<>();
    TextField fn=new TextField();
    TextField ln=new TextField();

    public void start(Stage s){
        TableColumn<Author,Integer> c1=new TableColumn<>("ID");
        c1.setCellValueFactory(new PropertyValueFactory<>("authorID"));
        TableColumn<Author,String> c2=new TableColumn<>("First");
        c2.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Author,String> c3=new TableColumn<>("Last");
        c3.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        table.getColumns().addAll(c1,c2,c3);
        load();

        Button add=new Button("Add");
        Button upd=new Button("Update");
        Button del=new Button("Delete");

        add.setOnAction(e->{
            try(Connection c=DatabaseUtil.getConnection();
                PreparedStatement ps=c.prepareStatement("INSERT INTO Authors (FirstName,LastName) VALUES (?,?)")){
                ps.setString(1,fn.getText());
                ps.setString(2,ln.getText());
                ps.executeUpdate();
                load();
            }catch(Exception ex){}
        });

        upd.setOnAction(e->{
            Author a=table.getSelectionModel().getSelectedItem();
            if(a==null)return;
            try(Connection c=DatabaseUtil.getConnection();
                PreparedStatement ps=c.prepareStatement("UPDATE Authors SET FirstName=?,LastName=? WHERE AuthorID=?")){
                ps.setString(1,fn.getText());
                ps.setString(2,ln.getText());
                ps.setInt(3,a.getAuthorID());
                ps.executeUpdate();
                load();
            }catch(Exception ex){}
        });

        del.setOnAction(e->{
            Author a=table.getSelectionModel().getSelectedItem();
            if(a==null)return;
            try(Connection c=DatabaseUtil.getConnection();
                PreparedStatement ps=c.prepareStatement("DELETE FROM Authors WHERE AuthorID=?")){
                ps.setInt(1,a.getAuthorID());
                ps.executeUpdate();
                load();
            }catch(Exception ex){}
        });

        VBox root=new VBox(10,table,new HBox(5,fn,ln,add,upd,del));
        s.setScene(new Scene(root,550,400));
        s.setTitle("Authors Manager");
        s.show();
    }

    void load(){
        List<Author> list=new ArrayList<>();
        try(Connection c=DatabaseUtil.getConnection();
            Statement st=c.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM Authors")){
            while(rs.next())
                list.add(new Author(rs.getInt("AuthorID"),rs.getString("FirstName"),rs.getString("LastName")));
        }catch(Exception e){}
        table.getItems().setAll(list);
    }

    public static void main(String[] args){launch();}
}
