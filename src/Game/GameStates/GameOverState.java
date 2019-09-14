package Game.GameStates;



import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;





import Main.Handler;

import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;

/*
 * Created by Gabriel Rodriguez ad Christian Robles on 13/09/2019
 */


//creates a screen called game over
//it appears whenever the snake collides with it self
public class GameOverState extends State{



	private int count = 0;

	private UIManager uiManager;



	public GameOverState(Handler handler) {

		super(handler);

		uiManager = new UIManager(handler);

		handler.getMouseManager().setUimanager(uiManager);



		uiManager.addObjects(new UIImageButton(handler.getWidth()/2-64, 400, 128, 64, Images.butstart, new ClickListlener() {
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
		//creates the game over screen
		g.setColor(Color.black);
		g.fillRect(0,0,handler.getWidth(),handler.getHeight());   	

		g.drawImage(Images.gameover,handler.getWidth()/2-150, handler.getHeight()/2 - 150, 300, 150,null);


		g.setFont(new Font("Courier",Font.BOLD,60));
		g.setColor(Color.RED);
		String lost = "GAME OVER!";
		g.drawString(lost, 125, 100);


		g.setFont(new Font("Courier",Font.BOLD,20));
		g.setColor(Color.WHITE);
		String playAgain = "PRESS START TO PLAY AGAIN.";
		g.drawString(playAgain, 145, 350);








		uiManager.Render(g);



	}



}