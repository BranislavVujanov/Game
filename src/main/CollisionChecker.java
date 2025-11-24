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
import entity.Player;
import java.awt.Rectangle;
import tile.Tile;

/**
 *
 * @author Branislav Vujanov
 */
public class CollisionChecker {

    GamePanel gamePanel;
    public int collisionCounter; 
    public boolean collisionOn;
   

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void checkCollisionWithNpc(Player p, Entity[] x) {
 
        if (gamePanel.gameState != gamePanel.level1State && gamePanel.gameState != gamePanel.level2State
               && gamePanel.gameState != gamePanel.level3State) return;
        
        //level passed
        if (collisionCounter == 3) {
            gamePanel.soundEffect.playSoundEffect(3);
            gamePanel.ui.frameCounter = 0;
            if (gamePanel.gameState == gamePanel.level3State) {
                gamePanel.gameState = gamePanel.winState;
                gamePanel.previousGameState = gamePanel.level1State;
            }
            else gamePanel.gameState = gamePanel.intermediateState;
            return;
        }

        for (int i = 0; i < x.length; i++) {
            if (x[i] != null && p.direction != null) {
                // Update player's solidArea based on direction
                Rectangle nextFramePlayerArea = new Rectangle((int) p.x, (int) p.y,
                                          p.solidArea.width, p.solidArea.height);
                switch (p.direction) {
                    case UP -> nextFramePlayerArea.y -= p.dy;
                    case DOWN -> nextFramePlayerArea.y += p.dy;
                    case LEFT -> nextFramePlayerArea.x -= p.dx;
                    case RIGHT -> nextFramePlayerArea.x += p.dx;
                }
                // Update npc's solidArea
                Rectangle npcArea = new Rectangle((int) x[i].x, (int) x[i].y,
                                          x[i].solidArea.width, x[i].solidArea.height); 
                // Check collision
                if (nextFramePlayerArea.intersects(npcArea)) {
                    gamePanel.soundEffect.playSoundEffect(2);
                    gamePanel.ui.displayMessage("TERMINATED!");
                    x[i] = null; // Remove NPC
                    collisionCounter++;
                }
            }
        }            
    }

    
        public boolean checkCollisionWithWall(Tile t) {
            if (gamePanel.gameState == gamePanel.level2State ||gamePanel.gameState == gamePanel.level3State) {

                Rectangle nextFrameX = new Rectangle((int) (gamePanel.player.x + gamePanel.player.dx), 
                        (int) gamePanel.player.y,gamePanel.player.width,gamePanel.player.height);
                if (nextFrameX.intersects(t.solidArea)) {
                        gamePanel.player.dx *= -0.35;
                        return true;
                    }

                Rectangle nextFrameY = new Rectangle((int) gamePanel.player.x,
                        (int) (gamePanel.player.y + gamePanel.player.dy),
                        gamePanel.player.width,gamePanel.player.height);
                if (nextFrameY.intersects(t.solidArea)) {
                        gamePanel.player.dy *= -0.35;
                        return true;
                    }
            }
            return false;
        }
    

    public void checkCollisionWithLightning(Tile t, Entity[] x) {
    if (gamePanel.gameState == gamePanel.level3State && t.active) {
        
        Rectangle playerNextFrame = new Rectangle((int) (gamePanel.player.x + gamePanel.player.dx), 
                (int) (gamePanel.player.y + gamePanel.player.dy),
                gamePanel.player.width,gamePanel.player.height);

        if (playerNextFrame.intersects(t.solidArea)) {
            t.active = false;
            gamePanel.soundEffect.playSoundEffect(4);
            for (int i = 0; i < x.length; i++) {
                if (x[i] == null)
                    continue;
                x[i].acceleration = x[i].acceleration * 0.5; 
                }
            } 
        }
    }
    
}

    

