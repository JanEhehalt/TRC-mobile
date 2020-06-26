/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author bfz
 */
public class SettingsScreen {
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    
    Texture controlsButtonTexture;
    Texture volumeButtonTexture;
    Texture volumeUpTexture;
    Texture volumeDownTexture;
    Texture volumeTexture;
    Texture backButtonTexture;
    
    Sprite controlsButtonSprite;
    Sprite volumeButtonSprite;
    Sprite volumeUpSprite;
    Sprite volumeDownSprite;
    Sprite volumeSprite;
    Sprite backButtonSprite;
    
    public SettingsScreen(){
        
            w = Gdx.graphics.getWidth();
            h = Gdx.graphics.getHeight();
            float wc = w/2;
            
            controlsButtonTexture = new Texture("sprites/controlsButton.png");
            volumeButtonTexture = new Texture("sprites/volumeButton.png");
            volumeUpTexture = new Texture("sprites/volumeUpButton.png");
            volumeDownTexture = new Texture("sprites/volumeDownButton.png");
            volumeTexture = new Texture("sprites/volume.png");
            backButtonTexture = new Texture("sprites/backButton.png");
            
            controlsButtonSprite = new Sprite(controlsButtonTexture);
            volumeButtonSprite = new Sprite(volumeButtonTexture);
            volumeUpSprite = new Sprite(volumeUpTexture);
            volumeDownSprite = new Sprite(volumeDownTexture);
            volumeSprite = new Sprite(volumeTexture);
            backButtonSprite = new Sprite(backButtonTexture);
    
            controlsButtonSprite.setPosition(200, 600);
            volumeButtonSprite.setPosition(200, 500);
            volumeDownSprite.setPosition(volumeButtonSprite.getX() + 230, volumeButtonSprite.getY()+28);
            volumeUpSprite.setPosition(volumeButtonSprite.getX() + 340, volumeButtonSprite.getY()+28);
            backButtonSprite.setPosition(0, 0);
    }
    
    
    public void render (SpriteBatch batch, float volume) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                
                
                
                
                float n = 0;
                if(volume != 0){
                n = volume / 1f;
                }
                
                int newWidth = (int) (n * volumeTexture.getWidth());
                if(newWidth == 0){
                TextureRegion[][] volumeRegion = TextureRegion.split(volumeTexture,1, volumeTexture.getHeight());
                volumeSprite = new Sprite(volumeRegion[0][0]);
                volumeSprite.setPosition(-5,-5);
                }
                else{
                TextureRegion[][] volumeRegion = TextureRegion.split(volumeTexture,newWidth, volumeTexture.getHeight());
                volumeSprite = new Sprite(volumeRegion[0][0]);
                volumeSprite.setPosition(volumeButtonSprite.getX() + 278, volumeButtonSprite.getY()+34);
                }
                
                
                batch.begin();
                controlsButtonSprite.draw(batch);
                volumeButtonSprite.draw(batch);
                volumeUpSprite.draw(batch);
                volumeDownSprite.draw(batch);
                volumeSprite.draw(batch);
                backButtonSprite.draw(batch);
                batch.end();
	}
    
    
    public int click(int x, int y){     // prueft ob cursor mit button (START) ueberlappt
            
            
        
            Rectangle r = new Rectangle();
            r.set(x, h-y, 1, 1);
            if(Intersector.overlaps(r, controlsButtonSprite.getBoundingRectangle())){
                return 3;   //ControlsScreen
            }
            if(Intersector.overlaps(r, backButtonSprite.getBoundingRectangle())){
                return 4;   //MainMenuScreen
            }
            if(Intersector.overlaps(r, volumeDownSprite.getBoundingRectangle())){
                return 9;   //volume DOWN
            }
            if(Intersector.overlaps(r, volumeUpSprite.getBoundingRectangle())){
                return 10;   //volume UP
            }
            
            return -1;
            
        }
    
    
}
