/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.model.entities;

import com.dungeoncrawler.model.Entity;
    

public class Projectile extends Entity{
    float xStart;
    float yStart;
    int direction;
    int lifetime;
    
    public Projectile(float xPos, float yPos, int lvl, int dmg, int id, boolean targetsPlayer){
        super(xPos, yPos, lvl);
        xStart = xPos;
        yStart = yPos;
        this.dmg = dmg;
        this.id = id;
        type = 2;
        this.lifetime = 0;
        this.targetsPlayer = targetsPlayer;
    }
    
    public float getxStart(){
        return xStart;
    }
    public float getyStart(){
        return yStart;
    }

    @Override
    public boolean move(int xPosPlayer, int yPosPlayer) {
        lifetime++;
        
        xPos += movementX;
        yPos += movementY;
        
        if(this.lifetime > 50){
            this.setToDelete(true);
        }
        
        return false;
    }
}
