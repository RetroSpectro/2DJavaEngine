package nova.game.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class SpriteSheet {
    public String path;
    public int width;
    public int height;

    public int[]pixels;
    public SpriteSheet(String path)
    {
        this.path=path;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(image==null)
        {
        return;
        }

        this.width = image.getWidth();
        this.height = image.getHeight();

        pixels = image.getRGB(0,0,width,height,null,0,width);
        
        //0xffffff;

        for (int i = 0; i < pixels.length; i++) {
            pixels[i]=(pixels[i]&0xff)/64;
        }


    }
}
