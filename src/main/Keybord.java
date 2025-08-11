/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Branislav Vujanov
 */
public class Keybord implements KeyListener{
    
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    GamePanel gamePanel;

    public Keybord(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (gamePanel.gameState == gamePanel.initialState || gamePanel.gameState == gamePanel.lossState || gamePanel.gameState == gamePanel.winState){
            switch (code) {
            case KeyEvent.VK_UP:
                gamePanel.commandNumber--;
                if (gamePanel.commandNumber < 0) gamePanel.commandNumber = 1;
                break;
            case KeyEvent.VK_DOWN:
                gamePanel.commandNumber++;
                if (gamePanel.commandNumber > 1) gamePanel.commandNumber = 0;
                break;
            case KeyEvent.VK_ENTER:
                if (gamePanel.commandNumber == 0) gamePanel.gameState = gamePanel.playState;
                if (gamePanel.commandNumber == 1) System.exit(0);
                            
                break;
            }
        }
        
        if (gamePanel.gameState == gamePanel.playState){
            switch (code) {
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break; 
            }
        }
    }

    @Override
    public void keyReleased (KeyEvent e) {
        int code = e.getKeyCode();
        
        switch (code) {
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break; 
        }
    }
    
}
