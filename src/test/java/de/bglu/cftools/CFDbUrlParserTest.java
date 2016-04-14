package de.bglu.cftools;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CFDbUrlParserTest {

    private final static String MOCK_DATABASE_URL = "mysql://testuser:testpassword@127.168.178.21:3306/dbname";

    private final static Map<String, String> enironment         = mockEnvironment();
    private final static Map<String, String> emptyEnvironment   = new HashMap<String, String>();

    private final static CFDbUrlParser parser           = new CFDbUrlParser(enironment);
    private final static CFDbUrlParser parserWithoutEnv = new CFDbUrlParser(emptyEnvironment);

    @Test
    public void it_should_parse_the_jdbc_url(){
        String url = parser.getUrl();
        assertEquals("jdbc:mysql://127.168.178.21:3306/dbname", url);
    }

    @Test
    public void jdbc_url_should_be_null_when_env_not_set(){
        String url = parserWithoutEnv.getUrl();
        assertNull(url);
    }


    @Test
    public void it_should_parse_the_user(){
        String user = parser.getUser();
        assertEquals("testuser", user);
    }

    @Test
    public void user_should_be_null_when_env_not_set(){
        String user = parserWithoutEnv.getUser();
        assertNull(user);
    }

    @Test
    public void it_should_parse_the_password(){
        String password = parser.getPassword();
        assertEquals("testpassword", password);
    }

    @Test
    public void password_should_be_null_when_env_not_set(){
        String password = parserWithoutEnv.getPassword();
        assertNull(password);
    }


    private static Map<String, String> mockEnvironment(){
        Map<String, String> environment = new HashMap<String, String>();
        environment.put(CFConstants.DATABASE_URL, MOCK_DATABASE_URL);

        return environment;
    }

}