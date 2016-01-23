package week2;

import edu.duke.FileResource;
import edu.duke.StorageResource;

public class FindAllGenes {

  private StorageResource mStorageResource = new StorageResource();

  public int findStopIndex(final String dna, final int index) {

    final int stop1 = getStop(dna, "tga", index);
    final int stop2 = getStop(dna, "taa", index);
    final int stop3 = getStop(dna, "tag", index);

    return Math.min(stop1, Math.min(stop2, stop3));
  }

  private int getStop(final String dna, final String codon, final int index) {
    int stop = dna.indexOf(codon, index);
    if (stop == -1 || (stop - index) % 3 != 0) {
      stop = dna.length();
    }
    return stop;
  }

  public void printAll(String dna) {

    System.out.println("DNA string is: " + dna);
    int start = 0;
    while (true) {
      int loc = dna.toLowerCase().indexOf("atg", start);
      if (loc == -1) {
        break;
      }

      int end = findStopIndex(dna.toLowerCase(), loc + 3);

      if (end != dna.length()) {
        String string = dna.substring(loc, end + 3);
        System.out.println("Gene found: " + string);
        System.out.println("Starting at " + loc + " and ending at " + end + "\n");
      }
      start = loc + 3;
    }
  }

  private StorageResource storeAll(String dna) {

    StorageResource storageResource = new StorageResource();

    int start = 0;
    while (true) {
      int loc = dna.toLowerCase().indexOf("atg", start);
      if (loc == -1) {
        break;
      }

      int end = findStopIndex(dna.toLowerCase(), loc + 3);

      if (end != dna.length()) {
        String string = dna.substring(loc, end + 3);
        storageResource.add(string);
      }
      start = loc + 3;
    }
    return storageResource;
  }


  public static float cgRatio(String dna) {

    int countCG = 0;

    for (int i = 0; i < dna.length(); i++) {
      if (dna.toLowerCase().charAt(i) == 'c' || dna.toLowerCase().charAt(i) == 'g') {
        countCG++;
      }
    }

    return ((float) countCG / dna.length());
  }

  private void testStorageFinder() {
    FileResource dnaFile = new FileResource("GRch38dnapart.fa");
//    StorageResource storageResource = storeAll(fileResource.asString());
//    StorageResource storageResource = storeAll("CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA");
//    System.out.println(storageResource.size());
//    printGenes(storageResource);


    System.out.println("Original DNA = " + dnaFile.asString());

    findStoreAllGenesFromDna(dnaFile.asString());
    printCodonCTGCount(dnaFile.asString());
    printAllGenes();
  }

  private void printAllGenes() {
    System.out.println("Total Number of Genes = " + mStorageResource.size());

    int noGenesLenghtLongerThan60Nucleotides = 0;
    for (String gene : mStorageResource.data()) {
      if (gene.length() > 60) {
        //System.out.println(gene);
        noGenesLenghtLongerThan60Nucleotides++;
      }
    }
    System.out.println("Total number of genes onger than 60 nucleotides =  " + noGenesLenghtLongerThan60Nucleotides);
    int noGenesCGRatioGreaterThan35On100 = 0;

    for (String gene : mStorageResource.data()) {
      if (cgRatio(gene) > 0.35) {
        noGenesCGRatioGreaterThan35On100++;
        //System.out.println(gene);
      }
    }
    System.out.println("Total genes whose C-G-ratio is higher than 0.35 = " + noGenesCGRatioGreaterThan35On100);

    int longestLength = 0;
    for (String gene : mStorageResource.data()) {
      if (gene.length() > longestLength) {
        longestLength = gene.length();
      }
    }
    System.out.println("Length of longest gene is: " + longestLength);
  }

  private void printCodonCTGCount(String dna) {
    dna = dna.toLowerCase();
    System.out.println("Total Length of dna strand = " + dna.length());
    System.out.println("Original dna strand = " + dna);

    System.out.println(dna.replaceAll("ctg", ""));
    int codonCTGCount = dna.length() - dna.replaceAll("ctg", "").length();
    System.out.println("CTG Codon Count: " + codonCTGCount / 3);
  }

  private void findStoreAllGenesFromDna(String dna) {
    System.out.println("Original DNA = " + dna);
    String originalDna = dna;
    dna = dna.toLowerCase();
    int start = 0;
    while (true) {
      int tag = dna.indexOf("atg", start);
      if (tag == -1) {
        break;
      }
      int end = findStopIndex(dna, tag + 3);
      if (end != dna.length()) {
        mStorageResource.add(originalDna.substring(tag, end + 3));
        start = end + 3;
      } else {
        start = start + 3;
      }
    }
  }


  private void testFinder() {


    printAll("ATGAAATGAAAA");

    System.out.println("\n");

    printAll("ccatgccctaataaatgtctgtaatgtaga");

    System.out.println("\n");

    printAll("CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA")
    ;
  }

  private void printGenes(StorageResource storageResource) {
    int lengthCounter = 0;
    for (String gene : storageResource.data()) {
      if (gene.length() > 60) {
        System.out.println(gene.length() + "\t" + gene);
        lengthCounter++;
      }
    }
    System.out.println();
    System.out.println("Number of Strings longer than 60 characters: " + lengthCounter);

    System.out.println();
    System.out.println("Strings with C-G-ratio higher than 0.35:");
    int cgRatioCounter = 0;
    for (String gene : storageResource.data()) {
      double ratio = cgRatio(gene);
      if (ratio > 0.35) {
        System.out.println(gene);
        cgRatioCounter++;
      }
    }
    System.out.println();
    System.out.println("Number of Strings with C-G-Ratio greater than 0.35: " + cgRatioCounter);

  }

  public static void main(String[] args) {
    FindAllGenes findAllGenes = new FindAllGenes();
//    findAllGenes.testFinder();
//    findAllGenes.testStorageFinder();
//    double ratio = findAllGenes.cgRatio("ATGCCATAG");
//    System.out.println(ratio);
    findAllGenes.testStorageFinder();
  }

}
