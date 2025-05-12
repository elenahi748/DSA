package main;

public class Viewpoint {
    public int x, y, width, height;
    private int mapWidth, mapHeight;

    public Viewpoint(int width, int height, int mapWidth, int mapHeight) {
        this.width = width;
        this.height = height;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.x = 0;
        this.y = 0;
    }

    public void follow(int playerX, int playerY) {
        x = playerX - width / 2;
        y = playerY - height / 2;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > mapWidth - width) x = mapWidth - width;
        if (y > mapHeight - height) y = mapHeight - height;
    }
}
