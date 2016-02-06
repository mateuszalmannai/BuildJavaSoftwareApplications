package week4;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

public class BabyBirths {

  private static final String FEMALE = "F";
  private static final String MALE = "M";

  private void printNames() {
    String fileName = "testing/example-small.csv";
    FileResource fileResource = new FileResource(fileName);
    for (CSVRecord record : fileResource.getCSVParser(false)) {
      int numBorn = Integer.parseInt(record.get(2));
      if (numBorn <= 100) {
        System.out.println("Name: " + record.get(0) +
          "\t\tGender: " + record.get(1) +
          "\tNum Born: " + record.get(2));

      }
    }
  }

  private void totalBirths(FileResource fileResource) {
    int totalBirths = 0;
    int uniqueBoyNames = 0;
    int uniqueGirlNames = 0;
    int totalBoys = 0;
    int totalGirls = 0;
    for (CSVRecord record : fileResource.getCSVParser(false)) {
      int numBorn = Integer.parseInt(record.get(2));
      totalBirths += numBorn;
      if (record.get(1).equals(MALE)) {
        totalBoys += numBorn;
        uniqueBoyNames++;
      } else {
        totalGirls += numBorn;
        uniqueGirlNames++;
      }
    }
    System.out.println("Total births: " + totalBirths);
    System.out.println("Total girls: " + totalGirls);
    System.out.println("Total boys: " + totalBoys);
    System.out.println("Number of unique boy names: " + uniqueBoyNames);
    System.out.println("Number of unique girl names: " + uniqueGirlNames);
  }

  /**
   * Get a parser with no header row
   * Because there's no header row, we access the data by indexing
   */
  private void readOneFile(int year) {
    String fileName = "data/yob" + year + ".txt";
    FileResource fileResource = new FileResource(fileName);
    CSVParser csvParser = fileResource.getCSVParser(false);
    for (CSVRecord record : csvParser) {
      String name = record.get(0);
      String gender = record.get(1);
    }
  }

  private void testTotalBirths() {
    FileResource fileResource = new FileResource("testing/yob2012short.csv");
//    totalBirths(fileResource);
    totalBirths(new FileResource());
  }


  /**
   * Method to return the rank of the name in the file for the given gender
   * @param year
   * @param name
   * @param gender
   * @return
   */
  private int getRank(int year, String name, String gender) {
    String fileName = "testing/yob" + year + ".csv";
    FileResource fileResource = new FileResource(fileName);
    CSVParser csvParser = fileResource.getCSVParser(false);
    int counter = 0;
    for (CSVRecord record : csvParser) {
      if (record.get(1).equals(gender)) {
        counter++;
        if (record.get(0).equals(name)) {
          return counter;
        }
      }
    }
    return -1;
  }

  private void testGetRank() {
    String name = "Frank";
    String gender = MALE;
    int rank = getRank(2012, name, gender);
    System.out.println("The rank for " + name + "with gender " + gender + " is " + rank);
  }


  /**
   * Method returns the name of the person in the file at this rank
   * @param year
   * @param rank
   * @param gender
   * @return
   */
  private String getName(int year, int rank, String gender) {
    String name = "NO NAME";
    String fileName = "testing/yob" + year + ".csv";
    FileResource fileResource = new FileResource(fileName);
    CSVParser csvParser = fileResource.getCSVParser(false);
    int counter = 0;
    for (CSVRecord record : csvParser) {
      if (record.get(1).equals(gender)) {
        counter++;
        if (counter == rank) {
          name = record.get(0);
        }
      }
    }
    return name;
  }

  private void testGetName() {
    int year = 2012;
    int rank = 450;
    String gender = MALE;
    String name = getName(year, rank, gender);
    System.out.println("Name of person at rank " + rank + " with gender " + gender +
    " in " + year + " is " + name);
  }

  private void whatIsNameInYear(String name, int year, int newYear, String gender) {
    int rank = getRank(year, name, gender);
    String newName = getName(newYear, rank, gender);
    System.out.println(name + " born in " + year + " would be " + newName + " if she were born in " + newYear);
  }

  /**
   * Method selects a range of files to process and returns an integer, the year
   * with the highest rank for the name and gender.
   * @param name
   * @param gender
   * @return
   */
  private int yearOfHighestRank(String name, String gender) {
    int yearWithHighestRankSoFar = 0;
    int highestRankSoFar = 0;
    DirectoryResource directoryResource = new DirectoryResource();
    for (File file : directoryResource.selectedFiles()) {

      int currentYear = Integer.parseInt(file.getName().substring(3, 7));
      int rank = getRank(currentYear, name, gender);
      if (rank == -1) {
        return -1;
      }
      if (highestRankSoFar == 0) {
        highestRankSoFar = rank;
      }

      if (rank <= highestRankSoFar) {
        yearWithHighestRankSoFar = currentYear;
      }
    }
    return yearWithHighestRankSoFar;
  }


  /**
   * Selects a range of files to process and returns a double representing the
   * average rank of the name and gender over the selected files.
   * @param name
   * @param gender
   * @return average
   */
  private double getAverageRank(String name, String gender){
    double sum = 0;
    int counter = 0;
    DirectoryResource directoryResource = new DirectoryResource();
    for (File file : directoryResource.selectedFiles()) {
      int year = Integer.parseInt(file.getName().substring(3, 7));
      int rank = getRank(year, name, gender);
      sum += rank;
      counter++;
    }
    return sum == 0 ? 0 : sum/counter;
  }

  /**
   * Method to return the total number of births of those names with the same gender
   * and same year who are ranked higher than name.
   * @param year
   * @param name
   * @param gender
   * @return
   */
  private int getTotalBirthsRankedHigher(int year, String name, String gender) {
    int result = 0;
    int rank = getRank(year, name, gender);
    String fileName = "testing/yob" + year + ".csv";
    FileResource fileResource = new FileResource(fileName);
    CSVParser csvParser = fileResource.getCSVParser(false);
    for (CSVRecord record : csvParser) {
      String recordName = record.get(0);
      String recordGender = record.get(1);
      int recordRank = getRank(year, recordName, recordGender);
      if (recordGender.equals(gender) && recordRank < rank) {
        result += Integer.parseInt(record.get(2));
      }
    }

    return result;
  }

  public static void main(String[] args) {
    BabyBirths babyBirths = new BabyBirths();
//    babyBirths.printNames();
//    System.out.println();
//    babyBirths.testTotalBirths();
//    babyBirths.testGetRank();

//    babyBirths.testGetName();

//    babyBirths.whatIsNameInYear("Owen", 1974, 2014, MALE);

//    int yearOfHighestRank = babyBirths.yearOfHighestRank("Mich", MALE);
//    System.out.println(yearOfHighestRank);

//    double averageRank = babyBirths.getAverageRank("Robert", MALE);
//    System.out.println(averageRank);

    int totalBirthsRankedHigher = babyBirths.getTotalBirthsRankedHigher(1990, "Drew", MALE);
    System.out.println(totalBirthsRankedHigher);

  }

}
