package database;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;



import entity.Mensuality;
import javafx.application.Platform;
import javafx.collections.ObservableSet;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import view.OneFacture;

public  class Helpers {
	
	private static Helpers helpers = null;
	
	private String printerName = null;
	
	public static Helpers getInstance()
	{
		if(helpers == null)
			helpers = new Helpers();
		return helpers;
	}
	

	
	inscriptiondb db = new inscriptiondb();
	
	

	
	
	
	public void EnregistrerFactureMensualite(String prenom,String paie,TextField montant,String idmois,String id)
	{

		try {
		 if(!prenom.equals(""))
		 {
		if(paie.equals("partiel"))
		{
			
			
		db.save("insert into facture(montant,paiement,eleve,type,moi,date) values(?,?,?,?,?,?)",new String[] {montant.getText(),"partiel",id,"0",db.getMoisIndex(idmois), new SimpleDateFormat("dd/MM/yyyy").format(new Date())} );
		Platform.runLater(new ActualisePays(id,db.getMoisIndex(idmois)));
		}
		else
		{
			
			db.save("insert into facture(montant,paiement,eleve,type,moi,date) values(?,?,?,?,?,?)", new String[] {montant.getText(),"total",id,"0",db.getMoisIndex(idmois), new SimpleDateFormat("dd/MM/yyyy").format(new Date())});
			db.save("update moiseleve set etat = '1' where eleve = ? and mois = ? ", new String[] {id,db.getMoisIndex(idmois)}); 
			
			
			
			
		} 
		 Alert a = new Alert(AlertType.INFORMATION);
			a.setTitle("information");
			a.setContentText("l'enregistrement a réussi");
			a.showAndWait(); 
			
		 }
		
			
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void print(String printerName)
	{
		Printer myPrinter = null; 
		ObservableSet<Printer> printers = Printer.getAllPrinters();
		for(Printer printer : printers){
		    if(printer.getName().matches(printerName)){
		        myPrinter = printer;
		    }
		}
		PrinterJob pJ = PrinterJob.createPrinterJob(myPrinter);

		if (pJ != null) {
		    boolean success = pJ.printPage(new OneFacture(new Mensuality()));
		    if (success) {
		        pJ.endJob();
		    }
		}
	}
	
	
	
	public void EnregistrerInscription(String nom, String prenom, String clas, String genr,String paie,TextField montant,String idmois)
	{
		 Date d = new Date();
		 String id="";
			Format form = new SimpleDateFormat("dd/MM/yyyy");
			String s = form.format(d);
	      String classi;
		
			try {
				
	         prenom = prenom.substring(0, 1).toUpperCase() + prenom.substring(1);
	         nom = nom.toUpperCase();
				
				classi = db.getClassIndex(clas);
				
				
				db.save("insert into eleve(nom,prenom,classe,dateinscription,genre,montant) values(?,?,?,?,?,?)",new String[]{nom, prenom, classi,s,genr,montant.getText()});
				
			//	 Alert a = new Alert(AlertType.INFORMATION);
				//	a.setTitle("information");
				//	a.setContentText("l'enregistrement a réussi");
				//	a.showAndWait(); 
				
				db.updateDB();
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			


	}
	
	
	public class ActualisePays implements Runnable

	{
		
		String eleve;
		String mois;
		
		

		public ActualisePays(String eleve, String mois) {
			super();
			this.eleve = eleve;
			this.mois = mois;
		}



		@Override
		public void run() {
			try {
				db.actualiserPartiels(eleve, mois);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}



}
