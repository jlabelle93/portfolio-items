import java.util.*;
import java.io.*;

public class GameDictionary {
	private ArrayList<String> usedWords = new ArrayList<String>();
	private ArrayList<String> words = new ArrayList<String>();

	public boolean wordPopulation() {
		File dictionary = new File("resources/dictionary.txt");
		try {
			if(dictionary.exists()) { 
				Scanner dictInput = new Scanner(dictionary);
				while(dictInput.hasNext()) {
					String inputWord = dictInput.nextLine();
					words.add(inputWord);
				}
				dictInput.close();
				return true;
			}
			else {
				throw new FileNotFoundException("The dictionary file has not been found. "
						+ "Please ensure the dictionary is located in resources/dictionary.txt");

			}
		}
		catch(FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
			return false;
		}	
	}

	public String wordSelection() {
		int sizeLimit = words.size();
		int selection = (int)(Math.random() * sizeLimit);
		String word = words.get(selection);
		usedWords.add(words.remove(selection));
		return word.toUpperCase();
	}

}