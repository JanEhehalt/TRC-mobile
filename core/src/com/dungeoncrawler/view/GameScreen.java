package com.dungeoncrawler.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.dungeoncrawler.model.Dungeon;
import com.dungeoncrawler.model.Entity;
import com.dungeoncrawler.model.entities.*;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;

public class GameScreen {
    
    
        //PLAYER
        public EntitySprite player;
        
        
        //ENTITIES
        public EntitySprite[] entitySprites;
        Entity[] entities;
        
        //DMG visualization
        BitmapFont font;
        DamageFontContainer[] dmgContainer;
        
        //MAP
        private Map m;
        TiledMapRenderer tmr;
        TiledMap tm;
        public ArrayList<AnimatedObject> objects;
        public ArrayList<AnimatedObject> mapItems;
        public ArrayList<AnimatedObject> doors;

        // MiniMap
        ShapeRenderer shapeRenderer;
        Sprite miniMapContainer;

        int mapX = 10;
        int mapY = 310;
        int gap = 2;
        int rectWidth = 15;
        int rectHeight = 10;
        float alpha = 0.5f;
        
        Timer animations;
        Timer animatePlayer;
        
        Timer roomChangeTimer;
        Timer doorUnlockTimer;
        int roomChangeAnimationState;
        boolean roomLoading;
        boolean unlockDoor;
        Texture roomChangeTexture;
        Sprite roomChangeSprite;
        TextureRegion[][] roomChangeTextureRegion;
        int roomChangeRow;
        
        Player p;
        
        HudContainer hc;
        
        // Sound
        public Music music;
        
        //Inventory TEST

        // CONTROLS
        ArrayList<Button> controls;
        int mouseX;
        int mouseY;
        
        float animationSpeed = 0.1f;
        
	public GameScreen(Dungeon d, float volume) {
                //CONTROLS
                
                entities = d.getCurrentEntities();
            
                //PLAYER
                Texture[] playerTexture = new Texture[4];
                String gender = d.getPlayer().getGender();
                
                
                playerTexture[0] = new Texture(Gdx.files.internal("sprites/player/player_"+d.getPlayer().getSkin()+"_"+gender+".png"));
                playerTexture[1] = new Texture(Gdx.files.internal("sprites/player/player_"+d.getPlayer().getSkin()+"_"+gender+".png"));
                playerTexture[2] = new Texture(Gdx.files.internal("sprites/player/player_"+d.getPlayer().getSkin()+"_"+gender+".png"));
                playerTexture[3] = new Texture(Gdx.files.internal("sprites/player/player_"+d.getPlayer().getSkin()+"_"+gender+".png"));
                        
                                
                //DMG visualization
                font = new BitmapFont();
                font.setColor(1, 0, 0, 1);
                dmgContainer = new DamageFontContainer[10];
                
                player = new EntitySprite(playerTexture, 64, 64);
                
                player.update(200, 200);
                
                //ENTITIES
                entitySprites = new EntitySprite[15];
                
                    
                //MAP
                float w = Gdx.graphics.getWidth();
                float h = Gdx.graphics.getHeight();
                
                m = new Map();
                
                
                Texture[] tempTextures = new Texture[d.getLevel().length];
                for(int i = 0; i < tempTextures.length; i++){
                    int j = i+1;
                    tempTextures[i] = new Texture(Gdx.files.internal("tilesets/tileset_floor_" + j + ".png"));
                }
                MapGenerator mg = new MapGenerator(tempTextures);
                
                m = mg.generateMap(d);
                mg.ichWillSpielen(m.getMaps());

                shapeRenderer = new ShapeRenderer();
                miniMapContainer = new Sprite(new Texture(Gdx.files.internal("sprites/miniMapContainer.png")));
                miniMapContainer.setPosition(mapX - 10, mapY - 20);
                
                tm = new TiledMap();
                tmr = new OrthogonalTiledMapRenderer(tm);
                
                music = Gdx.audio.newMusic(Gdx.files.internal("music/gamemusic.mp3"));
                music.setVolume(volume);
                music.play();
                
                animations = new Timer();
                animations.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        if(objects != null){
                            for(AnimatedObject object : objects){
                                object.updateAnimation();        
                            }
                        }
                    }
                },0, 0.1f);
                
                animatePlayer = new Timer();
                animatePlayer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        player.updateAnimation(p);
                        
                        for(int i = 0; i < entitySprites.length; i++){
                            if(entitySprites[i] != null){
                                entitySprites[i].updateAnimation(entities[i]);
                            }
                        }
                        
                    }
                }, 0, animationSpeed);
                
                //Inventory TEST
                
                hc = new HudContainer();
                
                
                
                roomChangeTimer = new Timer();
                roomLoading = false;
                roomChangeSprite = new Sprite();
                roomChangeRow = 0;
                roomChangeSprite.setPosition(0f, 0f);
                
                roomChangeTimer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                       if(roomChangeRow >= 9){
                           roomLoading = false;
                           roomChangeRow = 0;
                           roomChangeTimer.stop();
                       }
                       else{
                           roomChangeRow++;
                       }
                    }
                },0, 0.03f);

                // CONTROLS
                    controls = new ArrayList();
                    int hudX = -160;
                    int hudY = 25;
                    controls.add(new Button("sprites/controls/arrowLeft.png", hudX + 0, hudY + 50, 0));
                    controls.add(new Button("sprites/controls/arrowUp.png", hudX + 50, hudY + 100, 1));
                    controls.add(new Button("sprites/controls/arrowRight.png", hudX + 100, hudY + 50, 2));
                    controls.add(new Button("sprites/controls/arrowDown.png", hudX + 50, hudY + 0, 3));
                    controls.add(new Button("sprites/controls/arrowLeft.png", 550-170, 75, 4));
                    controls.add(new Button("sprites/controls/arrowUp.png", 600-170, 125, 5));
                    controls.add(new Button("sprites/controls/arrowRight.png", 650-170, 75, 6));
                    controls.add(new Button("sprites/controls/arrowDown.png", 600-170, 25, 7));
                    controls.add(new Button("sprites/controls/pickUp.png", 160-110, 30, 8));
                    controls.add(new Button("sprites/controls/equip1.png", 160-110, 120, 9));
                    controls.add(new Button("sprites/controls/drop.png", 160+110, 30, 10));
                    controls.add(new Button("sprites/controls/equip2.png", 160+110, 120, 11));
                    controls.add(new Button("sprites/controls/use.png", 600-170, 200, 12));
                    // INVENTORY
                    controls.add(new Button("sprites/controls/inventorySlot.png", -118, 334, 20));      //0
                    controls.add(new Button("sprites/controls/inventorySlot.png", -69, 334, 21));       //1
                    controls.add(new Button("sprites/controls/inventorySlot.png", -144, 282, 22));      //2
                    controls.add(new Button("sprites/controls/inventorySlot.png", -92, 282, 23));       //3
                    controls.add(new Button("sprites/controls/inventorySlot.png", -42, 282, 24));       //4
                    controls.add(new Button("sprites/controls/inventorySlot.png", -144, 231, 25));      //5
                    controls.add(new Button("sprites/controls/inventorySlot.png", -92, 231, 26));       //6
                    controls.add(new Button("sprites/controls/inventorySlot.png", -42, 231, 27));       //7
                
	}

	public void render (SpriteBatch batch, Player p, Entity[] e, int tileX, int tileY, int level, int roomPosX, int roomPosY, OrthographicCamera camera, int[][] miniMap, boolean touch) {

            shapeRenderer.setProjectionMatrix(camera.combined);

            entities = e;
            
            this.p = p;

            
            //playerMoving = (p.getMovementX() != 0 || p.getMovementY() != 0);

            //setzt player Sprite auf richtige Position
            player.update((int) p.getxPos(), (int) p.getyPos());
            if(p.getMovementX() > 1){
                player.setDirection(1);
                player.updateFlip();
            }
            else if(p.getMovementX() < -1){
                player.setDirection(0);
                player.updateFlip();
            }

            tm = getM().getMaps()[level][roomPosX][roomPosY].getMap();
            objects = getM().getMaps()[level][roomPosX][roomPosY].getObjects(); 
            mapItems = getM().getMaps()[level][roomPosX][roomPosY].getMapItems();
            doors = getM().getMaps()[level][roomPosX][roomPosY].getDoors();

            if(tm != null){
                tmr = new OrthogonalTiledMapRenderer(tm);
            }

            //MAP
            tmr.setView(camera);
            tmr.render();
            
            updateEntitySprites(e);
        
            ArrayList<EntitySprite> temp = new ArrayList<>();
            
            for(EntitySprite entity : entitySprites){
                if(entity != null){
                    temp.add(entity);
                }
            }
            
            EntitySprite[] renderArray = new EntitySprite[temp.size() + 1];
            for(int i = 0; i < renderArray.length; i++){
                if(i == renderArray.length - 1){
                    renderArray[i] = player;
                }
                else{
                    renderArray[i] = temp.get(i);
                }
            }
            
            Arrays.sort(renderArray);
            
            //BATCH
            batch.begin();

            for(AnimatedObject object : objects){
                object.getSprite().draw(batch);
            }

            for(AnimatedObject mapItem : mapItems){
                mapItem.getSprite().draw(batch);
            }
            
            for(AnimatedObject door : doors){
                door.getSprite().draw(batch);
            }
            
            for(EntitySprite eSprite : renderArray){
                eSprite.getSprites()[0].draw(batch);
            }
            
            if(roomLoading == true){
                roomChangeSprite.setRegion(roomChangeTextureRegion[0][roomChangeRow]);
                roomChangeSprite.draw(batch);
            }
            
            for(int x = 0; x < dmgContainer.length; x++){
                if(dmgContainer[x] != null){
                    String text = ""+dmgContainer[x].getValue();
                    font.draw(batch, text, dmgContainer[x].getCurrentX(), dmgContainer[x].getCurrentY());
                }
            }

            if(touch)
            for(Button button : controls){
                button.getSprite().draw(batch);
            }

            miniMapContainer.draw(batch);
            
            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            for(int i = 0; i < miniMap.length; i++){
                for(int j = 0; j < miniMap.length; j++){

                    // current Room
                    if(i == roomPosX && j == roomPosY){
                        shapeRenderer.setColor(0, 0.75f, 0, alpha);
                    }
                    // not found
                    else if(miniMap[i][j] == 0){
                        shapeRenderer.setColor(0, 0, 0, alpha);
                    }
                    // found
                    else if(miniMap[i][j] == 1){
                        shapeRenderer.setColor(0.2f, 0.2f, 0.2f, alpha);
                    }
                    // visited
                    else if(miniMap[i][j] == 2){
                        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, alpha);
                    }

                    if(miniMap[i][j] != 0) {
                        shapeRenderer.rect(i * gap + i * rectWidth + mapX, j * gap + j * rectHeight + mapY, rectWidth, rectHeight);
                        shapeRenderer.setColor(Color.RED);
                    }
                }
            }
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            // BUTTON HITBOXES
/*
            ShapeRenderer lol = new ShapeRenderer();
            lol.setProjectionMatrix(camera.combined);
            lol.begin(ShapeRenderer.ShapeType.Filled);
            for(Button button : controls){
                lol.rect(button.getxPos(), button.getyPos(), button.getWidth(), button.getHeight());
            }
            lol.circle(mouseX,mouseY,15);
            lol.end();
*/


	}
        
        public void generateEntitySprites(Entity[] e){
            entitySprites = new EntitySprite[15];
            
            for(int i = 0; i < e.length; i++){
                generateNewEntitySprite(e[i], i);
            }
        }
        
        public void generateNewEntitySprite(Entity e, int i){
            if(e != null){
                Texture[] tx = new Texture[1];
                
                //nimmt entity ID -> 0 = Archer || 1 = Swordsman || 2 = Arrow || 3 = Wizard
                double g = Math.random();
                String gender;
                if(g >= 0.5){
                    gender = "m";
                }
                else{
                    gender = "w";
                }
                
                switch(e.getId()){
                    case 0:
                        tx[0] = new Texture("sprites/archer/archer_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    
                    case 1:
                        tx[0] = new Texture("sprites/swordsman/swordsman_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                        
                    case 2:
                        tx[0] = new Texture("sprites/projectile/arrow.png");
                        entitySprites[i] = new EntitySprite(tx, 24, 5);
                        break;
                    
                    case 3:
                        tx[0] = new Texture("sprites/wizard/wizard_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                        
                    case 4:
                        tx[0] = new Texture("sprites/spell/spell.png");
                        entitySprites[i] = new EntitySprite(tx, 16, 16);
                        break;
                        
                    case 5:
                        tx[0] = new Texture("sprites/projectile/laser.png");
                        entitySprites[i] = new EntitySprite(tx, 36, 15);
                        break;
                        
                    case 6:
                        tx[0] = new Texture("sprites/wizard/firewizard_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                        
                    case 7:
                        tx[0] = new Texture("sprites/spell/firespell.png");
                        entitySprites[i] = new EntitySprite(tx, 16, 16);
                        break;
                    case 8:
                        tx[0] = new Texture("sprites/wizard/earthwizard_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 9:
                        tx[0] = new Texture("sprites/spell/earthspell.png");
                        entitySprites[i] = new EntitySprite(tx, 16, 16);
                        break;
                    case 10:
                        tx[0] = new Texture("sprites/swordsman/fireswordsman_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 11:
                        tx[0] = new Texture("sprites/archer/icearcher_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 12:
                        tx[0] = new Texture("sprites/projectile/icearrow.png");
                        entitySprites[i] = new EntitySprite(tx, 24, 5);
                        break;
                    case 13:
                        tx[0] = new Texture("sprites/archer/firearcher_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 14:
                        tx[0] = new Texture("sprites/projectile/firearrow.png");
                        entitySprites[i] = new EntitySprite(tx, 24, 5);
                        break;
                    case 15:
                        tx[0] = new Texture("sprites/swordsman/iceswordsman_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 16:
                        tx[0] = new Texture("sprites/wizard/icewizard_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 17:
                        tx[0] = new Texture("sprites/spell/icespell.png");
                        entitySprites[i] = new EntitySprite(tx, 16, 16);
                        break;
                    case 18:
                        tx[0] = new Texture("sprites/wizard/waterwizard_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 19:
                        tx[0] = new Texture("sprites/spell/waterspell.png");
                        entitySprites[i] = new EntitySprite(tx, 16, 16);
                        break;
                    case 20:
                        tx[0] = new Texture("sprites/wizard/healwizard_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 21:
                        tx[0] = new Texture("sprites/spell/healspell.png");
                        entitySprites[i] = new EntitySprite(tx, 16, 16);
                        break;
                    case 22:
                        tx[0] = new Texture("sprites/wizard/naturewizard_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 23:
                        tx[0] = new Texture("sprites/spell/naturespell.png");
                        entitySprites[i] = new EntitySprite(tx, 16, 16);
                        break;
                    case 24:
                        tx[0] = new Texture("sprites/wizard/darkwizard_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 25:
                        tx[0] = new Texture("sprites/spell/darkspell.png");
                        entitySprites[i] = new EntitySprite(tx, 16, 16);
                        break;
                    case 26:
                        tx[0] = new Texture("sprites/swordsman/darkswordsman_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 27:
                        tx[0] = new Texture("sprites/archer/darkarcher_"+gender+".png");
                        entitySprites[i] = new EntitySprite(tx, 64, 64);
                        break;
                    case 28:
                        tx[0] = new Texture("sprites/projectile/darkarrow.png");
                        entitySprites[i] = new EntitySprite(tx, 24, 5);
                        break;
                }

                entitySprites[i].update((int) e.getxPos() + 32, (int) e.getyPos() + 32);
                
                if(e.getType() == 2){
                    entitySprites[i].getSprites()[0].setRotation((float) Math.toDegrees(e.getAngle()));
                }
                
                if(e.isToDelete()){
                    entitySprites[i].setDie(2);
                }
                
                entitySprites[i].updateAnimation(e);
            }
        }
        
        public void updateEntitySprites(Entity[] e){
            for(int i = 0; i < e.length; i++){
                if(e[i] != null){
                    entitySprites[i].update((int) e[i].getxPos(), (int) e[i].getyPos());
                }
            }
        }
        
        public void deleteEntitySprite(int i){
	        entitySprites[i].getSprites()[0].getTexture().dispose();
            entitySprites[i] = null;
        }

        
        public void updateDamageContainer(){
            for(int x = 0; x < dmgContainer.length; x++){
                if(dmgContainer[x] != null){
                    if(dmgContainer[x].getCurrentLifetime() >= dmgContainer[x].getLifetime()){
                        dmgContainer[x] = null;
                    }
                    else{
                        dmgContainer[x].setCurrentLifetime(dmgContainer[x].getCurrentLifetime() + 1);
                        dmgContainer[x].setCurrentY(dmgContainer[x].getCurrentY() + 1);
                    }
                }
            }
        }
        
        public void cleanUp(){
            music.dispose();
            animations.clear();
            animatePlayer.clear();
        }
        
        public void startLoadingScreen(){
            roomChangeSprite = new Sprite(new Texture("sprites/roomChange.png"));
            roomChangeSprite.setPosition(0,0);
            roomChangeTextureRegion = roomChangeSprite.split(528,432);
            roomLoading = true;
            roomChangeTimer.start();
        }
        public void startUnlockScreen(){
            roomChangeSprite = new Sprite(new Texture("sprites/unlock.png"));
            roomChangeSprite.setPosition(400,400);
            roomChangeTextureRegion = roomChangeSprite.split(48,64);
            roomLoading = true;
            roomChangeTimer.start();
        }
        
        //GETTER
        public EntitySprite getPlayer(){
            return player;
        }
        
        public boolean getIsLoading(){
            return roomLoading;
        }

        /**
         * @return the m
         */
        public Map getM() {
            return m;
        }
        
        public void stop(){
            animations.stop();
            animatePlayer.stop();
        }
        public void resume(){
            animations.start();
            animatePlayer.start();
        }
        public void end(){
            animations.stop();
            animatePlayer.stop();
            cleanUp();
        }
        
        public void createDmgFont(int value, int startX, int startY){
            for(int i = 0; i < dmgContainer.length; i++){
                if(dmgContainer[i]== null){
                    dmgContainer[i] = new DamageFontContainer(value, startX, startY);
                    i = dmgContainer.length + 1;
                }
            }
        }
        public ArrayList<Integer> click(int x, int y){
            x = (int)(((float)x) / (float)Gdx.graphics.getWidth() * 700f) -170;
            y = 380- (int)(((float)y) / (float)Gdx.graphics.getHeight() * 380f)+ 25;
            mouseX = x;
            mouseY = y;
            ArrayList<Integer> clicks = new ArrayList();
            Circle mouse = new Circle(x,y,20);
            for(Button button : controls){
                if(Intersector.overlaps(mouse, button.getSprite().getBoundingRectangle())){
                    clicks.add(button.getId());
                }
            }
            return clicks;
        }
}
