/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import static entity.Direction.DOWN;
import static entity.Direction.LEFT;
import static entity.Direction.RIGHT;
import static entity.Direction.UP;
import entity.Entity;

/**
 *
 * @author Branislav Vujanov
 */
public class CollisionChecker {

    GamePanel gamePanel;
    public int collisionCounter; ;
    

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void checkCollisionWithNpc(Entity e, Entity[] x) {
 
        if (gamePanel.gameState == gamePanel.playState){

           
            if (collisionCounter == 3) {
                gamePanel.playSoundEffect(3);
                collisionCounter = 0;
                gamePanel.gameState = gamePanel.winState;
                gamePanel.ui.resetValues();
            }
            
            if (gamePanel.gameState != gamePanel.playState)collisionCounter = 0;
        
            for (int i = 0; i < x.length; i++) {
                if(x[i] != null){
                //entity solid area position
                e.solidArea.x = (int) e.x;
                e.solidArea.y = (int) e.y;
                //npc's solid area position
                x[i].solidArea.x = (int) x[i].x;
                x[i].solidArea.y = (int) x[i].y;

                if (e.direction != null){
                        switch (e.direction) {
                    case UP:
                        e.solidArea.y -= e.acceleration;
                        if (e.solidArea.intersects(x[i].solidArea)){
                            gamePanel.playSoundEffect(2);
                            gamePanel.ui.displayMessage("TERMINATED!)");
                            x[i] = null;
                            collisionCounter++;
                        }
                        break;  
                    case DOWN:
                        e.y += e.acceleration;
                        if (e.solidArea.intersects(x[i].solidArea)){
                            gamePanel.playSoundEffect(2);
                            gamePanel.ui.displayMessage("TERMINATED :)");
                            x[i] = null;
                            collisionCounter++;
                        }
                        break;
                    case LEFT:
                        e.x -= e.acceleration;
                        if (e.solidArea.intersects(x[i].solidArea)){
                            gamePanel.playSoundEffect(2);
                            gamePanel.ui.displayMessage("TERMINATED :)");
                            x[i] = null;
                            collisionCounter++;
                        }
                        break;
                    case RIGHT:
                        e.x += e.acceleration;
                        if (e.solidArea.intersects(x[i].solidArea)){
                            gamePanel.playSoundEffect(2);
                            gamePanel.ui.displayMessage("TERMINATED :)");
                            x[i] = null;
                            collisionCounter++;
                        }
                        break;
                        }
                    }      
                }
            }          
        }         
    }
    

}
