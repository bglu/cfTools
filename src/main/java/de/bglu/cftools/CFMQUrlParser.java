package de.bglu.cftools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Parser for the Cloud Foundry message queue url.
 *
 * In Cloud Foundry, there is a environment variable which specifies the message queue url
 * for the bound message.
 *
 * This variable will look like this:
 *  ampg://<username>:<password>@<host>:<port>/<vhost>
 *
 * Only supports RabbitMq 3 at the moment.
 */
public class CFMQUrlParser {

    /**
     * Map which holds the environment variables, where the database url is stored in.
     * May be the system environment variables (System.getEnv()) or a mocked map for unit testing.
     */
    private final Map<String, String> environmentVariables;
    /**
     * The logger.
     */
    private static final Logger _log = LoggerFactory.getLogger(CFMQUrlParser.class);

    /**
     * Stores the parsed user of the mq connection.
     */
    private String user;

    /**
     * Stores the parsed password of the mq connection
     */
    private String password;

    /**
     * Stores the parsed host of the mq connection
     */
    private String host;

    /**
     * Stores the parsed port of the mq connection
     */
    private String port;

    /**
     * Stores the parsed virtual host of the mq connection
     */
    private String vhost;

    /**
     * Constructs a parser
     * @param environmentVariables to privode mocked environment variables for unit testing
     */
    public CFMQUrlParser(Map<String, String> environmentVariables) {
        this.environmentVariables = environmentVariables;

        String mqUrl = getCFDMqUrl();
        if (! "".equals(mqUrl)) {
            parseMqUrl(mqUrl);
        }
    }

    /**
     * Constructs a parser
     */
    public CFMQUrlParser() {
        this(System.getenv());
    }

    /**
     * parses the mq url
     * @param mqUrl to be parsed
     */
    private void parseMqUrl (final String mqUrl){
        try {

            URI mqUri = new URI(mqUrl);
            user = mqUri.getUserInfo().split(":")[0];
            password = mqUri.getUserInfo().split(":")[1];
            host = mqUri.getHost();
            port = String.valueOf(mqUri.getPort());
            vhost = mqUri.getPath().replace("/", "");


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * some quick tests if the given url is a valid mq url
     * @param mqUrl to check
     * @return true if given url passes some basic sanity tests
     */
    public static boolean isValidMqUrl(final String mqUrl) {
        boolean valid = true;

        //should start with ampq://
        if (! mqUrl.startsWith("ampq://")){
            valid = false;
        }

        //should have three :
        if (countOccurances(mqUrl, ':') != 3) {
            valid = false;
        }

        //should have three /
        if (countOccurances(mqUrl, '/') != 3) {
            valid = false;
        }

        //should have one @
        if (countOccurances(mqUrl, '@') != 1) {
            valid = false;
        }

        return valid;
    }

    /**
     * @param text to check
     * @param token to count
     * @return the mumber of occurances of the given token in the given text
     */
    private static int countOccurances(final String text, final char token) {
        return  text.length() - text.replaceAll(""+token, "").length();
    }


    /*
    HELPERS
     */

    public String getCFDMqUrl() {
        String cfMqUrl = environmentVariables.get(CFConstants.RABBITMQ_URL);

        if (cfMqUrl != null) {
            _log.debug("Database url: {}", cfMqUrl);
        } else {
            _log.error("Database url not found!");
            cfMqUrl = "";
        }

        return cfMqUrl;
    }


    /*
    GETTERS
     */

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getVhost() {
        return vhost;
    }
}
