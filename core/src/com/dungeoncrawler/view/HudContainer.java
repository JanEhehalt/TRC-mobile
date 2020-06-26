/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dungeoncrawler.model.Item;
import com.dungeoncrawler.model.entities.Player;

/**
 *
 * @author bfz
 */
public class HudContainer {
    Sprite HudSprite;
    Texture[] InventoryItemTextures;
    Sprite[] InventoryItemSprites;
    float[] invXPos;
    float[] invYPos;
    int selected;
    Sprite selectedSprite;
    Sprite playerHealthSprite;
    float playerHealthX;
    float playerHealthY;
    
    BitmapFont font;
    float HudPosX;
    float HudPosY;
    
    Sprite Inventory;
    Sprite healthBarContainer;
    
    public HudContainer(){
        
        
        font = new BitmapFont();
        font.setColor(1, 1, 1, 1);
        Texture selectedTexture = new Texture("sprites/selected.png");
        selectedSprite = new Sprite(selectedTexture);
        Texture HudTexture = new Texture("sprites/hud.png");
        HudSprite = new Sprite(HudTexture);
        HudSprite.setX(-HudSprite.getWidth());
        HudSprite.setY(20f);
        InventoryItemTextures = new Texture[8];
        InventoryItemSprites = new Sprite[8];
        invXPos = new float[8];
        invYPos = new float[8];
        
        Texture inv = new Texture("sprites/hudInventory.png");
        Inventory = new Sprite(inv);
        
        Texture healthContainer = new Texture("sprites/playerHealthBarContainer.png");
        healthBarContainer = new Sprite(healthContainer);
        
        playerHealthSprite = new Sprite();
        
        HudPosX = HudSprite.getX();
        HudPosY = HudSprite.getY();
        
        playerHealthX = HudPosX+36f;
        playerHealthY = HudPosY+260f;
        
        healthBarContainer.setPosition(HudPosX + 8,HudPosY + 252);
        Inventory.setPosition(HudPosX + 7,HudPosY + 7);
        
        float InvX = Inventory.getX();
        float InvY = Inventory.getY();
        
        //Equipped 1
        invXPos[0] = InvX + 30f;
        invYPos[0] = InvY + 105f;
        //Equipped 2
        invXPos[1] = InvX + 78f;
        invYPos[1] = InvY + 105f;

        
        invXPos[2] = InvX + 3f;
        invYPos[2] = InvY + 54f;
        invXPos[3] = InvX + 54f;
        invYPos[3] = InvY + 54f;
        invXPos[4] = InvX + 105f;
        invYPos[4] = InvY + 54f;

        invXPos[5] = InvX + 3f;
        invYPos[5] = InvY + 3f;
        invXPos[6] = InvX + 54f;
        invYPos[6] = InvY + 3f;
        invXPos[7] = InvX + 105f;
        invYPos[7] = InvY + 3f;
        
        selected = 2;
        selectedSprite.setX(invXPos[selected] - 2f);
        selectedSprite.setY(invYPos[selected] - 2f);
        
        
        
    }
    
    public void updateHud(SpriteBatch batch, Player p){
            InventoryItemSprites = new Sprite[8];
                
            selected = p.getInv().getSelected();
            
            Item[] items = p.getInv().getItem();
            
            for(int x = 0; x < items.length; x++){
                    if(items[x] != null){
                        addItem(items[x], x);
                    }
            }
            selectedSprite.setX(invXPos[selected] - 2f);
            selectedSprite.setY(invYPos[selected] - 2f);
            
            float n;
            if(p.getHp() > 0 && p.getMaxhp() != 0){
                n = p.getHp() / p.getMaxhp();
            }
            else{
                n = 0.01f;
            }
            
            if(n > 1){
                n = 1;
            }
            if(n <= 0){
                n = 0.01f;
            }
            Texture playerHealthTexture = new Texture("sprites/playerHealthBar.png");
            int newWidth = (int) (n * playerHealthTexture.getWidth());
            TextureRegion[][] playerHealthRegion = TextureRegion.split(playerHealthTexture,newWidth, playerHealthTexture.getHeight());
            playerHealthSprite = new Sprite(playerHealthRegion[0][0]);
            playerHealthSprite.setPosition(playerHealthX, playerHealthY);
            
            batch.begin();
            HudSprite.draw(batch);
            
            healthBarContainer.draw(batch);
            Inventory.draw(batch);
            
            for(int i = 0; i < InventoryItemSprites.length ;i++){
                    if(InventoryItemSprites[i] != null){
                        InventoryItemSprites[i].draw(batch);
                    }
                }
            selectedSprite.draw(batch);
            playerHealthSprite.draw(batch);
            font.draw(batch, p.getHp() + " / " + p.getMaxhp(), playerHealthSprite.getX()+18, playerHealthSprite.getY()+14);
            if(items[selected] != null){
                
                String selectedName = "";
                int selectedPerkValue = 0;
                String perk = "";
                String lvl = "Lvl: "+ items[selected].getLvl();
                
                switch(items[selected].getId()){
                    case 0:
                        selectedName = "key  ";
                        break;
                    case 1:
                        selectedName = "Potion  ";
                        perk = "Heal: ";
                        selectedPerkValue = items[selected].getHeal();
                        break;
                    case 2:
                        selectedName = "Amulet  ";
                        perk = "Defense: ";
                        selectedPerkValue = items[selected].getDef();
                        break;
                    case 3:
                        selectedName = "Amulet  ";
                        perk = "Damage: ";
                        selectedPerkValue = items[selected].getDmg();
                        break;
                
                }
                font.draw(batch, selectedName + lvl, HudPosX + 40, HudPosY + 210);
                font.draw(batch, perk + selectedPerkValue, HudPosX + 40, HudPosY + 190);
            }
            
            
            batch.end();
            
            
    }
    
    public void addItem(Item item, int x){
            switch(item.getId()){
                case 0:
                    if(InventoryItemSprites[x] == null){
                        InventoryItemTextures[x] = new Texture("sprites/key.png");
                        InventoryItemSprites[x] = new Sprite(InventoryItemTextures[x]);
                        InventoryItemSprites[x].setX(invXPos[x]);
                        InventoryItemSprites[x].setY(invYPos[x]);
                        break;
                    }
                    break;
                case 1:
                    if(InventoryItemSprites[x] == null){
                        InventoryItemTextures[x] = new Texture("sprites/potion.png");
                        InventoryItemSprites[x] = new Sprite(InventoryItemTextures[x]);
                        InventoryItemSprites[x].setX(invXPos[x]);
                        InventoryItemSprites[x].setY(invYPos[x]);
                        break;
                    }
                    break;
                case 2:
                        if(InventoryItemSprites[x] == null){
                            InventoryItemTextures[x]= new Texture("sprites/amulet.png");
                            InventoryItemSprites[x] = new Sprite(InventoryItemTextures[x]);
                            InventoryItemSprites[x].setX(invXPos[x]);
                            InventoryItemSprites[x].setY(invYPos[x]);
                            break;
                        }
                    break;
                case 3:
                        if(InventoryItemSprites[x] == null){
                            InventoryItemTextures[x]= new Texture("sprites/dmgAmulet.png");
                            InventoryItemSprites[x] = new Sprite(InventoryItemTextures[x]);
                            InventoryItemSprites[x].setX(invXPos[x]);
                            InventoryItemSprites[x].setY(invYPos[x]);
                            break;
                        }
                    break;
            }
        }
    
    public void setSelected(int i){
            selected = i;
    }
}
