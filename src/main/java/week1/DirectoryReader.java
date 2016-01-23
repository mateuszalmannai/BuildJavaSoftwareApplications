package week1;

import edu.duke.DirectoryResource;

import java.io.File;

public class DirectoryReader {
  public void checkDirectory(){
    DirectoryResource directoryResource = new DirectoryResource();
    for (File file : directoryResource.selectedFiles() ) {
      System.out.println(file);
    }
  }

  public static void main(String[] args) {
    DirectoryReader directoryReader = new DirectoryReader();
    directoryReader.checkDirectory();
  }
}
