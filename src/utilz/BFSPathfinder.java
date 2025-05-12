package utilz;

import java.awt.Point;
import java.util.*;

import Tile.TileManager;
import enity.Player;

public class BFSPathfinder {
    /**
     * Tìm đường ngắn nhất từ start đến goal
     * 
     * @param start      Điểm bắt đầu
     * @param player     Điểm kết thúc
     * @param mapTileNum Bản đồ va chạm (true nếu là vật cản)
     * @param tileSize   Kích thước mỗi tile
     * @return Danh sách các điểm trên đường đi
     */
    public static List<Point> findPath(Point start, Point goal, int[][] mapTileNum, TileManager tileManager, int tileSize) {
        if (mapTileNum == null) {
            throw new IllegalArgumentException("mapTileNum must not be null when calling BFSPathfinder.findPath.");
        }

        int rows = mapTileNum.length;
        int cols = mapTileNum[0].length;

        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> parentMap = new HashMap<>();
        boolean[][] visited = new boolean[rows][cols];

        queue.add(start);
        visited[start.x][start.y] = true;

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(goal)) {
                return reconstructPath(start, goal, parentMap);
            }

            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < rows && ny < cols && !visited[nx][ny]) {
                    int tileNum = mapTileNum[nx][ny];
                    if (!tileManager.tile[tileNum].collision) {
                        visited[nx][ny] = true;
                        Point neighbor = new Point(nx, ny);
                        queue.add(neighbor);
                        parentMap.put(neighbor, current);
                    }
                }
            }
        }
        return null; // Không tìm thấy đường đi.
    }

    private static List<Point> reconstructPath(Point start, Point goal, Map<Point, Point> parentMap) {
        List<Point> path = new ArrayList<>();
        Point current = goal;

        while (!current.equals(start)) {
            path.add(current);
            current = parentMap.get(current);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }
}