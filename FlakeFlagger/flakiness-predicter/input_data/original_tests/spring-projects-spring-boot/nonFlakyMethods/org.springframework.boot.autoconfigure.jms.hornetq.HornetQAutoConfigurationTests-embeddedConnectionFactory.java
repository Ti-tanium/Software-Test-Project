@Test public void embeddedConnectionFactory(){
  load(EmptyConfiguration.class,"spring.hornetq.mode:embedded");
  HornetQProperties properties=this.context.getBean(HornetQProperties.class);
  assertEquals(HornetQMode.EMBEDDED,properties.getMode());
  assertEquals(1,this.context.getBeansOfType(EmbeddedJMS.class).size());
  org.hornetq.core.config.Configuration configuration=this.context.getBean(org.hornetq.core.config.Configuration.class);
  assertFalse("Persistence disabled by default",configuration.isPersistenceEnabled());
  assertFalse("Security disabled by default",configuration.isSecurityEnabled());
  HornetQConnectionFactory connectionFactory=this.context.getBean(HornetQConnectionFactory.class);
  assertInVmConnectionFactory(connectionFactory);
}
