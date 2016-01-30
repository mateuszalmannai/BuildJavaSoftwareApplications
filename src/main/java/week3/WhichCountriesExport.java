package week3;
/**
 * Reads a chosen CSV file of country exports and prints each country that exports coffee.
 *
 * @author Duke Software Team
 */

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class WhichCountriesExport {

  private static final String COUNTRY = "Country";
  private static final String VALUE = "Value (dollars)";
  private static final String EXPORTS = "Exports";

  private void listExporters(CSVParser parser, String exportOfInterest) {
    //for each row in the CSV File
    for (CSVRecord record : parser) {
      //Look at the "Exports" column
      //Check if it contains exportOfInterest: record.indexOf(exportOfInterest) != -1
      if (record.get(EXPORTS).contains(exportOfInterest)) {
        //If so, write down the "Country" from that row
        System.out.println(record.get(COUNTRY));
      }
    }
  }

  private void whoExportsCoffee() {
    FileResource fr = new FileResource("exportsmall.csv");
    CSVParser parser = fr.getCSVParser();
    listExporters(parser, "coffee");
  }


  private String countryInfo(CSVParser parser, String country) {
    String result = "NOT FOUND";
    for (CSVRecord record : parser) {
      if (record.get(COUNTRY).equals(country)) {
        result = record.get(COUNTRY) + ": " + record.get(EXPORTS) + ": " + record.get(VALUE);
      }
    }
    return result;
  }

  /**
   * Method that prints the names of all the countries that have both
   * exportItem1 and exportItem2 as export items
   */
  private void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
    System.out.println("\nCountries exporting both " + exportItem1 + " and " + exportItem2);
    for (CSVRecord record : parser) {
      String exportsColumn = record.get(EXPORTS);
      if (exportsColumn.contains(exportItem1) && exportsColumn.contains(exportItem2)) {
        System.out.println(record.get(COUNTRY));
      }
    }
    System.out.println();
  }

  /**
   * Method to return number of countries that export exportItem
   */
  private int numberOfExporters(CSVParser parser, String exportItem) {
    int result = 0;
    for (CSVRecord record : parser) {
      if (record.get(EXPORTS).contains(exportItem)) {
        result++;
      }
    }
    return result;
  }


  /**
   * Method to print names of countries and their Value amount for all
   * countries whose Value (dollars) string is longer than the amount string
   */
  private void bigExporters(CSVParser parser, String amount) {
    for (CSVRecord record : parser) {
      if (record.get(VALUE).length() > amount.length()) {
        System.out.println(record.get(COUNTRY) + " " + record.get(VALUE));
      }
    }
  }

  /**
   * Method to create CSVParser and call other methods
   */
  private void tester() {
//    FileResource fileResource = new FileResource("exports_small.csv");
    FileResource fileResource = new FileResource("exportdata.csv");
    CSVParser parser = fileResource.getCSVParser();
    String info = countryInfo(parser, "Nauru");
    System.out.println(info);

    parser = fileResource.getCSVParser();
    listExportersTwoProducts(parser, "cotton", "flowers");

    System.out.println("");
    parser = fileResource.getCSVParser();
    int numberOfExporters = numberOfExporters(parser, "cocoa");
    System.out.println("\nNumber of countries that export cocoa: " + numberOfExporters);
    System.out.println("***********************************************************");
    parser = fileResource.getCSVParser();
    bigExporters(parser, "$999,999,999,999");


  }

  public static void main(String[] args) {
//    new WhichCountriesExport().whoExportsCoffee();
    new WhichCountriesExport().tester();
  }
}
