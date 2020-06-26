/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Timer;
import com.dungeoncrawler.model.Item;
import java.util.ArrayList;

/**
 *
 * @author jonathan
 */
public class MapContainer {
    private TiledMap map;
    private ArrayList<AnimatedObject> objects;
    private ArrayList<AnimatedObject> mapItems;
    private ArrayList<AnimatedObject> doors;
    private boolean doorsDown;
    private EntitySprite[] enemies;
    private Timer doorTimer;
    private int doorState;
    
    public MapContainer(TiledMap map){
        this.map = map;
        this.objects = new ArrayList<>();
        this.mapItems = new ArrayList<>();
        this.enemies = new EntitySprite[15];
        doors = new ArrayList<>();
        doorTimer = new Timer();
        
        // 0: up, 1: animation, 2: down
        doorState = 0;
    }
    
    
    public void addItem(int width, int height, int xPos, int yPos, Item i){
        AnimatedObject ao;
        switch(i.getId()){
            case 0: // key
                ao = new AnimatedObject(new Texture("sprites/key.png"), width, height);
                mapItems.add(ao);
                ao.setSpritePosition(xPos, yPos);
                break;
            case 1: // potion
                ao = new AnimatedObject(new Texture("sprites/potion.png"), width, height);
                mapItems.add(ao);
                ao.setSpritePosition(xPos, yPos);
                break;
            case 2: // amulet
                ao = new AnimatedObject(new Texture("sprites/amulet.png"), width, height);
                mapItems.add(ao);
                ao.setSpritePosition(xPos, yPos);
                break;
            case 3: // DamageAmulet
                ao = new AnimatedObject(new Texture("sprites/dmgAmulet.png"), width, height);
                mapItems.add(ao);
                ao.setSpritePosition(xPos, yPos);
                break;
                
        }
    }
    
    public void lowerDoors(){
        if(doorState == 0){
            doorState = 1;

            for(AnimatedObject door : doors){
                door.setFrame(9);
            }

            doorTimer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    for(AnimatedObject door : doors){
                        door.updateBackwards();
                    }
                    if(doors.get(0).getFrame() == 0){
                        doorState = 2;
                        doorTimer.clear();
                    }
                }
            }, 0, 0.08f);
        }
    }
    
    public void raiseDoors(){
        if(doorState == 2){
            doorState = 1;

            for(AnimatedObject door : doors){
                door.setFrame(0);
            }

            doorTimer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    for(AnimatedObject door : doors){
                        door.updateAnimation();
                    }
                    if(doors.get(0).getFrame() >= 9){
                        doorState = 0;
                        doorTimer.clear();
                    }
                }
            }, 0, 0.08f);
        }
    }

    /**
     * @return the map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(TiledMap map) {
        this.map = map;
    }

    /**
     * @return the objects
     */
    public ArrayList<AnimatedObject> getObjects() {
        return objects;
    }

    /**
     * @param objects the objects to set
     */
    public void setObjects(ArrayList<AnimatedObject> objects) {
        this.objects = objects;
    }
    
    /**
     * @return the mapItems
     */
    public ArrayList<AnimatedObject> getMapItems() {
        return mapItems;
    }
    

    /**
     * @param mapItems the mapItems to set
     */
    public void setMapItems(ArrayList<AnimatedObject> mapItems) {
        this.mapItems = mapItems;
    }

    /**
     * @return the enemies
     */
    public EntitySprite[] getEnemies() {
        return enemies;
    }

    /**
     * @param enemies the enemies to set
     */
    public void setEnemies(EntitySprite[] enemies) {
        this.enemies = enemies;
    }
    
    public void setEnemies(EntitySprite enemy, int i) {
        this.enemies[i] = enemy;
    }

    /**
     * @return the doorsDown
     */
    public boolean isDoorsDown() {
        return doorsDown;
    }

    /**
     * @return the doors
     */
    public ArrayList<AnimatedObject> getDoors() {
        return doors;
    }

    /**
     * @param doors the doors to set
     */
    public void setDoors(ArrayList<AnimatedObject> doors) {
        this.doors = doors;
    }
    
}
