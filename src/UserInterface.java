import java.util.*;

//Created by Arya Agiwal, Oscar Goes, Om Goswami 2022

public class UserInterface {
	
	public static void main(String[] args) {
		Manager game = new Manager();
		//Statistics data = new Statistics();
		//data.getData(game.seeWordList());
		System.out.println("Welcome to the Jewdle solver.\n");
		
		//DEBUG
		//System.out.println(game);
		//System.out.println("Best guess: " + game.attemptGuess());
		//System.out.println(game);
		//game.updateManager("BGYGB");
		//System.out.println(game);
		
		int tries = 0;
		Scanner in = new Scanner(System.in);  
		while (tries < 6) {
			if (!game.guessAble()) {
				System.out.println("Uh oh.....");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("I'm out of words. You're on your own.");
				System.exit(0);
			}
			System.out.println("Best guess: " + game.attemptGuess());
			tries++;
			System.out.println("What did Jewdle return? Enter 6 character string with G for green, Y for yellow, B for black.");
			String current = in.next().toUpperCase();
			while (!isValid(current)) {
				System.out.println("What did Jewdle return? Enter 6 character string with G for green, Y for yellow, B for black.");
				current = in.next().toUpperCase();
			}
			int gCount = 0;
			for (int i = 0; i < 6; i++) {
				if (current.charAt(i) == 'G') {
					gCount++;
				}
			}
			if (gCount == 5 && tries <= 4) {
				//System.out.println("Activating special case");
				System.out.println("Best guess: " + game.specialUpdateManager(current));
				tries++;
				System.out.println("What did Jewdle return? Enter 6 character string with G for green, Y for yellow, B for black.");
				current = in.next().toUpperCase();
				while (!isValid(current)) {
					System.out.println("What did Jewdle return? Enter 6 character string with G for green, Y for yellow, B for black.");
					current = in.next().toUpperCase();
				}
				gCount = 0;
				for (int i = 0; i < 6; i++) {
					if (current.charAt(i) == 'G') {
						gCount++;
					}
				}
			}
			if (gCount == 6) {
				System.out.println("\nWhat's there to be happy about?");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Job's not finished. Job finished?");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("I don't think so.");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Cheater.");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Made by Arya Agiwal, Oscar Goes, and Om Goswami :)");
				System.exit(0);
			}
			System.out.println(game);
			game.updateManager(current);
			System.out.println(game);
			
			//DEBUGGING
			//System.out.println(game);
		}
		if (tries == 6) {
			System.out.println("Do you want to imagine something?");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Imagine losing with the help of a bot.");
		}
		in.close();
	}
	
	private static boolean isValid(String current) {
		if (current.length() != 6) {
			return false;
		}
		for (int i = 0; i < 6; i++) {
			if (current.charAt(i) != 'G' && current.charAt(i) != 'B' && current.charAt(i) != 'Y') {
				return false;
			}
		}
		return true;
	}
	
}
