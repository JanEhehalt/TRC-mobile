/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.model.items;

import com.dungeoncrawler.model.Item;

/**
 *
 * @author jonathan
 */
public class DmgAmulet extends Item {
    
    public DmgAmulet(int lvl) {
        super(lvl);
        this.dmg = 8 * lvl;
        this.movementBoost = 0.5f;
        this.id = 3;
    }
    
}
