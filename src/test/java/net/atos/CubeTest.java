package net.atos;

import java.net.URL;

import org.arquillian.cube.CubeIp;
import org.arquillian.cube.DockerUrl;
import org.arquillian.cube.HostIp;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class CubeTest {

    @HostIp
    private String hostIp;

    @CubeIp(containerName = "test")
    private String cubeIp;

    @DockerUrl(containerName = "test", exposedPort = 1080)
    @ArquillianResource
    private URL url;

    @Test
    @InSequence(1)
    public void basicTest() {
    	assertThat(true, is(true));
    }
   
    @Test
    @RunAsClient
    @InSequence(2)
    public void heartbeatTest() throws Exception {

        System.out.println("URL: "+url.toExternalForm());
        System.out.println("Host IP: "+hostIp);
        System.out.println("Cube IP: "+cubeIp);

        given().
        when().
          get("http://"+cubeIp+":1080/" + "proxy/").
        then().
          assertThat().body(containsString("<html>"));
    }

}
