/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 *
 * @author Branislav Vujanov
 */
public abstract class Entity {

    GamePanel gamePanel;
    public double x, y; //coordinates
    double dx, dy; // velocity
    public double acceleration;

    public BufferedImage image;
    
    public int width, height;
    
    public Direction direction;
    int frameCounter ;
    
    public Rectangle solidArea;


    
    
    public Entity(GamePanel gamePanel) {
        
        this.gamePanel = gamePanel;
    }
    
    public abstract void gettDefaultValues();
    public abstract void update();
    public  void draw(Graphics2D graphics2D ){
         graphics2D.drawImage(image, (int) x, (int) y, width, height, null);
    }
    
    public void getImage(String imagePath){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException ex) {
                ex.printStackTrace();
        }
    }
}
