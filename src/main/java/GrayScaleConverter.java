import edu.duke.DirectoryResource;
import edu.duke.ImageResource;
import edu.duke.Pixel;

import java.io.File;

public class GrayScaleConverter {
  private ImageResource grayScale(ImageResource inputImage) {
    ImageResource outputImage = new ImageResource(inputImage);
    for (Pixel pixel : outputImage.pixels() ) {
      Pixel inputImagePixel = inputImage.getPixel(pixel.getX(), pixel.getY());
      int average = (inputImagePixel.getRed() + inputImagePixel.getBlue() + inputImagePixel.getGreen()) / 3;
      pixel.setRed(average);
      pixel.setBlue(average);
      pixel.setGreen(average);
    }
    return outputImage;
  }

  private ImageResource renameImage(ImageResource resource) {
    String fileName = resource.getFileName();
    String newFileName = "gray-" + fileName;
    resource.setFileName(newFileName);
    return resource;
  }

  private void saveImage(ImageResource resource){
      resource.save();
  }

  private void drawImage(ImageResource resource) {
    resource.draw();
  }

  public static void main(String[] args) {
    GrayScaleConverter grayScaleConverter = new GrayScaleConverter();
    DirectoryResource directoryResource = new DirectoryResource();
    for (File file : directoryResource.selectedFiles()) {
      ImageResource inputImage = new ImageResource(file);
      ImageResource convertedImage = grayScaleConverter.grayScale(inputImage);
      ImageResource renamedImage = grayScaleConverter.renameImage(convertedImage);
      grayScaleConverter.drawImage(renamedImage);
      grayScaleConverter.saveImage(renamedImage);
    }
  }
}
