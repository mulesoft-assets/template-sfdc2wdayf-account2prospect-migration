/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.builders;

import java.util.HashMap;
import java.util.Map;

public class SfdcObjectBuilder {

	private Map<String, Object> fields;

	public SfdcObjectBuilder() {
		this.fields = new HashMap<String, Object>();
	}

	public SfdcObjectBuilder with(String field, Object value) {
		SfdcObjectBuilder copy = new SfdcObjectBuilder();
		copy.fields.putAll(this.fields);
		copy.fields.put(field, value);
		return copy;
	}

	public Map<String, Object> build() {
		return fields;
	}

	/*
	 * Creation methods
	 */

	public static SfdcObjectBuilder aContact() {
		return new SfdcObjectBuilder();
	}

	public static SfdcObjectBuilder aCustomObject() {
		return new SfdcObjectBuilder();
	}

	public static SfdcObjectBuilder aUser() {
		return new SfdcObjectBuilder();
	}

	public static SfdcObjectBuilder anAccount() {
		return new SfdcObjectBuilder();
	}

}
