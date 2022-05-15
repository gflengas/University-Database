import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;


public class DbApp {
	
	Connection conn;

	public DbApp() {
		conn = null;
	}
	
	
	public void dbConnect (String ip, int port, String database, String username, String password) {
		try {
			
	     	Class.forName("org.postgresql.Driver");
	     	conn = DriverManager.getConnection("jdbc:postgresql://"+ip+":"+port+"/"+database,username,password);
	    	System.out.println("Connection Established!");
	   		conn.setAutoCommit(false);
	   		}catch(Exception e) {
	   			e.printStackTrace();
			}
		}
	
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void abort() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void waitForEnter() {
		Scanner scn = new Scanner(System.in);
		System.out.println("Press Enter ...");
		scn.nextLine();
	}
	
	public void findGrades(){

		Scanner scn = new Scanner(System.in);
		String am;
		System.out.println("please insert the AM of the student:");
		am=scn.nextLine();
		
		//Create query
		try{
		Statement state = conn.createStatement();
		String query=("SELECT \"Course\".course_code,course_title,units, final_grade, exam_grade, lab_grade FROM \"Register\" inner join \"Student\" on \"Student\".amka=\"Register\".amka inner join \"Course\" on  \"Course\".course_code=\"Register\".course_code  where \"Register\".register_status='pass' and \"Student\".am='"+am+"'");
		//send query to db
		ResultSet results=state.executeQuery(query);
		
		System.out.println("Grades for Student: "+am);
		 while (results.next()){
							 
				 //Retrieve by column name
		         String cu = results.getString(1);
		         String t=results.getString(2);
		         int un=results.getInt(3);
		         double finGrade = results.getDouble(4);
		         double exGrade = results.getDouble(5);
				 double labGrade =results.getDouble(6);
				 
		         
				  //Display values
		         System.out.print("Course Code: " + cu+" ");
		         System.out.print("Course title: " + t);
		         System.out.print("Units: " + un);
		         System.out.print(", Final Grade: " + finGrade);
		         System.out.print(", Exam Grade: " + exGrade);
		         System.out.println(", Lab Grade: " + labGrade); 
				 
				 
				 
			 } 
		    } catch(SQLException e){
				e.printStackTrace();
			}
	
	}//End of findGrades()
	
	public void updateGrades(){
		
		Scanner scn = new Scanner(System.in);
		Scanner scn1 = new Scanner(System.in);
		Scanner scn2 = new Scanner(System.in);
		Scanner scn3 = new Scanner(System.in);
		String course,am;
		double exGrade, labGrade;
		
		System.out.println("Please insert am of the student:");
		am=scn.nextLine();
		System.out.println("please insert the course code:");
		course=scn1.nextLine();
		System.out.println("please insert the new exam grade: ");
		exGrade=scn2.nextDouble();
		System.out.println("please insert the new lab grade: ");
		labGrade=scn3.nextDouble();
		 
	     String update = ("update \"Register\" set exam_grade="+exGrade+",lab_grade="+labGrade+" where course_code='"+course+"' and serial_number="+"(select max(serial_number) from \"Register\" inner join \"Student\" on \"Register\".amka=\"Student\".amka where \"Student\".am="+"'"+am+"'"+" and course_code='"+course+"')"+"and amka=(select amka from \"Student\" s where s.am='"+am+"')");
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate(update);   
            
            System.out.println("Update done - You must commit your transaction !!! ");
            } catch (SQLException e) {
            System.out.println(e.getMessage());
								 }
        
		} //End of updateGrades
	
	
	public void generateBackup() {
		
		 Collection<String> result = new ArrayList<String>();	
		 ResultSet rs = null;
		 try {
			DatabaseMetaData dbm = conn.getMetaData();
			String types[] = { "TABLE" };
			rs = dbm.getTables(null, null, "", types);
			while (rs.next())
		     {
		       String str = rs.getString("TABLE_NAME");
		       result.add(str);
		     }
			System.out.println(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {	
		int choice,choice2;
		 
		Scanner input = new Scanner(System.in);
		Scanner input1 = new Scanner(System.in);
		

		DbApp db = new DbApp();
		db.dbConnect("localhost",5432,"Project2018", "postgres", "1951996");
		
		do {
		db.waitForEnter();
		System.out.println("Base Menu");
		System.out.println("1 - Start transaction/Commit transaction");
		System.out.println("2 - Specific Student's Grades");
		System.out.println("3 - Change Grades");
		System.out.println("4 - Generate Backup database");		
		System.out.println("0 - Exit");
		System.out.println("Your choice:");
		choice = input.nextInt();
		
		
		switch(choice){
		case 1:
			System.out.println("Commit - 1 | rollback - 2 ");
			choice2 = input1.nextInt();
			switch(choice2){
			case 1:				
				db.commit();			
			break;
			case 2:
				db.abort();			
			break;	
			}
			
		break;
		case 2:	
		db.findGrades();
		break;
		case 3:
		db.updateGrades();
		break;
		case 4:
		db.generateBackup();
		break;
		
		} //End of cases		
	} while (choice!=0);
	}
}
