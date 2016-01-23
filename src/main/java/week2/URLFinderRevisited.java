package week2;

import edu.duke.StorageResource;
import edu.duke.URLResource;

public class URLFinderRevisited {

  public StorageResource findURLs(String url) {
    URLResource page = new URLResource(url);
    String source = page.asString();
    StorageResource store = new StorageResource();
    int start = 0;

    while (true) {
      int index = source.indexOf("href=", start);
      if (index == -1) {
        break;
      }
      int firstQuote = index+6; // after href="
      int endQuote = source.indexOf("\"", firstQuote);
      String sub = source.substring(firstQuote, endQuote);
      if (sub.startsWith("http")) {
        store.add(sub);
      }
      start = endQuote + 1;
    }
    return store;
  }

  public static void main(String[] args) {
    URLFinderRevisited urlFinder = new URLFinderRevisited();
//    StorageResource storageResource = urlFinder.findURLs("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
    StorageResource storageResource = urlFinder.findURLs("http://www.dukelearntoprogram.com/course2/data/newyorktimes.html");
    int num_secure = 0;
    int num_dot_com = 0;
    int num_end_com = 0;
    int num_dots = 0;

    for (String link : storageResource.data()) {
      if (link.indexOf("https") != -1) {
        num_secure += 1;
      }
      if (link.indexOf(".com") != -1) {
        num_dot_com += 1;
      }
      if (link.endsWith(".com") || link.endsWith(".com/")) {
        num_end_com += 1;
      }

      num_dots += link.length() - link.replace(".", "").length();
    }
    System.out.println("num urls: " + storageResource.size());
    System.out.println("num secure: " + num_secure);
    System.out.println("num .com: " + num_dot_com);
    System.out.println("num end .com/: " + num_end_com);
    System.out.println("num dots: " + num_dots);
  }
}

