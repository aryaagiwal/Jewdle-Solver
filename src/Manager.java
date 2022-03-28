import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

//Created by Arya Agiwal, Oscar Goes, and Om Goswami 2022

public class Manager {
	//TODO: account for when wordle answer has repeats

	//constants
	private static final String WORD_LIST = "possible.txt";
	private static final Set<String> dictionary = getDictionary();
	private static final int[][] POS_FREQ = {
			{1187, 3896, 1821, 1742, 1833, 738},
			{1566, 134, 673, 585, 148, 51},
			{1756, 343, 849, 633, 483, 291},
			{1138, 116, 770, 860, 434, 2482},
			{673, 3069, 1385, 1932, 6622, 2627},
			{960, 55, 360, 373, 195, 57},
			{997, 116, 769, 853, 376, 665},
			{799, 946, 251, 540, 402, 464},
			{375, 2301, 1491, 2515, 1717, 270},
			{318, 15, 67, 75, 7, 2},
			{496, 114, 282, 755, 406, 183},
			{893, 1005, 1423, 1346, 1550, 805},
			{1193, 323, 968, 805, 373, 332},
			{467, 910, 1685, 1225, 1684, 1196},
			{488, 3269, 1510, 1381, 1216, 319},
			{1541, 471, 686, 740, 298, 173},
			{126, 38, 27, 35, 3, 0},
			{1239, 1530, 2152, 1178, 1301, 1997},
			{2664, 214, 1234, 1060, 726, 6360},
			{1331, 466, 1225, 1606, 1173, 1220},
			{563, 2013, 1047, 803, 721, 66},
			{419, 91, 310, 374, 107, 3},
			{627, 207, 440, 221, 105, 116},
			{19, 114, 126, 68, 21, 94},
			{177, 368, 360, 177, 175, 1624},
			{145, 33, 246, 275, 81, 22},
	};
	
	//instance variables
	TreeMap<String, Integer> currentWords;
	int guessesDone;
	String lastGuessed;
	
	//constructor
	public Manager() {
		currentWords = getWeights(dictionary);
		guessesDone = 0;
		lastGuessed = "";
	}
	
	//helper methods
	
	private static Set<String> getDictionary() {
		Set<String> dictionary = new TreeSet<>();
		try {
			Scanner input = new Scanner(new File(WORD_LIST));
			while (input.hasNext())
				dictionary.add(input.next().toLowerCase());
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to find this file: " + WORD_LIST);
		}
		return Collections.unmodifiableSet(dictionary);
	}
	
	private static TreeMap<String, Integer> getWeights(Set<String> dict) {
		TreeMap<String, Integer> currentList = new TreeMap<>();
		for(String temp : dict) {
			currentList.put(temp, calcWeight(temp));
		}
		return currentList;
	}

	private static int calcWeight(String word) {
		int weight = 0;
		Set<Character> chars = new HashSet<>();
		for(int i = 0; i < 6; i++) {
			weight += POS_FREQ[word.charAt(i) - 'a'][i];
			chars.add(word.charAt(i));
		}
		weight *= chars.size();
		weight /= 6;
		return weight;
	}
	
	private void updateGreen(int index) {
		char c = lastGuessed.charAt(index);
		TreeMap<String, Integer> updated = new TreeMap<>();
		for (String temp : currentWords.keySet()) {
			if (temp.charAt(index) == c) {
				updated.put(temp, currentWords.get(temp));
			}
		}
		currentWords = updated;
	}
	
	private void updateBlack(int index) {
		char c = lastGuessed.charAt(index);
		TreeMap<String, Integer> updated = new TreeMap<>();
		for (String temp : currentWords.keySet()) {
			if (	!(temp.charAt(0) == c ||
					temp.charAt(1) == c ||
					temp.charAt(2) == c ||
					temp.charAt(3) == c ||
					temp.charAt(4) == c ||
					temp.charAt(5) == c)
					) {
				updated.put(temp, currentWords.get(temp));
			}
		}
		currentWords = updated;
	}
	
	private void updateYellow(int index) {
		char c = lastGuessed.charAt(index);
		TreeMap<String, Integer> updated = new TreeMap<>();
		for (String temp : currentWords.keySet()) {
			if (	(temp.charAt(index) != c &&
					(temp.charAt(0) == c ||
					temp.charAt(1) == c ||
					temp.charAt(2) == c ||
					temp.charAt(3) == c ||
					temp.charAt(4) == c ||
					temp.charAt(5) == c)
					)) {
				updated.put(temp, currentWords.get(temp));
			}
		}
		currentWords = updated;
	}
	
	private void updateRepeat(int count, char ch) {
		if (count <= 1) {
			return;
		}
		//DEBUG: System.out.println("Running updateRepeat for char " + ch + " with count " + count);
		TreeMap<String, Integer> updated = new TreeMap<>();
		for (String word : currentWords.keySet()) {
			int curCount = 0;
			for (int c = 0; c < 6; c++) {
				if (word.charAt(c) == ch) {
					curCount++;
				}
			}
			if (curCount >= count) {
				updated.put(word, currentWords.get(word));
			}
		}
		currentWords = updated;
	}
	
	//methods
	public String attemptGuess() {
//		if (guessesDone == 0) {
//			currentWords.remove("soare");
//			lastGuessed = "soare";
//			guessesDone++;
//			return "soare";
//		} else {
			int max = 0;
			String bestGuess = "";
			for (String word : currentWords.keySet()) {
				int wordWeight = currentWords.get(word);
				if (wordWeight > max) {
					max = wordWeight;
					bestGuess = word;
				}
			}
			lastGuessed = bestGuess;
			currentWords.remove(bestGuess);
			guessesDone++;
			return bestGuess;
		//}
	}
	
	public void updateManager(String result) {
		
		HashMap<Character, Integer> duplicates = new HashMap<>();
		System.out.println("Updating and last guessed is: " + lastGuessed + " while result is: " + result);
		for (int c = 0; c < 6; c++) {
			if (result.charAt(c) == 'G') {
				updateGreen(c);
				if (duplicates.get(lastGuessed.charAt(c)) == null) {
					duplicates.put(lastGuessed.charAt(c), 1);
				} else {
					duplicates.put(lastGuessed.charAt(c), duplicates.get(lastGuessed.charAt(c)) + 1);
				}
			}
		}
		
		for (int c = 0; c < 6; c++) {
			if (result.charAt(c) == 'Y') {
				updateYellow(c);
				if (duplicates.get(lastGuessed.charAt(c)) == null) {
					duplicates.put(lastGuessed.charAt(c), 1);
				} else {
					duplicates.put(lastGuessed.charAt(c), duplicates.get(lastGuessed.charAt(c)) + 1);
				}
			}
		}
		
		for (int c = 0; c < 6; c++) {
			if (result.charAt(c) == 'B' && duplicates.get(lastGuessed.charAt(c)) == null) {
				//DEBUG: System.out.println("removing character " + lastGuessed.charAt(c));
				updateBlack(c);
			}
		}
		
		//DEBUG: System.out.println(duplicates);
		
		for (char c : duplicates.keySet()) {
			updateRepeat(duplicates.get(c),c);
		}
	}
	
	public String toString() {
		StringBuilder print = new StringBuilder("");
		for (String word : currentWords.keySet()) {
			print.append(word);
			print.append(": ");
			print.append(currentWords.get(word));
			print.append("\n");
		}
		return print.toString();
	}

	public boolean guessAble () {
		return currentWords.size() > 0;
	}

	public String specialUpdateManager (String result) {
		updateManager(result);
		int varChar = 0;
		for (int c = 0; c < 6; c++) {
			if (result.charAt(c) == 'B') {
				varChar = c;
			}
		}

		//System.out.println(currentWords);
		Set<Character> temp = new HashSet<>();
		for (String word : currentWords.keySet()) {
			temp.add(word.charAt(varChar));
		}
		//System.out.println(temp);
		//Making a new set of all words
		TreeMap<String, Integer> emergencySet = getWeights(dictionary);
		TreeMap<String, Integer> specialSet = new TreeMap<>();
		for (String word : emergencySet.keySet()) {
			int weight = 0;
			Set<Character> localTemp = new HashSet<>();
			for (int i = 0; i < 6; i++) {
				if (temp.contains(word.charAt(i)) && !localTemp.contains(word.charAt(i))) {
					weight++;
					localTemp.add(word.charAt(i));
				}
			}
			if (weight != 0) {
				specialSet.put(word,weight);
			}
		}
		//System.out.println(specialSet);

		int max = 0;
		String bestGuess = "";
		for (String word : specialSet.keySet()) {
			int wordWeight = specialSet.get(word);
			if (wordWeight > max) {
				max = wordWeight;
				bestGuess = word;
			}
		}
		lastGuessed = bestGuess;
		currentWords.remove(bestGuess);
		guessesDone++;
		System.out.println(bestGuess);
		return bestGuess;
	}

	public static Set<String> seeWordList() {
		return dictionary;
	}
}
