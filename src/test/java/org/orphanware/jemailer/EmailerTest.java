/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.orphanware.jemailer;

import org.orphanware.jemailer.Emailer;
import junit.framework.TestCase;

/**
 *
 * @author arash
 */
public class EmailerTest extends TestCase {


    public EmailerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testEmailer() {
        
        try{

            String[] args = {"-t", "to@email.com", "-f", "from@email.com",
                             "-s", "your site is down", "-b", "fix it please", "-h", "smtp.x.x", "-p", "465",
                              "-xu", "username", "-xp", "password"};
            Emailer emailer = new Emailer(args);
            
            assertTrue(true);

        } catch(Exception e){

            e.printStackTrace();
            assertTrue(false);
        }


    }

}
