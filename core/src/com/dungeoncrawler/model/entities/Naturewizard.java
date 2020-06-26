/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.model.entities;

import com.dungeoncrawler.StaticMath;
import com.dungeoncrawler.model.Entity;

/**
 *
 * @author jonathan
 */
public class Naturewizard extends Entity{

    int counter;
    final int minRange;
    final int maxRange;
    final int attackSpeed;
    
    public Naturewizard(float xPos, float yPos, int lvl) {
        super(xPos, yPos, lvl);
        
        this.maxhp = 75*lvl;
        this.hp = this.maxhp;
        this.direction = 1;
        this.dmg = 8*lvl;
        this.id = 22;
        this.type = 1;
        minRange = 60;
        maxRange = 150;
        attackSpeed = 80;
        counter = 0;
        // TODO: Sinnvolle Werte finden
        
        direction = 1;
    }

    @Override
    public boolean move(int xPosPlayer, int yPosPlayer) {
        if(!isToDelete()){
            
            double alpha = StaticMath.calculateAngle((int) this.xPos, (int) this.yPos, xPosPlayer + 32, yPosPlayer + 32);
            int distance = (int) StaticMath.calculateDistance((int) this.xPos, (int) this.yPos, xPosPlayer, yPosPlayer, alpha);

            if(distance >= minRange && distance <= maxRange && counter % attackSpeed == 0){
                return true;
            }
            else{
                movementX = (int) (3 * Math.cos(alpha));
                movementY = (int) (3 * Math.sin(alpha));

                if(distance < minRange){
                    movementX *= -1;
                    movementY *= -1;
                }
                else if(distance >= minRange && distance <= maxRange){
                    movementX = 0;
                    movementY = 0;
                }


                xPos += movementX;
                yPos += movementY;
            }

            if(alpha >= Math.PI / -2 && alpha <= Math.PI / 2){
                setDirection(1);
            }
            else{
                setDirection(0);
            }

            counter++;
        }
        
        return false;
    }
    
    @Override
    public Entity shoot(int xPosPlayer, int yPosPlayer){
        Spell a = null;
        
        if(!isToDelete()){
            double alpha = StaticMath.calculateAngle((int) this.xPos, (int) this.yPos, xPosPlayer, yPosPlayer);
            
            a = new Spell(this.xPos + 32, this.yPos + 32, this.lvl,(int) this.dmg, 23, true);
            int tempX = (int) (5 * Math.cos(alpha));
            int tempY = (int) (5 * Math.sin(alpha));

            a.setMovementX(tempX);
            a.setMovementY(tempY);
            a.setAngle(alpha);

            if((alpha >= 0 && alpha <= Math.PI / 2) || (alpha <= 2 * Math.PI && alpha >= 2 * Math.PI - Math.PI / 2)){
                setDirection(1);
            }
            else{
                setDirection(0);
            }
        }
        
        return a;
    }
}
