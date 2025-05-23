package Tile;

import main.MenuPanel;
import main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private  MenuPanel gp;

    public Tile[] tile;
    public int mapTileNum[][];

    private int baseX = 0, baseY = 0, offset = 64;

    Panel panel;
    private int width, height;

    public TileManager(Panel panel)
    {
        this.panel = panel;
        tile = new Tile[10];
        int mapCols = panel.maxScreenCol;
        int mapRows = panel.maxScreenRow;

        mapTileNum = new int[mapCols][mapRows]; // Đồng bộ kích thước với World
        getTileImage();
        loadMap("/Mapdata/Map02.txt");
        }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")); // Đảm bảo đường dẫn đúng
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/black.png"));
            tile[1].collision = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try {
            InputStream  is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                throw new RuntimeException("Could not load /Maps/Map02.txt");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while (col<panel.maxScreenCol && row< panel.maxScreenRow)
            {
                String line = br.readLine();

                while(col < panel.maxScreenCol)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == panel.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void draw(Graphics2D g2) {
        // Determine visible tiles based on viewport
        int startCol = panel.viewportX / panel.tileSize;
        int endCol = (panel.viewportX + panel.boardWidth) / panel.tileSize;
        int startRow = panel.viewportY / panel.tileSize;
        int endRow = (panel.viewportY + panel.boardHeight) / panel.tileSize;

        // Clamp to map boundaries
        startCol = Math.max(0, startCol);
        endCol = Math.min(mapTileNum.length - 1, endCol);
        startRow = Math.max(0, startRow);
        endRow = Math.min(mapTileNum[0].length - 1, endRow);

        // Render visible tiles
        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                int tileNum = mapTileNum[col][row];
                int drawX = col * panel.tileSize - panel.viewportX;
                int drawY = row * panel.tileSize - panel.viewportY;

                g2.drawImage(tile[tileNum].image, drawX, drawY, panel.tileSize, panel.tileSize, null);
            }
        }
    }

    public void drawCollisionAreas(Graphics2D g2) {
        g2.setColor(new Color(255, 0, 0, 100)); // Semi-transparent red
        //player wall collision
//        for (int row = 0; row < panel.maxScreenRow; row++) {
//            for (int col = 0; col < panel.maxScreenCol; col++) {
//                int tileNum = mapTileNum[col][row];
//                if (tile[tileNum].collision) {
//                    // Draw collision rectangle
//                    int x = col * panel.tileSize;
//                    int y = row * panel.tileSize;
//                    g2.fillRect(x, y, panel.tileSize, panel.tileSize);
//                }
//            }
        for (int row = 0; row < mapTileNum[0].length; row++) {
        for (int col = 0; col < mapTileNum.length; col++) {
            int tileNum = mapTileNum[col][row];
            int drawX = col * panel.tileSize;
            int drawY = row * panel.tileSize;

            g2.drawImage(tile[tileNum].image, drawX, drawY, panel.tileSize, panel.tileSize, null);
            }
        }
    }
}
