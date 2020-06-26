/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.model.entities;

import com.dungeoncrawler.StaticMath;
import com.dungeoncrawler.model.Entity;
    

public class Spell extends Entity{
    float xStart;
    float yStart;
    int direction;
    int lifetime;
    
    public Spell(float xPos, float yPos, int lvl, int dmg, int id, boolean targetsPlayer){
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
        
        double alpha = StaticMath.calculateAngle((int) this.xPos, (int) this.yPos, xPosPlayer, yPosPlayer);

        movementX = (int) (5 * Math.cos(alpha));
        movementY = (int) (5 * Math.sin(alpha));

        xPos += movementX;
        yPos += movementY;

        updateDirection();
        
        if(this.lifetime > 50){
            this.setToDelete(true);
        }
        
        return false;
    }
}
