package utilz;

import java.util.*;

public class AStarPathfinding {
    public static List<int[]> findPath(int[][] map, int startX, int startY, int goalX, int goalY) {
        int rows = map.length;
        int cols = map[0].length;
    
        // Kiểm tra tọa độ hợp lệ
        if (startX < 0 || startY < 0 || goalX < 0 || goalY < 0 || 
            startX >= cols || startY >= rows || 
            goalX >= cols || goalY >= rows) {
            System.out.println("Invalid start or goal coordinates.");
            return Collections.emptyList(); // Trả về danh sách rỗng nếu tọa độ không hợp lệ
        }
    
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(a -> a.fCost));
        boolean[][] closedSet = new boolean[rows][cols];
    
        Node[][] nodes = new Node[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                nodes[y][x] = new Node(x, y, Integer.MAX_VALUE, Integer.MAX_VALUE, null);
            }
        }
    
        Node startNode = nodes[startY][startX];
        startNode.gCost = 0;
        startNode.hCost = heuristicCost(startX, startY, goalX, goalY);
        startNode.fCost = startNode.gCost + startNode.hCost;
        openSet.add(startNode);
    
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.x == goalX && current.y == goalY) {
                return reconstructPath(current);
            }
    
            closedSet[current.y][current.x] = true;
    
            for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int neighborX = current.x + direction[0];
                int neighborY = current.y + direction[1];
    
                if (neighborX < 0 || neighborY < 0 || neighborX >= cols || neighborY >= rows || 
                    closedSet[neighborY][neighborX] || map[neighborY][neighborX] == 1) {
                    continue;
                }
    
                Node neighbor = nodes[neighborY][neighborX];
                int tentativeGCost = current.gCost + 1;
    
                if (tentativeGCost < neighbor.gCost) {
                    neighbor.gCost = tentativeGCost;
                    neighbor.hCost = heuristicCost(neighborX, neighborY, goalX, goalY);
                    neighbor.fCost = neighbor.gCost + neighbor.hCost;
                    neighbor.parent = current;
    
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
    
        return Collections.emptyList(); // Không tìm thấy đường đi
    }
    private static List<int[]> reconstructPath(Node node) {
        List<int[]> path = new ArrayList<>();
        while (node != null) {
            path.add(new int[]{node.x, node.y});
            node = node.parent;
        }
        Collections.reverse(path); // Đảo ngược danh sách để có đường đi đúng thứ tự
        return path;
    }

    private static int heuristicCost(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private static class Node {
        int x, y, gCost, hCost, fCost;
        Node parent;

        Node(int x, int y, int gCost, int hCost, Node parent) {
            this.x = x;
            this.y = y;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
            this.parent = parent;
        }
    }
}
