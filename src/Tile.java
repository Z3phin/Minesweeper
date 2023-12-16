import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

public class Tile {
    private final int x;
    private final int y;
    private int value;
    private boolean revealed;
    private boolean flagged;
    private boolean bomb;
    private boolean clickedBomb;

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
        this.clickedBomb = false;
    }

    /**
     * Reveals this tile.
     */
    public void reveal() {
        if (!this.revealed) {
           this.revealed = true;
        }
    }

    public boolean isRevealed() {
        return this.revealed;
    }

    public boolean isFlagged() {
        return flagged;
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
        Image image;

        if (flagged) {
            image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("flag" +
                            ".png")));
            drawImage(image, gc);
        } else if (!revealed) {
            image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("blank.png")));
            drawImage(image, gc);
        } else if (clickedBomb) {
            image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(
                    "clickedBomb.png")));
            drawImage(image, gc);
        } else if (isBomb()) {
            image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("bomb.png")));
            drawImage(image, gc);
        } else {
            image = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(
                    "exposedBlank.png")));
            drawImage(image, gc);
            renderText(gc);
        }

    }

    private void renderText(GraphicsContext gc) {
        if (value > 0) {
            int x = getX() * 40 + 20;
            int y = getY() * 40 + 20;
            gc.setFont(new Font("Trebuchet MS", 16));
            switch (value) {
                case 1 -> gc.setFill(Color.BLUE);
                case 2 -> gc.setFill(Color.GREEN);
                case 3 -> gc.setFill(Color.RED);
                case 4 -> gc.setFill(Color.DARKBLUE);
                case 5 -> gc.setFill(Color.BROWN);
                case 6 -> gc.setFill(Color.CYAN);
                case 7 -> gc.setFill(Color.BLACK);
                case 8 -> gc.setFill(Color.GREY);
            }
            gc.fillText(String.valueOf(getValue()), x, y);
        }
    }

    private void drawImage(Image image, GraphicsContext gc) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        gc.strokeRect(this.getX() * 40,this.getY() * 40, 40, 40);
        gc.drawImage(image, this.getX() * 40, this.getY() * 40);
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

    public void click() {
        if (!flagged && !revealed) {
            reveal();
            Game.board.incrementRevealed();
            if (isBomb()) {
                this.clickedBomb = true;
                Game.setGameOver();
            } else if (getValue() == 0) {
                Game.board.revealNeighbours(x, y);
            }
        }
    }

    public void rightClick() {
        if (!this.isRevealed()) {
            if (this.flagged) {
                this.removeFlag();
            } else {
                this.setFlag();
            }
        }

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
