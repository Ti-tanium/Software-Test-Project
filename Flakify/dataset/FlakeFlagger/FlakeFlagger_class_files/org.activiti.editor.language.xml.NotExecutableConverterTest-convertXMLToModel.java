package org.activiti.editor.language.xml;

import static org.junit.Assert.assertEquals;

import org.activiti.bpmn.model.BpmnModel;
import org.junit.Test;

public class NotExecutableConverterTest extends AbstractConverterTest {

  @Test
  public void convertXMLToModel() throws Exception {
    BpmnModel bpmnModel = readXMLFile();
    validateModel(bpmnModel);
  }

  protected String getResource() {
    return "notexecutablemodel.bpmn";
  }

  private void validateModel(BpmnModel model) {
    assertEquals("simpleProcess", model.getMainProcess().getId());
    assertEquals("Simple process", model.getMainProcess().getName());
    assertEquals(false, model.getMainProcess().isExecutable());
  }
}
