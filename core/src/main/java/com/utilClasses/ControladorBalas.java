package com.utilClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;


public class ControladorBalas {

    public final Array<ClaseBalas> balasActivas = new Array<>();


    public final Pool<ClaseBalas> balaPool = new Pool<ClaseBalas>() {
        @Override
        protected ClaseBalas newObject() {
            return new ClaseBalas();
        }
    };

    public void disparar(float x, float y, float radio,float velX, float velY) {
        ClaseBalas nuevaBala = balaPool.obtain();
        nuevaBala.Iniciar(x, y, radio,velX, velY);
        balasActivas.add(nuevaBala);
    }


    public void actualizarPantalla(float delta, float anchoPantalla, float altoPantalla ){

        for (int i = balasActivas.size - 1; i >= 0; i--){
            ClaseBalas b = balasActivas.get(i);
            b.actualizar(delta);

            if (b.posicion.x < -1 || b.posicion.x > anchoPantalla + 1 ||
                b.posicion.y < -1 || b.posicion.y > altoPantalla + 1){

                balasActivas.removeIndex(i);
                balaPool.free(b);
            }
        }
    }



    public void drawBulletsAndCollide(SpriteBatch c, Texture bullet, ControllerEnemies controllerEnemy){
        for (int i = balasActivas.size - 1; i >= 0; i--) {
            ClaseBalas b = balasActivas.get(i);

            if(controllerEnemy.isCollidingWithEnemy(b) == true){
                balasActivas.removeIndex(i);
                balaPool.free(b);
            }


            if (b.activa == true) {
                c.draw(bullet, b.posicion.x, b.posicion.y, b.radio / 16f, b.radio / 6f);
            }
        }
    }



    public void drawBulletsEnemies(SpriteBatch c, Texture bullet, Rectangle collisionPlayer){
        for (int i = balasActivas.size - 1; i >= 0; i--) {
            ClaseBalas b = balasActivas.get(i);

            if (b.collision.overlaps(collisionPlayer)) {
                Player.setLessHealth(2);
                b.activa = false;
            }

            if (b.activa == true) {
                c.draw(bullet, b.posicion.x, b.posicion.y, b.radio / 16f, b.radio / 6f);

            } else {
                balasActivas.removeIndex(i);
                balaPool.free(b);
            }
        }
    }
}
