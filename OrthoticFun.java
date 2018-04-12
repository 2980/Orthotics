/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orthoticsfun;

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
    static ExtendedPixel[][] extendedPixels;
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        BufferedImage original = ImageIO.read(new File("Hands.png"));
        
        
        pixels = new int[original.getWidth() * original.getHeight()];
        width = original.getWidth();
        height = original.getHeight();
        original.getRGB(0, 0, original.getWidth(), original.getHeight(), pixels, 0, original.getWidth());
        
        
        extendedPixels = new ExtendedPixel[width][height];
        
        for(int y = 0; y < height; y++){
            for(int x = 0;x < width; x++){
                extendedPixels[x][y] = new ExtendedPixel(getPixel(x, y));
            }
        }
        
        if(false)
        for(int i = 0; i < 12; i++){
            BufferedImage toSave = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            for(int y = 0; y < height; y++){
                for(int x = 0;x < width; x++){
                    int channel = extendedPixels[x][y].getChannel(i);
                    Color c = new Color(channel, channel, channel);
                    toSave.setRGB(x, y, c.getRGB());
                }
        }
            ImageIO.write(toSave, "PNG", new File("" + i + ".png"));
        }
        
        Rectangle tapeGuess = findHand();
        
        
        
        
        BufferedImage output =  new BufferedImage(original.getWidth() + MARGIN_SIZE, original.getHeight() + MARGIN_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
        
        int[] horizontalArray = new int[original.getWidth()];
        int[] verticalArray = new int[original.getHeight()];
        
        getHistograms(horizontalArray, verticalArray, pixels);
        
        int maxHorixontalArrayValue = Arrays.stream(horizontalArray).max().getAsInt();
        int maxVerticalArrayValue = Arrays.stream(verticalArray).max().getAsInt();
        
        
        Graphics2D g = (Graphics2D)output.getGraphics();
        
        g.drawImage(original, MARGIN_SIZE, MARGIN_SIZE, null);
        
        for(int i = 0; i < horizontalArray.length; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(MARGIN_SIZE + i, 0, MARGIN_SIZE+i, MARGIN_SIZE);
            g.setColor(Color.WHITE);
            int pixelLength = (int)(horizontalArray[i] / (float)maxHorixontalArrayValue*MARGIN_SIZE);
            g.drawLine(MARGIN_SIZE + i, 0 + MARGIN_SIZE - pixelLength, MARGIN_SIZE+i, MARGIN_SIZE);
            
        }
        
        for(int i = 0; i < verticalArray.length; i++)
        {
            g.setColor(Color.BLACK);
            g.drawLine(0, MARGIN_SIZE + i, MARGIN_SIZE, MARGIN_SIZE+i);
            g.setColor(Color.WHITE);
            int pixelLength = (int)(verticalArray[i] / (float) maxVerticalArrayValue*MARGIN_SIZE);
            g.drawLine(MARGIN_SIZE - pixelLength, MARGIN_SIZE+i, MARGIN_SIZE, MARGIN_SIZE+i);
            
        }
        
        g.setColor(new Color(0, 0, 255, 128));
        for(int y = 0; y < height; y++){
                for(int x = 0;x < width; x++){
         
                    if(isSkinColor(x, y))
                    {
                        g.fillRect(x + MARGIN_SIZE, y + MARGIN_SIZE, 1, 1);
                    }
                }
        }
        
        
        
        g.setColor(Color.BLACK);
        g.draw(tapeGuess);
        
        g.dispose();
        
        ImageIO.write(output, "PNG", new File("out.png"));
        
        
        
        
        
        
        
        
    }
    private static final int MARGIN_SIZE = 50;

    private static Rectangle findHand() {
        Rectangle toReturn = new Rectangle(100,100, 50, 50);
        
        return toReturn;
    }

    private static void getHistograms(int[] horizontalArray, int[] verticalArray, int[] pixels) {
        
        for(int i = 0; i < horizontalArray.length; i++)
        {
            int sum = 0;
            
            for(int y = 0; y < height; y++ )
            {
                
                
                if( isSkinColor(i,y) )
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
                
                if( isSkinColor(x,i) )
                {
                    sum++;
                }
                       
            }
            
            
           verticalArray[i] = sum;
        }
        
    }

    private static int getPixel(int x, int y) {
        return pixels[y * width + x];
    }

    private static boolean isSkinColor(int x, int y) {
        
        ExtendedPixel extendedPixel = extendedPixels[x][y];
       
        /*return extendedPixel.g < 160 && extendedPixel.g > 50 &&
                extendedPixel.b < 124 && extendedPixel.b > 30 &&
                extendedPixel.h < 30 && extendedPixel.h > 0 &&
                extendedPixel.s < 155 && extendedPixel.s > 85;*/
        
        return extendedPixel.cb < 120 && extendedPixel.cb > 80 &&
                extendedPixel.cr < 170 && extendedPixel.cr > 145;
        
        
        
        
    }
    
}
