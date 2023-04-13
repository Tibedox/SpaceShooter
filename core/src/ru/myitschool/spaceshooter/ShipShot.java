package ru.myitschool.spaceshooter;

import static ru.myitschool.spaceshooter.SpaceShooter.SCR_HEIGHT;
import static ru.myitschool.spaceshooter.SpaceShooter.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class ShipShot extends SpaceObject{
    public ShipShot(float x, float y, float width, float height) {
        super(x, y, width, height);
        vy = 8;
    }

    boolean outOfScreen() {
        return y > SCR_HEIGHT+height/2;
    }

    boolean overlap(EnemyShip e) {
        return Math.abs(x-e.x) < width/2+e.width/2 & Math.abs(y-e.y) < height/2+e.height/2;
    }
}
