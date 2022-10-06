@Test public void testRejectTransformersEAP_7_1_0() throws Exception {
  PathAddress subsystemAddress=PathAddress.pathAddress(UndertowExtension.SUBSYSTEM_PATH);
  PathAddress serverAddress=subsystemAddress.append(UndertowExtension.SERVER_PATH);
  PathAddress hostAddress=serverAddress.append(UndertowExtension.HOST_PATH);
  PathAddress httpsAddress=serverAddress.append(UndertowExtension.HTTPS_LISTENER_PATH);
  PathAddress ajpAddress=serverAddress.append(UndertowExtension.AJP_LISTENER_PATH);
  PathAddress httpAddress=serverAddress.append(UndertowExtension.HTTP_LISTENER_PATH);
  PathAddress servletContainer=subsystemAddress.append(UndertowExtension.PATH_SERVLET_CONTAINER);
  PathAddress byteBufferPath=subsystemAddress.append(UndertowExtension.BYTE_BUFFER_POOL_PATH);
  doRejectTest(ModelTestControllerVersion.EAP_7_1_0,EAP7_1_0,new FailedOperationTransformationConfig().addFailedAttribute(byteBufferPath,FailedOperationTransformationConfig.REJECTED_RESOURCE).addFailedAttribute(hostAddress,new FailedOperationTransformationConfig.NewAttributesConfig(HostDefinition.QUEUE_REQUESTS_ON_START)).addFailedAttribute(httpAddress,new FailedOperationTransformationConfig.NewAttributesConfig(HttpListenerResourceDefinition.PROXY_PROTOCOL,HttpListenerResourceDefinition.ALLOW_UNESCAPED_CHARACTERS_IN_URL)).addFailedAttribute(httpsAddress,new FailedOperationTransformationConfig.NewAttributesConfig(HttpListenerResourceDefinition.PROXY_PROTOCOL,HttpsListenerResourceDefinition.ALLOW_UNESCAPED_CHARACTERS_IN_URL)).addFailedAttribute(servletContainer,new FailedOperationTransformationConfig.NewAttributesConfig(ServletContainerDefinition.DEFAULT_COOKIE_VERSION,ServletContainerDefinition.FILE_CACHE_MAX_FILE_SIZE,ServletContainerDefinition.FILE_CACHE_METADATA_SIZE,ServletContainerDefinition.FILE_CACHE_TIME_TO_LIVE)).addFailedAttribute(ajpAddress,new FailedOperationTransformationConfig.NewAttributesConfig(ALLOW_UNESCAPED_CHARACTERS_IN_URL)));
}
