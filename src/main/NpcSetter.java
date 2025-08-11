/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Npc;

/**
 *
 * @author Branislav Vujanov
 */
public class NpcSetter {
    
    GamePanel gamePanel;

    public NpcSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    public void setNpc(){
        gamePanel.npc[0] = new Npc(gamePanel);
        gamePanel.npc[0].x = gamePanel.tileSize * 13;
        gamePanel.npc[0].y = gamePanel.tileSize * 3;
        
        gamePanel.npc[1] = new Npc(gamePanel);
        gamePanel.npc[1].x = gamePanel.tileSize * 13;
        gamePanel.npc[1].y = gamePanel.tileSize * 9;
        
        gamePanel.npc[2] = new Npc(gamePanel);
        gamePanel.npc[2].x = gamePanel.tileSize * 5;
        gamePanel.npc[2].y = gamePanel.tileSize * 9;
    }
    
}
