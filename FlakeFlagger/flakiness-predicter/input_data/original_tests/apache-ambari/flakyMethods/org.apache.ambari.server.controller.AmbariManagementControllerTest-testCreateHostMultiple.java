@Test public void testCreateHostMultiple() throws AmbariException {
  clusters.addHost("h1");
  clusters.addHost("h2");
  clusters.addHost("h3");
  clusters.addCluster("c1");
  clusters.addCluster("c2");
  clusters.getCluster("c1").setDesiredStackVersion(new StackId("HDP-0.1"));
  clusters.getCluster("c2").setDesiredStackVersion(new StackId("HDP-0.1"));
  clusters.getHost("h1").setOsType("centos5");
  clusters.getHost("h2").setOsType("centos5");
  clusters.getHost("h3").setOsType("centos5");
  clusters.getHost("h1").persist();
  clusters.getHost("h2").persist();
  clusters.getHost("h3").persist();
  Map<String,String> hostAttrs=new HashMap<String,String>();
  hostAttrs.put("attr1","val1");
  hostAttrs.put("attr2","val2");
  List<String> clusterNames=new ArrayList<String>();
  clusterNames.add("c1");
  clusterNames.add("c2");
  HostRequest r1=new HostRequest("h1",clusterNames,null);
  HostRequest r2=new HostRequest("h2",clusterNames,hostAttrs);
  HostRequest r3=new HostRequest("h3",null,hostAttrs);
  Set<HostRequest> set1=new HashSet<HostRequest>();
  set1.add(r1);
  set1.add(r2);
  set1.add(r3);
  controller.createHosts(set1);
  Assert.assertEquals(2,clusters.getClustersForHost("h1").size());
  Assert.assertEquals(2,clusters.getClustersForHost("h2").size());
  Assert.assertEquals(0,clusters.getClustersForHost("h3").size());
  Assert.assertEquals(2,clusters.getHost("h2").getHostAttributes().size());
  Assert.assertEquals(2,clusters.getHost("h3").getHostAttributes().size());
  Assert.assertEquals("val1",clusters.getHost("h2").getHostAttributes().get("attr1"));
  Assert.assertEquals("val2",clusters.getHost("h2").getHostAttributes().get("attr2"));
}
