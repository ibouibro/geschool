package view;

import java.io.File;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import database.inscriptiondb;
import entity.Mensuality;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class OneFacture extends GridPane {

	Mensuality mens;
	String anneeScolaire = "";
	 public ImageView getImage() {
		 
		    ImageView imageView = new ImageView();
	        File file = new File("src\\public\\cpck.jpg");
	        Image image = new Image(file.toURI().toString());
	        imageView.setImage(image);
	        imageView.setFitHeight(70);
	        imageView.setFitWidth(70);
	        return imageView;
	                             }

	public OneFacture(Mensuality mens) {
		super();
		this.mens = mens;
		
		
		Format form = new SimpleDateFormat("dd/MM/yyyy");
		String[] s = form.format(new Date()).split("/");
		if(Integer.parseInt(s[1])>9)
		{
			anneeScolaire= s[2] +"/"+ String.valueOf(Integer.parseInt(s[2])+1);
		}
		else
		{
			anneeScolaire= String.valueOf(Integer.parseInt(s[2])-1)+"/"+  s[2] ;
		} 
		setAlignment(Pos.CENTER);
		setHgap(20);
		setVgap(90);
		setPadding(new Insets(90, 15000, 60, 1500));

		setStyle("-fx-background-color:#FFFEF;-fx-pref-height:700;");
		setPrefHeight(600);

		Text scenetitle = new Text("FACTURE " + mens.getType().toUpperCase());
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		add(scenetitle, 0, 0, 2, 1);
		add(getLabel(anneeScolaire),3,0);
		
		
		add(new Label("Prénom :   "), 0, 2);
		add(getLabel(mens.getPrenom()), 1, 2);
		
		add(new Label("|   Nom :"), 2, 2);
		add(getLabel(mens.getNom()), 3, 2);
		
		add(new Label("Classe :"), 0, 3);
		add(getLabel(mens.getClasse()), 1, 3);
		
		add(new Label("|   Montant :"), 2, 3);
	    add(getLabel(mens.getMontant()), 3, 3);
		
		add(new Label("Date de paiement :"), 0, 4);
		add(getLabel(mens.getDate()), 1, 4);
		
		add(new Label("|   Mois :"), 2, 4);
		add(getLabel(mens.getMois()), 3, 4);
		
		add(new Label("Reste à payer :"), 0, 5);
		
		try {
				add(getLabel(new inscriptiondb().getRemaining(mens.getType(),mens.getMois(),mens.getClasse(),mens.getId())), 1, 4);
		} catch (Exception e) {
			
		} 
		
		add(new Label("|   Type Paiement :"), 2, 6);
		add(getLabel(mens.getPaiement()), 3, 6);
		

		add(getLabel(" *** CAISSIER : "), 4, 2);
		
		add(getImage(),0,10);
		add(new Label("Lot 8 cité ALIA DIENE, Route de l'aéroport, Ouest Foire, Dakar, Sénégal "),1,5,7,4);
		
	}
	
	public Label getLabel(String s)
	{
		Label l = new Label(s);
		l.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		return l;
	}
	
}
