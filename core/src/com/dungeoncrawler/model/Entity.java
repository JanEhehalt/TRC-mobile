package com.dungeoncrawler.model;

public abstract class Entity {
    
    protected float xPos;
    protected float yPos;
    protected float hp;
    protected float maxhp;
    protected float dmg;
    protected int lvl;
    protected float movementX;
    protected float movementY;
    protected int id;
    protected int type;
    protected int direction;
    protected Inventory inv;
    private boolean toDelete;
    private double angle;
    protected boolean targetsPlayer;
    protected int standartDef;
    protected int def;
    
    

    public Entity(float xPos, float yPos, int lvl){
        this.xPos = xPos;
        this.yPos = yPos;
        this.lvl = lvl;
        this.movementX = 0;
        this.movementY = 0;
        this.direction = 2;
        this.toDelete = false;
        this.targetsPlayer = true;
        this.standartDef = 0;
        this.def = 0;
    }
    
    public boolean attack(Entity e){
        if(e.getHp() - (this.dmg - e.getDef()) <= 0){
            e.setHp(0);
            e.toDelete = true;
            return true;
        }
        else{
            e.setHp(e.getHp() - (this.dmg - e.getDef()));
            
            /*
            if(e.getMovementX() >= 0){
                e.setMovementX(e.getMovementX() - 2);
            }
            else{
                e.setMovementX(e.getMovementX() + 2);
            }
            
            if(e.getMovementY() >= 0){
                e.setMovementY(e.getMovementY() - 2);
            }
            else{
                e.setMovementY(e.getMovementY() + 2);
            }
            */
            
            return false;
        }
        
        
    }
    
    public Entity shoot(int xPosPlayer, int yPosPlayer){
        return null;
    }
    
    public void update(){
        xPos += movementX;
        yPos += movementY;
    }
    public void updateX(){
        xPos += movementX;
    }
    public void updateY(){
        yPos += movementY;
    }
    public void downgrade(){
        xPos -= movementX;
        yPos -= movementY;
    }
    public void die(){
    
    }
    abstract public boolean move(int xPosPlayer, int yPosPlayer);
    
    public void updateDirection(){
        if(movementX > 1){
            direction = 1;
        }
        else if(movementX < -1){
            direction = 0;
        }
    }
    
    // GETTER + SETTER
    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getMaxhp() {
        return maxhp;
    }

    public void setMaxhp(float maxhp) {
        this.maxhp = maxhp;
    }

    public float getDmg() {
        return dmg;
    }

    public void setDmg(float dmg) {
        this.dmg = dmg;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }
    
    public float getMovementX(){
        return movementX;
    }
    
    public void setMovementX(float movementX){
        this.movementX = movementX;
    }

    public float getMovementY(){
        return movementY;
    }
    
    public void setMovementY(float movementY){
        this.movementY = movementY;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getDirection(){
        return direction;
    }
    
    public void setDirection(int direction){
        this.direction = direction;
    }
    
    public boolean getToDelete(){
        return this.isToDelete();
    }

    /**
     * @return the angle
     */
    public double getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * @return the toDelete
     */
    public boolean isToDelete() {
        return toDelete;
    }

    /**
     * @param toDelete the toDelete to set
     */
    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the targetsPlayer
     */
    public boolean isTargetsPlayer() {
        return targetsPlayer;
    }
    public int getDef(){
        return def;
    }
}