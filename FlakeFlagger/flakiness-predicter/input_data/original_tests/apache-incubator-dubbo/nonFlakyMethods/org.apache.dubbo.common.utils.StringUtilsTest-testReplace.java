@Test public void testReplace() throws Exception {
  assertThat(StringUtils.replace(null,"*","*"),nullValue());
  assertThat(StringUtils.replace("","*","*"),equalTo(""));
  assertThat(StringUtils.replace("any",null,"*"),equalTo("any"));
  assertThat(StringUtils.replace("any","*",null),equalTo("any"));
  assertThat(StringUtils.replace("any","","*"),equalTo("any"));
  assertThat(StringUtils.replace("aba","a",null),equalTo("aba"));
  assertThat(StringUtils.replace("aba","a",""),equalTo("b"));
  assertThat(StringUtils.replace("aba","a","z"),equalTo("zbz"));
  assertThat(StringUtils.replace(null,"*","*",64),nullValue());
  assertThat(StringUtils.replace("","*","*",64),equalTo(""));
  assertThat(StringUtils.replace("any",null,"*",64),equalTo("any"));
  assertThat(StringUtils.replace("any","*",null,64),equalTo("any"));
  assertThat(StringUtils.replace("any","","*",64),equalTo("any"));
  assertThat(StringUtils.replace("any","*","*",0),equalTo("any"));
  assertThat(StringUtils.replace("abaa","a",null,-1),equalTo("abaa"));
  assertThat(StringUtils.replace("abaa","a","",-1),equalTo("b"));
  assertThat(StringUtils.replace("abaa","a","z",0),equalTo("abaa"));
  assertThat(StringUtils.replace("abaa","a","z",1),equalTo("zbaa"));
  assertThat(StringUtils.replace("abaa","a","z",2),equalTo("zbza"));
}
