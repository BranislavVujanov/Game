/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;

/**
 *
 * @author Branislav Vujanov
 */
public class UI {
    
    GamePanel gamePanel;
   
    public Font font, bigFont, biggerFont;
    String message;
    boolean messageOn = false;
    int messageCounter;
    double time = 10;
    DecimalFormat df = new DecimalFormat("#0.00");
    int frameCounter = 0;
    //Images for non - game states
    public BufferedImage image;
    //cursor
    public int commandNumber = 0;
    
    

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
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
    
     public void resetValues() {
            time = 10;
            frameCounter = 0;
            gamePanel.collisionChecker.collisionCounter = 0;
            messageOn = false;
            gamePanel.npcSetter.setNpc();
            gamePanel.player.getDefaultValues();  
            gamePanel.lightning.active = true;
    }
     
    
    public void drawGameEnvironment (Graphics2D graphics2D){

        graphics2D.setFont(bigFont);
        graphics2D.setColor(Color.red);
        //clock
        if (frameCounter < gamePanel.FPS * 10 && (gamePanel.gameState == gamePanel.level1State
                || gamePanel.gameState == gamePanel.level2State 
                || gamePanel.gameState == gamePanel.level3State)){
            frameCounter++;
            time -= (double) 1/gamePanel.FPS; //Subtracts time each frame
            graphics2D.drawString(df.format(time) + "s", 680, 37);
        } 
        else{
                gamePanel.gameState = gamePanel.timesUpState;
                gamePanel.soundEffect.playSoundEffect(1);
        }
        //message
        if (messageOn){
            graphics2D.drawString(message, 226, 270);
            messageCounter++;
            if (messageCounter >= 90){
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
    
    public void drawTimesUpEnvironment(Graphics2D graphics2D){
        if (gamePanel.previousGameState == gamePanel.level2State)
                    gamePanel.wall.drawTile(graphics2D);
        if (gamePanel.previousGameState == gamePanel.level3State){
                    gamePanel.wall.drawTile(graphics2D);
                    gamePanel.lightning.drawTile(graphics2D);
        }            
        if (frameCounter < gamePanel.FPS * 12 && gamePanel.gameState == gamePanel.timesUpState) {
            frameCounter++;
            graphics2D.setFont(bigFont);
            graphics2D.setColor(Color.white);
            time = 0;
            graphics2D.drawString(df.format(time) + "s", 680, 37);

            graphics2D.setFont(biggerFont);
            graphics2D.setColor(Color.red);
            graphics2D.drawString("NO MOHR TIME…", 170, 235);
            graphics2D.drawString("ONLY DA PAIN", 175, 285);
        } else {
            gamePanel.gameState = gamePanel.lossState;
            gamePanel.previousGameState = -1;
            resetValues();
        }
    }

    public void drawEntities(Graphics2D graphics2D) {
            gamePanel.player.draw(graphics2D);
            for (int i = 0; i < gamePanel.npc.length; i++){ 
                if (gamePanel.npc[i] != null) gamePanel.npc[i].draw(graphics2D);
            } 
            if (gamePanel.gameState == gamePanel.level2State)
                    gamePanel.previousGameState = gamePanel.level2State;
            if (gamePanel.gameState == gamePanel.level3State)
                    gamePanel.previousGameState = gamePanel.level3State;       
    }
    
    public void drawMenuItems(Graphics2D graphics2D){
         graphics2D.drawString("X TERMINATE", 250, 470);
         graphics2D.drawString("KVEET", 250, 530);
         
         if (commandNumber == 0) graphics2D.drawString(">", 250 - gamePanel.tileSize, 470);
         else if (commandNumber == 1) graphics2D.drawString(">", 250 - gamePanel.tileSize, 530);
    }
  
    public void drawInitialScreen(Graphics2D graphics2D) {
        //background color
        graphics2D.setColor(Color.yellow);
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
         //title
         graphics2D.setFont(gamePanel.ui.biggerFont);
         graphics2D.setColor(Color.red);
         graphics2D.drawString("X-TERMINATOR", 180, 60);
         //image
         try {
                image = ImageIO.read(getClass().getResourceAsStream("/image/Terminator default.png"));
                graphics2D.drawImage(image, 30, 128, gamePanel.player.width ,
                                                        gamePanel.player.height, null);           
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
         //intro TXT
         graphics2D.setFont(gamePanel.graphics2D.getFont().deriveFont(14.7F));
         graphics2D.setColor(Color.red);
         graphics2D.drawString("HI, I AM T-800! U MIGHT REMEMBAH ME FROM", 100, 140);
         graphics2D.drawString("ACTION MOVIES SUCH AZZ 'DA TERMINATOR' OR", 100, 160);
         graphics2D.drawString("PERHAPS 'TERMINATOR 2'. WELL, AH AM BACK...", 100, 180);
         
         graphics2D.drawString("DERE ARE TREE RULES:", 30, 230);
         graphics2D.drawString("1. TO VIN, YOU MUST PASS TREE ROUNDZ", 30, 250);
         graphics2D.drawString("2. TO PAHSS AH ROUND - EXTAHMINATE ALL DA X’S", 30, 270);
         graphics2D.drawString("3. EACH ROUND LAHSTS TEN SEKUNDS", 30, 290);
         
         graphics2D.drawString("DA ARROW KEYS, YAH? DAT’S HOW YOU MOVE", 30, 330);
         graphics2D.drawString("TO EXTAHMINATE AN X — MAKE CONTACT VIT EET. ", 30, 350);
         graphics2D.drawString("FAILURE… IZ NOT AN OPTION!", 30, 400);
         
         
         //MENU
         graphics2D.setFont(gamePanel.ui.bigFont);
         drawMenuItems(graphics2D);
    }
    
    public void drawWinScreenImage (Graphics2D graphics2D){
        try {
                resetValues();
                image = ImageIO.read(getClass().getResourceAsStream("/image/ScreenPicture.png"));
                graphics2D.drawImage(image, 0, 0, 768, 576, null);
                graphics2D.setFont(biggerFont);
                graphics2D.setColor(Color.red);
                graphics2D.drawString("ALL X TERMINATED ! ", 130, 100);
                graphics2D.drawString("DA VURLD IZ ZAFE AGAIN", 60, 150);
                
                graphics2D.setFont( bigFont);
                graphics2D.drawString("GIVE ME MOHR! DO IT NAOW", 250, 450);
                if (commandNumber == 0) graphics2D.drawString(">", 250 - gamePanel.tileSize, 450);
                graphics2D.drawString("KVEET", 250, 510);
                if (commandNumber == 1) graphics2D.drawString(">", 250 - gamePanel.tileSize, 510);
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
    }

    public void drawLossScreenImage(Graphics2D graphics2D) { 
        //background color
        graphics2D.setColor(Color.yellow);
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        graphics2D.setFont(biggerFont);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("GAME OVER", 217, 235);
        graphics2D.setFont( bigFont);
        graphics2D.setColor(Color.RED);
        drawMenuItems(graphics2D);
    }
      
    public void drawIntermediateState(Graphics2D graphics2D) {

        if (frameCounter < gamePanel.FPS * 3){
                frameCounter++;
                
                graphics2D.setColor(Color.BLUE);
                graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

                graphics2D.setFont(bigFont);
                graphics2D.setColor(Color.red);
                graphics2D.drawString("AHL KLEE-AHH, BABY!" , 180, 250);
                graphics2D.drawString("DAH NEXT LEVEL STAHTS IN:", 120, 300);
                
                graphics2D.setFont(biggerFont);
                if (frameCounter < gamePanel.FPS * 1)
                    graphics2D.drawString("3", 370, 445);
                else if (frameCounter < gamePanel.FPS * 2)
                    graphics2D.drawString("2", 370, 445);
                else if (frameCounter < gamePanel.FPS * 3)
                    graphics2D.drawString("1", 370, 445);         
            } 
        else {
            resetValues();
            if (gamePanel.previousGameState == gamePanel.level2State)
                gamePanel.gameState = gamePanel.level3State;
            else
                gamePanel.gameState = gamePanel.level2State;
            }
    }
    
    
}
