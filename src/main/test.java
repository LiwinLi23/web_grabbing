package main;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import my.function.myhttp;

public class test {
	
	
	private static int colorToRGB(int alpha, int red, int green, int blue) {
		  
		  int newPixel = 0;
		  newPixel += alpha;
		  newPixel = newPixel << 8;
		  newPixel += red;
		  newPixel = newPixel << 8;
		  newPixel += green;
		  newPixel = newPixel << 8;
		  newPixel += blue;
		  
		  return newPixel;
		  
		}
	
	public static void main(String[] args) throws Exception 
	 {
		
		
		System.out.println(myhttp.Get("http://i.youku.com/u/UMzM3NTIyNjg0MA==?spm=a2h0k.11417342.soresults.dtitle"));
		
		if (true) return;
		
		//搜索百度页面
		/*File file = new File("D:\\aaa.jpg");
		 BufferedImage image = ImageIO.read(file);
		   
		 int width = image.getWidth(); 
		 int height = image.getHeight(); 
		   
		 BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY); 
		 for(int i= 0 ; i < width ; i++){ 
		  for(int j = 0 ; j < height; j++){ 
		  int rgb = image.getRGB(i, j); 
		  grayImage.setRGB(i, j, rgb); 
		  } 
		 } 
		   
		 File newFile = new File("D:\\bbb.jpg"); 
		 ImageIO.write(grayImage, "jpg", newFile); */
		
		
		BufferedImage bufferedImage 
		  = ImageIO.read(new File("D:\\aaa.jpg"));
		 BufferedImage grayImage = 
		  new BufferedImage(bufferedImage.getWidth(), 
		       bufferedImage.getHeight(), 
		       bufferedImage.getType());
		    
		   
		 for (int i = 0; i < bufferedImage.getWidth(); i++) {
		  for (int j = 0; j < bufferedImage.getHeight(); j++) {
		   final int color = bufferedImage.getRGB(i, j);
		   final int r = (color >> 16) & 0xff;
		   final int g = (color >> 8) & 0xff;
		   final int b = color & 0xff;
		   int gray = (int) (1.0 * r + 1 * g + 0.00 * b);;
		   System.out.println(i + " : " + j + " " + gray);
		   int newPixel = colorToRGB(255, gray, gray, gray);
		   grayImage.setRGB(i, j, newPixel);
		  }
		 }
		 File newFile = new File("D:\\ccc.jpg");
		 ImageIO.write(grayImage, "jpg", newFile);

	 }

}
