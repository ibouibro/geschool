package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Mensuality;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.IntegerStringConverter;

public class inscriptiondb extends classesdb {

	public inscriptiondb()
	{
		super();
	}
	
	
	public int getEleveCount() throws ClassNotFoundException
	{
		
		try {
			openConnection();
			ResultSet rs=stm.executeQuery("select count(*) from eleve ");
			closeConnection();
			while(rs.next())
			{
				
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConnection();
			return 0;
		}
		closeConnection();
		return 0;
	}
	
	
	
	public int getPartielPays(String indication) throws ClassNotFoundException
	{
		
		
		if(indication.equals("inscription"))
			indication= "Octobre";
		try {
			openConnection();
			ResultSet rs=stm.executeQuery("select count(distinct(eleve)) from moiseleve  where (etat= '2' or etat = '0') and mois = (select id from mois where nom = '"+indication+"')");
			closeConnection();
			while(rs.next())
			{
				
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConnection();
			return 0;
		}
		return 0;
	}
	
	public ObservableList<Student> getEleves(String s) throws ClassNotFoundException
	{
		ObservableList<Student> l = FXCollections.observableArrayList();
		try {
			
			openConnection();
			ResultSet rs=stm.executeQuery("select * from eleve e, classe c where e.classe = c.id and ( matricule = " + s + " or concat(prenom + \" \" + nom) = " + s +")");
			closeConnection();
			while(rs.next())
			{
				Student st = new Student(String.valueOf(rs.getInt(1)),rs.getString("nom"),rs.getString("prenom"),"",rs.getString("dateinscription"),rs.getString(9));
				l.add(st);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConnection();
			return l;
		}
		return l;
	}

	public ObservableList<Student> getElevesByClass(String classe) throws ClassNotFoundException
	{
		ObservableList<Student> l = FXCollections.observableArrayList();
		try {
			
			openConnection();
			ResultSet rs=stm.executeQuery("select * from eleve e, classe c where e.classe = c.id and c.nom = '" + classe + "'");
			closeConnection();
			while(rs.next())
			{
				Student st = new Student(String.valueOf(rs.getInt("id")),rs.getString("nom"),rs.getString("prenom"),"",rs.getString("dateinscription"),rs.getString(9));
				l.add(st);
				System.out.println("1");
			}
			
			return l;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConnection();
			return l;
		}
		
	}


	public ObservableList<Student> getAllEleve() throws ClassNotFoundException
	{
		ObservableList<Student> l = FXCollections.observableArrayList();
		try {
			
			openConnection();
			ResultSet rs=stm.executeQuery("select * from eleve e, classe c  where e.classe = c.id ");
			closeConnection();
			while(rs.next())
			{
				Student st = new Student(String.valueOf(rs.getInt("e.id")),rs.getString("e.nom"),rs.getString("prenom"),"",rs.getString("dateinscription"),rs.getString("c.nom"));
				l.add(st);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConnection();
			return  l;
		}
		return l;
	}


	public ObservableList<Student> getAllElevesByClass(String classe) throws ClassNotFoundException
	{
		ObservableList<Student> l = FXCollections.observableArrayList();
		try {
			
			openConnection();
			ResultSet rs=stm.executeQuery("select * from eleve e, classe c where c.id = e.classe  and  c.nom = " + classe );
			closeConnection();
			while(rs.next())
			{
				Student st = new Student(String.valueOf(rs.getInt(1)),rs.getString("1"),rs.getString("prenom"),"",rs.getString("dateinscription"),rs.getString(9));
				l.add(st);
			}
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConnection();
			return l;
		}
		return l;
	}


	
	public int MensualitesPartielles() throws ClassNotFoundException
	{
		ObservableList<Student> l = FXCollections.observableArrayList();
		try {
			String etat = "2";
			openConnection();
			
	   ResultSet rs=stm.executeQuery("select count(*) from moiseleve where mois!= '10' and etat= '"+etat+"'");

			while(rs.next())
			{
				return rs.getInt(1);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeConnection();
			return 0;
		}
		return 0;
	}













public String getClassIndex(String nom) throws ClassNotFoundException
{
	try {
		openConnection();
		ResultSet rs=stm.executeQuery("select id from classe where nom = '" + nom +"'" );
	
		closeConnection();
		while(rs.next())
		{
			System.out.println(rs.getInt(1));
			
			return String.valueOf(rs.getInt(1));
		}
		
	} catch (SQLException e) {
		closeConnection();
		return "0";
	}
	return "0";
}


public String selectLastEleve() throws ClassNotFoundException
{
	try {
		openConnection();
		ResultSet rs=stm.executeQuery("SELECT * FROM eleve ORDER BY ID DESC LIMIT 1");
		closeConnection();
		String a = "0";
		while(rs.next())
		{
			a=String.valueOf(rs.getInt(1));
					
		}
		System.out.println(a);
		return a;
		
	} catch (SQLException e) {
		closeConnection();
		return "0";
	}
	
}









public String getEleveId(String[] np, String classe) throws ClassNotFoundException, SQLException
{
	
	String nom =  np[0].toUpperCase();
	String prenom = null;
	if(np.length > 1)
    prenom = np[1].substring(0, 1).toUpperCase() + np[1].substring(1).toLowerCase();
		openConnection();
		ResultSet rs=stm.executeQuery("SELECT id from eleve where nom in ('" + nom + "', '" + prenom + "') and prenom in  ('" + nom + "', '" + prenom + "') ");
		closeConnection();
		int a=0;
		String re = "";
		while(rs.next())
		{
		
			a++;
			re = String.valueOf(rs.getInt("id"));
					
		}
		
		if(a > 1)
			return "";
		
		return re;
	
	
	
}






public String getRemaining(String type, String month, String classe,String StudentId) throws ClassNotFoundException, SQLException
{
	openConnection();
	
ResultSet	rs= stm.executeQuery("SELECT count(*) from facture");
	
if(rs==null)
		
	{
	
			return "0";
		
	}
	Integer val=0;
	IntegerStringConverter isc = new IntegerStringConverter();
	int factureType = 0;
	if(month.equals("Octobre"))
	{
		factureType=1;
	}
		
	try {
		openConnection();
		
		if(StudentId==null)
		{
			 rs=stm.executeQuery("select inscription from classe where nom ='"+classe+"'");
			while(rs.next())
			{
				
				return rs.getString(1);
						
			}
		}
		
		
		
		rs=stm.executeQuery("SELECT montant from  facture  where type = '"+ factureType +"' and moi = (select id from mois where nom = '"+month+"') and eleve='"+ StudentId +"'");
		
		
		while(rs.next())
		{
			val+= isc.fromString(rs.getString(1));
					
		}

		
		String topValue="0";
		
		if(factureType==1)
		{
			rs=stm.executeQuery("select inscription from classe where nom ='"+classe+"'");
			while(rs.next())
			{
				
				topValue= rs.getString(1);
						
			}
			
		}
		
		if( factureType==0)
		{
			rs=stm.executeQuery("select mensualite from classe where nom ='"+classe+"'");
			while(rs.next())
			{
				topValue= rs.getString(1);
						
			}
			
		}
		closeConnection();
		
	
	Integer intTopValue = isc.fromString(topValue);
		
	return isc.toString( intTopValue - val);
		
		
		
	} catch (SQLException e) {
		closeConnection();
		return "0";
	}
	
	

}




public List<String> getRemainingMonths(String id) throws ClassNotFoundException, SQLException
{

	openConnection();
	ResultSet rs=stm.executeQuery("select  distinct(m.nom) from moiseleve me, mois m where (me.etat='0' or me.etat = '2') and me.eleve = '"+id+"' and me.mois = m.id " );
	
List<String> p = new ArrayList();


	while(rs.next())
	{
		p.add(rs.getString(1));
	
	}
	
	closeConnection();
	
	return p;
	
	}





public int isNotComplet(String eleve,String mois, String type) throws ClassNotFoundException, SQLException // verify if the month is totally paid
{
	
	openConnection();
	ResultSet rs=null;
	
	rs=stm.executeQuery("select * from moiseleve where eleve = '"+ eleve +"' and mois =(select id from mois where nom = '" + mois +"' )" );

	closeConnection();
	
	while(rs.next())
	{

		return rs.getInt("etat");
	}
	
	return 0;
}








	
}
