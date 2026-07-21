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
import javax.swing.JPanel;
import tile.Tile;

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
    final public int FPS = 120;
    //Sys
    Thread gameThread;
    Keybord key = new Keybord(this);
    NpcSetter npcSetter = new NpcSetter(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public Sound sound = new Sound();
    public Sound soundEffect = new Sound();
    public UI ui = new UI(this);
    Graphics2D graphics2D ;
    //Tiles
    public Tile wall = new Tile(this, "/image/Wall.png", (screenWidth / 2) - (tileSize /2),
                                   (screenHeight / 2) - (tileSize /2) );
    public Tile lightning = new Tile(this, "/image/Lightning.png", 
                                            (screenWidth / 2) - (tileSize /2), (int)(screenHeight * 0.9));
    //Entities
    public Entity player = new Player(this, key);
    public Entity npc [] = new Entity[3];
    //game state
    public int gameState;
    public int previousGameState;
    public final int initialState = 1;
    public final int level1State = 2;
    public final int level2State = 3;
    public final int level3State = 4;
    public final int intermediateState = 5;
    public final int timesUpState = 6;
    public final int lossState = 7;
    public final int winState = 8;
    
    


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }
    
    public void gameSetup(){
        gameState = initialState;
        sound.playMusic(0);
        npcSetter.setNpc();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

  
    @Override
    public void run() {
        long drawInterval = 1000 / FPS; // milliseconds
        long nextDrawTime = System.currentTimeMillis() + drawInterval;

        while (gameThread != null) {
            //UPDATE info (position of entities ...)
            update();
            //DRAW the screen/frame according to updates
            repaint();
            try {
                long remainingTime = nextDrawTime - System.currentTimeMillis();
                Thread.sleep(Math.max(0, remainingTime));
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void update() {
        if (gameState == level1State){
           updateEntity();
        } 
        if (gameState == level2State){
           updateEntity();      
        } 
        if (gameState == level3State){
           updateEntity();
        } 
    }

    private void updateEntity(){
        player.update();
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) npc[i].update();  
        }
    }
    
    
    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        this.graphics2D = (Graphics2D) g; 

        switch (gameState) {
            case initialState:
                ui.drawInitialScreen(graphics2D);
                break;
            case level1State:
                ui.drawGameEnvironment(graphics2D);
                ui.drawEntities(graphics2D);
                break;
            case level2State:
                wall.drawTile(graphics2D);
                ui.drawGameEnvironment(graphics2D);
                ui.drawEntities(graphics2D);
                break;
            case level3State:
                wall.drawTile(graphics2D);
                lightning.drawTile(graphics2D);
                ui.drawGameEnvironment(graphics2D);
                ui.drawEntities(graphics2D);
                break;
            case winState:
                ui.drawWinScreenImage(graphics2D);
                break;
            case lossState:
                ui.drawLossScreenImage(graphics2D);
                break;
            case timesUpState:
                ui.drawEntities(graphics2D);
                ui.drawTimesUpEnvironment(graphics2D); 
                break;
            case intermediateState:
                ui.drawIntermediateState(graphics2D);
        }
        graphics2D.dispose();
    }
   
}
