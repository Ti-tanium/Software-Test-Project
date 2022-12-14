@Test public void testConfigUpdates() throws AmbariException {
  String clusterName="foo1";
  createCluster(clusterName);
  clusters.getCluster(clusterName).setDesiredStackVersion(new StackId("HDP-0.1"));
  String serviceName="HDFS";
  createService(clusterName,serviceName,null);
  String componentName1="NAMENODE";
  String componentName2="DATANODE";
  String componentName3="HDFS_CLIENT";
  createServiceComponent(clusterName,serviceName,componentName1,State.INIT);
  createServiceComponent(clusterName,serviceName,componentName2,State.INIT);
  createServiceComponent(clusterName,serviceName,componentName3,State.INIT);
  String host1="h1";
  clusters.addHost(host1);
  clusters.getHost("h1").setOsType("centos5");
  clusters.getHost("h1").persist();
  String host2="h2";
  clusters.addHost(host2);
  clusters.getHost("h2").setOsType("centos6");
  clusters.getHost("h2").persist();
  clusters.mapHostToCluster(host1,clusterName);
  clusters.mapHostToCluster(host2,clusterName);
  createServiceComponentHost(clusterName,null,componentName1,host1,null);
  createServiceComponentHost(clusterName,serviceName,componentName2,host1,null);
  createServiceComponentHost(clusterName,serviceName,componentName2,host2,null);
  createServiceComponentHost(clusterName,serviceName,componentName3,host1,null);
  createServiceComponentHost(clusterName,serviceName,componentName3,host2,null);
  Assert.assertNotNull(clusters.getCluster(clusterName).getService(serviceName).getServiceComponent(componentName1).getServiceComponentHost(host1));
  Assert.assertNotNull(clusters.getCluster(clusterName).getService(serviceName).getServiceComponent(componentName2).getServiceComponentHost(host1));
  Assert.assertNotNull(clusters.getCluster(clusterName).getService(serviceName).getServiceComponent(componentName2).getServiceComponentHost(host2));
  Assert.assertNotNull(clusters.getCluster(clusterName).getService(serviceName).getServiceComponent(componentName3).getServiceComponentHost(host1));
  Assert.assertNotNull(clusters.getCluster(clusterName).getService(serviceName).getServiceComponent(componentName3).getServiceComponentHost(host2));
  Map<String,String> configs=new HashMap<String,String>();
  configs.put("a","b");
  ConfigurationRequest cr1, cr2, cr3, cr4, cr5, cr6, cr7, cr8;
  cr1=new ConfigurationRequest(clusterName,"typeA","v1",configs);
  cr2=new ConfigurationRequest(clusterName,"typeB","v1",configs);
  cr3=new ConfigurationRequest(clusterName,"typeC","v1",configs);
  cr4=new ConfigurationRequest(clusterName,"typeD","v1",configs);
  cr5=new ConfigurationRequest(clusterName,"typeA","v2",configs);
  cr6=new ConfigurationRequest(clusterName,"typeB","v2",configs);
  cr7=new ConfigurationRequest(clusterName,"typeC","v2",configs);
  cr8=new ConfigurationRequest(clusterName,"typeE","v1",configs);
  controller.createConfiguration(cr1);
  controller.createConfiguration(cr2);
  controller.createConfiguration(cr3);
  controller.createConfiguration(cr4);
  controller.createConfiguration(cr5);
  controller.createConfiguration(cr6);
  controller.createConfiguration(cr7);
  controller.createConfiguration(cr8);
  Cluster cluster=clusters.getCluster(clusterName);
  Service s=cluster.getService(serviceName);
  ServiceComponent sc1=s.getServiceComponent(componentName1);
  ServiceComponent sc2=s.getServiceComponent(componentName2);
  ServiceComponentHost sch1=sc1.getServiceComponentHost(host1);
  Set<ServiceComponentHostRequest> schReqs=new HashSet<ServiceComponentHostRequest>();
  Set<ServiceComponentRequest> scReqs=new HashSet<ServiceComponentRequest>();
  Set<ServiceRequest> sReqs=new HashSet<ServiceRequest>();
  Map<String,String> configVersions=new HashMap<String,String>();
  configVersions.clear();
  configVersions.put("typeA","v1");
  configVersions.put("typeB","v1");
  configVersions.put("typeC","v1");
  schReqs.clear();
  schReqs.add(new ServiceComponentHostRequest(clusterName,serviceName,componentName1,host1,configVersions,null));
  Assert.assertNull(controller.updateHostComponents(schReqs));
  Assert.assertEquals(0,s.getDesiredConfigs().size());
  Assert.assertEquals(0,sc1.getDesiredConfigs().size());
  Assert.assertEquals(3,sch1.getDesiredConfigs().size());
  configVersions.clear();
  configVersions.put("typeC","v1");
  configVersions.put("typeD","v1");
  scReqs.clear();
  scReqs.add(new ServiceComponentRequest(clusterName,serviceName,componentName2,configVersions,null));
  Assert.assertNull(controller.updateComponents(scReqs));
  Assert.assertEquals(0,s.getDesiredConfigs().size());
  Assert.assertEquals(0,sc1.getDesiredConfigs().size());
  Assert.assertEquals(2,sc2.getDesiredConfigs().size());
  Assert.assertEquals(3,sch1.getDesiredConfigs().size());
  configVersions.clear();
  configVersions.put("typeA","v2");
  configVersions.put("typeC","v2");
  configVersions.put("typeE","v1");
  sReqs.clear();
  sReqs.add(new ServiceRequest(clusterName,serviceName,configVersions,null));
  Assert.assertNull(controller.updateServices(sReqs));
  Assert.assertEquals(3,s.getDesiredConfigs().size());
  Assert.assertEquals(3,sc1.getDesiredConfigs().size());
  Assert.assertEquals(4,sc2.getDesiredConfigs().size());
  Assert.assertEquals(4,sch1.getDesiredConfigs().size());
  Assert.assertEquals("v2",s.getDesiredConfigs().get("typeA").getVersionTag());
  Assert.assertEquals("v2",s.getDesiredConfigs().get("typeC").getVersionTag());
  Assert.assertEquals("v1",s.getDesiredConfigs().get("typeE").getVersionTag());
  Assert.assertEquals("v2",sc1.getDesiredConfigs().get("typeA").getVersionTag());
  Assert.assertEquals("v2",sc1.getDesiredConfigs().get("typeC").getVersionTag());
  Assert.assertEquals("v1",sc1.getDesiredConfigs().get("typeE").getVersionTag());
  Assert.assertEquals("v2",sc2.getDesiredConfigs().get("typeA").getVersionTag());
  Assert.assertEquals("v2",sc2.getDesiredConfigs().get("typeC").getVersionTag());
  Assert.assertEquals("v1",sc2.getDesiredConfigs().get("typeE").getVersionTag());
  Assert.assertEquals("v1",sc2.getDesiredConfigs().get("typeD").getVersionTag());
  Assert.assertEquals("v2",sch1.getDesiredConfigs().get("typeA").getVersionTag());
  Assert.assertEquals("v2",sch1.getDesiredConfigs().get("typeC").getVersionTag());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeE").getVersionTag());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeB").getVersionTag());
  configVersions.clear();
  configVersions.put("typeA","v1");
  configVersions.put("typeB","v1");
  configVersions.put("typeC","v1");
  schReqs.clear();
  schReqs.add(new ServiceComponentHostRequest(clusterName,serviceName,componentName1,host1,configVersions,null));
  Assert.assertNull(controller.updateHostComponents(schReqs));
  Assert.assertEquals(3,s.getDesiredConfigs().size());
  Assert.assertEquals(3,sc1.getDesiredConfigs().size());
  Assert.assertEquals(4,sc2.getDesiredConfigs().size());
  Assert.assertEquals(4,sch1.getDesiredConfigs().size());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeA").getVersionTag());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeC").getVersionTag());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeE").getVersionTag());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeB").getVersionTag());
  configVersions.clear();
  configVersions.put("typeC","v2");
  configVersions.put("typeD","v1");
  scReqs.clear();
  scReqs.add(new ServiceComponentRequest(clusterName,serviceName,componentName1,configVersions,null));
  Assert.assertNull(controller.updateComponents(scReqs));
  Assert.assertEquals(3,s.getDesiredConfigs().size());
  Assert.assertEquals(4,sc1.getDesiredConfigs().size());
  Assert.assertEquals(4,sc2.getDesiredConfigs().size());
  Assert.assertEquals(5,sch1.getDesiredConfigs().size());
  Assert.assertEquals("v2",sc1.getDesiredConfigs().get("typeA").getVersionTag());
  Assert.assertEquals("v2",sc1.getDesiredConfigs().get("typeC").getVersionTag());
  Assert.assertEquals("v1",sc2.getDesiredConfigs().get("typeD").getVersionTag());
  Assert.assertEquals("v1",sc1.getDesiredConfigs().get("typeE").getVersionTag());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeA").getVersionTag());
  Assert.assertEquals("v2",sch1.getDesiredConfigs().get("typeC").getVersionTag());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeD").getVersionTag());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeE").getVersionTag());
  Assert.assertEquals("v1",sch1.getDesiredConfigs().get("typeB").getVersionTag());
}
