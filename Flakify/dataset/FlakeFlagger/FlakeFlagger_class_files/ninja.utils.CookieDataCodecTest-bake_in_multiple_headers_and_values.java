package ninja.utils;

import static ninja.utils.CookieDataCodec.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * CookieDataCodec and CookieDataCodecTest are imported from Play Framework.
 * 
 * Enables us to use the same sessions as Play Framework if
 * the secret is the same.
 * 
 * Also really important because we want to make sure that our client
 * side session mechanism is widely used and stable.
 * We don't want to reinvent 
 * the wheel of securely encoding / decoding and signing cookie data.
 * 
 * All praise goes to Play Framework and their awesome work.
 * 
 */
public class CookieDataCodecTest {

    @Test public void bake_in_multiple_headers_and_values() throws UnsupportedEncodingException{final Map<String, String> inMap=new HashMap<String, String>(2);inMap.put("a","b");inMap.put("c","d");final String data=encode(inMap);final Map<String, String> outMap=new HashMap<String, String>(1);decode(outMap,data);assertEquals(outMap.size(),2);assertEquals("b",outMap.get("a"));assertEquals("d",outMap.get("c"));}

    private String oldEncoder(final Map<String, String> out) throws UnsupportedEncodingException {
        StringBuilder flash = new StringBuilder();
        for (String key : out.keySet()) {
            if (out.get(key) == null) continue;
            flash.append("\u0000");
            flash.append(key);
            flash.append(":");
            flash.append(out.get(key));
            flash.append("\u0000");
        }
        return URLEncoder.encode(flash.toString(), "utf-8");

    }
}
