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
public class Level {
    private Room[][] rooms;
    private int[] exit;
    
    public Level(int amount){
        this.rooms = new Room[amount][amount];
        exit = new int[2];
    }

    /**
     * @return the rooms
     */
    public Room[][] getRooms() {
        return rooms;
    }

    /**
     * @param rooms the rooms to set
     */
    public void setRooms(Room[][] rooms) {
        this.rooms = rooms;
    }
    
    public void setRoom(Room room, int x, int y){
        if(x < this.rooms.length && y < this.rooms.length){
            this.rooms[x][y] = room;
        }
    }
    
    public int[] getExit(){
        return exit;
    }
    
    public void setExit(int number, int i){
        exit[i] = number;
    }
    
}
