
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dungeoncrawler.view;

/**
 *
 * @author jonathan
 */
public class Map {
    private MapContainer[][][] maps;
    
    public Map(){
        
    }

    /**
     * @return the maps
     */
    public MapContainer[][][] getMaps() {
        return maps;
    }

    /**
     * @param maps the maps to set
     */
    public void setMaps(MapContainer[][][] maps) {
        this.maps = maps;
    }
    
}
