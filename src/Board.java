import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Board {
    private final Tile[][] tiles;
    private final int height;
    private final int width;
    private final int bombs;

    public Board(int height, int width, int bombs) {
         tiles = new Tile[height][width];
         this.height = height;
         this.width = width;
         this.bombs = bombs;
         populate();
         populateBombs();
    }

    public Tile getTile(int x, int y) {
        return this.tiles[y][x];
    }

    public void render(GraphicsContext gc) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x].render(gc);
            }
        }
    }

    public void click(int x, int y) {
        Tile tile = getTile(x,y);
        tile.click();
    }

    public void rightClick(int x, int y) {
        Tile tile = getTile(x,y);
        tile.rightClick();
    }
    private void populate() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = new Tile(x, y);
            }
        }
    }

    private void populateBombs() {
        Random random = new Random();
        int bombsToSet = this.bombs;

        while (bombsToSet > 0) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Tile tile = this.getTile(x, y);

            if (!tile.isBomb()) {
                tile.setBomb();
                bombsToSet--;
                increaseValueOfNeighbours(x, y);
            }

        }
    }

    private void increaseValueOfNeighbours(int x, int y) {
        int[] adjustments = {-1,0,1};
        for (int yAdjustment: adjustments) {
            for (int xAdjustment: adjustments) {
                int yOfNeighbour = y + yAdjustment;
                int xOfNeighbour = x + xAdjustment;

                if (withinBounds(xOfNeighbour, yOfNeighbour)) {
                    Tile neighbour = tiles[yOfNeighbour][xOfNeighbour];
                    neighbour.incrementValue();
                }
            }
        }
    }

    private boolean withinBounds(int x, int y) {
        return x < width
                && x >= 0
                && y < height
                && y >= 0;
    }

    public void revealNeighbours(int x, int y) {
        int[] adjustments = {-1,0,1};
        for (int yAdjustment: adjustments) {
            for (int xAdjustment: adjustments) {
                int yOfNeighbour = y + yAdjustment;
                int xOfNeighbour = x + xAdjustment;

                if (withinBounds(xOfNeighbour, yOfNeighbour)) {
                    click(xOfNeighbour, yOfNeighbour);
                }
            }
        }
    }

    public void reveal() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x].removeFlag();
                tiles[y][x].reveal();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                builder.append(tiles[y][x]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
