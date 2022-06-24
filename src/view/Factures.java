package view;


	import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.DatabaseHelper;
import database.facturedb;
import database.inscriptiondb;
import entity.Mensuality;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
	import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
	import javafx.scene.control.ContextMenu;
	import javafx.scene.control.Label;
	import javafx.scene.control.ListView;
	import javafx.scene.control.MenuItem;
	import javafx.scene.control.ScrollBar;
	import javafx.scene.control.ScrollPane;
	import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
	import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
	import javafx.scene.control.cell.PropertyValueFactory;
	import javafx.scene.input.MouseEvent;
	import javafx.scene.layout.AnchorPane;
	import javafx.scene.layout.BorderPane;
	import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

	public class Factures extends BorderPane { 
		
		Label nomvue = new Label("");
		String TypeFacture;
		ComboBox classe;
		Label lclass = new Label("classe");
		TextField chercher;
		Label lchercher = new Label("chercher");
		ListView mensualites;
		GridPane grid = new GridPane();
		int partiel=0;
		int total=0;
		 ObservableList<Mensuality> alldata = FXCollections.observableArrayList();
		 ObservableList<Mensuality> classdata = FXCollections.observableArrayList();
		 public Mensuality one;
		 
		 ObservableList<Mensuality> getCurrentData()
		 {
			 ObservableList<Mensuality> data = FXCollections.observableArrayList();
			 for(Mensuality m : alldata)
			 {
				 if(m.getClasse().equals(clas))
				 {
					 data.add(m);
				 }
			 }
			 if(data.size()>0)
				 return data;
			 return alldata;
		 }
		 
		public void setTableData( ObservableList<Mensuality> data)
		{
			 
		    
		    table.setItems(data);
		    for(Mensuality m : data)
		    {
		    	if(m.getPaiement().equals("partiel"))
		    	{
		    		partiel= partiel + 1;;
		    		
		    	}
		    	else
		    	{
		    		total= total + 1;
		    	}
		    	
		    	
		    }
		}
		
		public Factures(String TypeFacture)
		{
			setStyle("-fx-background-color:white");
			nomvue.setStyle("-fx-font-size:21px;");
		   this.TypeFacture =  TypeFacture;	
	       setTop(makeTop());
	       
	       try {
	    	   students= db.getNotPaid(new SimpleDateFormat("MM").format(new Date()));
				
				alldata = db.getFactures(TypeFacture);
				 classdata=alldata;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	       ScrollPane s = new ScrollPane();
		     s.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		     VBox vb = new VBox();
		     vb.setStyle("-fx-background-color:white");
		     s.setStyle("-fx-background-color:white");
		     nomvue.setText("FACTURE "+ "  " + TypeFacture.toUpperCase());
		     vb.getChildren().add(nomvue);
		     vb.getChildren().add(makeTable(alldata));
		     s.setContent(vb);
		    
	       setCenter(s);
	       setBottom(makeBottom());  
	       
	       grid.setStyle("-fx-background-color:#215060;");
	    
	    
		}
		
		TableView<Mensuality> table = new TableView();
		
		public TableView makeTable(  ObservableList<Mensuality> data)
		{
			 
		   table.setMinHeight(470);
		     TableColumn nom = new TableColumn("nom");
		     nom.setMinWidth(200);
		     nom.setCellValueFactory(
		             new PropertyValueFactory<Student, String>("nom"));
		     
		     TableColumn prenom = new TableColumn("Prénom");
		     prenom.setMinWidth(200);
		     prenom.setCellValueFactory(
		             new PropertyValueFactory<Student, String>("prenom"));


		     TableColumn classe = new TableColumn("classe");
		     classe.setMinWidth(200);
		     classe.setCellValueFactory(
		             new PropertyValueFactory<Student, String>("classe"));


		     TableColumn mois = new TableColumn("Mois");
		     mois.setMinWidth(200);
		     mois.setCellValueFactory(
		             new PropertyValueFactory<Student, String>("mois"));

		     TableColumn col = new TableColumn("collonne");
		     col.setMinWidth(200);
		 
		     table.getColumns().addAll(nom, prenom, classe, col);
		     if(TypeFacture.equals("mensualité"))
		     {
		    	 table.getColumns().add(mois);
		    	 table.getColumns().remove(col);
		     }
				    
		     setTableData(data);
		     
		     ContextMenu cm = new ContextMenu();
		     MenuItem afficher = new MenuItem("Afficher Facture");
		     MenuItem supprimer = new MenuItem("Supprimer");


/* table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
    	selectedMens=newSelection;
    	System.out.println("table selection ok");
    }
});  */


		     
		     cm.getItems().addAll(afficher,supprimer);
		   table.setContextMenu(cm);
		    
				EventHandler<ActionEvent> action = changeTabPlacement();
		     
				afficher.setOnAction(action);
					
					
			     return table;
		}
		
		public void oSetCenter(GridPane gp)
		{
			setCenter(gp);
		}
		
		public Mensuality selectedMens;
		
		facturedb db = new facturedb();
		
		inscriptiondb dbi = new inscriptiondb();

		
		 private EventHandler<ActionEvent> changeTabPlacement() {
		        return new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						
						oSetCenter(new OneFacture(selectedMens));
					}
		        };}
		 
		 
		 
		 public  EventHandler clicked()
			{
				return new EventHandler<MouseEvent>()
				{

					@Override
					public void handle(MouseEvent event) {
						
					
						     nomvue.setText("Pas En Règle");
						    table.setContextMenu(null);
						     table.setItems(students);

						
					}
				
				};
			}
		 
		 ObservableList<Mensuality> students = null;
	
		String month = new SimpleDateFormat("MM").format(new Date());
		
		ObservableList<Mensuality> currentData = FXCollections.observableArrayList();
		
		
		public AnchorPane makeBlock(String num, String text)
		{
			AnchorPane infot = new AnchorPane();
			Label total = new Label(text);
			total.setStyle("-fx-text-fill:blue;-fx-font-weight:40px;-fx-font-size:30px;");
	        Label wordt = new Label(num);
	        wordt.setStyle("-fx-font-size:15px;");
	        infot.setStyle("-fx-pref-width:190px;-fx-pref-height:70px;-fx-background-color:ghostwhite;-fx-border-color:#215060;");
	        total.setPadding(new Insets(12,30,12,12));
	        wordt.setPadding(new Insets(25,12,12,65));
	        infot.getChildren().add(wordt);
	        infot.getChildren().add(total); 
	        
	  /*      String ip = "Inscriptions Partielles";
	        String mp = "Mensualités partielles";
	        String pr = " Pas en Règle";
	        
	        if(num==pr)
	        {
	        	infot.addEventFilter(MouseEvent.MOUSE_PRESSED,
	        			
	        			new EventHandler()
	        			{

							@Override
							public void handle(Event arg0) {
								// TODO Auto-generated method stub
								
								  nomvue.setText("Pas En Règle");
								    table.setContextMenu(null);
								     table.setItems(students);

							}
	        		
	        			}
	        			
	        			);  }
	        
	        if(num==mp)
	        {
	        	infot.addEventFilter(MouseEvent.MOUSE_PRESSED,
	        			
	        			new EventHandler()
	        			{

							@Override
							public void handle(Event arg0) {
								// TODO Auto-generated method stub
								
								  nomvue.setText(mp.toUpperCase());
								    table.setContextMenu(null);
								     table.setItems(students);

							}
	        		
	        			}
	        			
	        			);  } */
	        
			return infot;
		}
		
		
		
		public GridPane makeBottom()
		{
			GridPane bas = new GridPane();
		       
		        
		     
		          try {
		        	bas.add(makeBlock("Inscriptions partielles :",String.valueOf(dbi.getPartielPays("inscription"))), 2, 2);
		 	         
		       
		    	       
					bas.add(makeBlock("Mensualités partielles :",String.valueOf(dbi.MensualitesPartielles())), 3, 2);
					
					bas.add(makeBlock("Pas en règle :",String.valueOf(new facturedb().getNotPaid(new SimpleDateFormat("M").format(new Date())).size())), 4, 2);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		          
		         bas.setStyle("-fx-background-color:#215060;");
		          
		          return bas;
		}
		
		
		
		

		
		
		
		public GridPane makeTop()
		{
			classe = new ComboBox();
			chercher = new TextField("Exemple : Ibrahima NIANG");
			
			
			
			grid.setPadding(new Insets(20, 20, 20, 40));
			    grid.add(lclass,5,2);
			    lclass.setPadding(new Insets(12,12,12,12));
			    grid.add(classe,10 , 2);
			    classe.setPadding(new Insets(0,20,12,12));
			    classe.setPrefWidth(200);
			    classe.setPrefHeight(21);
			    classe.setStyle("-fx-pref-height:21;-fx-font-size:16px;");
			    lclass.setStyle("-fx-text-fill:white;-fx-font-family:arial;-fx-font-size:17px;");
			    try {
					classe.getItems().addAll(db.getClasses());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			    
			    grid.add(lchercher, 20, 2);
			    lchercher.setPadding(new Insets(12,12,12,102));
	            grid.add(chercher, 25, 2);
	            chercher.setPadding(new Insets(12,12,12,12));
	            chercher.setPrefWidth(300);
	            chercher.setPrefHeight(21);
	            lchercher.setStyle("-fx-text-fill:white;-fx-font-family:arial;-fx-font-size:18px;");
	            
	        /*    chercher.textProperty().addListener((option,old,newv) ->
	            		{
	            			ObservableList<Mensuality> data = FXCollections.observableArrayList();
         	             	String value = String.valueOf(newv);
	            	             String[] tab = value.split(" ");
	            	             
	             	            if(tab.length>=2 &&  value!="Exemple : Ibrahima NIANG" )
	             	            {
	             	            	String str2= tab[1].toUpperCase()+tab[0].substring(0, 1).toUpperCase() + tab[0].substring(1).toLowerCase();
	             	            	
	             	            
	             	            	
	             	            		 for(int i=0;i<alldata.size();i++)
	 	             	            	{
	 	             	            		Mensuality m = alldata.get(i);
	 	             	                    String str1 = m.getNom()+m.getPrenom();
	 	             	                  System.out.println("clas : "+m.getClasse());
	 	             	            		if(str1.equals(str2) && m.getClasse().equals(clas))
	 	             	            		{
	 	             	            	
	 	             	            			data.add(m);
	 	             	            			
	 	             	            		}
	 	             	            	}
	             	            		 
	             	            	if(data.size() > 0)
	             	                table.setItems(data);
	             	            	
	             	            	if(data.size()==1)
	             	            		oSetCenter(new OneFacture(data.get(0)));
	             	            }
	             	            
	             	           if(value.length()==0 || data.size()==0)
	             	        	   table.setItems(getCurrentData());
	            	             
	            		});  */
	            
	      /*      classe.getSelectionModel().selectedItemProperty().addListener((option,old,newv) ->
        		{
        			  classdata = FXCollections.observableArrayList();
        			  clas = String.valueOf(newv);
        		
        				 for(int i=0;i < alldata.size();i++)
        			 {
        				 Mensuality m = alldata.get(i);
        				 if(m.getClasse().equals(clas))
        					 classdata.add(m);
        			
        					
        					 
        			 }
        				 
        			 
        				 table.setItems(classdata);
        			
        	
     	            	
     	            	
     	            }
        		);   */
	            
	           
		return grid;
		}
		
		
	
String clas;

}
