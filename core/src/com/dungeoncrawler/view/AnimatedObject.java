/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author jonathan
 */
public class AnimatedObject {
    private Sprite sprite;
    private TextureRegion[][] texture;
    private int frame;
    private int row;
    
    public AnimatedObject(Texture tx, int tileWidth, int tileHeight){
        texture = TextureRegion.split(tx, tileWidth, tileHeight);
        
        frame = (int) (Math.random()*texture[0].length);
        row = (int) (Math.random()*texture.length);
        
        sprite = new Sprite(texture[getRow()][getFrame()]);
    }

    public void updateAnimation(){
        if(getFrame() >= texture[0].length - 1){
            setFrame(0);
        }
        else{
            setFrame(getFrame() + 1);
        }
        
        sprite.setRegion(texture[getRow()][getFrame()]);
        
    }
    
    public void updateTexture(){
        sprite.setRegion(texture[getRow()][getFrame()]);
    }
    
    public void updateBackwards(){
        if(getFrame() <= 0){
            setFrame(texture[0].length - 1);
        }
        else{
            setFrame(getFrame() - 1);
        }
        
        sprite.setRegion(texture[getRow()][getFrame()]);
    }
    
    /**
     * @return the sprite
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * @param sprite the sprite to set
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public void setSpritePosition(int xPos, int yPos){
        sprite.setPosition(xPos, yPos);
    }

    /**
     * @return the texture
     */
    public TextureRegion[][] getTexture() {
        return texture;
    }

    /**
     * @param texture the texture to set
     */
    public void setTexture(TextureRegion[][] texture) {
        this.texture = texture;
    }

    /**
     * @return the frame
     */
    public int getFrame() {
        return frame;
    }

    /**
     * @param frame the frame to set
     */
    public void setFrame(int frame) {
        this.frame = frame;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }
}
