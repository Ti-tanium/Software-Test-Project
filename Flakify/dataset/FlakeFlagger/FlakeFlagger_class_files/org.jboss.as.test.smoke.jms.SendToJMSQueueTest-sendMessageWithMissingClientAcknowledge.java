/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.test.smoke.jms;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.as.test.integration.common.jms.JMSOperations;
import org.jboss.as.test.jms.auxiliary.CreateQueueSetupTask;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Basic JMS test using a customly created JMS queue
 *
 * @author <a href="jmartisk@redhat.com">Jan Martiska</a>
 */
@RunWith(Arquillian.class)
@ServerSetup(CreateQueueSetupTask.class)
public class SendToJMSQueueTest {

    private static final Logger logger = Logger.getLogger(SendToJMSQueueTest.class);

    private static final String MESSAGE_TEXT = "Hello world!";

    @Resource(mappedName = "/queue/myAwesomeQueue")
    private Queue queue;

    @Resource(mappedName = "/queue/myAwesomeQueue2")
    private Queue queue2;

    @Resource(mappedName = "/queue/myAwesomeQueue3")
    private Queue queue3;

    @Resource(mappedName = "/ConnectionFactory")
    private ConnectionFactory factory;

    @Test public void sendMessageWithMissingClientAcknowledge() throws Exception{Connection senderConnection=null;Connection consumerConnection=null;Session senderSession=null;Session consumerSession=null;MessageConsumer consumer=null;try {logger.trace("******* Creating connection for consumer");consumerConnection=factory.createConnection();logger.trace("Creating session for consumer");consumerSession=consumerConnection.createSession(false,Session.CLIENT_ACKNOWLEDGE);logger.trace("Creating consumer");consumer=consumerSession.createConsumer(queue3);logger.trace("Start session");consumerConnection.start();logger.trace("***** Start - sending message to topic");senderConnection=factory.createConnection();logger.trace("Creating session..");senderSession=senderConnection.createSession(false,Session.CLIENT_ACKNOWLEDGE);MessageProducer producer=senderSession.createProducer(queue3);TextMessage message=senderSession.createTextMessage("Hello world!");logger.trace("Sending..");producer.send(message);logger.trace("Message sent");senderConnection.start();} catch (Exception ex){ex.printStackTrace();} finally {logger.trace("Closing connections and sessions");if (senderSession != null){senderSession.close();}if (senderConnection != null){senderConnection.close();}}Message receivedMessage=null;try {logger.trace("Receiving");receivedMessage=consumer.receive(5000);try {Thread.sleep(1000);} catch (InterruptedException ex){}consumerSession.recover();receivedMessage=consumer.receive(5000);receivedMessage.acknowledge();} catch (Exception e){e.printStackTrace();Assert.fail(e.getMessage());} finally {if (consumerSession != null){consumerSession.close();}if (consumerConnection != null){consumerConnection.close();}}if (receivedMessage == null){Assert.fail("Message should have been re-delivered, but subsequent attempt to receive it returned null");}Assert.assertTrue("received a " + receivedMessage.getClass().getName() + " instead of a TextMessage",receivedMessage instanceof TextMessage);Assert.assertEquals(((TextMessage)receivedMessage).getText(),"Hello world!");Assert.assertTrue("Redelivered header should have been set to true",receivedMessage.getJMSRedelivered());}

}
