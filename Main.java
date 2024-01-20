package dbproje;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) throws SQLException { //Main metodu çalıştırılır.
		String user = "postgres";
        String pass = "123";
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PetSet", user,pass);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	 projectGUI gui = new projectGUI(conn);
                gui.display(); 
            }
        }); 
    }
}