/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dungeoncrawler.model.Entity;

/**
 *
 * @author jonathan
 */
public class EntitySprite implements Comparable<EntitySprite> {
    
    // 0: pants, 1: body, 2: head, 3: hair, 4: collision
    private Sprite[] sprites;
    private Rectangle collisionSprite;
    private Rectangle fullCollisionSprite;
    private TextureRegion[][][] regions;
    private int[] frames;
    private int attackState;
    private int secondaryAttackState;
    private int die;
    
    // 0: links, 1: rechts
    private int direction;
    
    public EntitySprite(Texture[] textures, int width, int height){
        sprites = new Sprite[1];
        regions = new TextureRegion[1][][];
        
        // 0: idle, 1: walking, 2: attack, 3: die, 4: secondaryAttack
        frames = new int[5];
        direction = 0;
        attackState = 0;
        secondaryAttackState = 0;
        die = 0;
        
        for(int i = 0; i < sprites.length; i++){
            regions[i] = TextureRegion.split(textures[i], width, height);
            sprites[i] = new Sprite(regions[i][0][0]);
            collisionSprite = new Rectangle(0, 0, sprites[0].getWidth() / 2, sprites[0].getHeight() / 4);
        }
        
        this.fullCollisionSprite = new Rectangle(0, 0, collisionSprite.getWidth(), sprites[0].getHeight());
    }
    
    public void updateAnimation(Entity e){
            if(e != null){
                boolean moves = false;
                if(e.getMovementX() != 0 || e.getMovementY() != 0){
                    moves = true;
                }
                direction = e.getDirection();

                if(getDie() >= 1){
                    updateDie();
                }
                else if(attackState == 1){
                    updateFlip();
                    updateAttack();
                }
                else if(secondaryAttackState == 1){
                    updateFlip();
                    updateSecondaryAttack();
                }
                else if(moves){
                    updateFlip();
                    updateWalking();
                }
                else{
                    updateIdle();
                }
        }
    }
    
    public void updateDie(){
        if(die == 2){
            sprites[0].setRegion(regions[0][4][9]);
            updateFlip();
        }
        else if(frames[3] >= 9){
            setDie(2);
        }
        else{
            frames[3]++;
            sprites[0].setRegion(regions[0][4][frames[3]]);
            updateFlip();
        }
    }
    
    public void updateAttack(){
        frames[0] = 0;
        frames[1] = 0;
        frames[4] = 0;
        
        if(frames[2] >= 6){
            frames[2] = 0;
            attackState = 2;
            updateFlip();
        }
        else{
            frames[2]++;
            
            sprites[0].setRegion(regions[0][3][frames[2]]);
            updateFlip();
        }
    }
    
    public void updateSecondaryAttack(){
        frames[0] = 0;
        frames[1] = 0;
        frames[2] = 0;
        
        if(frames[4] >= 3){
            frames[4] = 0;
            secondaryAttackState = 2;
        }
        else{
            frames[4]++;
            
            sprites[0].setRegion(regions[0][1][frames[4]]);
            updateFlip();
        }
    }
    
    public void updateIdle(){
        frames[1] = 0;
        frames[2] = 0;
        frames[4] = 0;
        
        if(frames[0] >= 9){
            frames[0] = 0;
        }
        else{
            frames[0]++;
        }
        
        sprites[0].setRegion(regions[0][2][frames[0]]);
        updateFlip();
    }
    
    public void updateWalking(){
        frames[0] = 0;
        frames[2] = 0;
        frames[4] = 0;
        
        if(frames[1] >= 9){
            frames[1] = 0;
        }
        else{
            frames[1]++;
        }
        
        
        sprites[0].setRegion(regions[0][0][frames[1]]);
        updateFlip();
    }
    
    public void updateFlip(){
        
        if(direction == 0 && !sprites[0].isFlipX()){
            sprites[0].flip(true, false);
        }
        else if(direction == 1 && sprites[0].isFlipX()){
            sprites[0].flip(false, false);
        }
    }
    
    public void update(int xPos, int yPos){
        for(int i = 0; i < sprites.length; i++){
            sprites[i].setPosition(xPos - sprites[0].getWidth() / 4, yPos);
        }
        
        updateCollision(xPos, yPos);
        
    }
    
    public void updateCollision(int xPos, int yPos){
        collisionSprite.setPosition(xPos, yPos);
        getFullCollisionSprite().setPosition(xPos, yPos);
    }
    public void updateCollisionX(int xPos){
        collisionSprite.setX(xPos);
    }
    public void updateCollisionY(int yPos){
        collisionSprite.setY(yPos);
    }

    /**
     * @return the sprites
     */
    public Sprite[] getSprites() {
        return sprites;
    }
    
    /**
     * @param sprites the sprites to set
     */
    public void setSprites(Sprite[] sprites) {
        this.sprites = sprites;
    }

    /**
     * @return the collisionSprite
     */
    public Rectangle getCollisionSprite() {
        return collisionSprite;
    }

    /**
     * @param collisionSprite the collisionSprite to set
     */
    public void setCollisionSprite(Rectangle collisionSprite) {
        this.collisionSprite = collisionSprite;
    }

    /**
     * @return the regions
     */
    public TextureRegion[][][] getRegions() {
        return regions;
    }

    /**
     * @param regions the regions to set
     */
    public void setRegions(TextureRegion[][][] regions) {
        this.regions = regions;
    }

    /**
     * @return the frames
     */
    public int[] getFrames() {
        return frames;
    }

    /**
     * @param frames the frames to set
     */
    public void setFrames(int[] frames) {
        this.frames = frames;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * @return the fullCollisionSprite
     */
    public Rectangle getFullCollisionSprite() {
        return fullCollisionSprite;
    }
    
    public int getAttackState(){
        return this.attackState;
    }
    
    public void resetAttackState(){
        this.attackState = 0;
        frames[2] = 0;
    }
    
    public void startAttack(){
        this.attackState = 1;
    }
    
    public int getSecondaryAttackState(){
        return this.secondaryAttackState;
    }
    
    public void resetSecondaryAttackState(){
        this.secondaryAttackState = 0;
        frames[4] = 0;
    }
    
    public void startSecondaryAttack(){
        this.secondaryAttackState = 1;
    }

    /**
     * @return the die
     */
    public int getDie() {
        return die;
    }

    /**
     * @param die the die to set
     */
    public void setDie(int die) {
        this.die = die;
    }

    @Override
    public int compareTo(EntitySprite t) {
        if(collisionSprite.getY() > t.getCollisionSprite().getY()){
            return -1;
        }
        else if(collisionSprite.getY() == t.getCollisionSprite().getY()){
            return 0;
        }
        else{
            return 1;
        }
    }
}
