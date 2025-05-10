package utilz;

import main.Panel;
import java.awt.*;

import enity.Monsters.Boss;

public class Raycasting {
    public static boolean canSeePlayer(Boss boss, Player player, Panel panel) {
        int x1 = boss.x, y1 = boss.y;
        int x2 = player.x, y2 = player.y;

        // Bresenham's Line Algorithm to check tiles
        int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1, sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            if (panel.cChecker.isTileBlocked(x1, y1)) return false; // Tile is blocked
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        return true; // No obstruction
    }
}
