package com.locidnet.minigame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FillViewport
import com.locidnet.minigame.screen.GameScreen
import com.locidnet.minigame.sprites.Helicopter

class MainLogicActivity : Game() {



    lateinit var gameScreen : GameScreen


    override fun create() {

        gameScreen = GameScreen()
        screen = gameScreen

        Gdx.gl.glClearColor(120f / 255f, 212f / 255f, 232f / 255f, 1f)

    }

    override fun render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        gameScreen.render(Gdx.graphics.deltaTime)
    }

    override fun dispose() {

        gameScreen.dispose()

    }
}
