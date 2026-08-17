package com.utilClasses;

public class Player{
    private static int health;
    private static int levelOfPlayer;
    private static int numberLevel;
    private static int coins;
    private static int damage;





    public static void setLessHealth(float lessHealth) {
        health -= lessHealth;
        if(health <= 0){
            health = 0;
        }
    }


    public static boolean isAlive(){
        return health > 0;
    }

    public static void score(){
        coins += 1;
    }


    public static void setHealth(int newHealth){
        health = newHealth;
    }

    public static float getHealth(){
        return health;
    }

    public static int getCoins(){
        return coins;
    }

    public static int getNumberLevel() {
        return numberLevel;
    }

    public void managerPlayer(){

    }

}
