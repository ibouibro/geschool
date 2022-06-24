package view;

import entity.Mensuality;
import entity.Student;
import entity.Syracuse_number;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Syracuse extends BorderPane {
	
	TextField chercher;
	Label lchercher = new Label("chercher");
	TableView table;
	GridPane grid = new GridPane();
	

	ObservableList<Syracuse_number> getSyracuseNumbers(int n)
	{
		ObservableList<Syracuse_number> data = FXCollections.observableArrayList();
		return data;
	}
	

	public Syracuse() {
		
		ObservableList<Syracuse_number> data = FXCollections.observableArrayList();
		
		data = getSyracuseNumbers(712);
		
		// TODO Auto-generated constructor stub
		 setTop(makeTop());
		 VBox vb = new VBox();
	     vb.setStyle("-fx-background-color:white");
	     
	     vb.getChildren().add(makeTable(data));
	     setCenter(vb);
	}
	
	
	
	
	public GridPane makeTop()
	{
		grid.add(lchercher, 20, 2);
	    lchercher.setPadding(new Insets(12,12,12,102));
        grid.add(chercher, 25, 2);
        chercher.setPadding(new Insets(12,12,12,12));
        chercher.setPrefWidth(300);
        chercher.setPrefHeight(21);
        lchercher.setStyle("-fx-text-fill:white;-fx-font-family:arial;-fx-font-size:18px;");
        
        return grid;
	}
	
	
	
	public TableView makeTable(  ObservableList<Syracuse_number> data)
	{
		 table = new TableView();
	   table.setMinHeight(470);
	     TableColumn indice = new TableColumn("indice");
	     indice.setMinWidth(200);
	     indice.setCellValueFactory(
	             new PropertyValueFactory<Syracuse_number, String>("indice"));
	     
	     TableColumn number = new TableColumn("number i");
	     number.setMinWidth(200);
	     number.setCellValueFactory(
	             new PropertyValueFactory<Syracuse_number, String>("number"));


	     TableColumn puissance = new TableColumn("puissance of 2");
	     puissance.setMinWidth(200);
	     puissance.setCellValueFactory(
	             new PropertyValueFactory<Syracuse_number, String>("puissance_of_2"));


	     TableColumn sum = new TableColumn("somme des puissance");
	     sum.setMinWidth(200);
	     sum.setCellValueFactory(
	             new PropertyValueFactory<Student, String>("sum_puissance"));

	     
	 
	     table.getColumns().addAll(indice, number, puissance, sum);
	    			    
	     table.setItems(data);


	     
	    
				
		     return table;
	}
	

}
