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
    public Vector2 positionEnemy;
    public float sizeEnemy;
    public float randomNumber;
    public float oscillation;
    public int typeOfMovement;
    public int typeOfEnemy;

    public enum ShotPattern {
        NONE,
        TARGETED,
        RADIAL
    }
    public ShotPattern shotPattern;

    public EnemyState currentState = EnemyState.ENTERING;
    public float targetHeight;
    public boolean isMovementDirection = true;
    public int waveRow;
    public float shotTimer = 0f;
    public float entryDelay = 0f;
    public float entryTimer = 0f;
    public boolean singleShot = false;
    public int shotOrder = 0;

    // TUTORIAL WAVE (LVL1)//
    public boolean isTutorialWave;
    public boolean hasShot = false;
    public float leaveTimer = 0f;
    private static final float LEAVE_TIMER = 3f;
    public float leaveDelay = LEAVE_TIMER;

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
                      float velocityEnemy, Vector2 positionEnemy,
                      float sizeEnemy, float oscillation, int typeOfMovement,int typeOfEnemy,
                      ShotPattern shotPattern,
                      boolean isTutorialWave) {
        this.health = health;
        this.attackProbability = attackProbability;
        collisionEnemy = new Rectangle();
        collisionEnemy.set(-100, -100, sizeEnemy / 2f, sizeEnemy / 2f);
        this.velocityEnemy = velocityEnemy;
        this.positionEnemy = positionEnemy.cpy();
        this.sizeEnemy = sizeEnemy;
        this.oscillation = oscillation;
        this.typeOfMovement = typeOfMovement;
        this.typeOfEnemy = typeOfEnemy;
        this.shotPattern = shotPattern;
        this.isTutorialWave = isTutorialWave;
    }


    public void newPosition(float delta, float oscillation, SpriteBatch batch, Sprite textureEnemy, int typeOfMovement) {

        if (currentState == EnemyState.ENTERING) {
            if(entryTimer < entryDelay) {
                entryTimer += delta;
            } else {
                positionEnemy.y -= velocityEnemy * delta;

                if (positionEnemy.y <= targetHeight) {
                    positionEnemy.y = targetHeight;
                    currentState = EnemyState.IN_POSITION;
                }
            }
        } else if (currentState == EnemyState.IN_POSITION) {

            if (positionEnemy.x > 14f) {
                isMovementDirection = false;
            } else if (positionEnemy.x < 0f) {
                isMovementDirection = true;
            }

            switch (typeOfMovement) {
                case 1 -> {
                    positionEnemy = MovementsEnemies.movementType1(positionEnemy, delta, oscillation, velocityEnemy, this.oscillation, isMovementDirection);
                }
                case 2 -> {
                    positionEnemy = MovementsEnemies.movementType2(positionEnemy, delta, oscillation, velocityEnemy, this.oscillation, isMovementDirection);
                }
                case 3 -> {
                    positionEnemy = MovementsEnemies.movementType3(positionEnemy, delta, oscillation, velocityEnemy, this.oscillation, isMovementDirection);
                }
                default -> {

                }
            }
        } else if (currentState == EnemyState.LEAVING) {
            updateLeavingMovement(delta);
        }


        collisionEnemy.setPosition(positionEnemy.x, positionEnemy.y);
        batch.draw(textureEnemy, positionEnemy.x, positionEnemy.y, sizeEnemy / 2, sizeEnemy / 2);
    }

    private void startLeaving() {
        currentState = EnemyState.LEAVING;
        float screenCenter = WORLD_WIDTH / 2f;
        if(positionEnemy.x < screenCenter) {
            exitTargetVelocityX =-EXIT_SPEED;
        } else {
            exitTargetVelocityX = EXIT_SPEED;
        }
    }

    private void updateLeavingMovement(float delta) {
        exitVelocityX += (exitTargetVelocityX - exitVelocityX) * EXIT_SMOOTHING * delta;
        positionEnemy.x += exitVelocityX * delta;
    }

    public boolean isOutOfBounds() {
        return positionEnemy.x < -EXIT_MARGIN || positionEnemy.x > WORLD_WIDTH + EXIT_MARGIN
        || positionEnemy.y < -EXIT_MARGIN || positionEnemy.y > WORLD_HEIGHT + EXIT_MARGIN;
    }


    public void drawEnemyAndShot(ControllerBullets c, float delta, Vector2 playerPosition) {
        if(singleShot) {
            if(currentState == EnemyState.IN_POSITION && !hasShot) {
                shotTimer += delta;
                float delay = 0.5f + shotOrder * 0.5f;

                if(shotTimer >= delay) {
                    switch(shotPattern) {
                        case TARGETED -> {shootAtPlayer(c, playerPosition);
                        }
                        case RADIAL -> {shootRadial(c);
                        }
                        case NONE -> {
                        }
                    }
                    hasShot = true;
                    leaveTimer = leaveDelay;
                }
            }

            if (hasShot) {
                leaveTimer -= delta;

                if (leaveTimer <= 0f) {
                    startLeaving();
                }
            }
            return;
        }

        if (isTutorialWave) {
            if (currentState == EnemyState.IN_POSITION && !hasShot) {
                shotTimer += delta;
                float delay = 1f + waveRow * 0.5f;
                if (shotTimer >= delay) {

                    switch (shotPattern) {
                        case TARGETED -> shootAtPlayer(c, playerPosition);
                        case RADIAL -> shootRadial(c);
                        case NONE -> {
                        }
                    }

                    hasShot = true;
                    leaveTimer = LEAVE_TIMER;
                }
            }
            if(hasShot) {
                leaveTimer -= delta;
                if (leaveTimer <= 0f) {
                    startLeaving();
                }
            }
          return;
        }
        switch (shotPattern) {
            case TARGETED -> {
                if (MathUtils.random() < delta * (1d / attackProbability) * 10f) {
                    shootAtPlayer(c, playerPosition);
                }
            }
            case RADIAL -> {
                if (MathUtils.random() < delta * (1d / attackProbability) * 10f) {
                    shootRadial(c);
                }
            }
            case NONE -> {
            }
        }
    }

    private void shootAtPlayer(ControllerBullets c, Vector2 playerPosition) {
        Vector2 direction = new Vector2(playerPosition).sub(positionEnemy);
        direction.nor();

        float bulletSpeed = 6f;
        c.shot(positionEnemy.x, positionEnemy.y, BULLET_SIZE, direction.x * bulletSpeed, direction.y * bulletSpeed);

    }

    private void shootRadial(ControllerBullets c) {
        int numberOfBullets = 8;
        float bulletSpeed = 4f;

        for(int i = 0; i < numberOfBullets; i++) {
            float angle = (360f / numberOfBullets) * i;
            float velX = MathUtils.cosDeg(angle) * bulletSpeed;
            float velY = MathUtils.sinDeg(angle) * bulletSpeed;

            c.shot(positionEnemy.x, positionEnemy.y, 3, velX, velY);

        }

    }

}
