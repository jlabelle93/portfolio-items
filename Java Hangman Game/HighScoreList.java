// Jacob Labelle 300306856
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScoreList {
	private static ArrayList<HighScoreUser> hsl = new ArrayList<HighScoreUser>();
	private static File list = new File("resources/highscores.txt");

	public HighScoreList() {
		try {	
			if(list.exists()) {
				Scanner input = new Scanner(list);
				while(input.hasNext()) {
					String name = input.next();
					int score = input.nextInt();
					HighScoreUser hsu = new HighScoreUser(name, score);
					hsl.add(hsu);
				}
				input.close();
			}
			else {
				list.createNewFile();
			}
		}
		catch(FileNotFoundException fnfe) {
			System.out.println("The Highscore List could not be created or located. (resources/highscores.txt)");
		}
		catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	public void addToList(HighScoreUser newbie) {
		boolean userAdded = false;
		if(hsl.isEmpty()) {
			hsl.add(newbie);
		}
		else {
			for(int index = 0; index < hsl.size(); index++) {
				HighScoreUser temp = hsl.get(index);
				if(newbie.getScore() >= temp.getScore() && !userAdded) {
					hsl.add(index, newbie);
					if(hsl.size() > 5) {
						hsl.remove(hsl.size()-1);
					}
					userAdded = true;
				}
			}
		}
	}

	public boolean isHighScore(int score) {
		if(hsl.isEmpty()) {
			return true;
		}
		else {
			for(int index = 0; index < hsl.size(); index++) {
				HighScoreUser temp = hsl.get(index);
				if(score > temp.getScore()) {
					return true;
				}
			}
		}
		return false;
	}

	public void printHighScores()  {
		System.out.println("All Time High Scores");
		System.out.println("====================");
		for(int index = 0; index < hsl.size(); index++) {
			System.out.println((index+1) + ". " + hsl.get(index));
		}
		System.out.println();
	}

	public void saveHighScores() {
		try {
			PrintWriter saving = new PrintWriter(list);
			for(int index = 0; index < hsl.size(); index++) {
				String name = hsl.get(index).getName();
				int score = hsl.get(index).getScore();
				HighScoreUser hsu = new HighScoreUser(name, score);
				saving.print(hsu  + "\n");
			}
			saving.close();
		} 
		catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}	
	}
}