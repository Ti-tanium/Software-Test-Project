/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2013, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.core.joran;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import ch.qos.logback.core.CoreConstants;
import org.junit.Test;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.ContextBase;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.action.ext.IncAction;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.spi.ElementSelector;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.TrivialStatusListener;
import ch.qos.logback.core.testUtil.RandomUtil;
import ch.qos.logback.core.util.CoreTestConstants;

public class TrivialConfiguratorTest {

  Context context = new ContextBase();
  HashMap<ElementSelector, Action> rulesMap = new HashMap<ElementSelector, Action>();

  @Test public void lbcore127() throws IOException,JoranException{String jarEntry="buzz.xml";String jarEntry2="lightyear.xml";File jarFile=makeRandomJarFile();fillInJarFile(jarFile,jarEntry,jarEntry2);URL url1=asURL(jarFile,jarEntry);URL url2=asURL(jarFile,jarEntry2);URLConnection urlConnection2=url2.openConnection();urlConnection2.setUseCaches(false);InputStream is=urlConnection2.getInputStream();TrivialConfigurator tc=new TrivialConfigurator(rulesMap);tc.setContext(context);tc.doConfigure(url1);is.read();is.close();assertTrue(jarFile.delete());assertFalse(jarFile.exists());}

  File makeRandomJarFile() {
    File outputDir = new File(CoreTestConstants.OUTPUT_DIR_PREFIX);
    outputDir.mkdirs();
    int randomPart = RandomUtil.getPositiveInt();
    return new File(CoreTestConstants.OUTPUT_DIR_PREFIX + "foo-" + randomPart
            + ".jar");
  }

  private void fillInJarFile(File jarFile, String jarEntryName)
          throws IOException {
    fillInJarFile(jarFile, jarEntryName, null);
  }

  private void fillInJarFile(File jarFile, String jarEntryName1,
                             String jarEntryName2) throws IOException {
    JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile));
    jos.putNextEntry(new ZipEntry(jarEntryName1));
    jos.write("<x/>".getBytes());
    jos.closeEntry();
    if (jarEntryName2 != null) {
      jos.putNextEntry(new ZipEntry(jarEntryName2));
      jos.write("<y/>".getBytes());
      jos.closeEntry();
    }
    jos.close();
  }

  URL asURL(File jarFile, String jarEntryName) throws IOException {
    URL innerURL = jarFile.toURI().toURL();
    return new URL("jar:" + innerURL + "!/" + jarEntryName);
  }

}
