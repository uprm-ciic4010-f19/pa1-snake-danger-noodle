package Worlds;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Static.Apple;
import Main.Handler;

import java.awt.*;
import java.util.LinkedList;


/**
 * Created by AlexVR on 7/2/2018.
 */

/*
 * Edited by Gabriel Rodriguez ad Christian Robles on 13/09/2019
 */
public abstract class WorldBase {

    //How many pixels are from left to right
    //How many pixels are from top to bottom
    //Must be equal
    public int GridWidthHeightPixelCount;

    //automatically calculated, depends on previous input.
    //The size of each box, the size of each box will be GridPixelsize x GridPixelsize.
    public int GridPixelsize;

    public Player player;

    protected Handler handler;


    public Boolean appleOnBoard;
    protected Apple apple;
    public Boolean[][] appleLocation;


    public Boolean[][] playerLocation;

    public LinkedList<Tail> body = new LinkedList<>();


    public WorldBase(Handler handler){
        this.handler = handler;

        appleOnBoard = false;


    }
    public void tick(){



    }

    public void render(Graphics g){
    	
    	//commenting the code remove the grid lines, they could also be
    	//painted as the same color of the background

//        for (int i = 0; i <= 600; i = i + GridPixelsize) {
//
//        	Color purple = new Color(191,84,183);
//            g.setColor(Color.BLACK);
//            g.drawLine(0, i, handler.getWidth() , i);
//            g.drawLine(i,0,i,handler.getHeight());
//
//        }



    }

}
