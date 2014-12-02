package hunt.snake.com.snaketreasurehunt;

import java.lang.reflect.Array;
import java.util.ArrayList;

import hunt.snake.com.framework.Graphics;

/**
 * Created by Tom on 11/30/14.
 */
public class BigGameElement extends GameElement {

    private ArrayList<GameElement> elements;
    private GameElement gameElement;


    public BigGameElement() {

    }

    @Override
    public void update() {}

    public void setInitialGameElement(GameElement gameElement) {
        this.gameElement = gameElement;
    }

    public void addAdditionalGameElement(GameElement element) {
        if(elements == null) {
            elements = new ArrayList<GameElement>();
            elements.add(gameElement);
        }

        elements.add(element);
    }

    @Override
    public void drawGameElement(Graphics g) {

        if(elements == null)
            gameElement.draw(g);
        else {
            for(GameElement element : elements)
                element.draw(g);
        }
    }

    @Override
    public void setPosition(Position position) {}
}
