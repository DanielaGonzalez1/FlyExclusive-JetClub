package appium.utils;

import org.apache.commons.collections.map.MultiValueMap;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Logger;
public class UtilsDatabase {
    private static UtilsDatabase _instance = null;
    private final Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    private Connection connection = null;
    String MSG_CONNECTION = "postmaster is accepting TCP/IP connections";
    int totalTries = 2;
    int currentTries = 0;

    UtilsDatabase() {}

    public static UtilsDatabase getInstance() {
        if ( _instance == null) _instance = new UtilsDatabase();
        return _instance;
    }

    private void connect() {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            logger.info("Connected to appium.database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Execute the query received and retrieves the auto generated key
     * @param sql query that emulates the CRUD
     * @return auto generated key of the executed query
     */
    public void executeQuery(String sql) {
        logger.info("dbManager started.");
        connect();

        try {
            logger.info("Query -> "+sql);
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            connection.commit();
            ps.close();
            logger.info("Method: executeQueryPreparedStatement was a success.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("dbManager finished.");
        closeConnection();
        
    }

    public String retrieveFirstData(String sql) {
        return readSpecificColumnQuery(sql);
    }

    public String retrieveSpecificData(String sql, String columnLabel) {
        return readQuery(sql, columnLabel);
    }

    public MultiValueMap retrieveAllData(String sql) {
        return readQuery(sql);
    }

    private String readSpecificColumnQuery(String sql) {
        Statement statement;
        String dataRetrieved = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery( sql );

            while ( results.next() ) {
                dataRetrieved = results.getString(1);
            }

            results.close();
            statement.close();

            logger.info("Method: readSpecificColumnQuery was a success. Data retrieved: " + dataRetrieved);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataRetrieved;
    }

    private String readQuery(String sql, String data) {
        Statement statement;
        String dataRetrieved = null;
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery( sql );

            while ( results.next() ) {
                dataRetrieved = results.getString(data);
            }

            results.close();
            statement.close();

            logger.info("Method: readQuery was a success. Data retrieved: " + dataRetrieved);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataRetrieved;
    }

    private MultiValueMap readQuery(String sql) {
        Statement statement;
        //List<HashMap<String, String>> data = new ArrayList<>();
        MultiValueMap data = new MultiValueMap();
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery( sql );
            HashMap<String, String> value = new HashMap<>();
            while ( results.next() ) {
                ResultSetMetaData metaData = results.getMetaData();
                int columnQty = metaData.getColumnCount();
                for( int i = 1; i <= columnQty; i++ ) {
                    data.put( metaData.getColumnName(i), results.getString(i) );
                    //value.put( metaData.getColumnName(i), results.getString(i) );
                    //data.add( value );
                }
            }

            results.close();
            statement.close();

            logger.info("Method: readQuery was a success. Data quantity retrieved: " + data.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void closeConnection() {
        try {
            connection.close();
            logger.info("Method: closeConnection was a success.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws InterruptedException {
        try {
            String url = UtilsProperties.getRunPropertiesDB().getProperty("dbUrl");
            String user = UtilsProperties.getRunPropertiesDB().getProperty("dbUser");
            String password = UtilsProperties.getRunPropertiesDB().getProperty("dbPassword");

            //connection = DriverManager.getConnection(url, user, password);
            connection = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            // TODO if this keeps on failing, maybe I will need to start kube process again
            // Es necesario, ya que el proceso que se levanta con KUBE tarda un poco en aprobar los protocolos TCP/IP
            if (e.getMessage().contains(MSG_CONNECTION) || currentTries < totalTries)
                currentTries++;
            //getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        currentTries = 0;
        return connection;
    }

}
