package week2;

/**
 * Finds a protein within a strand of DNA represented as a string of c,g,t,a letters.
 * A protein is a part of the DNA strand marked by start and stop codons (DNA triples)
 * that is a multiple of 3 letters long.
 *
 * @author Duke Software Team
 */

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;

public class TagFinder03 {
  private static final String STOP_TAG_01 = "tag";
  private static final String STOP_TAG_02 = "tga";
  private static final String STOP_TAG_03 = "taa";
  /**
   * Change method to have single return value
   *
   * @param dna
   * @return
   */
  private int startCodon = -1;
  private int afterBeginningCodon;

  private String findProtein(String dna) {
    dna = dna.toLowerCase();
    String result;

    // Identify the first start codon in the string
    startCodon = dna.indexOf("atg");
    // Indicates no gene in this strand of DNA, i.e. no start codon
    if (startCodon == -1) {
      result = "";
    }
    // 'start + 3' indicates we're searching for the stop codon after the beginning of the start codon
    afterBeginningCodon = startCodon + 3;

    int stop1 = dna.indexOf("tag", afterBeginningCodon);
    int stop2 = dna.indexOf("tga", afterBeginningCodon);
    int stop3 = dna.indexOf("taa", afterBeginningCodon);

    String stopCodon = "Stop codon ";
    if (isIntervalMultipleOfThree(stop1)) {
      result = dna.substring(startCodon, endIndex(stop1));
      stopCodon += "'tag' at index: " + stop1;
    } else if (isIntervalMultipleOfThree(stop2)) {
      result = dna.substring(startCodon, endIndex(stop2));
      stopCodon += "'tga' at index: " + stop2;
    } else if (isIntervalMultipleOfThree(stop3)) {
      result = dna.substring(startCodon, endIndex(stop3));
      stopCodon += "'taa' at index: " + stop3;
    } else {
      result = "";
      stopCodon = "Not found";
    }
    System.out.println(stopCodon);
    return result;
  }

  private int endIndex(int stopCodon) {
    // here the '+ 3' includes both the start and the stop codons in the string returned
    return stopCodon + 3;
  }

  private boolean isIntervalMultipleOfThree(int stopCodon) {
    boolean result = false;
    if ((stopCodon - startCodon) % 3 == 0) {
      result = true;
    }
    return result;
  }

  private void printAllStarts(String dna) {
    int start = 0;
    // loop exits when no startCodon found
    while (true) {
      int loc = dna.indexOf("atg", start);
      if (loc == -1) {
        break;
      }
      System.out.println("starts at " + loc);
      start = loc + 3;
    }
  }

  /**
   * Find a valid stop codon in dna that occurs after index.
   * If no valid stop codon found, return dna.length()
   * @param dna is String being searched
   * @param index is index where search starts
   * @return index of beginning of a valid stop codon,
   * or dna.length() if no valid codon
   */
  private int findStopIndex(String dna, int index) {

    int stop1 = dna.indexOf("tga", index);
    if (stop1 == -1 || (stop1-index) %3 != 0) {
      stop1 = dna.length();
    }
    int stop2 = dna.indexOf("taa", index);
    if (stop2 == -1 || (stop2-index) %3 != 0) {
      stop2 = dna.length();
    }
    int stop3 = dna.indexOf("tag", index);
    if (stop3 == -1 || (stop3-index) %3 != 0) {
      stop3 = dna.length();
    }
    return Math.min(stop1, Math.min(stop2, stop3));
  }


  /**\
   *
   * @param gene: string representing a gene
   * @return stopCodon of the gene, or empty string if the parameter is not a gene
   */
  private String stopCodonBack(String gene) {
    gene = gene.toLowerCase();

    String stopCodon;

    afterBeginningCodon = startCodon + 3;

    if (isIntervalMultipleOfThree(gene.indexOf(STOP_TAG_01, afterBeginningCodon))) {
      stopCodon = STOP_TAG_01;
    } else if (isIntervalMultipleOfThree(gene.indexOf(STOP_TAG_02, afterBeginningCodon))) {
      stopCodon = STOP_TAG_02;
    } else if (isIntervalMultipleOfThree(gene.indexOf(STOP_TAG_03, afterBeginningCodon))) {
      stopCodon = STOP_TAG_03;
    } else {
      stopCodon = "";
    }

    return stopCodon;
  }

  private String stopCodon(String dna) {

    String answer = findProtein(dna);
    int size = answer.length();

    if (size > 6) {
      return answer.substring(size - 3, size);
    } else {
      return "";
    }


  }


  private void testing() {
		String a = "cccatggggtttaaataataataggagagagagagagagttt";
		String ap = "atggggtttaaataataatag";
//    String a = "atgcctag";
//    String ap = "";
//    String a = "ATGCCCTAG";
//    String ap = "ATGCCCTAG";
//    String a = "AATGCTAGTTTAAATCTGA";
//    String ap = "ATGCTAGTTTAAATCTGA";
//    String a = "ataaactatgttttaaatgt";
//    String ap = "atgttttaa";
//    String a = "acatgataacctaag";
//    String ap = "";


    System.out.println("Input String with length " + a.length() + ": " + a);
    ap = ap.toLowerCase();
    String result = findProtein(a);


    if (ap.equals(result)) {
      System.out.println("Success for String: \"" + ap + "\", with length " + ap.length());
      System.out.println("Stop Codon found: \"" + stopCodon(a) + "\"");
    } else {
      System.err.println("Mistake for input: " + a);
      System.err.println("got: \"" + result + "\", not: " + ap);
    }
  }

  private void realTesting() {
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
    TagFinder03 tagFinder = new TagFinder03();
    tagFinder.testing();
//    tagFinder.realTesting();
  }
}
