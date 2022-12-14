@Test public void testCreateServiceComponentHostMultiple() throws AmbariException {
  String clusterName="foo1";
  createCluster(clusterName);
  String serviceName="HDFS";
  createService(clusterName,serviceName,null);
  String componentName1="NAMENODE";
  String componentName2="DATANODE";
  createServiceComponent(clusterName,serviceName,componentName1,State.INIT);
  createServiceComponent(clusterName,serviceName,componentName2,State.INIT);
  String host1="h1";
  clusters.addHost(host1);
  clusters.getHost("h1").setOsType("centos5");
  clusters.getHost("h1").persist();
  String host2="h2";
  clusters.addHost(host2);
  clusters.getHost("h2").setOsType("centos5");
  clusters.getHost("h2").persist();
  clusters.mapHostToCluster(host1,clusterName);
  clusters.mapHostToCluster(host2,clusterName);
  Set<ServiceComponentHostRequest> set1=new HashSet<ServiceComponentHostRequest>();
  ServiceComponentHostRequest r1=new ServiceComponentHostRequest(clusterName,serviceName,componentName1,host1,null,State.INIT.toString());
  ServiceComponentHostRequest r2=new ServiceComponentHostRequest(clusterName,serviceName,componentName2,host1,null,State.INIT.toString());
  ServiceComponentHostRequest r3=new ServiceComponentHostRequest(clusterName,serviceName,componentName1,host2,null,State.INIT.toString());
  ServiceComponentHostRequest r4=new ServiceComponentHostRequest(clusterName,serviceName,componentName2,host2,null,State.INIT.toString());
  set1.add(r1);
  set1.add(r2);
  set1.add(r3);
  set1.add(r4);
  controller.createHostComponents(set1);
  Assert.assertEquals(2,clusters.getCluster(clusterName).getServiceComponentHosts(host1).size());
  Assert.assertEquals(2,clusters.getCluster(clusterName).getServiceComponentHosts(host2).size());
  Assert.assertNotNull(clusters.getCluster(clusterName).getService(serviceName).getServiceComponent(componentName1).getServiceComponentHost(host1));
  Assert.assertNotNull(clusters.getCluster(clusterName).getService(serviceName).getServiceComponent(componentName1).getServiceComponentHost(host2));
  Assert.assertNotNull(clusters.getCluster(clusterName).getService(serviceName).getServiceComponent(componentName2).getServiceComponentHost(host1));
  Assert.assertNotNull(clusters.getCluster(clusterName).getService(serviceName).getServiceComponent(componentName2).getServiceComponentHost(host2));
}
