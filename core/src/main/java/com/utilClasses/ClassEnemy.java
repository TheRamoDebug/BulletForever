package com.utilClasses;

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
    public int typeOfMovement;

    public boolean isMovementDirection = false;


    public Rectangle collisionEnemy;


    public ClassEnemy(float health,
                      int attackProbability,
                      float velocityEnemy, Vector2 posicionEnemy,
                      float sizeEnemy, float oscilacionFija, int typeOfMovement) {
        this.health = health;
        this.attackProbability = attackProbability;
        collisionEnemy = new Rectangle();
        collisionEnemy.set(-100, -100, sizeEnemy / 2f, sizeEnemy / 2f);
        this.velocityEnemy = velocityEnemy;
        this.posicionEnemy = posicionEnemy.cpy();
        this.posicionEnemy.x += 50f;
        this.sizeEnemy = sizeEnemy;
        this.oscilacionFija = oscilacionFija;
        this.typeOfMovement = typeOfMovement;
    }


    public void newPosition(float delta, float oscilacion, SpriteBatch batch, Sprite textureEnemy, int typeOfMovement) {

        if (posicionEnemy.x > 14f) {
            isMovementDirection = false;
        } else if (posicionEnemy.x < 0f) {
            isMovementDirection = true;
        }


        switch (typeOfMovement) {
            case 1 -> {
                posicionEnemy = MovementsEnemys.movementType1(posicionEnemy, delta, oscilacion, velocityEnemy, oscilacionFija, isMovementDirection);
            }
            case 2 -> {
                posicionEnemy = MovementsEnemys.movementType2(posicionEnemy, delta, oscilacion, velocityEnemy, oscilacionFija, isMovementDirection);
            }
            case 3 -> {
                posicionEnemy = MovementsEnemys.movementType3(posicionEnemy, delta, oscilacion, velocityEnemy, oscilacionFija, isMovementDirection);
            }
            default -> {

            }
        }


        collisionEnemy.setPosition(posicionEnemy.x, posicionEnemy.y);
        batch.draw(textureEnemy, posicionEnemy.x, posicionEnemy.y, sizeEnemy / 2, sizeEnemy / 2);
    }


    public void drawEnemyAndShot(ControladorBalas c, float delta) {

        randomNumber = MathUtils.random(1, attackProbability);

        if (MathUtils.random() < delta * (1d / attackProbability) * 10f) {
            c.disparar(posicionEnemy.x, posicionEnemy.y, 3, 0, -7f);
        }
    }
}
