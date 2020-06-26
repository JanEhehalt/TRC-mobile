package com.dungeoncrawler.model.entities;

import com.dungeoncrawler.StaticMath;
import com.dungeoncrawler.model.Entity;

public class Darkswordsman extends Entity {
    
    public Darkswordsman(float xPos, float yPos, int lvl) {
        super(xPos, yPos, lvl);
        
        this.maxhp = 120*lvl;
        this.hp = this.maxhp;
        this.direction = 1;
        this.dmg = 12*lvl;
        this.id = 26;
        this.type = 0;
        
        // TODO: Sinnvolle Werte finden
        direction = 2;
    }
    
    @Override
    public boolean move(int xPosPlayer, int yPosPlayer){
        if(!isToDelete()){
            double alpha = StaticMath.calculateAngle((int) this.xPos, (int) this.yPos, xPosPlayer, yPosPlayer);

            movementX = (int) (4 * Math.cos(alpha));
            movementY = (int) (4 * Math.sin(alpha));

            xPos += movementX;
            yPos += movementY;

            updateDirection();
        }
        return false;
    }
    
    
}
