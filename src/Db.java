/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import Model.Espionaje;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author byde
 */
public class Db {
    private Connection conn = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
    Db()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/ogame?user=root&password=root");
            statement = conn.createStatement();
                            // Result set get the result of the SQL query
            
                            //writeResultSet(resultSet);
                            //writeResultSet(resultSet);

                            // PreparedStatements can use variables and are more efficient			
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean setEspionaje(String planeta, double metal, double cristal, double duty, int origen)
    {
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO espionajes values (null, ?, ?, ?, ? , ?, NOW())");
            preparedStatement.setString(1, planeta);
            preparedStatement.setDouble(2, metal);
            preparedStatement.setDouble(3, cristal);
            preparedStatement.setDouble(4, duty);
            preparedStatement.setInt(5, origen);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {

		}
	}
    
    public List<Espionaje> getEspionaje()
    {
        List<Espionaje> es = new ArrayList<Espionaje>();
        try {
            resultSet = statement.executeQuery("SELECT idespionaje, planeta, metal, cristal, deuterio FROM espionajes ORDER BY cristal DESC");
            while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
                        es.add(new Espionaje(resultSet.getString("planeta"),resultSet.getDouble("metal"), resultSet.getDouble("cristal"), resultSet.getDouble("deuterio"), resultSet.getInt("idespionaje")));
		}
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return es;
    }
    
    public void deleteEspionaje(int id)
    {
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM espionajes WHERE idespionaje = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
