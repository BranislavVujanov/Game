/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Rectangle;
import main.GamePanel;
import main.Keybord;

/**
 *
 * @author Branislav Vujanov
 */
public final class Player extends Entity {

    
    Keybord key;
    double power;
    double maxSpeed;
    double friction;
    double mass;
    

    public Player(GamePanel gamePanel, Keybord keybord) {
        super(gamePanel); 
        this.key= keybord;  
        gettDefaultValues();
        getImage("/player/Terminator default.png");
    }

    @Override
    public void gettDefaultValues() {

        //Set player's default values regarding movement
        x = 2 * gamePanel.tileSize;
        y = 1* gamePanel.tileSize;
        dx = 0;  // velocity
        dy = 0; // velocity
        power = 0.06;
        mass = 0.047 ; // value less than power value
        //Initial acceleration
        acceleration =  power - mass ;
        maxSpeed = 3.8;
        friction = 0.997; // value less than 1 
        
        //set player's size
        width = gamePanel.tileSize;
        height = gamePanel.tileSize;
        solidArea = new Rectangle((int) x, (int) y, width, height);
    }


    @Override
    public void update() {
        // Apply acceleration based on input        
        if (key.leftPressed)  {
            direction = Direction.LEFT;
            dx -= acceleration ;   
        }
        if (key.rightPressed){
            direction = Direction.RIGHT;
            dx += acceleration ;
        }
        if (key.upPressed){
            direction = Direction.UP;
            dy -= acceleration ;
        }
        if (key.downPressed){
            direction = Direction.DOWN;
            dy += acceleration ;
        }
        
        frameCounter++;
        if (frameCounter == 30) {
            frameCounter = 0;
            //to allow player to speed up at a rate  
           acceleration = (power + (power* 0.022)) - mass;
        }
   
        // Apply friction
        dx *= friction;
        dy *= friction;
        
        // Cap speed
        if (Math.abs(dx) >= maxSpeed) {
            dx = Math.copySign(maxSpeed, dx);
        }
        if (Math.abs(dy) >= maxSpeed) {
            dy = Math.copySign(maxSpeed, dy);
        }
        
        // Keeps player within the screen bounds and makes it rebound from the edge of the screen
        getXRebound(x);
        getYRebound(y);
        
        //checking for collision
        gamePanel.collisionChecker.checkCollisionWithNpc(this, gamePanel.npc);

        // Update position
        x += dx; 
        y += dy;   
    }

    
    public double getXRebound(double x){
        if (x <= 0 )  dx *= -2;
        else if (x >= gamePanel.screenWidth - width ) dx *= -2;    
        // Cap speed
        if (Math.abs(dx) >= maxSpeed) {
            dx = Math.copySign(maxSpeed, dx);
        }
        if (Math.abs(dy) >= maxSpeed) {
            dy = Math.copySign(maxSpeed, dy);
        }
        x = dx;
        return x;
    }
    
    public double getYRebound(double y){
        if (y <= 0) dy *= -2;
        else if (y >= gamePanel.screenHeight - height) dy *= -2;
        // Cap speed
        if (Math.abs(dx) >= maxSpeed) {
            dx = Math.copySign(maxSpeed, dx);
        }
        if (Math.abs(dy) >= maxSpeed) {
            dy = Math.copySign(maxSpeed, dy);
        }  
        y = dy;
        return y;
    }
}
