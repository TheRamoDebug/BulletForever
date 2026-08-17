package com.utilClasses;

import com.badlogic.gdx.Preferences;

public class Player{
    private static int health;
    private static int levelOfPlayer;
    private static int numberLevel;
    private static int coins;
    private static int damage;


    public static void loadInformation(){
        Preferences prefs = SettingsClass.getPrefs();

        coins = prefs.getInteger("coins", 0);
        health = prefs.getInteger("health", 3);
        numberLevel = prefs.getInteger("numberLevel", 1);
        levelOfPlayer = prefs.getInteger("levelPlayer", 1);
        damage = prefs.getInteger("damage", 1);
    }

    public static void saveInformation(){
        Preferences prefs = SettingsClass.getPrefs();

        prefs.putInteger("coins", coins);
        prefs.putInteger("health", health);
        prefs.putInteger("numberLevel", numberLevel);
        prefs.putInteger("levelPlayer", levelOfPlayer);
        prefs.putInteger("damage", damage);
        prefs.flush();
    }

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
