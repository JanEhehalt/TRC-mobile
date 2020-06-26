package com.dungeoncrawler.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

public class MainMenuScreen{
        
        
    
        //MENU-SCREEN
	
        Sprite startButtonSprite;
        Sprite quitButtonSprite;
        Sprite backgroundSprite;
        Sprite settingsButtonSprite;
        Sprite maleButton;
        Sprite femaleButton;
        
        boolean hidden;
        
       
        //CAMERA
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        OrthographicCamera camera;
        
        
        // Sound
        public Music music;
        
        //PLAYER PREVIEW
        Sprite playerSprite;
        TextureRegion[][][] playerRegion;
        int shownPlayer;
        Timer preview;
        int animationState;
        
        Sprite skinContainer;
        Sprite buttonRight;
        Sprite buttonLeft;
        
        String gender;
        
        
	public MainMenuScreen(float volume) {
            
                //MENU-SCREEN
                w = Gdx.graphics.getWidth();
                h = Gdx.graphics.getHeight();
                float wc = w/2;
                
                gender = "m";
                
                hidden = false;
                
                Texture backgroundTexture = new Texture("sprites/MAINSCREEN.png");
                Texture startButtonTexture = new Texture("sprites/startButton.png");
                Texture quitButtonTexture = new Texture("sprites/quitButton.png");
                Texture settingsButtonTexture = new Texture("sprites/settingsButton.png");
                Texture maleButtonTexture = new Texture("sprites/male.png");
                Texture femaleButtonTexture = new Texture("sprites/female.png");
                
                backgroundSprite = new Sprite(backgroundTexture);
                startButtonSprite = new Sprite(startButtonTexture);
                quitButtonSprite = new Sprite(quitButtonTexture);
                settingsButtonSprite = new Sprite(settingsButtonTexture);
                maleButton = new Sprite(maleButtonTexture);
                femaleButton = new Sprite(femaleButtonTexture);
                
                backgroundSprite.setX(0);
                backgroundSprite.setY(0);
                startButtonSprite.setX(backgroundSprite.getX() + 100);
                startButtonSprite.setY(backgroundSprite.getY() + 350);
                quitButtonSprite.setX(backgroundSprite.getX() + 100);
                quitButtonSprite.setY(backgroundSprite.getY() + 50);
                settingsButtonSprite.setPosition(backgroundSprite.getX() + 100, backgroundSprite.getX() + 150);
                
                
                
                //camera = new OrthographicCamera(1, h/w);
                //camera.translate(backgroundSprite.getWidth()/2, backgroundSprite.getHeight()/2);
                //camera.zoom = 1150f;
                //camera.update();
                
                Pixmap pm = new Pixmap(Gdx.files.internal("sprites/cursor.png"));
                Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
                pm.dispose();
                
                //ENTITIES
                
                //PLAYER PREVIEW
                
                playerRegion = new TextureRegion[7][][];
                shownPlayer = 0;
                animationState = 0;
                
                for(int i = 0; i < playerRegion.length; i++){
                    playerRegion[i] = TextureRegion.split(new Texture("sprites/player/player_"+i+"_"+gender+".png"), 64, 64);
                }
                
                playerSprite = new Sprite(playerRegion[shownPlayer][0][animationState]);
                
                
                skinContainer = new Sprite(new Texture("sprites/skinContainer.png"));
                buttonLeft = new Sprite(new Texture("sprites/left.png"));
                buttonRight = new Sprite(new Texture("sprites/right.png"));
                
                skinContainer.setPosition(backgroundSprite.getX() + 100,backgroundSprite.getY() + 250);
                buttonLeft.setPosition(skinContainer.getX() + 7, skinContainer.getY()+25);
                buttonRight.setPosition(skinContainer.getX() + 106, skinContainer.getY()+25);
                playerSprite.setPosition(skinContainer.getX() + 50, skinContainer.getY() + 15);
                
                maleButton.setPosition(skinContainer.getX()+165, skinContainer.getY());
                femaleButton.setPosition(skinContainer.getX()+165, skinContainer.getY());
                
                preview = new Timer();
                preview.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        if(animationState >= 9){
                            animationState = 0;
                        }
                        else{
                            animationState++;
                        }
                        playerSprite.setRegion(playerRegion[shownPlayer][0][animationState]);
                    }
                },0, 0.1f);
                
                
                // Sound
                music = Gdx.audio.newMusic(Gdx.files.internal("music/mainmenu.mp3"));
                music.setVolume(volume);
                music.play();
                
	}

        
	public void render (SpriteBatch batch) {
		
                
                batch.begin();
                //batch.setProjectionMatrix(camera.combined);
                if(hidden == false){
                    
                    backgroundSprite.draw(batch);
                    startButtonSprite.draw(batch);
                    quitButtonSprite.draw(batch);
                    settingsButtonSprite.draw(batch);
                    skinContainer.draw(batch);
                    buttonLeft.draw(batch);
                    buttonRight.draw(batch);
                    
                    
                    playerSprite.draw(batch);
                    
                    
                    
                    if(gender.equals("m")){
                        maleButton.draw(batch);
                    }
                    else{
                        femaleButton.draw(batch);
                    }
                }
                batch.end();
	}

        
        public int click(int x, int y){     // prueft ob cursor mit button (START) ueberlappt
            if(hidden == false){
                
                Rectangle r = new Rectangle();
                r.set(x, h-y, 1, 1);
                if(Intersector.overlaps(r, startButtonSprite.getBoundingRectangle())){
                    return 0;   //go ingame
                }
                if(Intersector.overlaps(r, quitButtonSprite.getBoundingRectangle())){
                    return 1;   //Exit Game
                }
                if(Intersector.overlaps(r, settingsButtonSprite.getBoundingRectangle())){
                    return 2;   //Settings
                }
                if(Intersector.overlaps(r, buttonLeft.getBoundingRectangle())){
                    if(shownPlayer > 0){
                        shownPlayer--;
                        return 6;
                    }
                    return -1;
                }
                if(Intersector.overlaps(r, buttonRight.getBoundingRectangle())){
                    if(shownPlayer < playerRegion.length - 1){
                        shownPlayer++;
                        return 6;
                    }
                    return -1;
                }
                if(Intersector.overlaps(r, buttonLeft.getBoundingRectangle())){
                    if(shownPlayer > 0){
                        shownPlayer--;
                        return 6;
                    }
                    return -1;
                }
                if(Intersector.overlaps(r, maleButton.getBoundingRectangle())){
                    if(gender.equals("m")){
                        gender = "w";
                        updateGender();
                        return 6;
                    }
                    else{
                        gender = "m";
                        updateGender();
                        return 6;
                    }
                }
            }
            return -1;
            
        }
        
        public void updateGender(){
            for(int i = 0; i < playerRegion.length; i++){
                    playerRegion[i] = TextureRegion.split(new Texture("sprites/player/player_"+i+"_"+gender+".png"), 64, 64);
            }
            playerSprite = new Sprite(playerRegion[shownPlayer][0][animationState]);
            playerSprite.setPosition(skinContainer.getX() + 50, skinContainer.getY() + 15);
        }
        
        public void cleanUp(){
            music.dispose();
        }
        
        public void hide(){
            hidden = true;
        }
        
        public void show(){
            hidden = false;
        }
        
        public boolean getHidden(){
            return hidden;
        }
        
        public int getSkin(){
            return shownPlayer;
        }
        public String getGender(){
            return gender;
        }
        
       
        }
