/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 *
 * @author Branislav Vujanov
 */
public class UI {
    
    GamePanel gamePanel;
   
    public Font font, bigFont, biggerFont;
    String message;
    boolean messageOn = false;
    int messageCouner;
    double time = 10;
    DecimalFormat df = new DecimalFormat("#0.00");
    int frameCounter = 0;
    CollisionChecker collisionChecker;
    

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.collisionChecker = gamePanel.collisionChecker;
        try {
            InputStream in = getClass().getResourceAsStream("/font/Schwarzenegger-203K.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, in);
            bigFont = font.deriveFont(20f);
            biggerFont = font.deriveFont(30f);
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void displayMessage(String message){
        this.message = message;
        messageOn = true;
    }
    
    public void draw (Graphics2D graphics2D){
        graphics2D.setFont(bigFont);
        graphics2D.setColor(Color.red);

        //clock
        if (frameCounter < gamePanel.FPS * 10 && gamePanel.gameState == gamePanel.playState){
            frameCounter++;
            time -= (double) 1/120; //Subtracts time each frame
            graphics2D.drawString(df.format(time) + "s", 680, 37);
        } 
        if (time <= 0){
            gamePanel.gameState = gamePanel.lossState;
            collisionChecker.collisionCounter = 0;
            gamePanel.playSoundEffect(1);
            resetValues();
        }
        
        //message
        if (messageOn){
            graphics2D.drawString(message, 226, 270);
            messageCouner++;
            if (messageCouner >= 90){
                messageCouner = 0;
                messageOn = false;
            }
        }
    }

    public void resetValues() {
            time = 10;
            frameCounter = 0;
            messageOn = false;
            gamePanel.npcSetter.setNpc();
            gamePanel.player.gettDefaultValues();
    }
    
  
}
