@Test @WithUserDetails(value="garth",userDetailsServiceBeanName="myUserDetailsService") public void aCreateStandaloneTaskForGroup(){
  Task standAloneTask=taskRuntime.create(TaskPayloadBuilder.create().withName("group task").withGroup("doctor").build());
  Page<Task> tasks=taskRuntime.tasks(Pageable.of(0,50));
  assertThat(tasks.getContent()).hasSize(1);
  Task task=tasks.getContent().get(0);
  assertThat(task.getAssignee()).isNull();
  assertThat(task.getStatus()).isEqualTo(Task.TaskStatus.CREATED);
  currentTaskId=task.getId();
}
