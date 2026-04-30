package org.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.*;

public class TitlesBrowser extends Application {
    TableView<AuthorBook> table=new TableView<>();
    TextField tf=new TextField();

    public void start(Stage s){
        TableColumn<AuthorBook,String> c1=new TableColumn<>("First");
        c1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<AuthorBook,String> c2=new TableColumn<>("Last");
        c2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<AuthorBook,String> c3=new TableColumn<>("ISBN");
        c3.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        TableColumn<AuthorBook,String> c4=new TableColumn<>("Title");
        c4.setCellValueFactory(new PropertyValueFactory<>("title"));
        c4.setPrefWidth(230);
        table.getColumns().addAll(c1,c2,c3,c4);

        Button b=new Button("Search");
        b.setOnAction(e->load(tf.getText().isEmpty()?"%":tf.getText()+"%"));

        VBox root=new VBox(10,new HBox(5,tf,b),table);
        load("%");
        s.setScene(new Scene(root,640,400));
        s.setTitle("Titles Browser");
        s.show();
    }

    void load(String p){
        List<AuthorBook> list=new ArrayList<>();
        String sql="SELECT a.FirstName,a.LastName,t.ISBN,t.Title FROM Authors a INNER JOIN AuthorISBN ai ON a.AuthorID=ai.AuthorID INNER JOIN Titles t ON ai.ISBN=t.ISBN WHERE a.LastName LIKE ? ORDER BY a.LastName,a.FirstName";
        try(Connection c=DatabaseUtil.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,p);
            ResultSet rs=ps.executeQuery();
            while(rs.next())
                list.add(new AuthorBook(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
        }catch(Exception e){}
        table.getItems().setAll(list);
    }

    public static void main(String[] args){launch();}
}
