@Test public void shouldBuildValidInjectorWithFewFieldsSet() throws Exception {
  final NamingStrategy mockNamingStrategy=mock(NamingStrategy.class);
  final ProcessorsFactory mockProcessorsFactory=mock(ProcessorsFactory.class);
  final UriLocatorFactory mockLocatorFactory=mock(UriLocatorFactory.class);
  final MetaDataFactory mockMetaDataFactory=Mockito.mock(MetaDataFactory.class);
  final BaseWroManagerFactory managerFactroy=new BaseWroManagerFactory();
  managerFactroy.setNamingStrategy(mockNamingStrategy);
  managerFactroy.setProcessorsFactory(mockProcessorsFactory);
  managerFactroy.setUriLocatorFactory(mockLocatorFactory);
  managerFactroy.setMetaDataFactory(mockMetaDataFactory);
  final Injector injector=InjectorBuilder.create(managerFactroy).build();
  assertNotNull(injector);
  final Sample sample=new Sample();
  injector.inject(sample);
  assertNotNull(sample.preProcessorExecutor);
  sample.namingStrategy.rename("",WroUtil.EMPTY_STREAM);
  verify(mockNamingStrategy).rename("",WroUtil.EMPTY_STREAM);
  sample.processorsFactory.getPostProcessors();
  verify(mockProcessorsFactory).getPostProcessors();
  sample.uriLocatorFactory.getInstance("");
  verify(mockLocatorFactory).getInstance("");
  sample.metaDataFactory.create();
  verify(mockMetaDataFactory).create();
  assertNotNull(sample.callbackRegistry);
  assertSame(injector,sample.injector);
  assertNotNull(sample.groupsProcessor);
  assertNotNull(sample.modelFactory);
  assertNotNull(sample.groupExtractor);
  assertNotNull(sample.cacheStrategy);
  assertNotNull(sample.hashBuilder);
  assertNotNull(sample.readOnlyContext);
  assertNotNull(sample.metaDataFactory);
  assertNotNull(sample.cacheKeyFactory);
  assertNotNull(sample.bundleProcessor);
}
