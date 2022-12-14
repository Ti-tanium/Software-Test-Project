package org.springframework.boot.context.embedded.jetty;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class JettyEmbeddedServletContainerFactoryTests {
@Test public void sslNeedsClientAuthenticationSucceedsWithClientCertificate() throws Exception {
  FileCopyUtils.copy("test",new FileWriter(this.temporaryFolder.newFile("test.txt")));
  AbstractEmbeddedServletContainerFactory factory=getFactory();
  factory.setDocumentRoot(this.temporaryFolder.getRoot());
  Ssl ssl=new Ssl();
  ssl.setKeyStore("src/test/resources/test.jks");
  ssl.setKeyStorePassword("secret");
  ssl.setKeyPassword("password");
  ssl.setClientAuth(ClientAuth.NEED);
  ssl.setTrustStore("src/test/resources/test.jks");
  ssl.setTrustStorePassword("secret");
  factory.setSsl(ssl);
  this.container=factory.getEmbeddedServletContainer();
  this.container.start();
  KeyStore keyStore=KeyStore.getInstance(KeyStore.getDefaultType());
  keyStore.load(new FileInputStream(new File("src/test/resources/test.jks")),"secret".toCharArray());
  SSLConnectionSocketFactory socketFactory=new SSLConnectionSocketFactory(new SSLContextBuilder().loadTrustMaterial(null,new TrustSelfSignedStrategy()).loadKeyMaterial(keyStore,"password".toCharArray()).build());
  HttpClient httpClient=HttpClients.custom().setSSLSocketFactory(socketFactory).build();
  HttpComponentsClientHttpRequestFactory requestFactory=new HttpComponentsClientHttpRequestFactory(httpClient);
  assertThat(getResponse(getLocalUrl("https","/test.txt"),requestFactory),equalTo("test"));
}

}