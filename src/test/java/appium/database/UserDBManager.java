package appium.database;

import appium.utils.SQLConstants;
import appium.utils.UtilsDatabase;
import java.util.logging.Logger;


public class UserDBManager {
    private final Logger logger = Logger.getLogger(String.valueOf(this.getClass()));

    private UtilsDatabase db;

    public UserDBManager(){
        this.db = UtilsDatabase.getInstance();
    }

    public void updateUser(String username, String id) {
        logger.info("User update in DB...");
        String query = SQLConstants.UPDATE_USER.query.replace("USER", username);
        query = query.replace("ID", id);

        db.executeQuery(query);
        
    }

}
