package com.utilClasses;

public class Player{
    private static float health;
    private static float damage;
    private static int levelOfPlayer;
    private static int numberLevel;




    public static void setLessHealth(float lessHealth) {
        health -= lessHealth;
        if(health <= 0){
            System.out.println("MORISTE");
        }
    }

    public static void setHealth(float newHealth){
        health = newHealth;
    }

    public static float getHealth(){
        return health;
    }


    public void managerPlayer(){

    }

}
