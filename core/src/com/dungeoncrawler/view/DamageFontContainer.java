/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.view;

import sun.awt.OSInfo;

/**
 *
 * @author Jan
 */
public class DamageFontContainer {
    private int value;
    private int currentX;
    private int currentY;
    private int lifetime;
    private int currentLifetime;
    
    public DamageFontContainer(int value, int startX, int startY){
        this.value = value;
        this.currentX = startX;
        this.currentY = startY;
        this.lifetime = 60;
        this.currentLifetime = 0;
        
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the currentX
     */
    public int getCurrentX() {
        return currentX;
    }

    /**
     * @param currentX the currentX to set
     */
    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    /**
     * @return the currentY
     */
    public int getCurrentY() {
        return currentY;
    }

    /**
     * @param currentY the currentY to set
     */
    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    /**
     * @return the lifetime
     */
    public int getLifetime() {
        return lifetime;
    }

    /**
     * @param lifetime the lifetime to set
     */
    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    /**
     * @return the lifelength
     */
    public int getCurrentLifetime() {
        return currentLifetime;
    }

    /**
     * @param currentLifetime the lifelength to set
     */
    public void setCurrentLifetime(int currentLifetime) {
        this.currentLifetime = currentLifetime;
    }
}
