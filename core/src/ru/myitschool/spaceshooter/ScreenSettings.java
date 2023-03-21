package ru.myitschool.spaceshooter;

import static ru.myitschool.spaceshooter.SpaceShooter.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenSettings implements Screen {
    SpaceShooter s;
    InputKeyboard keyboard; // экранная клавиатура

    Texture imgBackGround; // фон

    SpaceButton btnName;
    SpaceButton btnMode;
    SpaceButton btnSound;
    SpaceButton btnMusic;
    SpaceButton btnClearRecords;
    SpaceButton btnBack;

    boolean isEnterName;

    public ScreenSettings(SpaceShooter spaceShooter) {
        s = spaceShooter;
        keyboard = new InputKeyboard(SCR_WIDTH, SCR_HEIGHT/2, 8);

        imgBackGround = new Texture("space01.jpg");
        // создаём кнопки
        btnName = new SpaceButton(s.fontMedium, "Name: Noname", 50, 600);
        btnMode = new SpaceButton(s.fontMedium, "Mode: Easy", 50, 550);
        btnSound = new SpaceButton(s.fontMedium, "Sound: ON", 50, 500);
        btnMusic = new SpaceButton(s.fontMedium, "Music: ON", 50, 450);
        btnClearRecords = new SpaceButton(s.fontMedium, "Clear Records", 50, 400);
        btnBack = new SpaceButton(s.fontMedium, "Back", 50, 150);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // касания экрана/клики мышью
        if(Gdx.input.justTouched()) {
            s.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            s.camera.unproject(s.touch);
            if(isEnterName) {
                if(keyboard.endOfEdit(s.touch.x, s.touch.y)){
                    isEnterName = false;
                }
            } else {
                if (btnName.hit(s.touch.x, s.touch.y)) {
                    isEnterName = true;
                }
                if (btnMode.hit(s.touch.x, s.touch.y)) {
                    changeMode();
                }
                if (btnSound.hit(s.touch.x, s.touch.y)) {
                    s.soundOn = !s.soundOn;
                    if (s.soundOn) btnSound.setText("Sound: ON");
                    else btnSound.setText("Sound: OFF");
                }
                if (btnMusic.hit(s.touch.x, s.touch.y)) {
                    s.musicOn = !s.musicOn;
                    if (s.musicOn) {
                        btnMusic.setText("Music: ON");
                        //s.screenGame.sndMusic.play();
                    } else {
                        btnMusic.setText("Music: OFF");
                        //s.screenGame.sndMusic.stop();
                    }
                }
                if (btnClearRecords.hit(s.touch.x, s.touch.y)) {
                    btnClearRecords.setText("Records Pured");
                }
                if (btnBack.hit(s.touch.x, s.touch.y)) {
                    s.setScreen(s.screenIntro);
                }
            }
        }

        // события игры
        // ------------

        // отрисовка всего
        s.camera.update();
        s.batch.setProjectionMatrix(s.camera.combined);
        s.batch.begin();
        s.batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnName.font.draw(s.batch, btnName.text, btnName.x, btnName.y);
        btnMode.font.draw(s.batch, btnMode.text, btnMode.x, btnMode.y);
        btnSound.font.draw(s.batch, btnSound.text, btnSound.x, btnSound.y);
        btnMusic.font.draw(s.batch, btnMusic.text, btnMusic.x, btnMusic.y);
        btnClearRecords.font.draw(s.batch, btnClearRecords.text, btnClearRecords.x, btnClearRecords.y);
        btnBack.font.draw(s.batch, btnBack.text, btnBack.x, btnBack.y);
        if(isEnterName) {
            keyboard.draw(s.batch);
        }
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
        btnClearRecords.setText("Clear Records");
    }

    @Override
    public void dispose() {
        imgBackGround.dispose();
        keyboard.dispose();
    }

    void changeMode(){
        if(s.modeOfGame == MODE_EASY){
            s.modeOfGame = MODE_NORMAL;
            btnMode.setText("Mode: Normal");
        } else if(s.modeOfGame == MODE_NORMAL){
            s.modeOfGame = MODE_HARD;
            btnMode.setText("Mode: Hard");
        } else if(s.modeOfGame == MODE_HARD){
            s.modeOfGame = MODE_EASY;
            btnMode.setText("Mode: Easy");
        }
    }
}
