import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Tile {
    private final int x;
    private final int y;
    private int value;
    private boolean revealed;
    private boolean flagged;
    private boolean bomb;

    /**
     * Creates an instance of tile.
     *
     * @param x x coordinate position.
     * @param y y coordinate position.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = 0;
        this.revealed = false;
        this.flagged = false;
    }

    /**
     * Reveals this tile.
     */
    public void reveal() {
        if (!this.flagged || !this.revealed) {
           this.revealed = true;
        }
    }

    /**
     * Sets a flag on this tile
     */
    public void setFlag() {
        this.flagged = true;
    }

    /**
     * Removes a flag from this tile.
     */
    public void removeFlag() {
        this.flagged = false;
    }

    public void incrementValue() {
        value++;
    }

    /**
     * Returns the value of held by this tile.
     *
     * @return value held by this tile.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Returns the X coordinate of this tile.
     *
     * @return x coordinate of this tile.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the Y coordinate of this tile.
     *
     * @return y coordinate of this tile.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Renders this tile on the given graphics context.
     *
     * @param gc graphics context.
     */
    public void render(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(getX() * 50, getY() * 50,  50,50);
        gc.setFill(Color.BLACK);
        gc.strokeRect(getX() * 50, getY() * 50,  50,50);
        if (revealed) {
            renderText(gc);
        }

    }

    private void renderText(GraphicsContext gc) {
        int x = getX() * 50 + 25;
        int y = getY() * 50 + 25;
        if (this.isBomb()) {
            gc.fillText("!", x, y);
        } else {
            gc.fillText(String.valueOf(getValue()), x, y);
        }
    }

    /**
     * Sets this tile to have a bomb.
     */
    public void setBomb() {
        this.bomb = true;
    }

    /**
     * Returns whether this tile contains a bomb or not.
     *
     * @return True if this tile has a bomb, otherwise false.
     */
    public boolean isBomb() {
        return bomb;
    }

    @Override
    public String toString() {
        if (isBomb()) {
            return "{!}";
        } else {
            return "{" + value + "}";
        }
    }
}
