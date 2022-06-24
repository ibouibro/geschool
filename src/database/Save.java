package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Save extends DatabaseHelper {

	public Save() {
		super();
		
	}
	
	public void save(String sql,String[] donnees) throws ClassNotFoundException
    {

try {
	openConnection();
	pstm= con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
	for(int i=0;i < donnees.length; i++)
	{

	pstm.setObject(i+1,donnees[i]);
	
	}
	 pstm.executeUpdate();
	 closeConnection();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	closeConnection();
	e.printStackTrace();
}

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
		
			return a;
			
		} catch (SQLException e) {
			closeConnection();
			return "0";
		}
		
	}


	
	
	public void actualiserPartiels(String id,String idmois) throws ClassNotFoundException, SQLException
	{
		ResultSet rs=null;
		{
			int val=0;
			openConnection();
		    rs=stm.executeQuery("select montant from facture where moi = '" + idmois +"' and paiement = 'partiel' and eleve = '"+id+"'" );
		    while(rs.next())
		    {
		    	val+= Integer.parseInt(rs.getString(1));
		    }
		    
		    if(idmois=="10")
		    
		    rs=stm.executeQuery("select inscription  from classe c, eleve e where c.id = e.classe and e.id= '" + id +"'" );
		    else
		    	 rs=stm.executeQuery("select mensualite  from classe c, eleve e where c.id = e.classe and e.id= '" + id +"'" );
		    
		    closeConnection();
		    
		    while(rs.next())
		    {
		    	if(Integer.parseInt(rs.getString(1)) == val)
		    	this.save("update moiseleve set etat = '1' where eleve=? and mois = ?", new String[]{id,idmois});
		    	else 
		    		this.save("update moiseleve set etat = '2' where eleve=? and mois = ?", new String[]{id,idmois});
		   	 	
		    }
		
		}
		
		
		
		
	}
	
	public void updateDB() throws ClassNotFoundException, SQLException
	{
		openConnection();
ResultSet rs= stm.executeQuery("select e.*,c.inscription from eleve e,classe c where e.classe=c.id and e.id not in (select eleve from facture where type='1')");
		
		while(rs.next())
		{
			String paiement="partiel";
			
			if(Integer.parseInt(rs.getString("c.inscription"))==Integer.parseInt(rs.getString("e.montant")))
				paiement = "total";
			
			this.save("insert into facture(montant,eleve,date,type,moi,paiement) values(?,?,?,?,?,?)", new String[] {rs.getString("montant"),rs.getString("id"),rs.getString("dateinscription"),"1","10",paiement});

			if(this.etatExists(rs.getString("id"))==false)  // vérification existence de l'élève
			{
			String[] mois = {"1","2","3","4","5","6","7","10","11","12"};
			for(int i=0;i<mois.length;i++ )
			save("insert into moiseleve(mois,eleve,etat) values(?,?,?)", new String[]{mois[i],rs.getString("id"),"0"} );
			}
		}
	}

	
	public boolean etatExists(String id) throws ClassNotFoundException, SQLException
	{
		openConnection();
		ResultSet rs=stm.executeQuery("select count(*) from moiseleve where eleve ='"+id+"'" );
		closeConnection();
		while(rs.next())
		{
		    if(rs.getInt(1)==10)
		    {
			return true;
		    }
		}
		System.out.println("koooo");
		return false;

	}



	

}
