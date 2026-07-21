/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import static entity.Direction.DOWN;
import static entity.Direction.LEFT;
import static entity.Direction.RIGHT;
import static entity.Direction.UP;
import java.awt.Rectangle;
import java.util.Random;
import main.GamePanel;

/**
 *
 * @author Branislav Vujanov
 */
public final class Npc extends Entity {
    
    
    int frameCounter = 0;
    
 
    public Npc(GamePanel gamePanel) {
        super(gamePanel);
        getDefaultValues();
        getImage("/image/X.png");
    }

    @Override
    public void getDefaultValues() {
        acceleration = 0.75;
        //player's size
        width = gamePanel.tileSize / 2;
        height = gamePanel.tileSize / 2;
        solidArea = new Rectangle((int) x, (int) y, width, height);

    }

    @Override
    public void update() {
        
        frameCounter++;
        if (frameCounter == 80) {  //allows for NPC to change direction every 80 frames at random
            frameCounter = 0;
            Random random = new Random();
            int i = random.nextInt(1, 101); //random number 1-100
            if (i <= 25) direction = Direction.DOWN;
            if (i > 25 && i <= 50) direction = Direction.UP;
            if (i > 50 && i <= 75) direction = Direction.RIGHT;
            if (i > 75 && i <= 100) direction = Direction.LEFT;
        }

        if (direction != null){
            switch (direction) {
            case UP -> y -= acceleration;
            case DOWN -> y += acceleration;
            case LEFT -> x -= acceleration;
            case RIGHT -> x += acceleration;
            } 
        }
      
        //Check if npc have reached the end of the screen 
        y = getYScreenPosition(y);
        x = getXScreenPosition(x);   
    }
   
    
    public double getXScreenPosition(double x){
        if (x < 0 - width )  x = gamePanel.screenWidth;
        if (x + width > gamePanel.screenWidth)   x = 0 - width; 
        return x;
    }
    
    public double getYScreenPosition(double y){
        if (y < 0 - height ) y = gamePanel.screenHeight - height;
        if (y  > gamePanel.screenHeight ) y = 0 ;
        return y;
    }


}
