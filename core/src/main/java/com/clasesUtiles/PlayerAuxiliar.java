package com.clasesUtiles;

public class PlayerAuxiliar {
    private static float health;

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

}
