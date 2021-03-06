package com.connectfour.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.connectfour.Games;
import com.connectfour.NumberTextFieldFilter;
import com.connectfour.SimpleMenuScreenBuilder;
import com.connectfour.server.Host;

public class HostScreen implements Screen {

    private final Games game;
    private final SimpleMenuScreenBuilder builder;
    private Stage stage;
    private float menuWidth;
    private float menuHeight;
    private Host runnableHost;
    public boolean gameStarted = false;

    public HostScreen(Games game) {
        this.game = game;
        this.builder = new SimpleMenuScreenBuilder(game);
    }

    @Override
    public void show() {

        //TextField.TextFieldStyle style = game.skin.get(TextField.TextFieldStyle.class);
        //Muudab skini yee yee ass fonti meie seksikaks Roboto fontiks.
        //style.font = game.assetsLoader.manager.get(game.assetsLoader.robotoBlack);
        TextField input = new TextField("27016", game.skin);
        input.setTextFieldFilter(new NumberTextFieldFilter());
        input.setMessageText("Port");
        input.setWidth(500);

        //TextButton.TextButtonStyle bttnStyle = game.skin.get(TextButton.TextButtonStyle.class);
        //bttnStyle.font = game.assetsLoader.manager.get(game.assetsLoader.robotoBlack);
        TextButton hostButton = new TextButton("HOST", game.skin);
        hostButton.setWidth(100);
        hostButton.setHeight(50);

        hostButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                runnableHost = new Host(game, Integer.parseInt(input.getText()));
                Thread hostThread = new Thread(runnableHost);
                hostThread.setName("SERVERTHREAD");
                hostThread.start();
                return true;
            }
        });

        builder.initStage(new Actor[]{input, hostButton}, 0.1f);
        stage = builder.getStage();
        game.inputMultiplexer.addProcessor(stage);

        menuWidth = builder.menuWidth;
        menuHeight = builder.menuHeight;
    }

    @Override
    public void render(float delta) {
        Gdx.gl30.glClearColor(0, 0, 0, 1);
        Gdx.gl30.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().position.set(menuWidth / 2f, menuHeight / 2f, 0);
        game.batch.setProjectionMatrix(stage.getViewport().getCamera().combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        game.inputMultiplexer.removeProcessor(stage);
        stage.dispose();
        if (!gameStarted) {
            if (runnableHost != null) runnableHost.stop();
        } else {
            gameStarted = false;
        }
    }
}
