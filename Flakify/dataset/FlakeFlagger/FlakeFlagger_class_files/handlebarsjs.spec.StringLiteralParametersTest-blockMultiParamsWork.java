package handlebarsjs.spec;

import java.io.IOException;

import org.junit.Test;

import com.github.jknack.handlebars.AbstractTest;
import com.github.jknack.handlebars.HandlebarsException;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

public class StringLiteralParametersTest extends AbstractTest {

  @Test public void blockMultiParamsWork() throws IOException{String string="Message: {{#goodbye cruel world}}{{greeting}} {{adj}} {{noun}}{{/goodbye}}";String hash="{cruel: cruel, world: world}";Hash helpers=$("goodbye",new Helper<String>(){@Override public CharSequence apply(final String cruel,final Options options) throws IOException{return options.fn($("greeting","Goodbye","adj","cruel","noun","world"));}});shouldCompileTo(string,hash,helpers,"Message: Goodbye cruel world","block helpers with multiple params");}

}
