package ru.myitschool.spaceshooter;

import static ru.myitschool.spaceshooter.SpaceShooter.SCR_HEIGHT;
import static ru.myitschool.spaceshooter.SpaceShooter.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenGame implements Screen {
    SpaceShooter s;
    Texture imgSky;
    Texture imgShip;

    Sky[] skies = new Sky[2];
    SpaceShip ship;

    public ScreenGame(SpaceShooter spaceShooter) {
        s = spaceShooter;

        imgSky = new Texture("stars.png");
        imgShip = new Texture("ship.png");

        skies[0] = new Sky(0);
        skies[1] = new Sky(SCR_HEIGHT);
        ship = new SpaceShip(SCR_WIDTH/2, 100, 100, 100);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // касания экрана/клики мышью
        if(Gdx.input.isTouched()) {
            s.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            s.camera.unproject(s.touch);
            ship.vx = (s.touch.x - ship.x)/50;
        }

        // события игры
        for (int i = 0; i < skies.length; i++) {
            skies[i].move();
        }
        ship.move();

        // отрисовка всего
        s.camera.update();
        s.batch.setProjectionMatrix(s.camera.combined);
        s.batch.begin();
        for (int i = 0; i < skies.length; i++) {
            s.batch.draw(imgSky, skies[i].x, skies[i].y, skies[i].width, skies[i].height);
        }
        s.batch.draw(imgShip, ship.getX(), ship.getY(), ship.width, ship.height);
        s.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        imgSky.dispose();
        imgShip.dispose();
    }
}
