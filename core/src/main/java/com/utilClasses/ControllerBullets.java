package com.utilClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;


public class ControllerBullets {

    public final Array<ClassBullets> balasActivas = new Array<>();


    public final Pool<ClassBullets> balaPool = new Pool<>() {
        @Override
        protected ClassBullets newObject() {
            return new ClassBullets();
        }
    };

    public void shot(float x, float y, float radio,float velX, float velY) {
        ClassBullets newBullet = balaPool.obtain();
        newBullet.Start(x, y, radio,velX, velY);
        balasActivas.add(newBullet);
    }


    public void updateScreen(float delta, float widthScreen, float heightScreen ){

        for (int i = balasActivas.size - 1; i >= 0; i--){
            ClassBullets b = balasActivas.get(i);
            b.update(delta);

            if (b.position.x < -1 || b.position.x > widthScreen + 1 ||
                b.position.y < -1 || b.position.y > heightScreen + 1){

                balasActivas.removeIndex(i);
                balaPool.free(b);
            }
        }
    }



    public void drawBulletsAndCollide(SpriteBatch c, Texture bullet, ControllerEnemies controllerEnemy, StatsClass statsClass){
        for (int i = balasActivas.size - 1; i >= 0; i--) {
            ClassBullets b = balasActivas.get(i);

            if(controllerEnemy.isCollidingWithEnemy(b, statsClass)){
                balasActivas.removeIndex(i);
                balaPool.free(b);
            }


            if (b.state) {
                c.draw(bullet, b.position.x, b.position.y, b.radio / 16f, b.radio / 6f);
            }
        }
    }



    public void drawBulletsEnemies(SpriteBatch c, Texture bullet, Rectangle collisionPlayer){
        for (int i = balasActivas.size - 1; i >= 0; i--) {
            ClassBullets b = balasActivas.get(i);

            if (b.collision.overlaps(collisionPlayer)) {
                Player.setLessHealth(1);
                b.state = false;
            }

            if (b.state) {
                c.draw(bullet, b.position.x, b.position.y, b.radio / 16f, b.radio / 6f);

            } else {
                balasActivas.removeIndex(i);
                balaPool.free(b);
            }
        }
    }
}
