package com.connectfour.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.connectfour.Games;
import com.connectfour.SimpleMenuScreenBuilder;
import com.connectfour.server.Client;

public class JoinScreen implements Screen {

    private final Games game;
    private final SimpleMenuScreenBuilder builder;
    private Stage stage;

    private float menuWidth;
    private float menuHeight;

    public JoinScreen(Games game) {
        this.game = game;
        this.builder = new SimpleMenuScreenBuilder(game);
    }
    @Override
    public void show() {
        //TextField.TextFieldStyle style = game.skin.get(TextField.TextFieldStyle.class);
        //Muudab skini yee yee ass fonti meie seksikaks Roboto fontiks.
        //style.font = game.assetsLoader.manager.get(game.assetsLoader.robotoBlack);
        TextField input = new TextField("", game.skin);
        input.setMessageText("ip:port");
        input.setWidth(500);

        //TextButton.TextButtonStyle bttnStyle = game.skin.get(TextButton.TextButtonStyle.class);
        //bttnStyle.font = game.assetsLoader.manager.get(game.assetsLoader.robotoBlack);
        TextButton joinButton = new TextButton("JOIN", game.skin);
        joinButton.setWidth(100);
        joinButton.setHeight(50);

        joinButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String read = input.getText();
                String ip = null;
                Integer port = null;
                try {
                    ip = read.split(":")[0];
                    port = Integer.parseInt(read.split(":")[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("[CONSOLE] Ip aadress on valesti kirjutatud!");
                    return false;
                }
                Thread clientThread = new Thread(new Client(game, ip, port));
                clientThread.setName("CLIENTTHREAD");
                clientThread.start();
                return true;
            }
        });

        builder.initStage(new Actor[]{input, joinButton}, 0.1f);
        stage = builder.getStage();
        game.inputMultiplexer.addProcessor(stage);

        menuWidth = builder.menuWidth;
        menuHeight = builder.menuHeight;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

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
    }

}
