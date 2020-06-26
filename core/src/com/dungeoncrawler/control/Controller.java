
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dungeoncrawler.control;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.dungeoncrawler.view.*;
import com.dungeoncrawler.model.Dungeon;
import com.dungeoncrawler.model.DungeonGenerator;
import com.dungeoncrawler.model.entities.*;
import com.dungeoncrawler.model.Entity;
import com.badlogic.gdx.utils.Timer;
import com.dungeoncrawler.model.ItemContainer;
import java.util.ArrayList;

public class Controller extends ApplicationAdapter implements InputProcessor{
    
    
    SpriteBatch batch;
    Dungeon d;
    DungeonGenerator dg;
    Timer tEntities;
    
    //SCREENS
    
    MainMenuScreen mm;
    GameScreen gs;
    HudContainer hc;
    PauseScreen ps; 
    SettingsScreen ss;
    ControlsScreen cs;
    EndScreen es;
    
    //
    int tileX;
    int tileY;
    
    int roomPosX;
    int roomPosY;
    
    int roomX;
    int roomY;
    
    int level;

    int roomAmount;
    float volume;
    
    
    Timer entityMovement;
    
    boolean isPaused;
    
    int playerSkin;
    
    boolean checkDoor;
    boolean checkDie;
    
    boolean end;
    
    int kills;
    
    @Override
    public void create(){
        
        checkDoor = true;
        checkDie = true;
        
        kills = 0;
        
        end = false;
        
        playerSkin = 0;
        isPaused = false;
        
        volume = 0.05f;
        
        roomX = 10;
        roomY = 6;
        
        batch = new SpriteBatch();
        mm = new MainMenuScreen(volume);
        dg = new DungeonGenerator();
        gs = null;
        cs = null;
        hc = null;
        es = null;
        
        d = dg.generateDungeon(roomX - 1, roomY - 1, 48, new Player());
        dg.ichWillSpielen(d);
        
        tileX = roomX / 2;
        tileY = roomY / 2;
        
        roomAmount = d.getLevel()[0].getRooms().length;
            
        level = 0;

        roomPosX = roomAmount / 2;
        roomPosY = roomAmount / 2;
        
        d.setCurrentLevel(d.getLevel()[level]);
        d.setCurrentRoom(d.getCurrentLevel().getRooms()[roomPosX][roomPosY]);
        d.setCurrentEntities(d.getCurrentRoom().getEnemies());

        Gdx.input.setInputProcessor(this);
        
        
        entityMovement = new Timer();
        
        entityMovement.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                
                if(gs != null){
                    if(gs.player.getAttackState() == 2){
                        playerAttack(d.getCurrentEntities(), d.getPlayer(), d.getPlayer().getDirection());
                    }
                    
                    for(int i = 0; i < d.getCurrentEntities().length; i++){
                        
                        if(d.getCurrentEntities()[i] != null){
                            // Gets the collisions relevant sprites
                            MapObjects mapObjects = gs.getM().getMaps()[level][roomPosX][roomPosY].getMap().getLayers().get(0).getObjects();
                            MapObjects doors = gs.getM().getMaps()[level][roomPosX][roomPosY].getMap().getLayers().get(4).getObjects();
                            Rectangle playerSprite = gs.getPlayer().getFullCollisionSprite();

                            Entity temp = d.getCurrentEntities()[i];

                            int x = (int) temp.getxPos();
                            int y = (int) temp.getyPos();

                            boolean attacks = d.getCurrentEntities()[i].move((int) d.getPlayer().getxPos(), (int) d.getPlayer().getyPos());
                            if(d.getPlayer().isToDelete()){
                                attacks = false;
                            }
                            // Attacke wird gestartet, wenn noch keine laueft
                            if(attacks && gs.entitySprites[i].getAttackState() == 0){
                                gs.entitySprites[i].startAttack();
                            }

                            EntitySprite tempObject = gs.entitySprites[i];
                            tempObject.update((int) temp.getxPos(), (int) temp.getyPos());

                            boolean overlaps = false;
                            boolean delete = false;

                            if(Intersector.overlaps(tempObject.getCollisionSprite(), playerSprite) && temp.isTargetsPlayer()){
                                overlaps = true;
                                
                                if(d.getCurrentEntities()[i].getType() == 2 && d.getCurrentEntities()[i].isTargetsPlayer()){
                                    delete = true;
                                    d.getCurrentEntities()[i].attack(d.getPlayer());
                                    
                                }
                                else{
                                    if(d.getCurrentEntities()[i].getType() == 0 && !d.getPlayer().isToDelete()){
                                        switch(gs.entitySprites[i].getAttackState()){
                                            case 0:
                                                gs.entitySprites[i].startAttack();
                                                break;
                                            case 1:
                                                break;
                                            case 2:
                                                d.getCurrentEntities()[i].attack(d.getPlayer());
                                                gs.entitySprites[i].resetAttackState();
                                                break;
                                            default:
                                        }
                                    }
                                }
                            }
                            else{
                                
                                for(RectangleMapObject rectangleObject : mapObjects.getByType(RectangleMapObject.class)){
                                    Rectangle rectangle = rectangleObject.getRectangle();

                                    if(Intersector.overlaps(tempObject.getCollisionSprite(), rectangle)){
                                        overlaps = true;

                                        if(d.getCurrentEntities()[i].getType() == 2){
                                            delete = true;
                                        }

                                        break;
                                    }
                                }
                                
                                for(RectangleMapObject rectangleObject : doors.getByType(RectangleMapObject.class)){
                                    Rectangle rectangle = rectangleObject.getRectangle();

                                    if(Intersector.overlaps(tempObject.getCollisionSprite(), rectangle)){
                                        overlaps = true;

                                        if(d.getCurrentEntities()[i].getType() == 2){
                                            delete = true;
                                        }

                                        break;
                                    }
                                }

                                if(d.getCurrentEntities()[i].getType() != 2){
                                    for(int j = 0; j < gs.entitySprites.length; j++){
                                        if(i != j){
                                            if(d.getCurrentEntities()[j] != null && d.getCurrentEntities()[j].getType() != 2 && !d.getCurrentEntities()[j].isToDelete()){
                                                if(Intersector.overlaps(tempObject.getCollisionSprite(), gs.entitySprites[j].getCollisionSprite())){
                                                    overlaps = true;
                                                    break;
                                                }   
                                            }
                                        }
                                    }
                                }
                            }
                            
                            if(temp.getType() == 2 && !temp.isTargetsPlayer()){
                                for(int j = 0; j < gs.entitySprites.length; j++){
                                    if(i != j){
                                        if(d.getCurrentEntities()[j] != null && d.getCurrentEntities()[j].getType() != 2 && !d.getCurrentEntities()[j].getToDelete()){
                                            if(Intersector.overlaps(tempObject.getFullCollisionSprite(), gs.entitySprites[j].getFullCollisionSprite())){
                                                delete = true;
                                                boolean isDead = d.getCurrentEntities()[i].attack(d.getCurrentEntities()[j]);
                                                gs.createDmgFont((int) d.getPlayer().getDmg(),(int) d.getCurrentEntities()[j].getxPos(),(int) d.getCurrentEntities()[j].getyPos());
                                                if(isDead){
                                                    gs.entitySprites[j].setDie(1);
                                                    d.getCurrentEntities()[j].setToDelete(true);
                                                    kills++;
                                                }
                                                break;
                                            }   
                                        }
                                    }
                                }
                            }
                            
                            if(d.getCurrentEntities()[i].getType() == 1 && gs.entitySprites[i].getAttackState() == 2){
                                Entity arrow = d.getCurrentEntities()[i].shoot((int) d.getPlayer().getxPos(), (int) d.getPlayer().getyPos());
                                
                                for(int k = 5; k < d.getCurrentEntities().length; k++){
                                    if(d.getCurrentEntities()[k] == null){
                                        d.getCurrentEntities()[k] = arrow;
                                        gs.generateNewEntitySprite(arrow, k);
                                        break;
                                    }
                                }
                            }
                            
                            if(gs.entitySprites[i].getAttackState() == 2){
                                gs.entitySprites[i].resetAttackState();
                            }
                            
                            if(overlaps){
                                d.getCurrentEntities()[i].setxPos(x);
                                d.getCurrentEntities()[i].setyPos(y);

                                tempObject.update(x, y);
                            }

                            gs.entitySprites[i] = tempObject;

                            

                            if(delete || d.getCurrentEntities()[i].isToDelete()){
                                if(d.getCurrentEntities()[i].getType()== 2){
                                    d.getCurrentEntities()[i] = null;
                                    gs.deleteEntitySprite(i);
                                }
                                else{
                                    if(gs.entitySprites[i].getDie() == 0){
                                        gs.entitySprites[i].setDie(1);
                                    }
                                    else if(gs.entitySprites[i].getDie() == 2){
                                        //d.getCurrentEntities()[i] = null;
                                    }
                                }
                            }
                            
                            gs.updateDamageContainer();
                        }
                    }
                    
                    if(!hasEnemies()){
                        gs.getM().getMaps()[level][roomPosX][roomPosY].raiseDoors();
                    }
                }   
            }
        },0, 0.03f);
       
    }
    
    
    @Override
    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(end == true){
            if(es == null){
                isPaused = true;
                entityMovement.stop();
                gs.end();
                gs.getCamera().update();
                batch.setProjectionMatrix(gs.getCamera().combined);
                gs = null;
                hc = null;
                es = new EndScreen(kills);
            }
            es.render(batch, volume);
            return;
        }
        if(mm != null){
            mm.render(batch);
        }
        if(ss != null){
            ss.render(batch, volume);
        }
        if(cs != null){
            cs.render(batch);
        }
        if(ps != null){
            ps.render(batch, volume, gs.getCamera());
        }
        
        //PASSIERT IN GAMESCREEN
        if(gs != null && mm == null && isPaused == false && es == null){
            
                float tempX = d.getPlayer().getxPos();
                float tempY = d.getPlayer().getyPos();
                // Position des Players, etc. werden aktualisiert
                updateObjects(level, roomPosX, roomPosY);
                
                
                // tile[] beinhaltet die x und y Nummer des tiles, in dem sich der Player befindet
                tileX = (int) d.getPlayer().getxPos() / 48;
                tileY = (int) d.getPlayer().getyPos() / 48;
                
                if(tileX == 0 || tileX == roomX || tileY == 0 || tileY == roomY){
                    updateRoom((int) tempX, (int) tempY);
                }
                

                
                // Render methode zum rendern der einzelnen Sprites wird aufgerufen
                if(gs != null){;
                    d.getPlayer().updateItems();
                    gs.render(batch, d.getPlayer(), d.getCurrentEntities(), tileX, tileY, level, roomPosX, roomPosY);
                    hc.updateHud(batch, d.getPlayer());
                }
                
        }
    }
    
    
    @Override
    public void dispose () {
        batch.dispose();
    }
        
    public void updateObjects(int level, int roomPosX, int roomPosY){
        
        MapLayers layers = gs.getM().getMaps()[level][roomPosX][roomPosY].getMap().getLayers();
        MapObjects objects = layers.get(0).getObjects();
        MapObjects exit = layers.get(3).getObjects();
        MapObjects door = layers.get(4).getObjects();
        
        updatePlayer(objects, exit, door);
    }
    
    public void updatePlayer(MapObjects objects, MapObjects exit, MapObjects door){
        
        float x = d.getPlayer().getxPos();
        d.getPlayer().updateX();
        
        gs.getPlayer().updateCollisionX((int) d.getPlayer().getxPos());
        
        for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
            Rectangle rectangle = rectangleObject.getRectangle();
            
            if(Intersector.overlaps(rectangle, gs.getPlayer().getCollisionSprite())){
                
                d.getPlayer().setxPos(x);

            }
        }
        
        for(RectangleMapObject rectangleObject : exit.getByType(RectangleMapObject.class)){
            Rectangle tempDoor = rectangleObject.getRectangle();
            
            if(Intersector.overlaps(gs.getPlayer().getCollisionSprite(), tempDoor) && !d.getPlayer().checkKey()){
                d.getPlayer().setxPos(x);
            }
        }
        
        if(hasEnemies() && checkDoor){
            for(RectangleMapObject rectangleObject : door.getByType(RectangleMapObject.class)){
                Rectangle tempDoor = rectangleObject.getRectangle();

                if(Intersector.overlaps(gs.getPlayer().getCollisionSprite(), tempDoor)){
                    d.getPlayer().setxPos(x);
                }
            }
        }
        
        float y = d.getPlayer().getyPos();
        d.getPlayer().updateY();
        gs.getPlayer().updateCollision((int) d.getPlayer().getxPos(),(int) d.getPlayer().getyPos());
        
        for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
            Rectangle rectangle = rectangleObject.getRectangle();
            
            if(Intersector.overlaps(rectangle, gs.getPlayer().getCollisionSprite())){
                
                d.getPlayer().setyPos(y);

            }
        }
        
        for(RectangleMapObject rectangleObject : exit.getByType(RectangleMapObject.class)){
            Rectangle tempDoor = rectangleObject.getRectangle();
            
            if(Intersector.overlaps(gs.getPlayer().getCollisionSprite(), tempDoor) && !d.getPlayer().checkKey()){
                d.getPlayer().setyPos(y);
                
            }
        }
        
        if(hasEnemies() && checkDoor){
            for(RectangleMapObject rectangleObject : door.getByType(RectangleMapObject.class)){
                Rectangle tempDoor = rectangleObject.getRectangle();

                if(Intersector.overlaps(gs.getPlayer().getCollisionSprite(), tempDoor)){
                    d.getPlayer().setyPos(y);
                }
            }
        }
        
        if(d.getPlayer().getHp() <= 0 && checkDie){
            if(gs.player.getDie() == 0){
               gs.player.setDie(1);
               d.getPlayer().setToDelete(true);
            }
            else if(gs.player.getDie() == 2){
                end = true;
                return;
            }
        }
        
        d.getPlayer().updateDirection();
    }
    
    public void updateRoom(int tempX, int tempY){
        
        
        // Temp variablen werden wieder auf ihre Plätze geschrieben
        
        // Entities
        d.getCurrentRoom().setEnemies(d.getCurrentEntities());
        
        // Room
        d.getCurrentLevel().setRoom(d.getCurrentRoom(), roomPosX, roomPosY);
        
        // Level
        d.setLevel(d.getCurrentLevel(), level);
        
        clearEnemies();
        
        // oben
        if(tileX == (roomX / 2) && tileY == roomY){

            roomPosY += 1;
            d.getPlayer().setxPos((roomX / 2)* 48);
            d.getPlayer().setyPos(48);
        }

        // rechts
        if(tileX == roomX && tileY == (roomY / 2)){

            roomPosX += 1;
            d.getPlayer().setxPos(48);
            d.getPlayer().setyPos((roomY / 2)*48);
        }

        // unten
        if(tileX == (roomX / 2) && tileY == 0){

            roomPosY -= 1;
            d.getPlayer().setxPos((roomX / 2)*48);
            d.getPlayer().setyPos(roomY*48 - 48);
        }

        // links
        if(tileX == 0 && tileY == (roomY / 2)){

            roomPosX -= 1;
            d.getPlayer().setxPos((roomX*48) - 48);
            d.getPlayer().setyPos((roomY / 2)*48);
        }
        
        if(roomPosX == d.getCurrentLevel().getExit()[0] && roomPosY == d.getCurrentLevel().getExit()[1]){
            if(level < 6){
                
                d.getPlayer().deleteKey();
                gs.startUnlockScreen();
                
                d.getPlayer().updateStats(level + 1);
                
                
                level++;
                
                tileX = roomX / 2;
                tileY = roomY / 2;
                
                int roomAmount = d.getLevel()[level].getRooms().length;
                roomPosX = roomAmount / 2;
                roomPosY = roomAmount / 2;
            }
            else{ // Dungeon Exit
                end = true;
                return;
            }
        }
        
        d.setCurrentLevel(d.getLevel()[level]);
        d.setCurrentRoom(d.getCurrentLevel().getRooms()[roomPosX][roomPosY]);
        d.setCurrentEntities(d.getCurrentRoom().getEnemies());
        
        gs.generateEntitySprites(d.getCurrentEntities());
        
        gs.startLoadingScreen();
        
        if(hasEnemies()){
            gs.getM().getMaps()[level][roomPosX][roomPosY].lowerDoors();
        }
    }
    
    
    public ArrayList<ItemContainer> playerPickUp(){
        
        ArrayList<ItemContainer> tempItems = d.getCurrentRoom().getItems();
        ArrayList<AnimatedObject> tempSprites = gs.getM().getMaps()[level][roomPosX][roomPosY].getMapItems();
        ArrayList<ItemContainer> garbageCollector = new ArrayList<>();
        
        for(int i = tempItems.size() - 1; i >= 0; i--){
            if(tempSprites.get(i).getSprite() != null){
                if(Intersector.overlaps(gs.getPlayer().getCollisionSprite(), tempSprites.get(i).getSprite().getBoundingRectangle())){
                    garbageCollector.add(tempItems.get(i));


                    tempItems.remove(i);
                    tempSprites.remove(i);
                }
            }
        }
        
        d.getCurrentRoom().setItems(tempItems);
        
        return garbageCollector;
        
    }
    
    public Entity[] playerAttack(Entity e[], Player p, int attackDirection){
            EntitySprite player = gs.getPlayer();
            EntitySprite[] entitySprites = gs.entitySprites;
            
            if(player.getAttackState() == 0){
                player.startAttack();
            }
            else if(player.getAttackState() == 1){
                player.resetAttackState();
                player.startAttack();
            }
            else if(player.getAttackState() == 2){
                
                Rectangle collision = new Rectangle(0, 0, gs.player.getCollisionSprite().getWidth(), gs.player.getFullCollisionSprite().getHeight());
                
                if(attackDirection == 0){
                    collision.setPosition(p.getxPos() - 32f, p.getyPos());
                }
                else if(attackDirection== 1){
                    collision.setPosition(p.getxPos() + 32f, p.getyPos());
                }

                for(int i = 0; i < e.length ; i++){
                    if(entitySprites[i] != null && e[i] != null){
                        if(Intersector.overlaps(entitySprites[i].getCollisionSprite(), collision)){
                            if(entitySprites[i].getDie() == 0){
                                if(e[i].getHp() - p.getDmg() <= 0){
                                    e[i].setHp(0);
                                    gs.createDmgFont((int) p.getDmg(),(int)e[i].getxPos() + 10,(int) e[i].getyPos() + 20);
                                    e[i].setToDelete(true);
                                    kills++;
                                }
                                else{
                                    e[i].setHp(e[i].getHp() - p.getDmg());
                                    gs.createDmgFont((int) p.getDmg(),(int)e[i].getxPos() + 10,(int) e[i].getyPos() + 20);
                                }
                            }
                        }
                    }
                }
                
                player.resetAttackState();
            }
            
                return e;
        }
        
    @Override
    public boolean keyDown(int keycode) {
            
                if(keycode == Input.Keys.A){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().setMovementX(-d.getPlayer().getMovementSpeed());
                    }
                }
                
                if(keycode == Input.Keys.D){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().setMovementX(d.getPlayer().getMovementSpeed());
                    }
                }
                
                if(keycode == Input.Keys.S){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().setMovementY(-d.getPlayer().getMovementSpeed());
                    }
                } 
                
                if(keycode == Input.Keys.W){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().setMovementY(d.getPlayer().getMovementSpeed());
                    }
                } 
                
                if(keycode == Input.Keys.SPACE){
                    if(mm != null){}
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        d.setCurrentEntities(playerAttack(d.getCurrentEntities(), d.getPlayer(), 0));
                    }
                }
                
                
                
                if(keycode == Input.Keys.F){
                    if(mm != null){}
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        if(!d.getPlayer().inventoryFull()){
                            ArrayList<ItemContainer> garbage = playerPickUp();

                            for(ItemContainer item : garbage){
                                d.getPlayer().getInv().addItem(item.getItem());
                            }
                        }
                    }
                }
                
                if(keycode == Input.Keys.R){
                    if(mm != null){}
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        d.getPlayer().getInv().equipSlot(0);
                        d.getPlayer().updateItems();
                    }
                }
                if(keycode == Input.Keys.T){
                    if(mm != null){}
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        d.getPlayer().getInv().equipSlot(1);
                        d.getPlayer().updateItems();
                    }
                }
                
                
                
                if(keycode == Input.Keys.Q){
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        if(d.getPlayer().getInv().getItem(d.getPlayer().getInv().getSelected()) != null){
                            d.getCurrentRoom().spawnItem((int)d.getPlayer().getxPos(), (int)d.getPlayer().getyPos(), d.getPlayer().getInv().getItem(d.getPlayer().getInv().getSelected()));
                            gs.getM().getMaps()[level][roomPosX][roomPosY].addItem(48, 48,(int)d.getPlayer().getxPos(), (int)d.getPlayer().getyPos(), d.getPlayer().getInv().getItem(d.getPlayer().getInv().getSelected()));
                            d.getPlayer().getInv().dropItem();
                            d.getPlayer().updateItems();
                        }
                    }
                }
                
                if(keycode == Input.Keys.E){
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        d.getPlayer().useItem(d.getPlayer().getInv().getSelected());
                    }
                }
                
                if(keycode == Input.Keys.ESCAPE && !end){
                    if(gs != null && gs.getIsLoading() == false && isPaused == false){
                        stop();
                    }
                    else if(gs != null && gs.getIsLoading() == false && isPaused == true){
                        resume();
                    }
                }
                
                if(keycode == Input.Keys.LEFT){
                    if(mm != null){}
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        Entity lol = d.getPlayer().shoot((int) d.getPlayer().getxPos() - 1, (int) d.getPlayer().getyPos());
                        
                        for(int k = 5; k < d.getCurrentEntities().length; k++){
                            if(d.getCurrentEntities()[k] == null && gs.player.getSecondaryAttackState() != 1){
                                d.getCurrentEntities()[k] = lol;
                                gs.generateNewEntitySprite(lol, k);
                                gs.player.startSecondaryAttack();
                                break;
                            }
                        }
                    }
                }
                
                if(keycode == Input.Keys.UP){
                    if(mm != null){}
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        Entity lol = d.getPlayer().shoot((int) d.getPlayer().getxPos(), (int) d.getPlayer().getyPos() + 1);
                        
                        for(int k = 5; k < d.getCurrentEntities().length; k++){
                            if(d.getCurrentEntities()[k] == null && gs.player.getSecondaryAttackState() != 1){
                                d.getCurrentEntities()[k] = lol;
                                gs.generateNewEntitySprite(lol, k);
                                gs.player.startSecondaryAttack();
                                break;
                            }
                        }
                    }
                }
                if(keycode == Input.Keys.RIGHT){
                    if(mm != null){}
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        Entity lol = d.getPlayer().shoot((int) d.getPlayer().getxPos() + 1, (int) d.getPlayer().getyPos());
                        
                        for(int k = 5; k < d.getCurrentEntities().length; k++){
                            if(d.getCurrentEntities()[k] == null && gs.player.getSecondaryAttackState() != 1){
                                d.getCurrentEntities()[k] = lol;
                                gs.generateNewEntitySprite(lol, k);
                                gs.player.startSecondaryAttack();
                                break;
                            }
                        }
                    }
                }
                if(keycode == Input.Keys.DOWN){
                    if(mm != null){}
                    if(gs != null && gs.getIsLoading() == false && !d.getPlayer().isToDelete()){
                        Entity lol = d.getPlayer().shoot((int) d.getPlayer().getxPos(), (int) d.getPlayer().getyPos() - 1);
                        
                        for(int k = 5; k < d.getCurrentEntities().length; k++){
                            if(d.getCurrentEntities()[k] == null && gs.player.getSecondaryAttackState() != 1){
                                d.getCurrentEntities()[k] = lol;
                                gs.generateNewEntitySprite(lol, k);
                                gs.player.startSecondaryAttack();
                                break;
                            }
                        }
                    }
                }
                if(keycode == Input.Keys.NUM_0){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().getInv().setSelected(0);
                    }
                }
                if(keycode == Input.Keys.NUM_1){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().getInv().setSelected(0);
                    }
                }
                if(keycode == Input.Keys.NUM_2){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().getInv().setSelected(1);
                    }
                }
                if(keycode == Input.Keys.NUM_3){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().getInv().setSelected(2);
                    }
                }
                if(keycode == Input.Keys.NUM_4){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().getInv().setSelected(3);
                    }
                }
                if(keycode == Input.Keys.NUM_5){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().getInv().setSelected(4);
                    }
                }
                if(keycode == Input.Keys.NUM_6){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().getInv().setSelected(5);
                    }
                }
                if(keycode == Input.Keys.NUM_7){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().getInv().setSelected(6);
                    }
                }
                if(keycode == Input.Keys.NUM_8){
                    if(mm != null){
                    }
                    if(gs != null && !d.getPlayer().isToDelete()){
                            d.getPlayer().getInv().setSelected(7);
                    }
                }
                
                return true;
    }

    @Override
    public boolean keyUp(int keycode) {
                if(keycode == Input.Keys.A){
                    if(mm != null){
                    }
                    if(gs != null){
                        if(d.getPlayer().getMovementX() < 0){
                            d.getPlayer().setMovementX(0);
                        }
                    }
                }
                
                if(keycode == Input.Keys.D){
                    if(mm != null){
                    }
                    if(gs != null){
                        if(d.getPlayer().getMovementX() > 0){
                            d.getPlayer().setMovementX(0);
                        }
                    }
                }
                
                if(keycode == Input.Keys.S){
                    if(mm != null){
                    }
                    if(gs != null){
                        if(d.getPlayer().getMovementY() < 0){
                            d.getPlayer().setMovementY(0);
                        }
                    
                    }
                } 
                
                if(keycode == Input.Keys.W){
                    if(mm != null){
                    }
                    if(gs != null){
                        if(d.getPlayer().getMovementY() > 0){
                            d.getPlayer().setMovementY(0);
                        }
                    }
                } 
                
                return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button)
  {
    if(button == Input.Buttons.LEFT){
        switch(click(screenX, screenY)){
            case -1:  // -1: nothing hit -- 0: go ingame -- 1: EXIT game -- 2: goto settings -- 3: goto controls -- 4: goto MainMenuScreen -- 9: volume down -- 10: volume up -- 11: restart game

              return true;
            case 0:
                mm.cleanUp();
                mm = null;
                gs = new GameScreen(d, volume);
                gs.generateEntitySprites(d.getCurrentEntities());
                hc = new HudContainer();
                gs.startLoadingScreen();
                return true;

            case 1:
                mm.cleanUp();
                mm = null;
                gs = new GameScreen(d, volume);
                return true;

            case 2:
                mm.hide();
                cs = null;
                ss = new SettingsScreen();
                return true;

            case 3:
                if(mm != null){
                    mm.hide();
                }
                ss = null;
                cs = new ControlsScreen();
                return true;

            case 4:
                ss = null;
                cs = null;
                mm.show();
                return true;
            
            case 5:
                resume();
                return true;
                
            case 6:
                d.getPlayer().setSkin(mm.getSkin());
                d.getPlayer().setGender(mm.getGender());
                return true;
                
                
            case 9:
                if(volume > 0f){
                    volume -= 0.05f;
                }
                if(mm != null){
                    mm.music.setVolume(volume);
                }
                if(gs != null){
                    gs.music.setVolume(volume);
                }
                return true;   
            case 10:
                if(volume < 1f){
                    volume += 0.05f;
                }
                    
                if(mm != null){
                    mm.music.setVolume(volume);
                }
                if(gs != null){
                    gs.music.setVolume(volume);
                }
                return true;
            case 11:
                create();
                return true;
        }
          
        if(gs != null && gs.getIsLoading() == false){


          return true;
        }
    }
      return true;
    }

    public int click(int x, int y){
        if(es != null){
            return es.click(x, y);
        }
        if(ps != null){
            return ps.click(x,y);
        }
        if(mm != null && mm.getHidden() ==  false){
            return mm.click(x, y);
        }
        if(ss != null){
            return ss.click(x, y);
        }
        if(cs != null){
            return cs.click(x, y);
        }
        if(gs != null && isPaused == true){
        
        }
        return -1;
    }
  
  
    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        if(gs != null){
        if(i == -1 && d.getPlayer().getInv().getSelected() == 0){return true;}
        else if(i == 1 && d.getPlayer().getInv().getSelected() == 7){return true;}
        else{
            d.getPlayer().getInv().scroll(i);
            return true;
            }
        }
        return true;
    }
    
    public void stop(){
        isPaused = true;
        entityMovement.stop();
        gs.stop();
        cs = null;
        ps = new PauseScreen();
    }
    public void resume(){
        isPaused = false;
        entityMovement.start();
        gs.resume();
        
        ps = null;
        gs.startLoadingScreen();
    }
    
    public boolean hasEnemies(){
        for(Entity e : d.getCurrentEntities()){
            if(e != null && !e.isToDelete()){
                if(e.isTargetsPlayer()){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public void clearEnemies(){
        for(int i = 0; i < d.getCurrentEntities().length; i++){
            Entity e = d.getCurrentEntities()[i];
            
            if(e != null && !e.isToDelete()){
                if(!e.isTargetsPlayer()){
                    d.getCurrentEntities()[i] = null;
                    //gs.deleteEntitySprite(i);
                }
            }
        }
    }
}
