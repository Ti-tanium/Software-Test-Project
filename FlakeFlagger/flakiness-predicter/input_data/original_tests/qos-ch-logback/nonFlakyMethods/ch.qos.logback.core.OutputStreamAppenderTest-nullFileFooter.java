@Test public void nullFileFooter(){
  String FILE_HEADER="FILE_HEADER ";
  String PRESENTATION_HEADER="PRESENTATION_HEADER";
  String PRESENTATION_FOOTER="PRESENTATION_FOOTER ";
  String FILE_FOOTER=null;
  headerFooterCheck(FILE_HEADER,PRESENTATION_HEADER,PRESENTATION_FOOTER,FILE_FOOTER);
}
