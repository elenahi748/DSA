package utilz;

package enity.Monsters;

import main.Panel;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BFS {
    public static List<Point> findPathToPlayer(Boss boss, Player player, Panel panel) {
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> cameFrom = new HashMap<>();
        Set<Point> visited = new HashSet<>();

        Point start = new Point(boss.x, boss.y);
        Point goal = new Point(player.x, player.y);

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, goal);
            }

            for (Point neighbor : getNeighbors(current, panel)) {
                if (!visited.contains(neighbor) && !panel.cChecker.isTileBlocked(neighbor.x, neighbor.y)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return new ArrayList<>(); // No path found
    }

    private static List<Point> getNeighbors(Point current, Panel panel) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Right, Left, Down, Up

        for (int[] dir : directions) {
            int newX = current.x + dir[0] * panel.tileSize;
            int newY = current.y + dir[1] * panel.tileSize;
            neighbors.add(new Point(newX, newY));
        }

        return neighbors;
    }

    private static List<Point> reconstructPath(Map<Point, Point> cameFrom, Point current) {
        List<Point> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(0, current);
            current = cameFrom.get(current);
        }
        return path;
    }
}
