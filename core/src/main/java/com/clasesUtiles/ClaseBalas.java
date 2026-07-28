package com.clasesUtiles;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ClaseBalas implements Poolable {
    public Vector2 velocidad = new Vector2();
    public Vector2 posicion = new Vector2();
    public float radio;
    public boolean activa = false;
    public Rectangle collision = new Rectangle();



    public void Iniciar(float x, float y, float radio, float velX, float velY) {
        posicion.set(x, y);
        velocidad.set(velX, velY);
        this.radio = radio;
        collision.set(x,y,radio / 16f,radio / 6f );
        activa = true;
    }

    public void actualizar(float delta){
        posicion.x += velocidad.x * delta;
        posicion.y += velocidad.y * delta;
        collision.setPosition(posicion.x , posicion.y);
    }


    @Override
    public void reset() {
        posicion.set(0,0);
        velocidad.set(0,0);
        activa = false;
    }
}
