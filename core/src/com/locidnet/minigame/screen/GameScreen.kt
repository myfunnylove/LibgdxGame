package com.locidnet.minigame.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.FillViewport
import com.locidnet.minigame.sprites.Helicopter
import com.locidnet.minigame.sprites.Tube
import com.locidnet.minigame.utils.Const
import java.util.*

/**
 * Created by myfunnylove on 12.12.2017.
 */
class GameScreen : Screen {


     var batch : SpriteBatch

     var stage: Stage
     var helicopter : Helicopter
     var leftTube : Tube
     var rightTube : Tube

     var lastXPosition = 0f
     var random : Random
     var camera : OrthographicCamera
     var viewport  :FillViewport

     var gameOver = false

    init {

        camera  = OrthographicCamera()
        camera.setToOrtho(false,Gdx.graphics.width.toFloat(),Gdx.graphics.height.toFloat())
        viewport = FillViewport(Gdx.graphics.width.toFloat(),Gdx.graphics.height.toFloat(),camera)



        batch = SpriteBatch()
        batch.projectionMatrix = camera.combined
        stage = Stage(viewport,batch)

        Gdx.input.inputProcessor = stage

        random = Random()


        lastXPosition = Gdx.graphics.width / 2f

        helicopter = Helicopter()
        helicopter.run {
            x = ( (camera.viewportWidth / 2) - (50 / 2))
            y = Helicopter.Body.START_Y

            setOrigin(Helicopter.Body.ORIGIN,Helicopter.Body.ORIGIN)
            setBounds(x,y,camera.viewportWidth,Helicopter.Body.HEIGHT)

            setSize(Helicopter.Body.WIDTH,Helicopter.Body.HEIGHT)
//            addListener(inputListener)

        }

        stage.addActor(helicopter)


        leftTube = Tube()
        leftTube.run {
            x = 0f
            y = camera.viewportHeight - Tube.Body.HEIGHT
            setOrigin(Helicopter.Body.ORIGIN,Helicopter.Body.ORIGIN)
            setBounds(x,y,Gdx.graphics.width.toFloat(),Tube.Body.HEIGHT)
            setSize(random.nextInt(Tube.Body.MAX_WIDTH).toFloat(),Tube.Body.HEIGHT)
        }

        stage.addActor(leftTube)


        rightTube = Tube()
        rightTube.run {
            x = leftTube.width + Tube.Body.INTERVAL
            y = leftTube.y
            setOrigin(Helicopter.Body.ORIGIN,Helicopter.Body.ORIGIN)
            setBounds(x,y,camera.viewportWidth,Tube.Body.HEIGHT)
            setSize(texture.width.toFloat() + (texture.width -  leftTube.width),Tube.Body.HEIGHT)
        }

        stage.addActor(rightTube)
    }
    override fun hide() {

    }

    override fun show() {
    }

    override fun render(delta: Float) {


        stage.act(delta)
        stage.draw()

//        camera.position.y += Helicopter.Move.SPEED

        if(leftTube.y < 0 && !gameOver){
            Gdx.app.log(Const.TAG,"Kotta")

            leftTube.run {
                x = 0f
                y = camera.viewportHeight - Tube.Body.HEIGHT
                setSize(random.nextInt(Tube.Body.MAX_WIDTH).toFloat(),Tube.Body.HEIGHT)
            }

            rightTube.run {
                x = leftTube.width + Tube.Body.INTERVAL
                y = leftTube.y
                setSize(texture.width.toFloat() + (texture.width -  leftTube.width),Tube.Body.HEIGHT)
            }
        }

        leftTube.update(delta)
        rightTube.update(delta)
        helicopter.update(delta)
        handleInput()


        if (leftTube.isCollided(helicopter) || rightTube.isCollided(helicopter)){
            gameOver = true
        }

        if (gameOver) helicopter.y = leftTube.y - helicopter.height
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
        stage.getViewport().update(width, height, true);
    }

    override fun dispose() {
        helicopter.dispose()

    }



    fun handleInput(){

      helicopterInput()

    }


    fun helicopterInput() {
        if (Gdx.input.isTouched && Gdx.input.y > (camera.viewportHeight -  (helicopter.y + helicopter.height))){

            if (Gdx.input.x.toFloat() > lastXPosition){

                lastXPosition = Gdx.input.x.toFloat()


                helicopter.addAction(Actions.rotateTo(Helicopter.Rotate.ROTATE_L,Helicopter.Rotate.ROTATE_DURATION, Helicopter.Rotate.TRANSITION))
            }else if( Gdx.input.x.toFloat() < lastXPosition){
                lastXPosition = Gdx.input.x.toFloat()

                helicopter.addAction(Actions.rotateTo(Helicopter.Rotate.ROTATE_R,Helicopter.Rotate.ROTATE_DURATION, Helicopter.Rotate.TRANSITION))
            }

            helicopter.addAction(Actions.moveTo(Gdx.input.x.toFloat() - (helicopter.width / 2),helicopter.y,Helicopter.Move.MOVE_DURATION,Helicopter.Move.TRANSITION))


            if (helicopter.x < 0)  helicopter.x = 0f
            if (helicopter.x >  camera.viewportWidth)  helicopter.x = camera.viewportWidth - helicopter.width


        }else{

           if (helicopter.x == (lastXPosition - (helicopter.width / 2))){
               helicopter.addAction(Actions.rotateTo(Helicopter.Rotate.ROTATE_DEF,Helicopter.Rotate.ROTATE_DURATION, Helicopter.Rotate.TRANSITION))
           }

        }
    }

}