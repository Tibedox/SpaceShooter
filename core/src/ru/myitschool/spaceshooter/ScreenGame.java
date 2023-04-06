package ru.myitschool.spaceshooter;

import static ru.myitschool.spaceshooter.SpaceShooter.SCR_HEIGHT;
import static ru.myitschool.spaceshooter.SpaceShooter.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class ScreenGame implements Screen {
    SpaceShooter s;
    boolean isAccelerometerPresent;
    Texture imgSky;
    Texture imgShip;
    Texture imgEnemy;

    Sky[] skies = new Sky[2];
    ArrayList<EnemyShip> enemy = new ArrayList<>();
    SpaceShip ship;

    long timeEnemySpawn, timeEnemyInterval = 1500;

    public ScreenGame(SpaceShooter spaceShooter) {
        s = spaceShooter;
        // проверяем наличие акселерометра в устройстве
        isAccelerometerPresent = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        imgSky = new Texture("stars.png");
        imgShip = new Texture("ship.png");
        imgEnemy = new Texture("enemy.png");

        skies[0] = new Sky(0);
        skies[1] = new Sky(SCR_HEIGHT);
        ship = new SpaceShip(SCR_WIDTH/2, 100, 100, 100);

        enemy.add(new EnemyShip(100, 100));
        timeEnemySpawn = TimeUtils.millis();
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
        } else if(isAccelerometerPresent) { // проверяем наклон акселерометра
            ship.vx = -Gdx.input.getAccelerometerX()*2;
        }

        // события игры
        for (int i = 0; i < skies.length; i++) {
            skies[i].move();
        }
        if(TimeUtils.millis() > timeEnemySpawn+timeEnemyInterval) {
            enemy.add(new EnemyShip(100, 100));
            timeEnemySpawn = TimeUtils.millis();
        }
        for (int i = 0; i < enemy.size(); i++) {
            enemy.get(i).move();
        }
        ship.move();

        // отрисовка всего
        s.camera.update();
        s.batch.setProjectionMatrix(s.camera.combined);
        s.batch.begin();
        for (int i = 0; i < skies.length; i++) {
            s.batch.draw(imgSky, skies[i].x, skies[i].y, skies[i].width, skies[i].height);
        }
        for (int i = 0; i < enemy.size(); i++) {
            s.batch.draw(imgEnemy, enemy.get(i).getX(), enemy.get(i).getY(), enemy.get(i).width, enemy.get(i).height);
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
        imgEnemy.dispose();
    }
}
