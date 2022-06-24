package view;

import java.io.File;
import java.sql.SQLException;



import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import database.Helpers;
import database.classesdb;
import database.facturedb;
import entity.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;






public class inscription extends BorderPane {
	
	String typePaiement;
	ComboBox sex = new ComboBox();
	Label m = new Label("Montant");
	Label trouve = new Label();
	public TextField nom = new TextField();
	public TextField id = new TextField();
	public TextField prenom = new TextField();
	public TextField naiss = new TextField();
	public ComboBox classe = new ComboBox();
	public ComboBox genre = new ComboBox();
	public ComboBox paiemen = new ComboBox();
	public TextField montant = new TextField();
	public ComboBox mois = new ComboBox();
	String entete = null;
	String val1;
	String val2;
	String genr = null;
	String paie = null;
	String clas = null;
	String idmois;
	String ideleve = null;
	String le;
	Button deja = new Button("d�j� inscrit ?");
	public Button val = new Button("Valider le paiement");
	ObservableList<Student> data = FXCollections.observableArrayList();
	
	
	GridPane grid = new GridPane();
	public inscription(String tp) 
	{
				typePaiement = tp;
	
				if(tp=="ci")
					idmois="Octobre";
				if(tp=="inscription")
					entete= "NOUVELLE INSCRIPTION";
				if(tp=="mensualit�")
					entete= "NOUVEAU PAIEMENT";
	
		initialise();
		
		setTop(makeTop());
		
		setCenter(makeForm());
	
        
        setBottom(makeBottom());    
}
	
	
	
	public  EventHandler buttonExited(Button b)
	{
		EventHandler<MouseEvent> x = new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				b.setStyle("-fx-background-color: #419060;-fx-pref-width:250;-fx-text-fill:white;");
			}
	
		};
		return x;
	}
	

	Helpers helpers = Helpers.getInstance();
	
	
	public  EventHandler SavePaiement()
	{
		EventHandler<MouseEvent> x = new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event) {
			
				if(typePaiement.equals("inscription"))
				{
					
					
					helpers.EnregistrerInscription( nom.getText(), prenom.getText(), clas, genr, paie, montant, idmois);
					
					}
				
				Platform.runLater(new SFacture());
				
				
			
				
			}
	
		};
		return x;
	}

	
	
	public  EventHandler buttonHandler(final Button b)
	{
		EventHandler<MouseEvent> x = new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				b.setStyle("-fx-background-color: #215060;-fx-pref-width:250;-fx-text-fill:white;");
			}
	
		};
		return x;
	}
	
	
	
	public GridPane makeForm()
	{
	//	GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(20);
		grid.setPadding(new Insets(90, 1500, 60, 200));

		grid.setStyle("-fx-background-color:#FFFEF;-fx-pref-height:100;");
		grid.setPrefHeight(100);

		Text scenetitle = new Text(entete);
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		grid.add(new Label("Classe de l'�l�ve"), 0, 1);
		grid.add(classe, 1, 1);
		
		
		grid.add(m, 0, 6);
		grid.add(montant, 1, 6);
		
montant.setDisable(true);
grid.add(val, 1, 8);
 

val.addEventFilter(MouseEvent.MOUSE_CLICKED, SavePaiement());

 paiemen.getSelectionModel().selectedItemProperty().addListener((option, old, newv) -> 


{
	paie=String.valueOf(newv);
	if(paie=="partiel")
	{
	montant.setDisable(false);
	}
	else
	{
		montant.setDisable(true);

	}
});   

classe.getSelectionModel().selectedItemProperty().addListener((option, old, newv) -> 
{
	
	clas= String.valueOf(newv);
	
	
	
	if(typePaiement == "mensualit�")
	{
		try {
			data = db.getElevesByClass(String.valueOf(newv));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}else
	{
		try {
			montant.setText(db.getRemaining("mensualit�","Octobre",clas,null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
});

id.textProperty().addListener((observable, oldValue, newValue) -> {
	

	if(typePaiement=="mensualit�")
	{
	ideleve = newValue;
	val1 ="";
	for(int i=0;i < data.size() ;i++)
	{
		Student s= data.get(i);
		if(s.getId().equals(newValue))
		{
	val1 = s.getNom() + " " + s.getPrenom();
	prenom.setText(val1);
		try {
			mois.getItems().clear();
			mois.getItems().addAll(db.getRemainingMonths(newValue));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
		
		}
		break;
		}
		
	}
	}
	
	
	
		
});

paiemen.getSelectionModel().selectedItemProperty().addListener((option, old, newv) -> 
{
	paie = String.valueOf(newv);
	
		
});


mois.getSelectionModel().selectedItemProperty().addListener((option, old, newv) -> 
{
	idmois = String.valueOf(newv);
	try {
		System.out.println(db.isNotComplet(id.getText(),idmois,typePaiement));
		if(db.isNotComplet(id.getText(),idmois,typePaiement)==2)
		{
			paiemen.setValue("partiel");
			paie="partiel";
			
		}else
		{
			paiemen.setValue("total");
			paie="total";
		}
		
		montant.setText(db.getRemaining(typePaiement, idmois, clas,id.getText()));
	} catch (Exception e) {
		
	} 
});
		
		if(typePaiement.equals("inscription") || typePaiement.equals("ci"))
			
		{
			
			grid.add(new Label("Pr�nom de l'�l�ve"), 0, 2);
			grid.add(prenom, 1, 2);
			
			grid.add(new Label("Nom de l'�l�ve"), 0, 3);
			grid.add(nom, 1, 3);
		
		
		grid.add(new Label("genre"), 0, 4);
		grid.add(genre, 1, 4);
		
		if(typePaiement.equals("ci"))
		{
			grid.add(new Label("Identifiant de l'�l�ve"), 0, 5);
			grid.add(id, 1, 5);
		}
		else
		{
			grid.add(new Label("Type Paiement"), 0, 5);
			grid.add(paiemen, 1, 5);
		}
		
		
		genre.getSelectionModel().selectedItemProperty().addListener((option, old, newv) -> 
		{
			genr = String.valueOf(newv);
		});
		
		
		}
		else
		{
			grid.add(new Label("Pr�nom puis Nom"), 0, 4);
			grid.add(prenom, 1, 4);
			grid.add(trouve, 2, 4);
			
			grid.add(new Label("Pour le mois de"), 0, 3);
			grid.add(mois, 1, 3);
			
			grid.add(new Label("Identifiant de l'�l�ve"), 0, 2);
			grid.add(id, 1, 2);
			
			grid.add(new Label("Type Paiement"), 0, 5);
			grid.add(paiemen, 1, 5);
			
			
			
		
			prenom.textProperty().addListener((observable, oldValue, newValue) -> {

				
				
				if(prenom.getText()!=null)
				{
				String[] np = String.valueOf(newValue).split(" ");
				
			try {
					id.setText(db.getEleveId(np, clas));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
				
			});   

			 
		}
		

return grid;

	}
	
	database.inscriptiondb db = new database.inscriptiondb();
	
	
	
	public AnchorPane makeBlock(String num, String text, String fill, String back)
	{
		AnchorPane infot = new AnchorPane();
		Label total = new Label(text);
		total.setStyle("-fx-text-fill:"+fill+";-fx-font-weight:40px;-fx-font-size:30px;");
        Label wordt = new Label(num);
        wordt.setStyle("-fx-font-size:15px;-fx-text-fill:"+fill+";");
        infot.setStyle("-fx-pref-width:190px;-fx-pref-height:70px;-fx-text-fill:"+fill+";-fx-border-radius:0px;-fx-background-color:"+back+";-fx-border-color:#215060;");
        total.setPadding(new Insets(12,30,12,12));
        wordt.setPadding(new Insets(25,12,12,65));
        infot.getChildren().add(wordt);
        infot.getChildren().add(total); 
        
        if(text=="Pas en r�gle :")
        {
        	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
        		   @Override 
        		   public void handle(MouseEvent e) { 
        			   
        		  
        		   } 
        		};   
        		//Adding event Filter 
        		infot.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        }
        
		return infot;
	}
	
	
	public GridPane makeBottom()
	{
		GridPane bas = new GridPane();
	       
	        
	     
	          try {
	        	bas.add(makeBlock("Inscriptions partielles :",String.valueOf(db.getPartielPays("inscription")),"white","#215060"), 1, 2);
	 	         
	        	bas.add(makeBlock("total inscrits :",String.valueOf( db.getAllEleve().size()),"#215060","white"), 2, 2);
	    	       
				bas.add(makeBlock("Mensualit�s partielles :",String.valueOf(db.MensualitesPartielles()),"white","#215060"), 3, 2);
				
				bas.add(makeBlock("Pas en r�gle :",String.valueOf(new facturedb().getNotPaid(new SimpleDateFormat("M").format(new Date())).size()),"#215060","white"), 4, 2);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          
	          bas.setStyle("-fx-background-color:#215060;");
	          
	          return bas;
	}
	
	
	
	 public ImageView getImage() {
		 
		    ImageView imageView = new ImageView();
	        File file = new File("src\\public\\cpck.jpg");
	        Image image = new Image(file.toURI().toString());
	        imageView.setImage(image);
	        imageView.setFitHeight(70);
	        imageView.setFitWidth(70);
	        return imageView;
	                             }
	
	
	
	public GridPane makeTop()
	{
		GridPane grid = new GridPane();
		grid.setPrefHeight(90);
	     
	       grid.setStyle("-fx-background-color:#215060;");
	       
	       grid.add(getImage(), 1,1);
	       
	       return grid;
	}
	
	
	

	
	
	
	
	
	public void initialise()
	{
		deja.setStyle("-fx-background-color:#443322;-fx-text-fill:white;");
		val.setStyle("-fx-background-color:#419060;-fx-pref-width:250;-fx-text-fill:white;");
		val.addEventFilter(MouseEvent.MOUSE_ENTERED, buttonHandler(val));
		val.addEventFilter(MouseEvent.MOUSE_EXITED, buttonExited(val));
		paiemen.getItems().addAll("partiel","total");
		nom.setStyle("-fx-pref-width:250;");
		sex.setStyle("-fx-pref-width:250;");
		genre.setStyle("-fx-pref-width:250;");
		genre.getItems().addAll("masculin","f�minin");
		prenom.setStyle("-fx-pref-width:250;");
		naiss.setStyle("-fx-pref-width:250;");
		paiemen.setStyle("-fx-pref-width:250;");
		montant.setStyle("-fx-pref-width:250;");
		classe.setStyle("-fx-pref-width:250;");
		sex.getItems().addAll("masculin","f�minin");
		List<String> p;
		try {
			p = new classesdb().getClasses();
			for(int i=0;i<p.size() - 4 ;i++)
			{
				classe.getItems().add(p.get(i));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	
	
	
public class SFacture implements Runnable

{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(typePaiement.equals("mensualit�"))
		{
			helpers.EnregistrerFactureMensualite(prenom.getText(), paie, montant, idmois, id.getText());		
		
			
		}
		
		nom.setText("");
		prenom.setText("");
		classe.setValue(null);
		genre.getSelectionModel().clearSelection();
		mois.getItems().removeAll();
		paiemen.setValue(null);
		id.setText("");
		montant.setText(null);
		
	}
	
}
		
	


}
