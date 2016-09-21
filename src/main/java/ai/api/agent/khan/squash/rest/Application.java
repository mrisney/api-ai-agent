package ai.api.agent.khan.squash.rest;

/**
 * The application entry point when Spark is run in a servlet context.
 *
 * @author Marc Risney
 */
import static spark.Spark.after;
import static spark.Spark.post;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import ai.api.agent.khan.squash.model.Fulfillment;
import ai.api.agent.khan.squash.service.GooglePlacesService;
import ai.api.agent.khan.squash.utils.JsonUtil;
import spark.servlet.SparkApplication;

public class Application implements SparkApplication {

	public void init() {

		post("/webhook", (req, res) -> {

			Fulfillment fulfillment = new Fulfillment();
			String jsonRequest = req.body().toString();

			JsonObject result = Json.parse(jsonRequest).asObject().get("result").asObject();
			String action = result.get("action").asString();

			switch (action) {

			case CONSTANT.FIND_COURTS_ACTION:
				fulfillment = GooglePlacesService.findCourtsNear(result);
				break;
			case CONSTANT.LIST_COURTS_ACTION:
				fulfillment = GooglePlacesService.getCourts(result);
				break;
			default:
				break;
			}
			return JsonUtil.toJson(fulfillment);
		});

		after((req, res) -> {
			res.type("application/json");
		});
	}
}