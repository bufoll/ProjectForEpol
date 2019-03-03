import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.fabric.jdbc.FabricMySQLDriver;

public class Database {

    private Connection connection;
    public Database() throws SQLException{
        DriverManager.registerDriver(new FabricMySQLDriver() );
    }

    public Connection getConnection(String url, String user1, String password1) throws SQLException{
        if(connection != null){
            return connection;

        }
        connection = DriverManager.getConnection(url,user1,password1);
        return connection;
    }
}
