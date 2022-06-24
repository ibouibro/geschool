package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Mensuality;
import entity.Student;
import entity.classe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.util.converter.IntegerStringConverter;

public class DatabaseHelper {
	
	public DatabaseHelper() {
		super();
	}
	protected Connection con;
	protected Statement stm;
	private static DatabaseHelper db;
	protected PreparedStatement pstm;
	public static DatabaseHelper getInstance()
	{
		if(db==null)
db=new DatabaseHelper();
return db;
	}
	public void closeConnection()
	{
	if(stm!=null)
	{
		stm=null;
	}

	if(con!=null)
		con=null;
	}

	
	public void openConnection() throws ClassNotFoundException 
	{
		Class.forName("com.mysql.jdbc.Driver");
		
		String url="jdbc:mysql://localhost:3306/geschool";
		try {
			con=DriverManager.getConnection(url,"root","");
			stm=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		
	
	
public ObservableList<Student> notPaidYet(String precedentMonth, String classe) throws ClassNotFoundException, SQLException
{
	openConnection();
	
	ObservableList<Student> students = FXCollections.observableArrayList();
	
	ResultSet rs = stm.executeQuery("select * from eleve e, moiseleve me, classe c where e.id = me.eleve and e.classe=c.id and me.etat != '1' group by e.id ");
	
	closeConnection();
	
	while(rs.next())
	{
		Student student = new Student(rs.getString("e.id"),rs.getString("e.nom"),rs.getString("e.prenom"),null,null,rs.getString("c.nom"));
		students.add(student);
	}
	
	return students;
}







}

