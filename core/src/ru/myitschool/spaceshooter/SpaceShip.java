package ru.myitschool.spaceshooter;

import static ru.myitschool.spaceshooter.SpaceShooter.SCR_WIDTH;

public class SpaceShip extends SpaceObject{
    public SpaceShip(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    void move() {
        super.move();
        outOfScreen();
    }

    void outOfScreen() {
        if(x<0+width/2) {
            x = 0+width/2;
            vx = 0;
        }
        if(x>SCR_WIDTH-width/2) {
            x = SCR_WIDTH-width/2;
            vx = 0;
        }
    }
}
