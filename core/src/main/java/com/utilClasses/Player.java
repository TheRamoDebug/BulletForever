package com.utilClasses;

import com.badlogic.gdx.Preferences;

public class Player {

    private static int health;
    private static int levelOfPlayer;
    private static int numberLevel;
    private static int enemiesDeath;
    private static int damage;
    private static boolean deleteLife = false;

    public static void loadInformation() {
        Preferences prefs = SettingsClass.getPrefs();

        enemiesDeath = prefs.getInteger("enemiesDeath", 0);
        numberLevel = prefs.getInteger("numberLevel", 1);

        selectLevel();
    }

    public static void saveInformation() {
        Preferences prefs = SettingsClass.getPrefs();

        prefs.putInteger("enemiesDeath", enemiesDeath);
        prefs.putInteger("numberLevel", numberLevel);

        prefs.flush();
    }

    public static void selectLevel() {
        if (enemiesDeath >= 0) {
            levelOfPlayer = 1;
            damage = 10;
            health = 6;
        }
    }

    public static void setLessHealth() {
        health -= 1;

        deleteLife = true;

        if (health <= 0) {
            health = 0;
        }
    }

    public static boolean canRestLife() {
        return deleteLife;
    }

    public static void changeBool() {
        deleteLife = false;
    }

    public static void score() {
        enemiesDeath += 1;
    }

    public static boolean isAlive() {
        return health > 0;
    }

    public static void setHealth(int newHealth) {
        health = newHealth;
    }

    public static int getDamage() {
        return damage;
    }

    public static int getHealth() {
        return health;
    }

    public static int getEnemiesDeath() {
        return enemiesDeath;
    }

    public static int getNumberLevel() {
        return numberLevel;
    }

    public void managerPlayer() {

    }
}
