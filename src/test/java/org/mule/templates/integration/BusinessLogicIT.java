/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.integration;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleEvent;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.construct.Flow;
import org.mule.processor.chain.SubflowInterceptingChainLifecycleWrapper;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.templates.builders.SfdcObjectBuilder;

import com.mulesoft.module.batch.BatchTestHelper;

/**
 * The objective of this class is to validate the correct behavior of the Mule
 * Template that make calls to external systems.
 * 
 * The test will update the SFDC test account, then invoke the migration batch process and finally check that the corresponding
 * prospect in Workday is correctly updated.
 */
public class BusinessLogicIT extends AbstractTemplateTestCase {

	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	protected static final int TIMEOUT_SECONDS = 300;
	private BatchTestHelper helper;
	private String SFDC_TEST_ACCOUNT_ID;
	
	private static Flow retrieveProspectFromWorkdayFlow;
	private static SubflowInterceptingChainLifecycleWrapper updateAccountInSalesforceFlow;

	@Rule
	public DynamicPort port = new DynamicPort("http.port");

	/**
	 * Sets up the test prerequisites.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		helper = new BatchTestHelper(muleContext);

		final Properties props = new Properties();
		try {
			props.load(new FileInputStream(PATH_TO_TEST_PROPERTIES));
		} catch (Exception e) {
			logger.error("Error occured while reading mule.test.properties", e);
		}
		SFDC_TEST_ACCOUNT_ID = props.getProperty("sfdc.testaccount.id");
		
		getAndInitializeFlows();
	}

	/**
	 * Performs update on the SFDC test account.
	 * 
	 * @param name
	 *            String - new name
	 * @param website
	 *            String - new website
	 * @throws Exception
	 */
	public void editTestDataInSandbox(String name, String website) throws Exception {
		// create object to edit the test account with
		Map<String, Object> account = SfdcObjectBuilder.anAccount()
				.with("Id", SFDC_TEST_ACCOUNT_ID)
				.with("Name", name)
				.with("Website", website)
				.with("BillingPostalCode", "90210")
				.build();
		List<Map<String, Object>> payload = new ArrayList<>();
		payload.add(account);

		// run the flow
		updateAccountInSalesforceFlow.process(getTestEvent(payload, MessageExchangePattern.REQUEST_RESPONSE));
	}
	
	/**
	 * Initialise all the test flows
	 * 
	 * @throws InitialisationException
	 */
	private void getAndInitializeFlows() throws InitialisationException {
		// Flow for retrieving prospect from Workday instance
		retrieveProspectFromWorkdayFlow = getFlow("retrieveProspectFlow");

		// Flow for updating account in Salesforce instance
		updateAccountInSalesforceFlow = getSubFlow("updateAccountFlow");
		updateAccountInSalesforceFlow.initialise();
	}

	/**
	 * Tests if update of a SFDC test Account results in Workday Prospect update
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMainFlow() throws Exception {
		// edit test data
		String name = generateUniqueName();
		String website = generateUniqueWebsite();
		editTestDataInSandbox(name, website);

		// run the main migration flow
		runFlow("mainFlow");
		helper.awaitJobTermination(TIMEOUT_SECONDS * 1000, 500);
		helper.assertJobWasSuccessful();

		// get updated prospect from Workday
		MuleEvent event = retrieveProspectFromWorkdayFlow.process(getTestEvent(SFDC_TEST_ACCOUNT_ID, MessageExchangePattern.REQUEST_RESPONSE));
		List<Map<String, String>> response = (List<Map<String, String>>) event.getMessage().getPayload();

		// assertions
		assertEquals("Workday should return one result", 1, response.size());
		assertEquals("The name should be the same", response.get(0).get("Name"), name);
		assertEquals("The website should be the same", response.get(0).get("Website"), website);
		assertEquals("The phone should be the same", response.get(0).get("Phone"), "123-4567");
		assertEquals("The postal code should be the same", response.get(0).get("PostalCode"), "90210");		
	}

	/**
	 * Generates unique website based on current time.
	 * 
	 * @return String - Website
	 */
	public String generateUniqueWebsite() {
		return "http://test." + System.currentTimeMillis() + ".com";
	}

	/**
	 * Generates unique name based on current time.
	 * 
	 * @return String - name
	 */
	public String generateUniqueName() {
		return "Test" + System.currentTimeMillis();
	}

}
