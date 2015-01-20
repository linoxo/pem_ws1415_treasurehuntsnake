package hunt.snake.com.snaketreasurehunt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import hunt.snake.com.framework.Graphics;

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
        drawGameElement(g, 0, 0);
    }

    @Override
    public void drawGameElement(Graphics g, int deltaX, int deltaY) {
        if(elements == null)
            gameElement.draw(g, deltaX, deltaY);
        else {
            for(GameElement element : elements)
                element.draw(g, deltaX, deltaY);
        }
    }

    @Override
    public void setPosition(Position position) {}

    public GameElement getGameElement() {
        return gameElement;
    }

    public List<GameElement> getGameElements() {
        return elements;
    }
}
