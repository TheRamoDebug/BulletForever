package com.clasesUtiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ClassEnemy {
    public float health;
    public int attackProbability;
    public float velocityEnemy;
    public Vector2 posicionEnemy;
    public float sizeEnemy;
    public float randomNumber;
    public float oscilacionFija;


    public Rectangle collisionEnemy;
    public boolean isMovementDirection = true;


    public ClassEnemy(float health,
                      int attackProbability,
                      float velocityEnemy, Vector2 posicionEnemy,
                      float sizeEnemy, float oscilacionFija)
    {
        this.health = health;
        this.attackProbability = attackProbability;
        collisionEnemy = new Rectangle();
        collisionEnemy.set(-100,-100,sizeEnemy / 2f, sizeEnemy / 2f);
        this.velocityEnemy = velocityEnemy;
        this.posicionEnemy = posicionEnemy.cpy();
        this.posicionEnemy.x += 25f;
        this.sizeEnemy = sizeEnemy;
        this.oscilacionFija = oscilacionFija;
    }




    public void newPosition(float delta, float oscilacion){
        if(posicionEnemy.x > 14f){
            isMovementDirection = false;
        } else if (posicionEnemy.x < 0f) {
            isMovementDirection = true;
        }

        if (isMovementDirection == true){
            posicionEnemy.x += velocityEnemy * delta;
        }

        if (isMovementDirection == false){
            posicionEnemy.x -= velocityEnemy * delta;
        }



        posicionEnemy.y = (MathUtils.sin(oscilacionFija + oscilacion * velocityEnemy) * 1.5f) + 5f;



        collisionEnemy.setPosition(posicionEnemy.x, posicionEnemy.y);
    }






    public void drawEnemyAndShot(SpriteBatch batch, Sprite textureEnemy, ControladorBalas c){
        batch.draw(textureEnemy, posicionEnemy.x, posicionEnemy.y,sizeEnemy / 2, sizeEnemy / 2);

        randomNumber = MathUtils.random(1,attackProbability);

        if(randomNumber == 1) {
            c.disparar(posicionEnemy.x, posicionEnemy.y, 1, 0, -2f);
        }

    }



}
