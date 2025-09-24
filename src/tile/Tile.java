/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

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
public class Tile {
    
    public BufferedImage tile;
    public GamePanel gamePanel;
    int x, y;
    public Rectangle solidArea;
    public boolean active = true;
     

    public Tile(GamePanel gamePanel, String imagePath, int x, int y) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        getTileImage(imagePath);
        this.solidArea = new Rectangle( this.x, this.y, gamePanel.tileSize, gamePanel.tileSize);
    }

    public final void getTileImage(String imagePath) {
        try {
            tile = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void drawTile(Graphics2D graphics2D){
        if (active)
        graphics2D.drawImage(tile, x, y, gamePanel.tileSize, gamePanel.tileSize, null);

    }
    
    

}
