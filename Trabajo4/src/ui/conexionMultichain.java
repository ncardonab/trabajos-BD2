package ui;

import multichain.command.CommandManager;

public class conexionMultichain {
	
	public static CommandManager multichainConn() {
	CommandManager commandManager = new CommandManager("localhost", "9230" ,"multichainrpc", "Dz8NL8SjpvoeMeE4c9WYYqSuTT4VzAyYuj9WwKriAHWY" );
	return commandManager;
	}
}
