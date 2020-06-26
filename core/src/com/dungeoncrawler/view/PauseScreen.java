/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author bfz
 */
public class PauseScreen{
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    
    
    BitmapFont font;
    
    Texture volumeButtonTexture;
    Texture volumeUpTexture;
    Texture volumeDownTexture;
    Texture volumeTexture;
    Texture backButtonTexture;
    Texture quitButtonTexture;
    Texture controlsButtonTexture;
    
    Sprite controlsButtonSprite;
    Sprite volumeButtonSprite;
    Sprite volumeUpSprite;
    Sprite volumeDownSprite;
    Sprite volumeSprite;
    Sprite backButtonSprite;
    Sprite quitButtonSprite;
    
    Sprite controlsContainerSprite;
    
    boolean showControls;
    
    public PauseScreen(){
        
            w = Gdx.graphics.getWidth();
            h = Gdx.graphics.getHeight();
            float wc = w/2;
            showControls = false;
            font = new BitmapFont();
            
            controlsButtonTexture = new Texture("sprites/controlsButton.png");
            volumeButtonTexture = new Texture("sprites/volumeButton.png");
            volumeUpTexture = new Texture("sprites/volumeUpButton.png");
            volumeDownTexture = new Texture("sprites/volumeDownButton.png");
            volumeTexture = new Texture("sprites/volume.png");
            backButtonTexture = new Texture("sprites/backButton.png");
            quitButtonTexture = new Texture("sprites/quitButton.png");
            
            controlsButtonSprite = new Sprite(controlsButtonTexture);
            volumeButtonSprite = new Sprite(volumeButtonTexture);
            volumeUpSprite = new Sprite(volumeUpTexture);
            volumeDownSprite = new Sprite(volumeDownTexture);
            volumeSprite = new Sprite(volumeTexture);
            backButtonSprite = new Sprite(backButtonTexture);
            quitButtonSprite = new Sprite(quitButtonTexture);
    
            controlsButtonSprite.setPosition(200, 600);
            volumeButtonSprite.setPosition(200, 500);
            volumeDownSprite.setPosition(volumeButtonSprite.getX() + 230, volumeButtonSprite.getY()+28);
            volumeUpSprite.setPosition(volumeButtonSprite.getX() + 340, volumeButtonSprite.getY()+28);
            backButtonSprite.setPosition(0, 0);
            quitButtonSprite.setPosition(200,300);
            
            controlsContainerSprite = new Sprite(new Texture("sprites/controlsContainer.png"));
            controlsContainerSprite.setPosition(800,250);
                
    }
    
    
    public void render (SpriteBatch batch, float volume, OrthographicCamera camera) {
                
                
                
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
                camera.update();
                batch.setProjectionMatrix(camera.combined);
                volumeButtonSprite.draw(batch);
                volumeUpSprite.draw(batch);
                volumeDownSprite.draw(batch);
                volumeSprite.draw(batch);
                backButtonSprite.draw(batch);
                controlsButtonSprite.draw(batch);
                //quitButtonSprite.draw(batch);
                
                
                if(showControls){
                    controlsContainerSprite.draw(batch);
                    //font.getData().setScale(2);
                    font.draw(batch,"UP:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+380);
                    font.draw(batch,"W", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+380);
                    font.draw(batch,"LEFT:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+360);
                    font.draw(batch,"A", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+360);
                    font.draw(batch,"DOWN:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+340);
                    font.draw(batch,"S", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+340);
                    font.draw(batch,"RIGHT:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+320);
                    font.draw(batch,"D", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+320);

                    font.draw(batch,"SHOOT UP:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+280);
                    font.draw(batch,"ARROW-KEY UP", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+280);
                    font.draw(batch,"SHOOT LEFT:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+260);
                    font.draw(batch,"ARROW-KEY LEFT", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+260);
                    font.draw(batch,"SHOOT DOWN:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+240);
                    font.draw(batch,"ARROW-KEY DOWN", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+240);
                    font.draw(batch,"SHOOT RIGHT:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+220);
                    font.draw(batch,"ARROW-KEY RIGHT", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+220);

                    font.draw(batch,"MELEE ATTACK:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+180);
                    font.draw(batch,"SPACE", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+180);

                    font.draw(batch,"EQUIP ITEM:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+140);
                    font.draw(batch,"R", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+140);
                    font.draw(batch,"DROP ITEM:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+120);
                    font.draw(batch,"Q", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+120);
                    font.draw(batch,"USE ITEM:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+100);
                    font.draw(batch,"E", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+100);
                    font.draw(batch,"SELECT ITEM:", controlsContainerSprite.getX() + 20, controlsContainerSprite.getY()+80);
                    font.draw(batch,"SCROLL WHEEL", controlsContainerSprite.getX() + 220, controlsContainerSprite.getY()+80);
                }
                batch.end();
	}
    
    
    public int click(int x, int y){     // prueft ob cursor mit button (START) ueberlappt
            
            
        
            Rectangle r = new Rectangle();
            r.set(x, h-y, 1600, 900);
            /*
            if(Intersector.overlaps(r, quitButtonSprite.getBoundingRectangle())){
                return 11;   //restart Game
            }
            */
            if(Intersector.overlaps(r, backButtonSprite.getBoundingRectangle())){
                return 5;   //proceed Game
            }
            if(Intersector.overlaps(r, controlsButtonSprite.getBoundingRectangle())){
                if(showControls){
                    showControls = false;
                }
                else{
                    showControls = true;
                }
                return -1;   //controlsScreen
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
