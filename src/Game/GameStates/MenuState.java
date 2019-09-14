package Game.GameStates;


import Main.Handler;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */

/*
 * Edited by Gabriel Rodriguez ad Christian Robles on 13/09/2019
 */
public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


        uiManager.addObjects(new UIImageButton(handler.getWidth()/2-64, handler.getHeight()/2, 128, 64, Images.butstart, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                State.setState(handler.getGame().gameState);
            }
        }));
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();

    }

    @Override
    public void render(Graphics g) {
    	//creates the home screen or menu screen 
        g.setColor(Color.black);
        g.fillRect(0,0,handler.getWidth(),handler.getHeight());
        g.drawImage(Images.title,0,0-20,handler.getWidth(),handler.getHeight(),null);
        
        g.setFont(new Font("Courier",Font.BOLD,60));
        g.setColor(Color.WHITE);
        String java = "JAVA";
        String snake = "SNAKE";
        g.drawString(java, 220, 80);
        g.drawString(snake, 210,510);
        uiManager.Render(g);

    }


}
