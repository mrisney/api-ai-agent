[![license](http://img.shields.io/badge/license-APACHE2-blue.svg?style=flat)](https://raw.githubusercontent.com/mrisney/api-ai-agent/master/LICENSE)

# Example api.ai, twilio SMS integration

Example agent integrating [api.ai](http://api.ai) with [twilio SMS](https://twilio.com) service.

## Dial +1(831) 77SQUASH
You can test this directly by texting SMS @ +1-831-777-8274

* Type find a court near [enter your city, location], will find squash courts anywhere in the world.
* If more than one result, you can ask for subset by typing e.g. : 'first 2'.



<img src="https://raw.githubusercontent.com/mrisney/api-ai-agent/master/media/iphone-screen-cast.gif" width="200">

## Deploy

Edit the Google Maps API key to the configuration file: src/main/resources/config.properties:
```
google.places.apikey=<YOUR_GOOGLE_API_KEY>
```

Edit pom.xml as per your application, run maven build, deploy to heroku:
```
mvn clean heroku:deploy-war
```
After deployment of the war file to the servlet container,
go to http://api.ai
Configure your Fulfillment Webhook URL (which is the REST endpoint defined, using SparkJava), and set as enabled:

```
<YOUR_SERVER_URL>/webhook
https://api-ai-agent-khan-squash.herokuapp.com/webhook

```
<p align="center">
<img src="https://raw.githubusercontent.com/mrisney/api-ai-agent/master/media/fulfillment-screen-shot.png" width="400">
</p>

\* The parameters and actions are defined in api.ai console, and are specific to this application.
The actions serve as router type values, these are defined in api.ai.
The Java CONSTANTS are mapped to these values. The parameters are also defined in the intents in api.ai
and are subsequently used to retrieve the values from the JSON payloads.
This is a simplistic application.
Using a Case Switch - as essentially a controller, is admittedly a bit crude.
Good controller design is beyond the scope of this example application.  

```
  // Actions used in intents
  public static final String FIND_COURTS_ACTION = "findCourts";
  public static final String LIST_COURTS_ACTION = "listCourts";

  // Parameters used in intents
  public static final String GEO_CITY_PARAMETER = "geo-city";
  public static final String GEO_STATE_PARAMETER = "geo-state-us";
  public static final String LIST_COUNT_PARAMETER = "count";

```

\** Included in this project is an export of the agent used in this application
[KhanSquashIO.zip] (https://raw.githubusercontent.com/mrisney/api-ai-agent/master/export/KhanSquashIO.zip)
