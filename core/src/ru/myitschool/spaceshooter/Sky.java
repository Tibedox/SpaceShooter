package ru.myitschool.spaceshooter;

import static ru.myitschool.spaceshooter.SpaceShooter.SCR_HEIGHT;
import static ru.myitschool.spaceshooter.SpaceShooter.SCR_WIDTH;

public class Sky extends SpaceObject{
    public Sky(float y) {
        super(0, y, SCR_WIDTH, SCR_HEIGHT);
        vy = -1;
    }

    @Override
    void move() {
        super.move();
        outOfScreen();
    }

    void outOfScreen() {
        if(y < -SCR_HEIGHT) {
            y = SCR_HEIGHT;
        }
    }
}
