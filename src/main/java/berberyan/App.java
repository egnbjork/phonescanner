package berberyan;

import berberyan.controller.Cli;

public class App {
	private App(){}
	
    public static void main( String[] args ) {
    		new Cli(args).start();
    }
}
