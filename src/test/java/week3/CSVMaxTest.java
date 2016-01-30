package week3;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class CSVMaxTest {

  private CSVMax csvMax;
  private FileResource fileResource;

  @Before
  public void setUp() throws Exception {
    csvMax = new CSVMax();

  }

  @Test
  public void testHottestHourInFile() throws Exception {

  }

  @Test
  public void testColdestHourInFile() throws Exception {

  }

  @Test
  public void testLowestHumidityInFile() throws Exception {
    fileResource = new FileResource("nc_weather/2014/weather-2014-01-20.csv");

    CSVRecord record = csvMax.lowestHumidityInFile(fileResource.getCSVParser());

    assertThat(record.get("Humidity"), is("24"));
    assertThat(record.get("DateUTC"), is("2014-01-20 19:51:00"));
  }

  @Test
  public void testLowestHumidityInManyFiles() throws Exception {
    CSVRecord record = csvMax.lowestHumidityInManyFiles();
    assertThat(record.get("Humidity"), is("25"));

  }

  @Test
  public void testGetLowestOfTwo() throws Exception {

  }

  @Test
  public void testFileWithColdestTemperature() throws Exception {

  }

  @Test
  public void testAverageTemperatureInFile() throws Exception {

  }

  @Test
  public void testTestFileWithColdestTemperature() throws Exception {

  }

  @Test
  public void testHottestInManyDays() throws Exception {

  }
}