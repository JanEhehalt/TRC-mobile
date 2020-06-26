/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.model;

import com.dungeoncrawler.model.entities.Player;

/**
 *
 * @author jonathan
 */
public class Dungeon {
    private Level level[];
    private Player player;
    
    private Level currentLevel;
    private Room currentRoom;
    private Entity[] currentEntities;
    
    public Dungeon(Player player){
        this.level = new Level[7];
        this.player = player;
    }
    
    public void update(){
        // TODO: Implementieren
    }
    
    /**
     * @return the level
     */
    public Level[] getLevel() {
        return this.level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Level[] level) {
        this.level = level;
    }
    
    public void setLevel(Level level, int i){
        if(i < this.level.length){
            this.level[i] = level;
        }
    }
    
    /**
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the currentLevel
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @param currentLevel the currentLevel to set
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * @return the currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * @param currentRoom the currentRoom to set
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * @return the currentEntities
     */
    public Entity[] getCurrentEntities() {
        return currentEntities;
    }

    /**
     * @param currentEntities the currentEntities to set
     */
    public void setCurrentEntities(Entity[] currentEntities) {
        this.currentEntities = currentEntities;
    }
    
}
