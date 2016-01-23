package week2;

/**
 * Finds a protein within a strand of DNA represented as a string of c,g,t,a letters.
 * A protein is a part of the DNA strand marked by start and stop codons (DNA triples)
 * that is a multiple of 3 letters long.
 *
 * @author Duke Software Team 
 */
import edu.duke.*;
import java.io.*;

public class TagFinder02 {
	public String findProtein(String dna) {
		int start = dna.indexOf("atg");
		if (start == -1) {
			return "";
		}
		int stop = dna.indexOf("tag", start+3);
		if ((stop - start) % 3 == 0) {
			return dna.substring(start, stop+3);
		}
		else {
			return "";
		}
	}
	
	public void testing() {
//		String a = "cccatggggtttaaataataataggagagagagagagagttt";
//		String ap = "atggggtttaaataataatag";
		//String a = "atgcctag";
		//String ap = "";
		String a = "ATGCCCTAG";
    a = a.toLowerCase();
		String ap = "ATGCCCTAG";
    ap = ap.toLowerCase();
		String result = findProtein(a);
		if (ap.equals(result)) {
			System.out.println("success for " + ap + " length " + ap.length());
		}
		else {
      System.err.println("mistake for input: " + a);
			System.err.println("got: " + result);
			System.err.println("not: " + ap);
		}
	}

	public void realTesting() {
		DirectoryResource dr = new DirectoryResource();
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
			String s = fr.asString();
			System.out.println("read " + s.length() + " characters");
			String result = findProtein(s);
			System.out.println("found " + result);
		}
	}

  public static void main(String[] args) {
    TagFinder02 tagFinder = new TagFinder02();
    tagFinder.testing();
//    tagFinder.realTesting();
  }
}
