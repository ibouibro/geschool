package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Mensuality;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class facturedb extends classesdb {

	public facturedb()
	{
		super();
	}
	
	public ObservableList<Mensuality> getNotPaid(String currentMonth) throws ClassNotFoundException, SQLException // verify wether the month is totally paid
	{
		ObservableList<Mensuality> students = FXCollections.observableArrayList();
		
		System.out.println(currentMonth);
		
		openConnection();
		ResultSet rs=null;
		
		rs=stm.executeQuery("select  * from  eleve e, moiseleve me, classe c where e.id=me.eleve and c.id=e.classe and me.etat != '1' and mois <  '" + currentMonth +"' group by e.id " );

		closeConnection();
		
		while(rs.next())
		{
	Mensuality student = new Mensuality(String.valueOf(rs.getInt("e.id")), rs.getString("e.nom"), rs.getString("e.prenom"),null,null,null,null,null,rs.getString("c.nom"));
			students.add(student);
		}
		
		return students;
	}

public ObservableList<Mensuality> getFactures(String im) throws ClassNotFoundException
{
	int a ;
	if(im =="mensualité")
	{
		a = 0;
	}
	else
	{
		a = 1;
	}
	
	ObservableList<Mensuality> l = FXCollections.observableArrayList();
	try {
		
		openConnection();
		ResultSet rs=stm.executeQuery("select * from facture f, eleve e, mois m, classe c where f.eleve = e.id and f.moi=m.id and c.id=e.classe and f.type = '"+ a + "'" );
	
		while(rs.next())
		{
			
			Mensuality st = new Mensuality(rs.getString("e.id"),rs.getString("e.nom"),rs.getString("e.prenom"),rs.getString("montant"),rs.getString("date"),rs.getString("m.nom"),rs.getString("paiement"),im,rs.getString("c.nom"));
			l.add(st);
		
		}
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		closeConnection();
		return null;
	}
	return l;
}



}
