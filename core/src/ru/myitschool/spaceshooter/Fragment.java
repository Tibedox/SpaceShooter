package ru.myitschool.spaceshooter;

import static ru.myitschool.spaceshooter.SpaceShooter.SCR_HEIGHT;
import static ru.myitschool.spaceshooter.SpaceShooter.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class Fragment extends SpaceObject{
    int type;

    public Fragment(float x, float y, float size) {
        super(x, y, 0, 0);
        width = MathUtils.random(size/10, size/3);
        height = MathUtils.random(size/10, size/3);
        float a = MathUtils.random(0f, 360f);
        float v = MathUtils.random(0.1f, 5f);
        vx = v * MathUtils.sin(a);
        vy = v * MathUtils.cos(a);
        type = MathUtils.random(0, 3);
    }

    boolean outOfScreen() {
        return x < -width/2 || y < -height/2 || x > SCR_WIDTH+width/2 || y > SCR_HEIGHT+height/2;
    }
}
