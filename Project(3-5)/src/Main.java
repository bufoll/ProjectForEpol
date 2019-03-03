import java.sql.*;
import java.util.Calendar;
import com.jcraft.jsch.*;
public class Main {

    //info for SFTP
    private final static String user = "Roma"; // TODO:Your username
    private final static String password = "2013255"; // TODO:Your password
    private final static String host = "127.0.0.1"; // TODO:Your host
    private final static int port = 22; // TODO:Your port

    //info for database
    private final static int port1 = 3306; // TODO: port
    private final static String sql_user = "root"; // TODO: Your userName
    private final static String  sql_password = "2013255"; //  TODO: Your password
    private final static String host1 = "localhost"; // TODO: host name
    private final static String url = "jdbc:mysql://" + host1 + ":" + port1 + "/"  + "?useSSL=false";

    public static void main(String[] args) throws Exception{
        try {

            JSch jsch = new JSch();
            //create session
            Session session = jsch.getSession(user, host, port);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.setPassword(password);
            session.connect();
            System.out.println("1: session connected");

            //open channel
            Channel channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("2: channel connected");

            ChannelSftp channelSftp = (ChannelSftp) channel;
            System.out.println("3: Start");

            channelSftp.put("\\path\\to\\remotedir\\doc.txt", "\\path\\to\\localdir\\CopyDoc.txt");

            //Disconnects channelSftp and session
            channelSftp.disconnect();
            session.disconnect();
        }catch (JSchException e){
            e.printStackTrace();
        }catch (SftpException e){
            e.printStackTrace();
        }

        //DB

       try {
           //Class.forName("com.mysql.cj.jdbc.Driver");
           Database db = new Database();
           Connection conn = db.getConnection(url, sql_user,  sql_password);
           Statement statement = conn.createStatement();

           //Drop Database
           String DB = "DROP DATABASE IF EXISTS DataBaseForProject";
           statement.executeUpdate(DB);

           //create Datebase
           DB = "create database DataBaseForProject";
           statement.execute(DB);
           System.out.println("Create database");

           //use this database
           statement.execute("use DataBaseForProject");

           //Create table
           statement.execute("Create table if not exists DataBaseForProject.FileInfo" +
                   "(TimeCopyFile datetime NOT NULL," +
                   "NameCopyFile varchar(60) NOT NULL)" +
                   "ENGINE = InnoDB;");

           long timeNow = Calendar.getInstance().getTimeInMillis();
           java.sql.Timestamp dateTime = new java.sql.Timestamp(timeNow);

           String insert = "INSERT INTO FileInfo (TimeCopyFile, NameCopyFile)" +
                   "VALUES (?,?)";

           //filling the table
           PreparedStatement preparedStatement = conn.prepareStatement(insert);
           preparedStatement.setTimestamp(1,dateTime);
           preparedStatement.setString(2,"Doc.txt");
           preparedStatement.executeUpdate();

           //display the table
           String query = "Select * From FileInfo";
           ResultSet resultSet = statement.executeQuery(query);
           System.out.println("---Table of contents---");
           while (resultSet.next()) {
               String NameCopyFile;
               String TimeCopyFile;
               NameCopyFile = resultSet.getString("NameCopyFile");
               TimeCopyFile = resultSet.getString("TimeCopyFile");
               InfoFile infoFile = new InfoFile(NameCopyFile, TimeCopyFile);
               System.out.println(infoFile);
           }
           statement.close();
           conn.close();
       }catch (SQLException e){
           e.printStackTrace();
       }
    }
}
