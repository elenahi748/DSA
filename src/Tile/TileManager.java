package Tile;

import main.MenuPanel;
import main.Panel;
import main.Viewpoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private  MenuPanel gp;

    public  Tile[] tile;
    public int mapTileNum[][];

    private int baseX = 0, baseY = 0, offset = 64;

    Panel panel;
    private int width, height;

    public TileManager(Panel panel)
    {
        tile = new Tile[10];
        this.panel = panel;

        mapTileNum = new int[panel.maxMapCol][panel.maxMapRow];

        width = panel.tileSize * 4 / 3;
        height = panel.tileSize * 4 / 3;
        getTileImage();
        loadMap("/Mapdata/Map02.txt");
    }


    public void getTileImage()
    {
        try{
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/black.png"));
            tile[1].collision = false;

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[0].collision = true;
        }catch (IOException e)
        { e.printStackTrace(); }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int row = 0; row < panel.maxMapRow; row++) {
                String line = br.readLine();
                String[] numbers = (line != null) ? line.trim().split(" ") : new String[0];
                for (int col = 0; col < panel.maxMapCol; col++) {
                    int num = 1; // Mặc định là nền (không phải tường)
                    if (col < numbers.length) num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }
            }
            br.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

public void draw(Graphics2D g2, Viewpoint viewpoint) {
    int tileSize = panel.tileSize;

    int startCol = viewpoint.x / tileSize;
        int endCol = Math.min((viewpoint.x + panel.boardWidth) / tileSize, panel.maxMapCol - 1);
        int startRow = viewpoint.y / tileSize;
        int endRow = Math.min((viewpoint.y + panel.boardHeight) / tileSize, panel.maxMapRow - 1);

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                int tileNum = mapTileNum[col][row];
                int screenX = col * tileSize - viewpoint.x;
                int screenY = row * tileSize - viewpoint.y;
                g2.drawImage(tile[tileNum].image, screenX, screenY, tileSize, tileSize, null);
            }
        }
}

    public void drawCollisionAreas(Graphics2D g2, Viewpoint viewpoint) {
        g2.setColor(new Color(255, 0, 0, 100));
        // for (int row = 0; row < panel.maxScreenRow; row++) {
        //     for (int col = 0; col < panel.maxScreenCol; col++) {
        //         int tileNum = mapTileNum[col][row];
        //         if (tile[tileNum].collision) {
        //             int x = col * panel.tileSize - viewpoint.x;
        //             int y = row * panel.tileSize - viewpoint.y;
        //             g2.fillRect(x, y, panel.tileSize, panel.tileSize);
        //         }
        //     }
        // }
    }
}
