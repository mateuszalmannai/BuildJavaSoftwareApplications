package week2;

import edu.duke.URLResource;

public class URLFinderOld {

  public void findURLs(String url, String searchString) {
    URLResource resource = new URLResource(url);
    for (String line : resource.lines()) {
      if (line.toLowerCase().contains(searchString)) {
        int index = line.toLowerCase().indexOf("youtube.com");
//        System.out.println(line.lastIndexOf("\"", index));
        int beginIndex = line.lastIndexOf("\"", index);
//        System.out.println(index);
//        System.out.println(line.indexOf("\"", index));
        int endIndex = line.indexOf("\"", index) + 1;
//        System.out.println(line);
        System.out.println(line.substring(beginIndex, endIndex));
      }
    }
  }


  public void findURLsAnotherWay(String url){
    URLResource file = new URLResource(url);
    for (String item : file.words()) {
      String itemLower = item.toLowerCase();
      int pos = itemLower.indexOf("youtube.com");
      if (pos != -1) {
        int beg = item.lastIndexOf("\"", pos);
        int end = item.indexOf("\"", pos + 1);
        System.out.println(item.substring(beg + 1, end));
      }
    }
  }

  public static void main(String[] args) {
    URLFinderOld urlFinder = new URLFinderOld();
    urlFinder.findURLs("http://www.dukelearntoprogram.com/course2/data/manylinks.html", "youtube.com");
    urlFinder.findURLsAnotherWay("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
  }
}

