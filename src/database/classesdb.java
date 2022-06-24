package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.classe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class classesdb extends Save {
	
	public classesdb()
	{
		super();
	}
	
	
	public List<String> getClasses() throws ClassNotFoundException, SQLException
	{
		openConnection();
		ResultSet rs=stm.executeQuery("select * from classe" );
		closeConnection();
	List<String> p = new ArrayList();;
	int i=0;
		while(rs.next())
		{
			p.add(rs.getString("nom"));
			i++;
		}
		
		p.add("primaire");

		p.add("secondaire");
		
		p.add("3eme cycle");
		p.add("toutes");
		return p;
		}

	

public String getMoisIndex(String m) throws ClassNotFoundException
{
	try {
		openConnection();
		ResultSet rs=stm.executeQuery("SELECT id from mois where nom ='"+m+"'");
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


public ObservableList<classe> getAllClasse() 
{
	ObservableList<classe> ca = FXCollections.observableArrayList();
	try {
	
		try {
			openConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stm = con.createStatement();
		ResultSet rs=stm.executeQuery("select inscription, mensualite, limite, nom, mere from classe ");
		closeConnection();
		while(rs.next())
		{
			classe c = new classe();
			c.setInsc(rs.getString(1));
			c.setMens(rs.getString(2));
			c.setLim(rs.getString(3));
	        c.setNom(rs.getString(4));
	        c.setMere(rs.getString(5));
	        ca.add(c);
	        
			
		}
		return ca;
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		closeConnection();
		return ca;
		
	}
	
}


	
}
