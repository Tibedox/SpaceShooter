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
}
