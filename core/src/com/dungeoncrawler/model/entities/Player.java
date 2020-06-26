/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.model.entities;

import com.dungeoncrawler.StaticMath;
import com.dungeoncrawler.model.Entity;
import com.dungeoncrawler.model.Inventory;
import com.dungeoncrawler.model.Item;
/**
 *
 * @author Jan
 */
public class Player extends Entity {
    
    float standartDmg;
    float standartMaxHp;
    
    int skin;
    String gender;
    float standartMovementSpeed;
    float movementSpeed;
    
    public Player() {
        super(200, 200, 1);
        this.skin = 0;
        this.lvl = 1;
        this.gender = "m";
        this.maxhp = 50 * lvl;
        this.hp = this.maxhp;
        this.standartMaxHp = 50 * lvl;
        this.standartDef = 2 * lvl;
        this.def = standartDef;
        
        this.dmg = 20*lvl;
        this.standartDmg = this.dmg;
        id = -1;
        type = -1;
        inv = new Inventory();
        // TODO: Sinnvolle Werte finden
        this.targetsPlayer = false;
        
        this.standartMovementSpeed = 3;
        this.movementSpeed = this.standartMovementSpeed;
        
    }
    
    public void updateStats(int ey){
        this.lvl = ey+1;
        this.maxhp = 50 * this.lvl;
        this.standartMaxHp = 50 * this.lvl;
        this.standartDmg = 20 * this.lvl;
        this.dmg = this.standartDmg;
        this.standartDef = 4 * lvl;
        this.def = this.standartDef;
        updateItems();
    }
    
    public void pickUp(Item item){
        inv.addItem(item);
    }
    public Inventory getInv(){
        return inv;
    }

    public void updateItems(){
        this.dmg = this.standartDmg;
        this.def = this.standartDef;
        this.maxhp = this.standartMaxHp;
        this.movementSpeed = this.standartMovementSpeed;
        if(hp > maxhp){
            hp = maxhp;
        }
        
        if(inv.getItem(0) != null && inv.getItem(1) == null){
            this.dmg = this.standartDmg + inv.getItem(0).getDmg();
            this.maxhp = this.standartMaxHp + inv.getItem(0).getExtraHp();
            this.def = this.standartDef + inv.getItem(0).getDef();
            this.movementSpeed = this.standartMovementSpeed + inv.getItem(0).getMovementBoost();
        }
        if(inv.getItem(1) != null && inv.getItem(0) == null){
            this.dmg = this.standartDmg + inv.getItem(1).getDmg();
            this.maxhp = this.standartMaxHp + inv.getItem(1).getExtraHp();
            this.def = this.standartDef + inv.getItem(1).getDef();
            this.movementSpeed = this.standartMovementSpeed + inv.getItem(1).getMovementBoost();
        }
        if(inv.getItem(1) != null &&inv.getItem(0) != null){
            this.dmg = this.standartDmg + inv.getItem(0).getDmg() + inv.getItem(1).getDmg();
            this.maxhp = this.standartMaxHp + inv.getItem(0).getExtraHp()+ inv.getItem(1).getExtraHp();
            this.def = this.standartDef + inv.getItem(0).getDef() + inv.getItem(1).getDef();
            this.movementSpeed = this.standartMovementSpeed + inv.getItem(0).getMovementBoost() + inv.getItem(1).getMovementBoost();
        }
            
            
    }
    
    public void useItem(int x){
        if(inv.getItem(x) != null){
            switch(inv.getItem(x).getId()){
                case 0:
                    // nix lol weil key
                    break;
                case 1:
                    if(hp >= maxhp){
                        
                    }
                    else{
                    if(hp + inv.getItem(x).getHeal() >= maxhp){
                        hp = maxhp;
                    }
                    else{
                        this.hp = hp + inv.getItem(x).getHeal();
                    }
                    inv.setItem(x, null);
                    }
                    break;
                case 2:
                    // nix lol weil amulet
                    break;
            }
            
        }
    }

    public boolean move(int x, int y){
        return false;
    }
    
    @Override
    public Entity shoot(int xPosPlayer, int yPosPlayer){
        Projectile a = null;
        
        if(!isToDelete()){
            double alpha = StaticMath.calculateAngle((int) this.xPos, (int) this.yPos, xPosPlayer, yPosPlayer);
            
            a = new Projectile(this.xPos + 32, this.yPos + 32, this.lvl,(int) this.dmg,  5, false);
            int tempMovementX = (int) (8 * Math.cos(alpha));
            int tempMovementY = (int) (8 * Math.sin(alpha));

            a.setMovementX(tempMovementX + (0.5f * movementX));
            a.setMovementY(tempMovementY + (0.5f * movementY));
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
    
    public boolean checkKey(){
        return inv.checkKey();
    }
    
    public void deleteKey(){
        inv.deleteKey();
    }
    
    public void setSkin(int i){
        skin = i;
    }
    public int getSkin(){
        return skin;
    }
    public void setGender(String i){
        gender = i;
    }
    public String getGender(){
        return gender;
    }
    public boolean inventoryFull(){
        return inv.inventoryFull();
    }
    public float getMovementSpeed(){
        return movementSpeed;
    }
    
    
}
