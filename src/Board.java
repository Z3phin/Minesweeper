import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Board {
    private final Tile[][] tiles;
    private final int height;
    private final int width;
    private final int bombs;
    private int revealed;

    /**
     * Creates an instance
     *
     * @param height
     * @param width
     * @param bombs
     */
    public Board(int height, int width, int bombs) {
         this.tiles = new Tile[height][width];
         this.height = height;
         this.width = width;
         this.bombs = bombs;
         this.revealed = 0;
         populate();
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

    public void incrementRevealed() {
        revealed++;
        if (revealed == (width * height - bombs)) {
            Game.setGameWin();
        }
    }
    private void populate() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = new Tile(x, y);
            }
        }
    }

    public void populateBombs(int avoidX, int avoidY) {
        Random random = new Random();
        int bombsToSet = this.bombs;

        while (bombsToSet > 0) {
            int randX = random.nextInt(width);
            int randY = random.nextInt(height);
            if (randX != avoidX && randY != avoidX) {
                Tile tile = this.getTile(randX, randY);

                if (!tile.isBomb()) {
                    tile.setBomb();
                    bombsToSet--;
                    increaseValueOfNeighbours(randX, randY);
                }
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

    public void flagAllBombs() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = tiles[y][x];
                if (tile.isBomb() && !tile.isFlagged()) {
                    tile.setFlag();
                }
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
