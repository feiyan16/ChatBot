

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PokemonAPI {

	public static String getPokemon(String pokemon) throws IOException, InterruptedException {
		String URL = URLMaker(pokemon);
		return URLRead(URL);
	}

	public static String URLMaker(String pokemon) {
		String URL = "https://pokeapi.co/api/v2/pokemon/" + pokemon.trim() + "/";

		return URL;
	}

	public static String parsePokemon(String JSON) {
		String abilities_ = "";
		JsonParser parse = new JsonParser();
		JsonObject jObj = (JsonObject) parse.parse(JSON);
		JsonArray abilities = (JsonArray) jObj.get("abilities");
		for (int i = 0; i < abilities.size(); i++) {
			JsonObject obj = abilities.get(i).getAsJsonObject();
			JsonObject ability = obj.getAsJsonObject();
			ability = ability.getAsJsonObject("ability");
			String name = ability.get("name").getAsString();
			abilities_ += (name + (i == abilities.size() - 2 ? " & " : (i < abilities.size() - 1 ? ", " : "")));
		}

		JsonArray forms = (JsonArray) jObj.get("forms");
		JsonObject obj_ = forms.get(0).getAsJsonObject();
		String pokemon = obj_.get("name").getAsString();

		String pokeOutput = pokemon + "'s abilities: " + abilities_;

		return pokeOutput;
	}

	public static String URLRead(String URL) throws IOException, InterruptedException {
		// create client
		HttpClient client = HttpClient.newHttpClient();
		// build request using URL
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
		// Get response as String
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		return parsePokemon(response.body());
	}

}
