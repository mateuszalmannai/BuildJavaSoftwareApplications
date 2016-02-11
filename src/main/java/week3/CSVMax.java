package week3;
/**
 * Find the highest (hottest) temperature in a file of CSV weather data.
 *
 * @author Duke Software Team
 */

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

public class CSVMax {

  private static final String TEMPERATURE = "TemperatureF";
  private static final String HUMIDITY = "Humidity";
  private static final String DATE_UTC = "DateUTC";

  protected CSVRecord hottestHourInFile(CSVParser parser) {
    //start with largestSoFar as nothing
    CSVRecord largestSoFar = null;
    //For each row (currentRow) in the CSV File
    for (CSVRecord currentRow : parser) {
      //If largestSoFar is nothing
      largestSoFar = getLargestOfTwo(largestSoFar, currentRow);
    }
    //The largestSoFar is the answer
    return largestSoFar;
  }

  protected CSVRecord coldestHourInFile(CSVParser parser) {
    CSVRecord lowestSoFar = null;
    for (CSVRecord currentRow : parser) {
      if (lowestSoFar == null) {
        lowestSoFar = currentRow;
      } else {

        double currentTemp = Double.parseDouble(currentRow.get(TEMPERATURE));
        if (currentTemp != -9999) {
          double lowestTemp = Double.parseDouble(lowestSoFar.get(TEMPERATURE));
          if (currentTemp < lowestTemp) {
            lowestSoFar = currentRow;
          }
        }
      }
    }
    return lowestSoFar;
  }

  protected CSVRecord lowestHumidityInFile(CSVParser parser) {
    CSVRecord lowestSoFar = null;
    for (CSVRecord currentRow : parser) {
      lowestSoFar = getLowestOfTwo(lowestSoFar, currentRow);
    }
    return lowestSoFar;
  }

  protected CSVRecord getLowestOfTwo(CSVRecord lowestSoFar, CSVRecord currentRow) {
    if (lowestSoFar == null) {
      lowestSoFar = currentRow;
    } else {
      String currentHumidityString = currentRow.get(HUMIDITY);
      if (!currentHumidityString.equals("N/A")) {
        int currentHumidity = Integer.parseInt(currentHumidityString);
        int lowestHumidity = Integer.parseInt(lowestSoFar.get(HUMIDITY));

        if (currentHumidity < lowestHumidity) {
          lowestSoFar = currentRow;
        }
      }
    }
    return lowestSoFar;
  }

  private void testLowestHumidityInFile() {
    FileResource fileResource = new FileResource("nc_weather/2014/weather-2014-07-22.csv");
    CSVRecord csv = lowestHumidityInFile(fileResource.getCSVParser());
    System.out.println("Lowest Humidity was " + csv.get(HUMIDITY) +
      " at " + csv.get(DATE_UTC));

  }


  protected String fileWithColdestTemperature() {
    CSVRecord lowestSoFar = null;
    String lowestFileSoFar = null;
    DirectoryResource directoryResource = new DirectoryResource();
    for (File file : directoryResource.selectedFiles()) {
      FileResource fileResource = new FileResource(file);
      CSVRecord currentRow = coldestHourInFile(fileResource.getCSVParser());
      if (lowestSoFar == null) {
        lowestSoFar = currentRow;
        lowestFileSoFar = file.getName();
      } else {
        double currentTemp = Double.parseDouble(currentRow.get(TEMPERATURE));
        double lowestTemp = Double.parseDouble(lowestSoFar.get(TEMPERATURE));
        if (currentTemp < lowestTemp) {
          lowestSoFar = currentRow;
          lowestFileSoFar = file.getName();
        }
      }
    }
    System.out.println(lowestSoFar.get(TEMPERATURE) + " " + lowestSoFar.get(DATE_UTC));

    return lowestFileSoFar;
  }

  protected double averageTemperatureInFile(CSVParser parser) {
    double sum = 0;
    int counter = 0;
    for (CSVRecord record : parser) {
      sum += Double.parseDouble(record.get(TEMPERATURE));
      counter++;
    }

    return sum/counter;
  }

  protected double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
    double sum = 0;
    int counter = 0;
    for (CSVRecord record : parser) {
      String humidity = record.get(HUMIDITY);
      if (!humidity.equals("N/A") && Integer.parseInt(humidity) >= value) {
        sum += Double.parseDouble(record.get(TEMPERATURE));
        counter++;
      }
    }
    return sum == 0 ? 0 : sum/counter;
  }

  private void testAverageTemperatureWithHighHumidityInfile(){
    FileResource fileResource = new FileResource("nc_weather/2013/weather-2013-09-02.csv");
    double averageTemperatureInFile = averageTemperatureWithHighHumidityInFile(fileResource.getCSVParser(), 80);
    if (averageTemperatureInFile == 0) {
      System.out.println("No temperatures with that humidity");
    } else {
      System.out.println("Average temperature in file is " + averageTemperatureInFile);
    }
  }


  private void testAverageTemperatureInfile() {
    FileResource fileResource = new FileResource("nc_weather/2013/weather-2013-08-10.csv");
    double averageTemperatureInFile = averageTemperatureInFile(fileResource.getCSVParser());
    System.out.println("Average temperature in file is " + averageTemperatureInFile);
  }

  protected void testFileWithColdestTemperature() {
    String fileWithColdestTemperature = null;
    fileWithColdestTemperature = fileWithColdestTemperature();
    FileResource fileResource = new FileResource("nc_weather/2014/" + fileWithColdestTemperature);
    CSVRecord csvRecord = coldestHourInFile(fileResource.getCSVParser());
    System.out.println("Coldest day was in file " + fileWithColdestTemperature);
    System.out.println("Coldest temperature on that day was " + csvRecord.get(TEMPERATURE));
    System.out.println("All the Temperatures on the coldest day were:");
    for (CSVRecord record : fileResource.getCSVParser()) {
      System.out.println(record.get(DATE_UTC) + ": " + record.get(TEMPERATURE));
    }
  }

  private void testManyFilesForColdestTemperature(){
    String s = fileWithColdestTemperature();
    System.out.println(s);
  }

  private void testColdestHourInFile() {
    FileResource fileResource = new FileResource("nc_weather/2014/weather-2014-05-01.csv");
    CSVRecord lowest = coldestHourInFile(fileResource.getCSVParser());
    System.out.println("Lowest temperature was " + lowest.get(TEMPERATURE) +
      " at " + lowest.get(DATE_UTC));
  }

  private void testHottestInDay() {
    FileResource fr = new FileResource("data/2015/weather-2015-01-02.csv");
    CSVRecord largest = hottestHourInFile(fr.getCSVParser());
    System.out.println("hottest temperature was " + largest.get(TEMPERATURE) +
      " at " + largest.get("TimeEST"));
  }

  protected CSVRecord lowestHumidityInManyFiles() {
    CSVRecord lowestSoFar = null;
    DirectoryResource directoryResource = new DirectoryResource();
    // iterate over files
    for (File file : directoryResource.selectedFiles()) {
      FileResource fileResource = new FileResource(file);
      // use method to get largest in file
      CSVRecord currentRow = lowestHumidityInFile(fileResource.getCSVParser());
      lowestSoFar = getLowestOfTwo(lowestSoFar, currentRow);
    }
    return lowestSoFar;
  }

  private void testLowestHumidityInManyFiles() {
    CSVRecord lowestHumidityInManyFiles = lowestHumidityInManyFiles();
    System.out.println("Lowest Humidity was " + lowestHumidityInManyFiles.get(HUMIDITY) +
      " at " + lowestHumidityInManyFiles.get(DATE_UTC));
  }

  protected CSVRecord hottestInManyDays() {
    CSVRecord largestSoFar = null;
    DirectoryResource directoryResource = new DirectoryResource();
    // iterate over files
    for (File file : directoryResource.selectedFiles()) {
      FileResource fileResource = new FileResource(file);
      // use method to get largest in file
      CSVRecord currentRow = hottestHourInFile(fileResource.getCSVParser());
      largestSoFar = getLargestOfTwo(largestSoFar, currentRow);
    }
    return largestSoFar;
  }

  private CSVRecord getLargestOfTwo(CSVRecord largestSoFar, CSVRecord currentRow) {
    if (largestSoFar == null) {
      largestSoFar = currentRow;
    } else {
      //Otherwise
      double currentTemp = Double.parseDouble(currentRow.get(TEMPERATURE));
      double largestTemp = Double.parseDouble(largestSoFar.get(TEMPERATURE));
      //Check if currentRow’s temperature > largestSoFar’s
      if (currentTemp > largestTemp) {
        //If so update largestSoFar to currentRow
        largestSoFar = currentRow;
      }
    }
    return largestSoFar;
  }

  private void testHottestInManyDays() {
    CSVRecord largest = hottestInManyDays();
    System.out.println("Hottest temperature was " + largest.get(TEMPERATURE) + " at " + largest.get(DATE_UTC));
  }


  public static void main(String[] args) {
    CSVMax csvMax = new CSVMax();
//    csvMax.testHottestInDay();
//    csvMax.testHottestInManyDays();
//    csvMax.testColdestHourInFile();
//    csvMax.testFileWithColdestTemperature();
    csvMax.testManyFilesForColdestTemperature();
//    csvMax.testLowestHumidityInFile();
//    csvMax.testLowestHumidityInManyFiles();
//    csvMax.testAverageTemperatureInfile();
//    csvMax.testAverageTemperatureWithHighHumidityInfile();
  }
}
