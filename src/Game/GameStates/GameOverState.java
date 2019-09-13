package Game.GameStates;

 

import java.awt.Color;

import java.awt.Graphics;

 

import javax.swing.JButton;

 

import Main.Handler;

import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

 

public class GameOverState extends State{

               

                private int count = 0;

    private UIManager uiManager;

 

    public GameOverState(Handler handler) {

        super(handler);

        uiManager = new UIManager(handler);

        handler.getMouseManager().setUimanager(uiManager);

 

       

 

        uiManager.addObjects(new UIImageButton(225, (223+(64+16))+(64+16), 128, 64, Images.Resume, () -> {
           handler.getMouseManager().setUimanager(null);

           State.setState(handler.getGame().menuState);

        }));

 

 

 

 

 

    }

 

    @Override

    public void tick() {

        handler.getMouseManager().setUimanager(uiManager);

        uiManager.tick();

        count++;

        if( count>=30){

            count=30;

        }

        if(handler.getKeyManager().pbutt && count>=30){

            count=0;

 

            State.setState(handler.getGame().gameState);

        }

 

 

    }

 

    @Override

    public void render(Graphics g) {

        g.drawImage(Images.gameover,0,0,600,600,null);

       

        JButton button = new JButton("Do you want t play again?");

        button.setBackground(Color.RED);

        button.setBounds(200, 500, 200, 50);

        button.setRolloverEnabled(true);

        button.setVisible(true);

       

 

        uiManager.Render(g);

       

    }

 

}