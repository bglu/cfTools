package de.bglu.cftools;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class CFMQUrlParserTest {

    private final static String MOCK_MQ_URL = "ampq://testuser:testpassword@127.168.178.21:5672/testvhost";

    private final static Map<String, String> environment        = mockEnvironment();
    private final static Map<String, String> emptyEnvironment   = new HashMap<String, String>();

    private final static CFMQUrlParser parser           = new CFMQUrlParser(environment);
    private final static CFMQUrlParser parserWithoutEnv = new CFMQUrlParser(emptyEnvironment);

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

    @Test
    public void it_should_parse_the_host(){
        String host = parser.getHost();
        assertEquals("127.168.178.21", host);
    }

    @Test
    public void host_should_be_null_when_env_not_set(){
        String host = parserWithoutEnv.getHost();
        assertNull(host);
    }

    @Test
    public void it_should_parse_the_port(){
        String port = parser.getPort();
        assertEquals("5672", port);
    }

    @Test
    public void port_should_be_null_when_env_not_set(){
        String port = parserWithoutEnv.getPort();
        assertNull(port);
    }

    @Test
    public void it_should_parse_the_vhost(){
        String vhost = parser.getVhost();
        assertEquals("testvhost", vhost);
    }

    @Test
    public void vhost_should_be_null_when_env_not_set(){
        String vhost = parserWithoutEnv.getVhost();
        assertNull(vhost);
    }

    @Test
    public void mqurl_should_be_valid(){
        assertTrue(CFMQUrlParser.isValidMqUrl(MOCK_MQ_URL));
    }

    private static Map<String, String> mockEnvironment(){
        Map<String, String> environment = new HashMap<String, String>();
        environment.put(CFConstants.RABBITMQ_URL, MOCK_MQ_URL);

        return environment;
    }

}