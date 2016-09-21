package ai.api.agent.khan.squash.service;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import ai.api.agent.khan.squash.model.Fulfillment;
import ai.api.agent.khan.squash.rest.CONSTANT;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;

public class GooglePlacesService {
	
	private static Logger log = LoggerFactory.getLogger(GooglePlacesService.class);

	private static String getAPIKey() {
		Properties properties = new Properties();
		try {
			properties.load(GooglePlacesService.class.getResourceAsStream("/config.properties"));
		} catch (Exception e) {
			log.error(e.toString());
		}
		return properties.getProperty("google.places.apikey");
	}

	public static Fulfillment findCourtsNear(JsonObject resultJSONObject) {
		String speech = null, displayText = null;
		JsonObject parameters = resultJSONObject.get("parameters").asObject();
		String location = parameters.getString(CONSTANT.GEO_CITY_PARAMETER, "");
		if (parameters.getString(CONSTANT.GEO_STATE_PARAMETER, "").length() > 0) {
			location += ", " + parameters.getString(CONSTANT.GEO_STATE_PARAMETER, "");
		}

		try {

			GooglePlaces client = new GooglePlaces(getAPIKey());
			List<Place> places = client.getPlacesByQuery("Squash courts : " + location, GooglePlaces.MAXIMUM_RESULTS);
			int results = places.size();
			switch (results) {
			case 0:
				break;
			case 1:
				String name = places.get(0).getName();
				String address = places.get(0).getAddress();
				speech = displayText = name + " located @ " + address;
				break;
			default:
				speech = displayText = "I found " + results + " places near " + location;
				break;
			}

		} catch (Exception e) {
			log.error(e.toString());
		}

		return new Fulfillment(speech, displayText, CONSTANT.FULFILLMENT_SOURCE);
	}

	public static Fulfillment getCourts(JsonObject resultJSONObject) {

		String speech = null, displayText = null;
		StringBuffer sb = new StringBuffer();

		try {
			GooglePlaces client = new GooglePlaces(getAPIKey());
			JsonArray contexts = resultJSONObject.get("contexts").asArray();

			if (contexts.size() > 0) {

				JsonObject parameters = contexts.get(0).asObject().get("parameters").asObject();

				String count = parameters.getString(CONSTANT.LIST_COUNT_PARAMETER, "0");
				Integer max = Integer.valueOf(count);
				String location = parameters.getString(CONSTANT.GEO_CITY_PARAMETER, "") + ", "
						+ parameters.getString(CONSTANT.GEO_STATE_PARAMETER, "");
				Param param = new Param("language").value("en");
				for (Place place : client.getPlacesByQuery("Squash courts : " + location, max, param)) {
					sb.append(place.getName() + "\n" + place.getAddress() + "\n\n");
				}

			}

		} catch (Exception e) {
			log.error(e.toString());
		}

		speech = displayText = sb.toString();
		return new Fulfillment(speech, displayText, CONSTANT.FULFILLMENT_SOURCE);
	}
}
