package ru.myitschool.spaceshooter;

import static ru.myitschool.spaceshooter.SpaceShooter.SCR_HEIGHT;
import static ru.myitschool.spaceshooter.SpaceShooter.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class ScreenGame implements Screen {
    SpaceShooter s;
    boolean isAccelerometerPresent;

    Texture imgSky;
    Texture imgShip;
    Texture imgEnemy;
    Texture imgShot;
    Texture imgFragment;
    Sound sndShoot;
    Sound sndExplosion;

    Sky[] skies = new Sky[2];
    ArrayList<EnemyShip> enemy = new ArrayList<>();
    ArrayList<ShipShot> shots = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    SpaceShip ship;

    long timeEnemySpawn, timeEnemyInterval = 1500;
    long timeShotSpawn, timeShotInterval = 600;

    public ScreenGame(SpaceShooter spaceShooter) {
        s = spaceShooter;
        // проверяем наличие акселерометра в устройстве
        isAccelerometerPresent = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        imgSky = new Texture("stars.png");
        imgShip = new Texture("ship.png");
        imgEnemy = new Texture("enemy.png");
        imgShot = new Texture("shipshot.png");
        imgFragment = new Texture("fragmentenemy.png");
        sndShoot = Gdx.audio.newSound(Gdx.files.internal("blaster.mp3"));
        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));

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
        } else if(isAccelerometerPresent) { // проверяем наклон акселерометра
            ship.vx = -Gdx.input.getAccelerometerX()*2;
        }

        // события игры
        // небо
        for (int i = 0; i < skies.length; i++) {
            skies[i].move();
        }

        // вражеские корабли
        spawnEnemy();
        for (int i = 0; i < enemy.size(); i++) {
            enemy.get(i).move();
            if(enemy.get(i).outOfScreen()) {
                enemy.remove(i);
                i--;
            }
        }

        // наши выстрелы
        spawnShot();
        for (int i = 0; i < shots.size(); i++) {
            shots.get(i).move();
            if(shots.get(i).outOfScreen()) {
                shots.remove(i);
                i--;
                continue;
            }
            // попадание выстрела в вражеский корабль
            for (int j = 0; j < enemy.size(); j++) {
                if(shots.get(i).overlap(enemy.get(j))) {
                    for (int k = 0; k < 3333; k++) {
                        fragments.add(new Fragment(enemy.get(j).x, enemy.get(j).y, enemy.get(j).width));
                    }
                    enemy.remove(j);
                    shots.remove(i);
                    i--;
                    if(s.sound) sndExplosion.play();
                    break;
                }
            }
        }

        // обломки
        for (int i = 0; i < fragments.size(); i++) {
            fragments.get(i).move();
            if(fragments.get(i).outOfScreen()) {
                fragments.remove(i);
                i--;
            }
        }

        // наш космический корабль
        ship.move();

        // отрисовка всего
        s.camera.update();
        s.batch.setProjectionMatrix(s.camera.combined);
        s.batch.begin();
        for (int i = 0; i < skies.length; i++) {
            s.batch.draw(imgSky, skies[i].x, skies[i].y, skies[i].width, skies[i].height);
        }
        for (int i = 0; i < fragments.size(); i++) {
            s.batch.draw(imgFragment, fragments.get(i).getX(), fragments.get(i).getY(), fragments.get(i).width, fragments.get(i).height);
        }
        for (int i = 0; i < enemy.size(); i++) {
            s.batch.draw(imgEnemy, enemy.get(i).getX(), enemy.get(i).getY(), enemy.get(i).width, enemy.get(i).height);
        }
        for (int i = 0; i < shots.size(); i++) {
            s.batch.draw(imgShot, shots.get(i).getX(), shots.get(i).getY(), shots.get(i).width, shots.get(i).height);
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
        imgShot.dispose();
        imgFragment.dispose();
    }

    void spawnEnemy() {
        if(TimeUtils.millis() > timeEnemySpawn+timeEnemyInterval) {
            enemy.add(new EnemyShip(100, 100));
            timeEnemySpawn = TimeUtils.millis();
        }
    }

    void spawnShot() {
        if(TimeUtils.millis() > timeShotSpawn+timeShotInterval) {
            shots.add(new ShipShot(ship.x, ship.y, ship.width, ship.height));
            timeShotSpawn = TimeUtils.millis();
            if(s.sound) sndShoot.play();
        }
    }
}
