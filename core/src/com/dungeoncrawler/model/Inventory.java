package com.dungeoncrawler.model;

import com.dungeoncrawler.model.items.Key;


public class Inventory {
    
    Item items[];
    int selected;
    int width;
    int height;
    
    public Inventory(){
        items = new Item[8];
        selected = 2;
    }
    
    public void addItem(Item i)
    {
        for(int n = 2; n < items.length; n++){
            if(items[n] == null){
                items[n] = i;
                n = items.length + 1;
                break;
            } 
        }
    }
    
    public void equipSlot(int slot){
        if(selected == 0 || selected == 1){
            Item temp0 = items[selected];
            items[selected] = null;
                addItem(temp0);
        }
        else{
            if(items[selected] == null || items[selected].getId() == 0 || items[selected].getId() == 1){}
            else{
                Item temp = items[slot];
                items[slot] = items[selected];
                items[selected] = temp;
            }
        }
    }
    
    
    public boolean inventoryFull(){
        for(int i = 2; i<items.length; i++){
            if(items[i] == null){
                return false;
            }
        }
        return true;
    }
    
    public void setItem(int x, Item i){
        items[x] = i;
    }
    
   
    
    public boolean checkKey(){
        for(int i = 0; i < items.length; i++){
            if(items[i] != null){
                if(items[i].getId() == 0){
                    return true;
                }
            }
        }
        return false;
    }
    
    public void deleteKey(){
        for(int i = 0; i < items.length; i++){
            if(items[i] != null){
                if(items[i].getId() == 0){
                    items[i] = null;
                    i = items.length + 1;
                }
            }
        }
    }
    
    
    public void scroll(int i){
        selected += i;
    }
    
    public void dropItem(){
        items[selected] = null;
    }
    
    
    public Item getItem(int x){
        return items[x];
    }
    
    public Item[] getItem(){
        return items;
    }
    
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getSelected(){
        return selected;
    }
    public void setSelected(int selected){
        this.selected = selected;
    }
    
}
