package com.journaldev.spring.helper;


/**
 * Multimap created using Enum
 * good strategy to opt when matrix is required
 * @author zthakb
 *
 */
public enum State {
	
	ACT("ACT", "Australia/ACT"), 
	NSW("NSW", "Australia/ACT"), 
	NT("NT", "Australia/North"), 
	QLD("QLD", "Australia/Queensland"),
	SA("SA", "Australia/South"), 
	TAS("TAS", "Australia/ACT"), 
	VIC("VIC", "Australia/ACT"), 
	WA("WA", "Australia/West");

	private String state;
	private String timeZone;

	State(String state, String timeZone) {
		this.state = state;
		this.timeZone = timeZone;
	}

	public String getState() {
		return this.state;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public void setState(String state) {
		this.state = state;
	}

}
