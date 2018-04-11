/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple;

import java.awt.Color;

/**
 *
 * @author B Ricks, PhD <bricks@unomaha.edu>
 */
public class ExtendedPixel {
    
    public int r;
    public int g;
    public int b;
    
    public int h;
    public int s;
    public int v;
    
    public int y;
    public int cb;
    public int cr;
    
    public int nr;
    public int ng;
    public int nb;

    public ExtendedPixel(int rgb) {
        
        Color c = new Color(rgb);
        int red = c.getRed();
        int green = c.getGreen();
        int blue = c.getBlue();
        
        r = red;
        g = green;
        b = blue;
        
        float sumRGB = r + g + b;
        
        nr = (int) ((r/sumRGB) * 255);
        ng = (int) ((g/sumRGB) * 255);
        nb = (int) ((b/sumRGB) * 255);
        
        y = (int) (.299 * r + .587 * g + 0.114 * b);
        cb = (int) (128 + -.169 * r -.331 * g + 0.500 * b);
        cr = (int) (128 + .500 * r -.419 * g + -0.081 * b);
        
        float[] hsbvals = new float[3];
        Color.RGBtoHSB(r, g, b, hsbvals);
        
        h = (int) (hsbvals[0] * 255);
        s = (int) (hsbvals[1] * 255);
        v = (int) (hsbvals[2] * 255);
        
    }
    
    public int getChannel(int channel)
    {
        switch(channel)
        {
            case 0:
                return r;
            case 1:
                return g;
            case 2:
                return b;
            case 3:
                return h;
            case 4:
                return s;
            case 5:
                return v;
            case 6:
                return y;
            case 7:
                return cb;
            case 8:
                return cr;
            case 9:
                return nr;
            case 10:
                return ng;
            case 11:
                return nb;
        }
        
        return -1;
    }
    
    
    
}
