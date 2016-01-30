package week3;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class FirstCSVExample {
  public void readFood(){
    FileResource fileResource = new FileResource("foods.csv");
    CSVParser parser = fileResource.getCSVParser();
    for (CSVRecord record : parser) {
      System.out.print(record.get("Name") + ": ");
      System.out.print(record.get("Favorite Color") + " ");
      System.out.println(record.get("Favorite Food"));
    }
  }

  public static void main(String[] args) {
    FirstCSVExample example = new FirstCSVExample();
    example.readFood();
  }
}
