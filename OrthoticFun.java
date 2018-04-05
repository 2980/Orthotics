/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orthotic.fun;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author unouser
 */
public class OrthoticFun {

    /**
     * @param args the command line arguments
     */
    
    static int width;
    static int height;
    static int[] pixels;
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        BufferedImage original = ImageIO.read(new File("Hands.png"));
        
        
        pixels = new int[original.getWidth() * original.getHeight()];
        width = original.getWidth();
        height = original.getHeight();
        original.getRGB(0, 0, original.getWidth(), original.getHeight(), pixels, 0, original.getWidth());
        
        Rectangle tapeGuess = findTape(pixels);
        
        
        
        
        BufferedImage output =  new BufferedImage(original.getWidth() + 20, original.getHeight() + 20, BufferedImage.TYPE_4BYTE_ABGR);
        
        int[] horizontalArray = new int[original.getWidth()];
        int[] verticalArray = new int[original.getHeight()];
        
        getHistograms(horizontalArray, verticalArray, pixels);
        
        int maxHorixontalArrayValue = Arrays.stream(horizontalArray).max().getAsInt();
        int maxVerticalArrayValue = Arrays.stream(verticalArray).max().getAsInt();
        
        
        Graphics2D g = (Graphics2D)output.getGraphics();
        
        g.drawImage(original, 20, 20, null);
        
        for(int i = 0; i < horizontalArray.length; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(20 + i, 0, 20+i, 20);
            g.setColor(Color.WHITE);
            int pixelLength = (int)(horizontalArray[i] / (float)maxHorixontalArrayValue*20);
            g.drawLine(20 + i, 0 + 20 - pixelLength, 20+i, 20);
            
        }
        
        for(int i = 0; i < verticalArray.length; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(0, 20 + i, 20, 20+i);
            g.setColor(Color.WHITE);
            int pixelLength = (int)(verticalArray[i] / (float) maxVerticalArrayValue*20);
            g.drawLine(20 - pixelLength, 20+i,  20, 20+i);
            
        }
        
        
        g.setColor(Color.BLACK);
        g.draw(tapeGuess);
        
        g.dispose();
        
        ImageIO.write(output, "PNG", new File("out.png"));
        
        
        
        
        
        
        
        
    }

    private static Rectangle findTape(int[] pixels) {
        
        
        
        
        
        Rectangle toReturn = new Rectangle(100,100, 50, 50);
        
        return toReturn;
        
        
    }

    private static void getHistograms(int[] horizontalArray, int[] verticalArray, int[] pixels) {
        
        for(int i = 0; i < horizontalArray.length; i++)
        {
            int sum = 0;
            
            for(int y = 0; y < height; y++ )
            {
                int pixel = getPixel(i, y);
                Color c = new Color(pixel);
                
                if( isDarkerThanBackground(c) )
                {
                    sum++;
                }
                       
            }
            
            
           horizontalArray[i] = sum;
            
        }
        
        for(int i = 0; i < verticalArray.length; i++)
        {
            
            int sum = 0;
            
            for(int x = 0; x < width; x++ )
            {
                int pixel = getPixel(i, x);
                Color c = new Color(pixel);
                
                if( isDarkerThanBackground(c) )
                {
                    sum++;
                }
                       
            }
            
            
           horizontalArray[i] = sum;
        }
        
    }

    private static int getPixel(int x, int y) {
        return pixels[y * width + x];
    }

    private static boolean isDarkerThanBackground(Color c) {
       
        int threshold = 100;
        
        return c.getRed() < threshold && c.getGreen() < threshold && c.getBlue() < threshold;
        
        
    }
    
}
