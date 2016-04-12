package de.bglu.cftools;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class CFMQUrlParserTest {

    private final static String MOCK_MQ_URL = "ampq://testuser:testpassword@127.168.178.21:5672/testvhost";

    @Test
    public void it_should_parse_the_user(){
        Map<String, String> environment = mockEnvironment();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String user = parser.getUser();
        assertEquals("testuser", user);
    }

    @Test
    public void user_should_be_null_when_env_not_set(){
        Map<String, String> environment = new HashMap<String, String>();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String user = parser.getUser();
        assertNull(user);
    }

    @Test
    public void it_should_parse_the_password(){
        Map<String, String> environment = mockEnvironment();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String password = parser.getPassword();
        assertEquals("testpassword", password);
    }

    @Test
    public void password_should_be_null_when_env_not_set(){
        Map<String, String> environment = new HashMap<String, String>();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String password = parser.getPassword();
        assertNull(password);
    }

    @Test
    public void it_should_parse_the_host(){
        Map<String, String> environment = mockEnvironment();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String host = parser.getHost();
        assertEquals("127.168.178.21", host);
    }

    @Test
    public void host_should_be_null_when_env_not_set(){
        Map<String, String> environment = new HashMap<String, String>();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String host = parser.getHost();
        assertNull(host);
    }

    @Test
    public void it_should_parse_the_port(){
        Map<String, String> environment = mockEnvironment();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String port = parser.getPort();
        assertEquals("5672", port);
    }

    @Test
    public void port_should_be_null_when_env_not_set(){
        Map<String, String> environment = new HashMap<String, String>();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String port = parser.getPort();
        assertNull(port);
    }

    @Test
    public void it_should_parse_the_vhost(){
        Map<String, String> environment = mockEnvironment();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String vhost = parser.getVhost();
        assertEquals("testvhost", vhost);
    }

    @Test
    public void vhost_should_be_null_when_env_not_set(){
        Map<String, String> environment = new HashMap<String, String>();
        CFMQUrlParser parser = new CFMQUrlParser(environment);

        String vhost = parser.getVhost();
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