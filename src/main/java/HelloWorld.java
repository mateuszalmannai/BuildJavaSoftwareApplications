import resourceClasses.FileResource;

public class HelloWorld {
  public void runHello() {
    FileResource hello = new FileResource("hello_unicode.txt");
    for (String line : hello.lines()) {
      System.out.println(line);
    }
  }

  public static void main(String[] args) {
    HelloWorld helloWorld = new HelloWorld();
    helloWorld.runHello();
  }
}
