public class ChatBotMain {

	public static void main(String[] args) throws Exception {
		ChatBotLogic bot = new ChatBotLogic();
		bot.setVerbose(true);
		bot.connect("irc.freenode.net");
		bot.joinChannel("#AskFM");
	}

}
