package Objects;

import static Utilities.Constants.GAME_HEIGHT;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Sign extends Object{

    private String text;
    private boolean hasBeenRead = false;
    private int textOpacity = 50;

    public Sign(int x, int y, int objType, String text) {
        super(x, y, objType);
        initHitbox(32, GAME_HEIGHT);
        xDrawOffset = 0;
		yDrawOffset = 12;
        hitbox.y += yDrawOffset - GAME_HEIGHT;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean hasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    public void setTextOpacity(int textOpacity) {
        this.textOpacity = textOpacity;
    }

    public void drawText(Graphics g, int xOffset, String text) {
        g.setColor(new Color(255,255,255,textOpacity+=2));
        if(textOpacity > 253) { // fade in the text
            textOpacity = 253;
        }
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        int wordsPerLine = 3; // How many words to write before going to the next line
        String[] textArray = text.split(" "); // Putting the text into an array of seperate strings for each word
        int stringIndex = 0; // The index of the current word
        String currentTextToDraw = ""; // The the words of the current line
        int totalLines = 1; // The amount of lines of text

        while(stringIndex < textArray.length) {
            currentTextToDraw = ""; // Reset the current word
            int currentWordsToDraw = Math.min(wordsPerLine, textArray.length-stringIndex); // Figure out how many words to draw for this line
            int i = 0;
            for(i = stringIndex; i < stringIndex + currentWordsToDraw; i++) {
                currentTextToDraw += " " + textArray[i]; // add the current word to the text to be drawn for this line
            }
            stringIndex = i;
            g.drawString(currentTextToDraw, x-(currentTextToDraw.length()/2) * 12 - xOffset, y - (100 - 30*totalLines++));
        }
    }
}
