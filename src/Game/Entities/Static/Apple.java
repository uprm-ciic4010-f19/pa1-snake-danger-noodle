package Game.Entities.Static;


import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */

/*
 * Edited by Gabriel Rodriguez ad Christian Robles on 13/09/2019
 */
public class Apple {

    private Handler handler;

    public int xCoord;
    public int yCoord;

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
    public static boolean isGood; //created an instance called isGood; it tells if the apple
    							  //is good or rotten
    


}
