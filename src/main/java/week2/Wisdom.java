package week2;
/**
 * Print the line that contains the first occurrence of the word "wisdom" in the given text file.
 *
 * @author Duke Software Team 
 */

import edu.duke.FileResource;
import edu.duke.StorageResource;

public class Wisdom {
	private void findWisdom() {
		FileResource fr = new FileResource("confucius.txt");
		for (String l : fr.lines()) {
			if (l.contains("wisdom")) {
				System.out.println(l);
				break;  // only the first line with the word wisdom is printed
			}
		}
	}

  /**
   * Find unique words in a file
   */
  private StorageResource getWords(){
    final FileResource file = new FileResource("confucius.txt");
    final StorageResource store = new StorageResource();
    for (String word : file.words()) {
      if (!store.contains(word)) { // only store each word once
        store.add(word);
      }
    }
    return store;
  }




  public static void main(String[] args) {
    Wisdom wisdom = new Wisdom();
    wisdom.findWisdom();
    StorageResource words = wisdom.getWords();
    for (String word : words.data()) {
      System.out.println(word);
    }
  }
}
