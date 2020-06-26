/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author bfz
 */
public class ControlsScreen {
    float w;
    float h;
    BitmapFont font;
    
    
    Sprite backButtonSprite;
    Sprite controlsContainerSprite;
    
    public ControlsScreen(){
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        float wc = w/2;
        font = new BitmapFont();
    
        backButtonSprite = new Sprite(new Texture("sprites/backButton.png"));
        backButtonSprite.setPosition(0,0);
        controlsContainerSprite = new Sprite(new Texture("sprites/controlsContainer.png"));
        controlsContainerSprite.setPosition(480,180);
        
        
    }
    
    public void render (SpriteBatch batch) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
                batch.begin();
                
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
                
                backButtonSprite.draw(batch);
                batch.end();
	}
    
    public int click(int x, int y){     // prueft ob cursor mit button (START) ueberlappt
            
            Rectangle r = new Rectangle();
            r.set(x, h-y, 1, 1);
            if(Intersector.overlaps(r, backButtonSprite.getBoundingRectangle())){
                return 2;   //go settingScreen
            }
            
            return -1;
            
        }
    
    
    
}
