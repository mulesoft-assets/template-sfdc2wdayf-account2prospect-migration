/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates;

import com.workday.revenue.GetProspectsRequestType;
import com.workday.revenue.ProspectRequestCriteriaType;
import com.workday.revenue.ProspectResponseGroupType;

public class ProspectRequest {

	public static GetProspectsRequestType createNoCriteria() {
		GetProspectsRequestType request = new GetProspectsRequestType();
		
		// no criteria - select everything		
		ProspectResponseGroupType responseGroup = new ProspectResponseGroupType();
		responseGroup.setIncludeProspectData(true);
		responseGroup.setIncludeReference(true);
		
		request.setResponseGroup(responseGroup);
		return request;
	}
	
	public static GetProspectsRequestType createByName(String name) {
		GetProspectsRequestType request = new GetProspectsRequestType();
		
		ProspectRequestCriteriaType criteria = new ProspectRequestCriteriaType();
		criteria.setProspectName(name);
		
		ProspectResponseGroupType responseGroup = new ProspectResponseGroupType();
		responseGroup.setIncludeProspectData(true);
		responseGroup.setIncludeReference(true);
		
		request.setRequestCriteria(criteria);
		request.setResponseGroup(responseGroup);
		return request;
	}
	
	public static GetProspectsRequestType createByID(String id) {
		GetProspectsRequestType request = new GetProspectsRequestType();
		
		ProspectRequestCriteriaType criteria = new ProspectRequestCriteriaType();
		criteria.setProspectID(id);
		
		ProspectResponseGroupType responseGroup = new ProspectResponseGroupType();
		responseGroup.setIncludeProspectData(true);
		responseGroup.setIncludeReference(true);
		
		request.setRequestCriteria(criteria);
		request.setResponseGroup(responseGroup);
		return request;
	}

}
