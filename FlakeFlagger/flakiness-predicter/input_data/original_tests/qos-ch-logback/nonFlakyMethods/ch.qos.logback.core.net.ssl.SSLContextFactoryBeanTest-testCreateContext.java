@Test public void testCreateContext() throws Exception {
  factoryBean.setKeyManagerFactory(keyManagerFactory);
  factoryBean.setKeyStore(keyStore);
  factoryBean.setTrustManagerFactory(trustManagerFactory);
  factoryBean.setTrustStore(trustStore);
  factoryBean.setSecureRandom(secureRandom);
  assertNotNull(factoryBean.createContext(context));
  assertTrue(keyManagerFactory.isFactoryCreated());
  assertTrue(trustManagerFactory.isFactoryCreated());
  assertTrue(keyStore.isKeyStoreCreated());
  assertTrue(trustStore.isKeyStoreCreated());
  assertTrue(secureRandom.isSecureRandomCreated());
  assertTrue(context.hasInfoMatching(SSL_CONFIGURATION_MESSAGE_PATTERN));
  assertTrue(context.hasInfoMatching(KEY_MANAGER_FACTORY_MESSAGE_PATTERN));
  assertTrue(context.hasInfoMatching(TRUST_MANAGER_FACTORY_MESSAGE_PATTERN));
  assertTrue(context.hasInfoMatching(KEY_STORE_MESSAGE_PATTERN));
  assertTrue(context.hasInfoMatching(TRUST_STORE_MESSAGE_PATTERN));
  assertTrue(context.hasInfoMatching(SECURE_RANDOM_MESSAGE_PATTERN));
}
