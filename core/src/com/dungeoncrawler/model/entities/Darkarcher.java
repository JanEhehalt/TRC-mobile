package com.dungeoncrawler.model.entities;

import com.dungeoncrawler.StaticMath;
import com.dungeoncrawler.model.Entity;

public class Darkarcher extends Entity{
    
    int counter;
    final int minRange;
    final int maxRange;
    final int attackSpeed;
    
    public Darkarcher(float xPos, float yPos, int lvl) {
        super(xPos, yPos, lvl);
        
        this.maxhp = 100*lvl;
        this.hp = this.maxhp;
        this.direction = 1;
        this.dmg = 7*lvl;
        this.id = 27;
        this.type = 1;
        minRange = 80;
        maxRange = 240;
        attackSpeed = 100;
        counter = 0;
        // TODO: Sinnvolle Werte finden
        
        direction = 2;
        
    }

    @Override
    public boolean move(int xPosPlayer, int yPosPlayer) {
        if(!isToDelete()){
            
            double alpha = StaticMath.calculateAngle((int) this.xPos, (int) this.yPos, xPosPlayer, yPosPlayer);
            int distance = (int) StaticMath.calculateDistance((int) this.xPos, (int) this.yPos, xPosPlayer, yPosPlayer, alpha);

            if(distance >= minRange && distance <= maxRange && counter % attackSpeed == 0){
                return true;
            }
            else{
                movementX = (int) (4 * Math.cos(alpha));
                movementY = (int) (4 * Math.sin(alpha));

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
        Projectile a = null;
        
        if(!isToDelete()){
            double alpha = StaticMath.calculateAngle((int) this.xPos, (int) this.yPos, xPosPlayer, yPosPlayer);
            
            a = new Projectile(this.xPos + 32, this.yPos + 32, this.lvl,(int) this.dmg,  28, true);
            int tempX = (int) (6 * Math.cos(alpha));
            int tempY = (int) (6 * Math.sin(alpha));

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
