package week1;

import edu.duke.DirectoryResource;
import edu.duke.ImageResource;
import edu.duke.Pixel;

import java.io.File;

public class ColorInverter {

  private static final int RGB_MAX = 255;

  private ImageResource invertColors(ImageResource inputImage) {
    ImageResource outputImage = new ImageResource(inputImage);
    for (Pixel outputPixel : outputImage.pixels()) {
      Pixel inputPixel = inputImage.getPixel(outputPixel.getX(), outputPixel.getY());
      outputPixel.setRed(RGB_MAX - inputPixel.getRed());
      outputPixel.setGreen(RGB_MAX - inputPixel.getGreen());
      outputPixel.setBlue(RGB_MAX - inputPixel.getBlue());
    }
    return outputImage;
  }

  private ImageResource renameImage(ImageResource resource) {
    String fileName = resource.getFileName();
    String newFileName = "inverted-" + fileName;
    resource.setFileName(newFileName);
    return resource;
  }

  private void saveImage(ImageResource resource) {
    resource.save();
  }

  private void drawImage(ImageResource resource) {
    resource.draw();
  }

  public static void main(String[] args) {
    ColorInverter colorInverter = new ColorInverter();
    DirectoryResource directoryResource = new DirectoryResource();

    for (File file : directoryResource.selectedFiles()) {
      System.out.println(file);
      ImageResource inputImage = new ImageResource(file);
      ImageResource colorInvertedImage = colorInverter.invertColors(inputImage);
      ImageResource renamedImage = colorInverter.renameImage(colorInvertedImage);
      colorInverter.drawImage(renamedImage);
      colorInverter.saveImage(renamedImage);
    }
  }
}
