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
    public EnemyState currentState = EnemyState.ENTERING;
    public float targetHeight;

    public boolean isMovementDirection = MathUtils.randomBoolean();

    // TUTORIAL WAVE (LVL1)//
    public boolean isTutorialWave;
    public boolean hasShot = false;
    public float leaveTimer = 0f;

    private static final float LEAVE_TIMER = 4f;

    //EXIT MOVEMENT AND CONSTANTS
    private float exitVelocityX = 0f;
    private float exitTargetVelocityX = 0f;
    private static final float EXIT_SMOOTHING = 3f;
    private static final float EXIT_SPEED = 5f;

    private static final float WORLD_HEIGHT = 9f;
    private static final float WORLD_WIDTH = 16f;
    private static final float EXIT_MARGIN = 2f;
    private static final float BULLET_SIZE = 3f;

    public Rectangle collisionEnemy;

    public enum EnemyState {
        ENTERING,
        IN_POSITION,
        LEAVING
    }

    public ClassEnemy(float health,
                      int attackProbability,
                      float velocityEnemy, Vector2 posicionEnemy,
                      float sizeEnemy, float oscilacionFija, int typeOfMovement,
                      boolean isTutorialWave) {
        this.health = health;
        this.attackProbability = attackProbability;
        collisionEnemy = new Rectangle();
        collisionEnemy.set(-100, -100, sizeEnemy / 2f, sizeEnemy / 2f);
        this.velocityEnemy = velocityEnemy;
        this.posicionEnemy = posicionEnemy.cpy();
        this.sizeEnemy = sizeEnemy;
        this.oscilacionFija = oscilacionFija;
        this.typeOfMovement = typeOfMovement;
        this.isTutorialWave = isTutorialWave;
    }


    public void newPosition(float delta, float oscilacion, SpriteBatch batch, Sprite textureEnemy, int typeOfMovement) {

        if (currentState == EnemyState.ENTERING) {
            posicionEnemy.y -= velocityEnemy * delta;

            if (posicionEnemy.y <= targetHeight) {
                posicionEnemy.y = targetHeight;
                currentState = EnemyState.IN_POSITION;
            }

        } else if (currentState == EnemyState.IN_POSITION) {

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

            if (isTutorialWave && hasShot) {
                leaveTimer -= delta;
                if (leaveTimer <= 0f) {
                    startLeaving();
                }
            }

        } else if (currentState == EnemyState.LEAVING) {
            updateLeavingMovement(delta);
        }


        collisionEnemy.setPosition(posicionEnemy.x, posicionEnemy.y);
        batch.draw(textureEnemy, posicionEnemy.x, posicionEnemy.y, sizeEnemy / 2, sizeEnemy / 2);
    }

    private void startLeaving() {
        currentState = EnemyState.LEAVING;
        float screenCenter = WORLD_WIDTH / 2f;
        if(posicionEnemy.x < screenCenter) {
            exitTargetVelocityX =-EXIT_SPEED;
        } else {
            exitTargetVelocityX = EXIT_SPEED;
        }
    }

    private void updateLeavingMovement(float delta) {
        exitVelocityX += (exitTargetVelocityX - exitVelocityX) * EXIT_SMOOTHING * delta;
        posicionEnemy.x += exitVelocityX * delta;
    }

    public boolean isOutOfBounds() {
        return posicionEnemy.x < -EXIT_MARGIN || posicionEnemy.x > WORLD_WIDTH + EXIT_MARGIN
        || posicionEnemy.y < -EXIT_MARGIN || posicionEnemy.y > WORLD_HEIGHT + EXIT_MARGIN;
    }


    public void drawEnemyAndShot(ControllerBullets c, float delta, Vector2 playerPosition) {

        if (isTutorialWave) {
            if (currentState == EnemyState.IN_POSITION && !hasShot) {
                shootAtPlayer(c, playerPosition);
                hasShot = true;
                leaveTimer = LEAVE_TIMER;
            }
            return;
        }
        if (MathUtils.random() < delta * (1d / attackProbability) * 10f) {
            c.shot(posicionEnemy.x, posicionEnemy.y, 3, 0, -7f);
        }
    }

    private void shootAtPlayer(ControllerBullets c, Vector2 playerPosition) {
        Vector2 direction = new Vector2(playerPosition).sub(posicionEnemy);
        direction.nor();

        float bulletSpeed = 6f;
        c.shot(posicionEnemy.x, posicionEnemy.y, BULLET_SIZE, direction.x * bulletSpeed, direction.y * bulletSpeed);

    }
}
