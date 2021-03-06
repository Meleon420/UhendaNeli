package com.connectfour.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.connectfour.Games;
import com.connectfour.SimpleMenuScreenBuilder;

public class GameMenuScreen implements Screen {

    private Games game;
    private Viewport viewport;
    private Camera cam;
    private Stage stage;
    public HostScreen HOSTSCREEN;

    private float worldWidth, worldHeight;

    public GameMenuScreen(Games game){
        this.game = game;
    }

    @Override
    public void show() {
        game.local1v1 = false;
        this.HOSTSCREEN = new HostScreen(game);
        ImageButton host = new ImageButton(new SpriteDrawable(new Sprite((Texture) game.assetsLoader.manager.get(game.assetsLoader.host))));
        host.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.player2.setAI(false);
                game.changeScreen(HOSTSCREEN);
                return true;
            }
        });

        ImageButton join = new ImageButton(new SpriteDrawable(new Sprite((Texture) game.assetsLoader.manager.get(game.assetsLoader.join))));
        join.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.player2.setAI(false);
                game.changeScreen(new JoinScreen(game));
                return true;
            }
        });

        ImageButton pvaibutton = new ImageButton(new SpriteDrawable(new Sprite((Texture) game.assetsLoader.manager.get(game.assetsLoader.vsai))));
        pvaibutton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.player2.setAI(true);
                game.changeScreen(game.CONNECTFOUR);
                return true;
            }
        });
        ImageButton pvpbutton = new ImageButton(new TextureRegionDrawable(new TextureRegion((Texture) game.assetsLoader.manager.get(game.assetsLoader.pvp))));

        pvpbutton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.player2.setAI(false);
                game.local1v1 = true;
                game.changeScreen(game.CONNECTFOUR);
                return true;
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(host).width(200).height(100);
        table.row();
        table.add(join).width(200).height(100);
        table.row();
        table.add(pvaibutton).width(200).height(100);
        table.row();
        table.add(pvpbutton).width(200).height(100);
        /*
        SimpleMenuScreenBuilder builder = new SimpleMenuScreenBuilder(game);
        builder.initStage(new Actor[]{host, join, pvaibutton, pvpbutton}, 0.2f);
        stage = builder.getStage();

        worldWidth = builder.menuWidth;
        worldHeight = builder.menuHeight;
         */
        stage = new Stage(game.viewport,game.batch);
        stage.addActor(table);
        game.inputMultiplexer.addProcessor(stage);


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
        /*
        stage.getCamera().position.set(worldWidth / 2f, worldHeight / 2f, 0);
        game.batch.setProjectionMatrix(stage.getViewport().getCamera().combined);

         */
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
