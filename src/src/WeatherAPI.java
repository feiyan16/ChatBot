

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.*;

public class WeatherAPI {
	
	private static final String APIKey = "9e7007fc63b077ae00241b438ff5d995";

	public static String getWeather(String city) throws IOException, InterruptedException {
		String url = URLMaker(city);
		return URLRead(url);
	}
	
	public static boolean testForSpace(String city) {
		boolean status = false;
		for (int i = 0; i < city.length(); i++) {
			if (Character.isWhitespace(city.charAt(i))) {
				status = true;
			}
		}
		return status;
	}
	
	public static String URLMaker(String city) {
		String city_ = "", URL = null;
		boolean isSpace = testForSpace(city);
		if (isSpace) {
			String[] split = city.split(" ");
			for (int i = 0; i < split.length; i++) {
				city_ += split[i] + (i < split.length - 1? "%20" : "");
			}
			URL = "http://api.openweathermap.org/data/2.5/weather?q=" + city_ + "&APPID=" + APIKey; 
		} else 
			URL = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + APIKey;
		return URL;
	}

	public static String URLRead(String URL) throws IOException, InterruptedException {
		// create client
		HttpClient client = HttpClient.newHttpClient();
		// build request using URL
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		// Get response as String 
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		return parseWeather(response.body());
	}
	
	public static double kelvinToFarenheit (double k) {
		return (k - 273.15) * (9/5) + 32;
	}

	public static String parseWeather(String json) {

		JsonElement jElement = new JsonParser().parse(json);
		JsonObject _main = jElement.getAsJsonObject();
		_main = _main.getAsJsonObject("main");
		double temp = _main.get("temp").getAsDouble();
		double temp_min = _main.get("temp_min").getAsDouble();
		double temp_max = _main.get("temp_max").getAsDouble();
		Object obj = new JsonParser().parse(json);
		JsonObject o = (JsonObject) obj;
		String name = o.get("name").getAsString();
		
		double tempf = kelvinToFarenheit(temp);
		double temp_minf = kelvinToFarenheit(temp_min);
		double temp_maxf = kelvinToFarenheit(temp_max);

		String weatherOutput = "The temperature in " + name + " is 🌡" + (int)tempf + "℉" + ", with a high of ☀️" 
		+ (int)temp_maxf + "℉" + " and a low of ❄️" + (int)temp_minf + "℉";

		return weatherOutput;
	}
}
