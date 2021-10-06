

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.*;

public class USANewsAPI {

	private static final String APIKey = "c3331d1c3f964c2bb8f669be3d492a5f";

	public static String getNews(String query) throws IOException, InterruptedException {
		String URL = URLMaker(query);
		return URLRead(URL);
	}

	public static boolean testForSpace(String query) {
		boolean status = false;

		for (int i = 0; i < query.length(); i++) {
			if (Character.isWhitespace(query.charAt(i))) {
				status = true;
			}
		}
		return status;
	}

	public static String URLMaker(String query) {
		String query_ = "", URL = null;
		boolean isSpace = testForSpace(query);
		if (isSpace == true) {
			String[] split = query.split(" ");
			for (int i = 0; i < split.length; i++) {
				query_ = split[i] + (i < split.length - 1 ? "%20" : "");
			}
			URL = "http://newsapi.org/v2/top-headlines?q=" + query_ + "&sources=usa-today&apiKey=" + APIKey;
		} else {
			URL = "http://newsapi.org/v2/top-headlines?q=" + query + "&sources=usa-today&apiKey=" + APIKey;
		}
		return URL;
	}

	public static String parseNews(String JSON) {
		String newsOutput = null;
		JsonParser parse = new JsonParser();
		JsonObject jObj = (JsonObject) parse.parse(JSON);
		JsonArray articles = (JsonArray) jObj.get("articles");
		if (articles.size() != 0) {
			JsonObject obj = articles.get(0).getAsJsonObject();
			String title = obj.get("title").getAsString();
			String desc = obj.get("description").getAsString();
			String url = obj.get("url").getAsString();
			newsOutput = "USA Today: " + title + " Description: " + desc + " To read more, click here: " + url;
		} else
			newsOutput = "There are no headlines in USA Today regarding this topic";
		return newsOutput;

	}

	public static String URLRead(String URL) throws IOException, InterruptedException {
		// create client
		HttpClient client = HttpClient.newHttpClient();
		// build request using URL
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		// Get response as String
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		return parseNews(response.body());
	}

}
