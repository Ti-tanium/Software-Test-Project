@Test public void testSubsystemDescriptions() throws Exception {
  List<ModelNode> empty=Collections.emptyList();
  KernelServices servicesA=createKernelServicesBuilder(AdditionalInitialization.ADMIN_ONLY_HC).setBootOperations(empty).build();
  final ModelNode operation=createReadResourceDescriptionOperation();
  final ModelNode result=servicesA.executeOperation(operation);
  Assert.assertEquals(ModelDescriptionConstants.SUCCESS,result.get(ModelDescriptionConstants.OUTCOME).asString());
  servicesA.shutdown();
}
