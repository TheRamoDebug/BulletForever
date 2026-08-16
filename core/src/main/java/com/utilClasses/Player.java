package com.utilClasses;

public class Player{
    private static float health;
    private static float damage;
    private static int levelOfPlayer;
    private static int numberLevel;
    private static int coins = 0;




    public static void setLessHealth(float lessHealth) {
        health -= lessHealth;
        if(health <= 0){
            health = 0;
        }
    }

    public static void setHealth(float newHealth){
        health = newHealth;
    }

    public static float getHealth(){
        return health;
    }

    public static boolean isAlive(){
        return health > 0;
    }

    public static void score(){
        coins += 1;
    }

    public static int getCoins(){
        return coins;
    }

    public void managerPlayer(){

    }

}
