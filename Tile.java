import javafx.scene.canvas.GraphicsContext;


public class Tile {
    private final int x;
    private final int y;
    private final int value;
    private boolean revealed;
    private boolean flagged;

    /**
     * Creates an instance of tile.
     *
     * @param x x coordinate position.
     * @param y y coordinate position.
     * @param value value held by this tile.
     */
    public Tile(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
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
        gc.fillRect(getX(), getY(), 50,50);
        gc.fillText(String.valueOf(getValue()), getX(),getY());
    }
}