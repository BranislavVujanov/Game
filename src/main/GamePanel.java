/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;

/**
 *
 * @author Branislav Vujanov
 */
public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    public final int tileSize = 48; //48x48 pixels tile
    public final int noScreenColumns = 16;
    public final int noScreenRows = 12;
    public final int screenWidth = tileSize * noScreenColumns; // 768 pixels
    public final int screenHeight = tileSize * noScreenRows;  //576
    //FPS
    public int FPS = 120;
    
    //Sys
    Thread gameThread;
    Keybord key = new Keybord(this);
    NpcSetter npcSetter = new NpcSetter(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Sound sound = new Sound();
    public Sound soundEffect = new Sound();
    public UI ui = new UI(this);
    Graphics2D graphics2D ;
    //Entities
    Entity player = new Player(this, key);
    public Entity npc [] = new Entity[3];
    
    //game state
    public int gameState;
    public final int initialState = 1;
    public final int playState = 2;
    public final int lossState = 3;
    public final int winState = 4;
    
    //Images for non - game states
    public BufferedImage image;
    
    //cursor
    public int commandNumber = 0;
    

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }
    
    public void gameSetup(){
        gameState = initialState;
        playMusic(0);
        npcSetter.setNpc();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInteval = 1000 / FPS;
        long nextDrawTime = (long) (System.currentTimeMillis() + drawInteval);

        while (gameThread != null) {
            
            //UPDATE info (position of entities ...)
            update();
            //DRAW the screen according to updates
            repaint();

            try {
                long remainingTime = nextDrawTime - System.currentTimeMillis();
                long executingTime = System.currentTimeMillis();
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep(remainingTime);

                nextDrawTime += drawInteval;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameState == playState){
            player.update();
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) npc[i].update(); 
            }  
        } 
       
    }

    @Override
    public void paintComponent  (Graphics g) {
        super.paintComponent(g);
        this.graphics2D = (Graphics2D) g; 
        
        //Title screen
        if (gameState == initialState){
            drawInitialScreen();   
        }
        
        //play mode
         if (gameState == playState){
            drawEntities();
            ui.draw(graphics2D); 
        } 
        //Win
        else if (gameState == winState){
            drawWinScreedImage(graphics2D);
        }
        //Loss
        else if (gameState == lossState){
            drawLossScreenImage();

        }

        
        graphics2D.dispose();
    }
    
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }
    
    public void playSoundEffect(int i){
        soundEffect.setFile(i);
        soundEffect.play();
    }
    
    public void drawWinScreedImage (Graphics2D graphics2D){
        try {
                image = ImageIO.read(getClass().getResourceAsStream("/player/ScreenPicture.png/"));
                graphics2D.drawImage(image, 0, 0, 768, 576, null);
                graphics2D.setFont(ui.biggerFont);
                graphics2D.setColor(Color.red);
                graphics2D.drawString("ALL X TERMINATED :) ", 150, 100);
                
                graphics2D.drawString("MORE PLEASE", 250, 450);
                if (commandNumber == 0) graphics2D.drawString(">", 250 - tileSize, 450);
                graphics2D.drawString("QUIT", 250, 510);
                if (commandNumber == 1) graphics2D.drawString(">", 250 - tileSize, 510);
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
    }

    private void drawInitialScreen() {
        //background color
        graphics2D.setColor(Color.yellow);
        graphics2D.fillRect(0, 0, screenWidth, screenHeight);
         //title
         graphics2D.setFont(ui.biggerFont);
         graphics2D.setColor(Color.red);
         graphics2D.drawString("X-TERMINATOR", 180, 60);
         //image
         try {
                image = ImageIO.read(getClass().getResourceAsStream("/player/Terminator default.png"));
                graphics2D.drawImage(image, 30, 128, player.width , player.height, null);
                
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
         //intro TXT
         graphics2D.setFont(graphics2D.getFont().deriveFont(14F));
         graphics2D.setColor(Color.red);
         graphics2D.drawString("HI, I AM T-800! U MIGHT REMEMBER ME FROM", 100, 140);
         graphics2D.drawString("ACTION MOVIES SUCH AS 'THE TERMINATOR' OR", 100, 160);
         graphics2D.drawString("PERHAPS 'TERMINATOR 2'.  WELL, I AM BACK...", 100, 180);
         
         graphics2D.drawString("THERE ARE 3 RULES:", 30, 230);
         graphics2D.drawString("1.  TO WIN U HAVE TO EXTERMINATE ALL X -S", 30, 250);
         graphics2D.drawString("2. TO EXTERMINE X, MAKE A CONTACT WITH IT", 30, 270);
         graphics2D.drawString("3. U HAVE 10 SECONDS", 30, 290);
         
         graphics2D.drawString("USE ARROW KEYS TO CONTROL THE DIRECTION", 30, 330);
         graphics2D.drawString("LET'S EXTERMINATE THOSE X -S!", 30, 350);
         
         
         //MENU
         graphics2D.setFont(ui.bigFont);
         drawMenuItems();
        
    }

    private void drawLossScreenImage() {
            //background color
            graphics2D.setColor(Color.yellow);
            graphics2D.fillRect(0, 0, screenWidth, screenHeight);
            
            graphics2D.setFont(ui.biggerFont);
            graphics2D.setColor(Color.red);
            graphics2D.drawString("TIME IS UP!" , 217, 150);
            graphics2D.drawString("GAME OVER", 217, 280);
            graphics2D.setFont( ui.bigFont);
            drawMenuItems();
    }

    public void drawMenuItems(){
        graphics2D.drawString("X TERMINATE", 250, 450);
         if (commandNumber == 0) graphics2D.drawString(">", 250 - tileSize, 450);
         graphics2D.drawString("QUIT", 250, 510);
         if (commandNumber == 1) graphics2D.drawString(">", 250 - tileSize, 510);
    }

    public void drawEntities() {
            player.draw(graphics2D);
            for (int i = 0; i < npc.length; i++){ 
                if (npc[i] != null) npc[i].draw(graphics2D);
            }
            
    }
   
}
