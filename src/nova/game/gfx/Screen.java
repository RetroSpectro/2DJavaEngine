package nova.game.gfx;

public class Screen {
    public static final int MAP_WIDTH = 64;
    public static final int MAP_MASK = MAP_WIDTH-1;

    public int[] pixels;

    public int xOffset = 0;
    public int yOffset = 0;

    public int width;
    public int height;

    public SpriteSheet sheet;


    public Screen(int width, int height, SpriteSheet sheet) {
        this.width = width;
        this.height = height;
        this.sheet = sheet;

        pixels = new int[width*height];


    }

    public  void render(int xPos, int yPos, int tile, int color)
    {
        xPos -=xOffset;
        yPos -=yOffset;
        int xTile = tile%32;
        int yTile  = tile/32;
        int tileOffset = (xTile<<3)+(yTile<<3)*sheet.width;
        for (int i = 0; i < 8; i++) {
            int ySheet = i;

            if(i+yPos<0||i+yPos>=height)continue;

            for (int j = 0; j < 8; j++) {
                int xSheet = j;

                if(j+xPos<0||j+xPos>=width)continue;
                int col = (color>>(sheet.pixels[xSheet+ySheet*sheet.width+tileOffset]*8))&255;
                if(col<255)
                {
                    pixels[(j+xPos)+(i+yPos)*width]= col;
                }


            }
        }
    }
}
