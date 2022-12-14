@Test @WithUserDetails(value="salaboy",userDetailsServiceBeanName="myUserDetailsService") public void getProcessInstances(){
  Page<ProcessInstance> processInstancePage=processRuntime.processInstances(Pageable.of(0,50));
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(0);
  processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey(CATEGORIZE_HUMAN_PROCESS).withVariable("expectedKey",true).withBusinessKey("my business key").build());
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(1);
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().withBusinessKey("other key").build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(0);
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().withBusinessKey("my business key").build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(1);
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().suspended().build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(0);
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().active().build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(1);
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().active().suspended().build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(1);
  ProcessInstance processInstance=processInstancePage.getContent().get(0);
  ProcessInstance suspendedProcessInstance=processRuntime.suspend(ProcessPayloadBuilder.suspend(processInstance));
  assertThat(suspendedProcessInstance).isNotNull();
  assertThat(suspendedProcessInstance.getStatus()).isEqualTo(ProcessInstance.ProcessInstanceStatus.SUSPENDED);
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().active().build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(0);
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().suspended().build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(1);
  processRuntime.resume(ProcessPayloadBuilder.resume(processInstance));
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().suspended().build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(0);
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50),ProcessPayloadBuilder.processInstances().active().build());
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(1);
  ProcessInstance getSingleProcessInstance=processRuntime.processInstance(processInstance.getId());
  assertThat(getSingleProcessInstance).isNotNull();
  assertThat(getSingleProcessInstance.getStatus()).isEqualTo(ProcessInstance.ProcessInstanceStatus.RUNNING);
  ProcessInstance deletedProcessInstance=processRuntime.delete(ProcessPayloadBuilder.delete(getSingleProcessInstance));
  assertThat(deletedProcessInstance).isNotNull();
  processInstancePage=processRuntime.processInstances(Pageable.of(0,50));
  assertThat(processInstancePage).isNotNull();
  assertThat(processInstancePage.getContent()).hasSize(0);
}
