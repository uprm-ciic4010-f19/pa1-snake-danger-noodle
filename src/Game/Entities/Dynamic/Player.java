package Game.Entities.Dynamic;

import Main.*;
import Game.GameStates.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.GameStates.State;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

	public int lenght;
	public boolean justAte;
	private Handler handler;

	public double speed = 5.0;

	public double score = 0;
	
	public int applesEaten = 0;

	public int piece = 0;

	public String tails = " ";

	public Graphics g;

	public int xCoord;
	public int yCoord;

	public int moveCounter;

	public String direction;//is your first name one?

	public Player(Handler handler){
		this.handler = handler;
		xCoord = 0;
		yCoord = 0;
		moveCounter = 0;

		direction= " "; //it was "Right"
		justAte = false;
		lenght= 1; 


	}

	public void tick(){
		moveCounter ++; //

		if(moveCounter>=speed) {
			checkCollisionAndMove();
			moveCounter=0;
		}
		//all moves when snake has more then one segment and prevents backtracking
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
		
//		else //moves allowed when the snake has just on segment
//			{
//			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)){
//				direction="Up";
//
//			}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)){
//				direction="Down";
//
//			}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)){
//				direction="Left";
//
//			}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) ){
//				direction="Right";
//			}
//			}
		
		
		//when N key is clicked...
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)){
			if (handler.getWorld().body != null)
			{   //...adds a piece to it's tail.
				Tail tail = new Tail(this.xCoord,this.yCoord,handler);
				handler.getWorld().body.addLast(tail);
				handler.getWorld().playerLocation[tail.x][tail.y] = true;
				
				lenght++;
			}
		
		}
		//when '+'(it's is supposed to be '+' but it doesn't work) key is clicked...
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS))
		{   //...speed increases
			speed = speed - 0.5;
			
		}
		//when '-' key is clicked...
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS))
		{//...speed decreases
			speed = speed + 0.5;
		}
		
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE))
		{
			PauseState pauseState = new PauseState(handler);
			State.setState(pauseState);
		}
		else
		{
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
						kill();
					}
				}

			}
		}
	}

	public void checkCollisionAndMove(){
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;


		switch (direction){
		case "Left":
			if(xCoord==0){
				xCoord = handler.getWorld().GridWidthHeightPixelCount-1;
			}else{
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				xCoord = 0;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				yCoord = handler.getWorld().GridWidthHeightPixelCount-1;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
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
					}
					if(handler.getWorld().appleLocation[i][j]) g.setColor(Color.RED);

					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}

			}
		}


	}

	public void Eat(){

		if (speed > 0)
			speed = speed - 0.5; //
		else;

		score = score + Math.sqrt(2.0*score + 1.0);
		
		//Font font = new Font(String.valueOf(score), Font.PLAIN, 10);
		
//		g.setColor(Color.BLACK);
//		g.drawString(String.valueOf(score), 300, 300);
		
		

		//System.out.println(score);
		lenght++;
		applesEaten++;
		
		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;


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
					} System.out.println("Tu biscochito"); //don't now what to do
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
//		System.out.println(lenght);
//		System.out.println("apples: " + applesEaten);
		
	}

	public void kill(){
		//lenght = 0;
		
		
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
