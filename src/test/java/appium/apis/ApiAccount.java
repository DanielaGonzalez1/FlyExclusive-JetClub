package appium.apis;

import appium.utils.UtilsProperties;
import io.appium.java_client.ios.IOSDriver;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class ApiAccount {

    private static final Logger logger = Logger.getLogger(String.valueOf(ApiAccount.class));
    private Properties props;

    public ApiAccount(){ this.props = UtilsProperties.getRunPropertiesServ();
    }

    public Response login(String username, String password){

        String host = this.props.getProperty("host");
        String path = this.props.getProperty("login");
        String endpoint = host+path;
        logger.info("Service: "+endpoint);

        JSONObject request = new JSONObject();
        request.put("userNameOrEmailAddress", username);
        request.put("password", password);
        request.put("rememberMe", true);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .log()
                .body()
                .body(request.toString())
                .when()
                .post(endpoint);

        logger.info("status code: "+response.statusCode());

        return response;
    }

    public Response logout(){

        String host = this.props.getProperty("host");
        String path = this.props.getProperty("logout");
        String endpoint = host+path;
        logger.info("Service: "+endpoint);

        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get(endpoint);

        logger.info("status code: "+response.statusCode());
        return response;
    }

    public Response selectWinner(String poolId, String enrolledId){

        String host = this.props.getProperty("host");
        String path = this.props.getProperty("select_winner");
        String endpoint = host+path;
        logger.info("Service: "+endpoint);

        JSONObject request = new JSONObject();
        request.put("poolId", poolId);
        request.put("enrolledId", enrolledId);
        request.put("newTeamAbbreviation", "string");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .log()
                .body()
                .body(request.toString())
                .when()
                .put(endpoint);

        logger.info("status code: "+response.statusCode());
        return response;
    }

    public Response pool_admin(){

        String host = this.props.getProperty("host");
        String path = this.props.getProperty("pool_admin");
        String endpoint = host+path;
        logger.info("Service: "+endpoint);

        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete(endpoint);

        logger.info("status code: "+response.statusCode());
        return response;
    }


}
