package utils;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class AppUtils {
    public static Driver getDriver(){
        String url = "neo4j://localhost:7687";
        String username = "neo4j";
        String password ="12345678";
        return GraphDatabase.driver(url, AuthTokens.basic(username, password));
    }
}
