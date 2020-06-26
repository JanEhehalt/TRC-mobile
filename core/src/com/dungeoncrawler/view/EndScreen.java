/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.view;

import com.badlogic.gdx.Gdx;
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
 * @author Jan
 */
public class EndScreen {
        float w;
        float h;
    
        Sprite restartButton;
        
        BitmapFont font;
        
        int kills;
    
    
    public EndScreen(int kills){
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        float wc = w/2;
        
        this.kills = kills;
        
        font = new BitmapFont();
            
        restartButton = new Sprite(new Texture("sprites/restartButton.png"));
        restartButton.setPosition(w / 2 - restartButton.getWidth() / 2, h / 4 - restartButton.getHeight() / 2);
    }
        
    public void render (SpriteBatch batch, float volume) {
                
                batch.begin();
                restartButton.draw(batch);
                font.getData().setScale(5);
                font.draw(batch, "ENDE", w/2-100, h-h/8);
                font.getData().setScale(2);
                font.draw(batch, "Kills: "+kills, restartButton.getX()+30, restartButton.getY() + 200);
                batch.end();
	}
    
    public int click(int x, int y){
            
            Rectangle r = new Rectangle(x,h-y,1,1);
            if(Intersector.overlaps(r, restartButton.getBoundingRectangle())){
                return 11;   //NEUSTART
            }
            return -1;
            
    }
}
