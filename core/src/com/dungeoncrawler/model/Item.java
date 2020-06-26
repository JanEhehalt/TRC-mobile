/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.model;

/**
 *
 * @author jonathan
 */
public abstract class Item {
    protected int dmg;
    protected int heal;
    protected int def;
    protected int lvl;
    protected int id;
    protected int extraHp;
    protected float movementBoost;
    
    public Item(int lvl){
        this.lvl = lvl;
    }

    
    public int getId(){
        return this.id;
    }
    public int getDmg(){
        return this.dmg;
    }
    public int getHeal(){
        return this.heal;
    }
    public int getLvl(){
        return this.lvl;
    }
    public int getDef(){
        return this.def;
    }
    public int getExtraHp(){
        return this.extraHp;
    }
    public float getMovementBoost(){
        return this.movementBoost;
    }
    /**
     * @return the amount
     */
    
    
    
}

