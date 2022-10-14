package org.assertj.core.api.date;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class DateAssert_isInSameMinuteWindowAs_Test {
@Test public void should_verify_assertion_with_date_arg_string_with_default_format() throws ParseException {
  assertionInvocationWithStringArg(dateAsStringWithDefaultFormat);
  verifyAssertionInvocation(parse(dateAsStringWithDefaultFormat));
}

}