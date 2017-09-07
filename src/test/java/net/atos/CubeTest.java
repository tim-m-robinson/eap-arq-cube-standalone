package net.atos;

import java.net.URL;
import java.nio.file.Paths;

import javax.ws.rs.core.Application;

import org.arquillian.cube.CubeIp;
import org.arquillian.cube.DockerUrl;
import org.arquillian.cube.HostIp;
import org.arquillian.cube.HostPort;

import org.hamcrest.core.Is;

//import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class CubeTest {

    @HostIp
    private String ip;

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

        given().
        when().
          get(url.toExternalForm() + "/proxy/").
        then().
          assertThat().body(containsString("<html>"));
    }

}
