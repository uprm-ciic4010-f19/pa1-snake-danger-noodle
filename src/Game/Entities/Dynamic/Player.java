package Game.Entities.Dynamic;

import Game.Entities.Static.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.GameStates.GameOverState;
import Game.GameStates.PauseState;
import Game.GameStates.State;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */

/*
 * Edited by Gabriel Rodriguez ad Christian Robles on 13/09/2019
 */
public class Player {

	public int lenght;
	public boolean justAte;
	private Handler handler;

	public double speed = 5.0;

	public double score = 0; //initial value of the score


	public Graphics g;
	
	public int steps = 0; //counts the steps of the snake

	public int xCoord;
	public int yCoord;

	public int moveCounter;

	public String direction;//is your first name one?

	public Player(Handler handler){
		this.handler = handler;
		xCoord = 0;
		yCoord = 0;
		moveCounter = 0;

		direction= " "; //it was "Right", by doing this i just gave the player
						//the option to choose what direction will the snake go
						//at first.
		justAte = false;
		lenght= 1; 


	}

	public void tick(){
		moveCounter ++; //

		if(moveCounter>=speed) {
			checkCollisionAndMove();
			moveCounter=0;
		}
		//direction of the movements and prevents backtracking
		if(lenght >= 1)
		{
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) && direction != "Down"){
				direction="Up";

			}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)&& direction != "Up"){
				direction="Down";

			}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) && direction != "Right"){
				direction="Left";

			}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) && direction != "Left"){
				direction="Right";
			}

		}
		
		//when N key is clicked...
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N )&& lenght >1){
			if (handler.getWorld().body != null)
			{   //...it adds a piece to it's tail.
				Tail tail = new Tail(this.xCoord,this.yCoord,handler);
				handler.getWorld().body.addLast(tail);
				handler.getWorld().playerLocation[tail.x][tail.y] = true;
				
				lenght++;
			}
		
		}
		//when '='(it's is supposed to be '+' but '=' is more practical) key is clicked...
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS))
		{   //...speed increases
			speed = speed - 0.5; //speed increasing formula
			
		}
		//when '-' key is clicked...
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS))
		{//...speed decreases
			speed = speed + 0.5; //speed decreasing formula
		}
		
		// when the ESC key is pressed...
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE))
		{	//...it pauses the game.
			PauseState pauseState = new PauseState(handler);
			State.setState(pauseState);
		}
		else
		{	//checks if the snake collides with itself...
			for ( Tail index : handler.getWorld().body )
			{
				int headX = handler.getWorld().body.element().x;
				int headY = handler.getWorld().body.element().y;
				int tailX = index.x;
				int tailY = index.y;
				if (handler.getWorld().body.indexOf(index) != 0)
				{	
					if ((headX == tailX) && (headY == tailY))
					{	
						//...and calls a game over screen
						GameOverState gameover = new GameOverState(handler);
						State.setState(gameover);
					}
				}

			}
		}
	}

	public void checkCollisionAndMove(){
		
		steps++; //adds one to the steps variable every time the method is called
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;


		switch (direction){
		case "Left":
			if(xCoord==0){ //teleports from left corner to right corner
				xCoord = handler.getWorld().GridWidthHeightPixelCount-1;
			}else{
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){ //teleports from right corner to left corner
				xCoord = 0;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){ //teleports from up corner to down corner
				yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){//teleports from down corner to up corner
				yCoord = 0;
			}else{
				yCoord++;
			}
			break;
		}
		handler.getWorld().playerLocation[xCoord][yCoord]=true;


		if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));
		}

	}


	public void render(Graphics g,Boolean[][] playeLocation){
		Random r = new Random();
		
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
				//

				if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
					if(playeLocation[i][j])
					{
						//						{
						//						if (piece%2 == 0)  g.setColor(Color.GREEN);
						//						else g.setColor(Color.YELLOW);
						//						piece++;
						//						}
						Color rColor = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
						g.setColor(rColor);
						g.fillRect((i*handler.getWorld().GridPixelsize),
								(j*handler.getWorld().GridPixelsize),
								handler.getWorld().GridPixelsize,
								handler.getWorld().GridPixelsize);
					}
					if(handler.getWorld().appleLocation[i][j]) 
					{
						if (steps >= handler.getWidth()) {
							Color brown = new Color(102,51,0);
							g.setColor(brown);
							
							Apple.isGood = false;

						}
						else {
							g.setColor(Color.RED);
						Apple.isGood = true;
						}
						g.fillOval((i*handler.getWorld().GridPixelsize),
								(j*handler.getWorld().GridPixelsize),
								handler.getWorld().GridPixelsize,
								handler.getWorld().GridPixelsize);
						//made apples round
					}
					g.setColor(Color.WHITE); //sets the a color

					g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); //sets a font

					g.drawString("Score: " + String.valueOf(score), 10,15); // prints the score

					 

					
				}

			}
		}


	}

	public void Eat(){

		if (speed > 0)
			speed = speed - 0.5; //speed increasing formula
		else;

		if(Apple.isGood) //if the apple is not rotten...
		score = score + Math.sqrt(2.0*score + 1.0); //...increase the score
		//if the apple is rotten...
		else {
			if (score > 0) score = score - Math.sqrt(2.0*score + 1.0); //...decrease the score,...
			
			if (score < 0) score = 0; //...if it results negative, change it to 0
			}
			
		
		steps = 0; //resets the steps
		
		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;
		
		//if the apple is rotten and and the snake has more than one segment...
		if (!Apple.isGood && lenght > 1) {
			//...removes the last tail
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y]=false;
			handler.getWorld().body.removeLast();
			lenght = lenght - 1;
		}
		
		else if (!Apple.isGood && lenght ==1)
		{
			// the snake stays the same
		}
		else
		{
			//depending on the direction of the snake it adds the tail where it has to go
			switch (direction){
			case "Left":
				if( handler.getWorld().body.isEmpty()){
					if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
						tail = new Tail(this.xCoord+1,this.yCoord,handler);
					}else{
						if(this.yCoord!=0){
							tail = new Tail(this.xCoord,this.yCoord-1,handler);
						}else{
							tail =new Tail(this.xCoord,this.yCoord+1,handler);
						}
					}
				}else{
					if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
						tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
					}else{
						if(handler.getWorld().body.getLast().y!=0){
							tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
						}else{
							tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

						}
					}

				}
				break;
			case "Right":
				if( handler.getWorld().body.isEmpty()){
					if(this.xCoord!=0){
						tail=new Tail(this.xCoord-1,this.yCoord,handler);
					}else{
						if(this.yCoord!=0){
							tail=new Tail(this.xCoord,this.yCoord-1,handler);
						}else{
							tail=new Tail(this.xCoord,this.yCoord+1,handler);
						}
					}
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						if(handler.getWorld().body.getLast().y!=0){
							tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
						}else{
							tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
						}
					}

				}
				break;
			case "Up":
				if( handler.getWorld().body.isEmpty()){
					if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
						tail=(new Tail(this.xCoord,this.yCoord+1,handler));
					}else{
						if(this.xCoord!=0){
							tail=(new Tail(this.xCoord-1,this.yCoord,handler));
						}else{
							tail=(new Tail(this.xCoord+1,this.yCoord,handler));
						}
					}
				}else{
					if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}else{
						if(handler.getWorld().body.getLast().x!=0){
							tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
						}else{
							tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
						}
					}

				}
				break;
			case "Down":
				if( handler.getWorld().body.isEmpty()){
					if(this.yCoord==0){//Changed operators (!=)
						tail=(new Tail(this.xCoord,this.yCoord-1,handler));
					}else{
						if(this.xCoord==0){ //Changed operators (!=)
							tail=(new Tail(this.xCoord-1,this.yCoord,handler));
						}else{
							tail=(new Tail(this.xCoord+1,this.yCoord,handler));
						} //System.out.println("Tu biscochito"); //don't now what to do
					}
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						if(handler.getWorld().body.getLast().x!=0){
							tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
						}else{
							tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
						}
					}

				}
				break;
			}
			handler.getWorld().body.addLast(tail);
			handler.getWorld().playerLocation[tail.x][tail.y] = false;//truth value was true

			lenght++; //adds 1 to the length of the snake every time the method is called
		}

	}

	public void kill(){
		
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;

			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
}
