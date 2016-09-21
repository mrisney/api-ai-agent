package ai.api.agent.khan.squash.rest;

public class CONSTANT {

	private CONSTANT() {}

	// Actions used in intents
	public static final String FIND_COURTS_ACTION = "findCourts";
	public static final String LIST_COURTS_ACTION = "listCourts";

	// Parameters used in intents
	public static final String GEO_CITY_PARAMETER = "geo-city";
	public static final String GEO_STATE_PARAMETER = "geo-state-us";
	public static final String LIST_COUNT_PARAMETER = "count";

	// Source in Fulfillment
	public static final String FULFILLMENT_SOURCE = "Google places API";
}
