package week1;

import edu.duke.URLResource;

public class HelloWorld {
  public void runHello() {
//    FileResource resource = new FileResource("hello_unicode.txt");
    URLResource resource = new URLResource("http://nytimes.com");
    for (String line : resource.lines()) {
      System.out.println(line);
    }
  }

  public static void main(String[] args) {
    HelloWorld helloWorld = new HelloWorld();
    helloWorld.runHello();
  }
}
