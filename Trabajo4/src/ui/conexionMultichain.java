package ui;

import multichain.command.CommandManager;

public class conexionMultichain {
	
	public static CommandManager multichainConn() {
	CommandManager commandManager = new CommandManager("localhost", "4406" ,"multichainrpc", "5PmndBiSh963NaKUd5SKyEYi9gFB2VCyYimTDGF8sML3" );
	return commandManager;
	}
}
