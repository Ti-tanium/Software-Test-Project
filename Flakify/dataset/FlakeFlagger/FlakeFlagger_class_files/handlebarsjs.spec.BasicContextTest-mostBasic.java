package handlebarsjs.spec;

import java.io.IOException;

import org.junit.Test;

import com.github.jknack.handlebars.AbstractTest;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

public class BasicContextTest extends AbstractTest {


  @Test public void mostBasic() throws IOException{shouldCompileTo("{{foo}}","{foo: foo}","foo");}
}
