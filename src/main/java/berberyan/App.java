package berberyan;

import berberyan.controller.CommandLineInterface;

public class App {
	private App(){}

	public static void main( String[] args ) {
		new CommandLineInterface(args).start();
	}
}
