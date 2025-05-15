package Tile;

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

    public  Tile[] tile;
    public int mapTileNum[][];

    private final Panel panel;
    private final int width, height;

    public TileManager(Panel panel)
    {
        tile = new Tile[10];
        this.panel = panel;

        mapTileNum = new int[panel.maxMapCol][panel.maxMapRow];

        width = panel.tileSize * 4 / 3;
        height = panel.tileSize * 4 / 3;
        getTileImage();
        // loadMap("/Mapdata/Map01.txt"); //Map
    }

    public void setMap(String mapType) {
        String mapPath = "/Mapdata/Map01.txt"; // Mặc định là Tiny (Map01)

        if ("Tiny".equals(mapType)) {
            mapPath = "/Mapdata/Map01.txt";
        } else if ("Medium".equals(mapType)) {
            mapPath = "/Mapdata/Map02.txt";
        } else if ("Big".equals(mapType)) {
            mapPath = "/Mapdata/Map03.txt";
        }

        loadMap(mapPath);
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/black.png"));
            tile[1].collision = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        System.out.println("Loading map from: " + filePath); // Log để debug
        try (InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            for (int row = 0; row < panel.maxMapRow; row++) {
                String line = br.readLine();
                if (line == null) break; // Nếu không có thêm dòng nào, dừng đọc
                String[] numbers = line.trim().split(" ");

                for (int col = 0; col < panel.maxMapCol; col++) {
                    int num = 0; // Mặc định là tile "wall"
                    if (col < numbers.length) {
                        try {
                            num = Integer.parseInt(numbers[col]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid tile number at row " + row + ", col " + col);
                        }
                    }
                    // Đảm bảo chỉ số tile hợp lệ
                    mapTileNum[col][row] = Math.max(0, Math.min(num, tile.length - 1));
                }
            }

        } catch (IOException e) {
            System.err.println("Failed to load map from: " + filePath);
            e.printStackTrace();
        }
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

                if (tileNum < 0 || tileNum >= tile.length || tile[tileNum] == null) {
                    continue;
                }

                int screenX = col * tileSize - viewpoint.x;
                int screenY = row * tileSize - viewpoint.y;
                g2.drawImage(tile[tileNum].image, screenX, screenY, tileSize, tileSize, null);
            }
        }
    }

    public boolean[][] getWalkableMap() {
        int cols = panel.maxScreenCol;
        int rows = panel.maxScreenRow;

        boolean[][] walkableMap = new boolean[cols][rows];

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                int tileIndex = mapTileNum[col][row];
                walkableMap[col][row] = !tile[tileIndex].collision; //trả về true nếu đi đưọc (collision = false)
            }
        }
        return walkableMap;
    }

    public void drawCollisionAreas(Graphics2D g2, Viewpoint viewpoint) {
        g2.setColor(new Color(255, 0, 0, 100));
        // for (int row = 0; row < panel.maxScreenRow; row++) {
        //     for (int col = 0; col < panel.maxScreenCol; col++) {
        //         int tileNum = mapTileNum[col][row];

        //         if (tileNum < 0 || tileNum >= tile.length || !tile[tileNum].collision) {
        //             continue;
        //         }

        //         int x = col * panel.tileSize - viewpoint.x;
        //         int y = row * panel.tileSize - viewpoint.y;
        //         g2.fillRect(x, y, panel.tileSize, panel.tileSize);
        //     }
        // }
    }
}
