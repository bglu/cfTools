package de.bglu.cftools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Parser for the Cloud Foundry Database Url.
 *
 * In Cloud Foundry, there is a environment variable which specifies the database url
 * for the bound database service.
 *
 * This variable will look like this:
 *  mysql://<username>:<password>@<host>:<port>/<dbname>
 *
 * Only supports mysql at the moment.
 */
public class CFDbUrlParser {

    /**
     * Map which holds the environment variables, where the database url is stored in.
     * May be the system environment variables (System.getEnv()) or a mocked map for unit testing.
     */
    private final Map<String, String> environmentVariables;
    /**
     * The logger.
     */
    private static final Logger _log = LoggerFactory.getLogger(CFDbUrlParser.class);

    /**
     * Stores the parsed jdbc driver.
     */
    private String driver;

    /**
     * Stores the parsed user of the jdbc connection.
     */
    private String user;

    /**
     * Stores the parsed password of the jdbc connection.
     */
    private String password;

    /**
     * Stores the parsed jdbc url to connect to.
     */
    private String url;


    /*
    CONSTRUCTORS
     */

    /**
     * Constructs a parser
     * @param environmentVariables to privode mocked environment variables for unit testing
     */
    public CFDbUrlParser(final Map<String, String> environmentVariables){
        this.environmentVariables = environmentVariables;

        final String dbUrl = getCFDatabseUrl();

        //only parse that url if it was set
        if (! "".equals(dbUrl)){
            parseDatabaseUrl(dbUrl);
        }
    }

    /**
     * Constructs a parser.
     * Uses the system environment variables to parse the database url
     */
    public CFDbUrlParser(){
        this(System.getenv());
    }


    /**
     * parses the given database url.
     * @param dbUrl to parse
     */
    public void parseDatabaseUrl(final String dbUrl)  {
        try {

            URI dbUri = new URI(dbUrl);

            driver = inferDriverClass(dbUrl);
            user = dbUri.getUserInfo().split(":")[0];
            password = dbUri.getUserInfo().split(":")[1];

            String authPart = user + ":" + password + "@";
            url = "jdbc:"+dbUrl.replace(authPart, "");

        } catch (URISyntaxException e) {
            _log.error("Failed to parse Cloud Foundry DB Url", e);

            driver = url = user = password = null;
        }
    }

    private String inferDriverClass(String dbUrl) {
        String driver;

        if(dbUrl.contains("mysql")) {
            driver = "com.mysql.jdbc.Driver";
        }
        else {
            throw new NotImplementedException();
        }
        return driver;
    }


    /*
    Helpers
     */

    public String getCFDatabseUrl() {
        String cfDatabseUrl = environmentVariables.get(CFConstants.DATABASE_URL);

        if (cfDatabseUrl != null) {
            _log.debug("Database url: {}", cfDatabseUrl);
        } else {
            _log.error("Database url not found!");
            cfDatabseUrl = "";
        }

        return cfDatabseUrl;
    }

    /*
    GETTERS
     */

    public String getDriver() {
        return driver;
    }
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
