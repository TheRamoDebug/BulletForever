package com.utilClasses;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MovementsEnemys {


    public static Vector2 movementType1(Vector2 posicionEnemy, float delta, float oscilacion, float velocityEnemy, float oscilacionFija, boolean isMovementDirection){

        if (posicionEnemy.x > 14f) {
            isMovementDirection = false;
        } else if (posicionEnemy.x < 0f) {
            isMovementDirection = true;
        }

        if (isMovementDirection) {
            posicionEnemy.x += velocityEnemy * delta;
        }

        if (!isMovementDirection) {
            posicionEnemy.x -= velocityEnemy * delta;
        }

        float targetY = (MathUtils.sin(oscilacionFija + oscilacion * velocityEnemy) * 1.5f) + 5f;
        posicionEnemy.y += (targetY - posicionEnemy.y) * 3f * delta;

        return posicionEnemy;
    }

    public static Vector2 movementType2(Vector2 posicionEnemy, float delta, float oscilacion, float velocityEnemy, float oscilacionFija, boolean isMovementDirection){


        if (isMovementDirection) {
            posicionEnemy.x += velocityEnemy * delta;
        }

        if (!isMovementDirection) {
            posicionEnemy.x -= velocityEnemy * delta;
        }

        posicionEnemy.y = (MathUtils.sin(oscilacionFija + oscilacion * velocityEnemy * 4)) + 5f;

        return posicionEnemy;
    }

    public static Vector2 movementType3(Vector2 posicionEnemy, float delta, float oscilacion, float velocityEnemy, float oscilacionFija, boolean isMovementDirection){


        if (isMovementDirection ) {
            posicionEnemy.x += velocityEnemy * delta;
        }

        if (!isMovementDirection) {
            posicionEnemy.x -= velocityEnemy * delta;
        }

        if (MathUtils.sin(oscilacion + oscilacionFija * 16) < -0.9){

            if (posicionEnemy.y < 8f) {
                posicionEnemy.y -= 0.5f + 5f;
            }

        } else if (MathUtils.sin(oscilacion + oscilacionFija * 16) > 0.9) {
            if (posicionEnemy.y > 5f) {
                posicionEnemy.y += 0.5f + 5f;
            }
        }


        return posicionEnemy;
    }


    public static Vector2 movementType4(Vector2 posicionEnemy, float delta, float oscillation, float velocityEnemy, float oscillationFinal, boolean isMovementDirection){


        if (isMovementDirection) {
            posicionEnemy.x += velocityEnemy * delta;
        }

        if (!isMovementDirection) {
            posicionEnemy.x -= velocityEnemy * delta;
        }
        posicionEnemy.y = 7f;

        //posicionEnemy.y = (MathUtils.sin(oscilacionFija + oscilacion * velocityEnemy) * 1.5f) + 5f;

        return posicionEnemy;
    }



}
