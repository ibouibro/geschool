package main;


import java.awt.Menu;

import java.awt.MenuItem;
import java.io.File;

import javax.swing.JButton;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.Factures;
import view.inscription;
import view.parametres;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			/*
			
			String[] commande = {"cmd.exe","/c","Mysql -u root -p"};
			System.out.println("Exécution de la commande:\n");
			 
			ProcessBuilder pb = new ProcessBuilder(commande);
				Process p = pb.start();
			System.out.println("En attente2:\n");
			
			while(p.waitFor()==0)
			{
				
			}
			
			System.out.println("Commande exécuté\n");
			
			*/
			
			
			AnchorPane parent = new AnchorPane();
			VBox box = new VBox();
			MenuBar menuBar = new MenuBar();
			MenuItem  item1 = new MenuItem("nouveau");
			
			
			menuBar.setStyle("-fx-pref-width:2100px;");
			
			
			box.getChildren().add(menuBar);
			box.getChildren().add(bp);
			parent.getChildren().add(box);
			
			bp.setCenter(new inscription("mensuality"));
			
	     	bp.setLeft(initialiseMenu());
			Scene scene = new Scene(parent,1100,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	BorderPane bp = new BorderPane();
	
	
	
	
	
	public  EventHandler clicked(final Button b)
	{
		
		
	
		EventHandler<MouseEvent> x = new EventHandler<MouseEvent>()
		{

			
			public void handle(MouseEvent event) {
				
				// TODO Auto-generated method stub
				if(b.getText().equals("Factures Inscription"))
				{
					
					bp.setCenter(new Factures("inscription"));
					}
				
				if(b.getText().equals("Paramètres de Classes"))
				{
					bp.setCenter(new parametres());
					
				}
				
				if(b.getText().equals("Factures Mensualités"))
				{
					bp.setCenter(new Factures("mensualité"));
				}
				if(b.getText().equals("Nouvelle Inscription"))
				{
					bp.setCenter(new inscription("inscription"));
				}
				if(b.getText().equals("Nouveau Paiement"))
				{
					bp.setCenter(new inscription("mensualité"));
				}
				if(b.getText().equals("Inscription"))
				{
					bp.setCenter(new inscription("ci"));
				}
				
				
			    
			}
	
		};
		return x;
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public  EventHandler buttonEvent(final Button b)
	{
		EventHandler<MouseEvent> x = new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				b.setStyle("-fx-background-color: #419060;-fx-border-color: #	215060;-fx-text-fill:white;-fx-pref-width: 250px;-fx-font-family:arial;-fx-font-size:17px;-fx-pref-height: 40px;");
			}
	
		};
		
	
		
		return x;
	}
	
	public EventHandler<MouseEvent> buttonExited(final Button b)
	{

		return new EventHandler<MouseEvent>()
				{

					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
					b.setStyle("-fx-background-color: #215060;-fx-border-color: #419060;-fx-text-fill:white;-fx-pref-width: 250px;-fx-font-family:arial;-fx-font-size:17px;-fx-pref-height: 40px;");
						
						
					}
			
				};
		

			
				
	
	}
	
	public VBox initialiseMenu()
	{
		
		Button geschool = new Button("GESCHOOL");
		Button b = new Button("Nouvelle Inscription");
		Button m = new Button("MENU");
		m.setStyle("-fx-background-color: #419060;-fx-border-color:#215060;-fx-text-fill:white;-fx-pref-width: 250px;-fx-font-family:arial;-fx-font-size:17px;-fx-pref-height: 40px;");
		Button a = new Button("Factures Inscription");
		Button fm = new Button("Factures Mensualités");
		Button d = new Button("Paramètres de Classes");
		Button pay = new Button("Nouveau Paiement");
		geschool.setStyle("-fx-background-color: #010199;-fx-border-color:#215060;-fx-text-fill:white;-fx-pref-width: 250px;-fx-font-family:arial;-fx-font-size:30px;-fx-pref-height: 60px;");
		
		
		VBox men = new VBox();
		men.setStyle("-fx-pref-width:250px;-fx-background-color:#215060;");
		men.getStylesheets().add("application.css");
      
	    Button[] buttons = {a,fm,b,d,pay};
	    
		makeButtons(buttons);

men.getChildren().add(geschool);
		// men.getChildren().add(m);
		for(int i=0;i<buttons.length;i++)
		{
			men.getChildren().add(buttons[i]);
		}
		
	
		
		return men;
		
		
	}
	
	public void makeButtons(Button[] b)
	{
		for (int i=0; i< b.length; i++)
		{
			b[i].addEventFilter(MouseEvent.MOUSE_CLICKED, clicked(b[i]));
			b[i].addEventFilter(MouseEvent.MOUSE_ENTERED, buttonEvent(b[i]));
			b[i].addEventFilter(MouseEvent.MOUSE_EXITED, buttonExited(b[i]));
			
		}
	}
}
