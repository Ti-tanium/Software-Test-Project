@Test public void should_maintain_equals_and_hashCode_contract(){
  assertMaintainsEqualsAndHashCodeContract(description,new TextDescription("Yoda"));
}
