import java.io.IOException;
import org.jibble.pircbot.PircBot;

public class ChatBotLogic extends PircBot{
	public ChatBotLogic() {
        this.setName("AskBot");
    }
	
    public void onMessage(String channel, String sender, String login, 
    									String hostname, String message) {
    	if (message.contains("hello") || message.contains("Hello") || message.contains("hi") || message.contains("Hi")) {
    		sendMessage(channel, "Hey " + sender + " 😀!");
    		sendMessage(channel, "What would you like to ask me? These are your available commands: 🤓");
    		sendMessage(channel, "\\weather🌦: city_name/ city_name, country_name");
    		sendMessage(channel, "\\headline📰: query");
    		sendMessage(channel, "\\pokedex🐱: pokemon_name");
    	} else if ((message.contains("bye") || message.contains("Bye")) && (message.contains("thank") || message.contains("Thank"))) {
    		sendMessage(channel, "Your welcome! Goodbye " + sender + " 😀!");
    	} else if ((message.contains("thank") || message.contains("Thank"))) {
    		sendMessage(channel, "Your welcome 😀!");
    	} else if (message.contains("bye") || message.contains("Bye") || message.contains("goodbye") || message.contains("Goodbye")) {
    		sendMessage(channel, "😀 Goodbye!" + sender + "!");
    	} else if (message.contains("disconnect")){
    		disconnect();
    	} else if (message.contains("\\weather")) {
    		String msg = message.toString();
    		char[] msgArray = msg.toCharArray();
    		int index = 0;
    		String city = "";
      		for(int i = 0; i < msg.length(); i++) {
      			if(msgArray[i] == ':') {
      				index = i;
      			}
      		}
      		for(int j = index + 2; j < msg.length(); j++) {
      			city += msgArray[j];
      		}
      		try {
				sendMessage(channel, WeatherAPI.getWeather(city));
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else if (message.contains("\\headline")) {
    		String msg = message.toString();
    		char[] msgArray = msg.toCharArray();
    		String query = "";
    		int index = 0;
    		for(int i = 0; i < msg.length(); i++) {
      			if(msgArray[i] == ':') {
      				index = i;
      			}
      		}
    		for(int j = index + 2; j < msg.length(); j++) {
      			query += msgArray[j];
      		}
    		try {
				sendMessage(channel, WashingtonPostAPI.getNews(query));
				sendMessage(channel, CNNAPI.getNews(query));
				sendMessage(channel, CNBCAPI.getNews(query));
				sendMessage(channel, BBCAPI.getNews(query));
				sendMessage(channel, FoxNewsAPI.getNews(query));
				sendMessage(channel, USANewsAPI.getNews(query));
				sendMessage(channel, WallStreetAPI.getNews(query));
				sendMessage(channel, ABCAPI.getNews(query));
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}	
    	} else if(message.contains("\\pokedex")) {
    		String msg = message.toString();
    		char[] msgArray = msg.toCharArray();
    		String pokemon = "";
    		int index = 0;
    		for(int i = 0; i < msg.length(); i++) {
      			if(msgArray[i] == ':') {
      				index = i;
      			}
      		}
    		for(int j = index + 2; j < msg.length(); j++) {
      			pokemon += msgArray[j];
      		}
    		try {
				sendMessage(channel, PokemonAPI.getPokemon(pokemon));
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else {
    		String apology = "Sorry, I didn't understand your command 😞️ (*cough* yet... I will learn 🦾), "
    				+ "please try again with the following commands: ";
    		sendMessage(channel, apology);
    		sendMessage(channel, "\\weather🌦: city_name/ city_name, country_name");
    		sendMessage(channel, "\\headline📰: query");
    		sendMessage(channel, "\\pokedex🐱: pokemon_name");
    		sendMessage(channel, "Make sure your spelling is correct 😊✏️");
    		
    	}
    }
}
