package com.locidnet.minigame.sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.EventListener

/**
 * Created by myfunnylove on 12.12.2017.
 */
class Helicopter : Actor() ,ISprite{


    object Body {
        val WIDTH = 75f
        val HEIGHT = 75f
        val START_Y = 150f
        val ORIGIN = 50f

    }

    object Rotate {
        val ROTATE_L = -30f
        val ROTATE_R = 30f
        val ROTATE_DEF = 0f
        val ROTATE_DURATION = 0.1f
        val TRANSITION = Interpolation.linear

    }

    object Move {
        val MOVE_DURATION = 0.1f
        val TRANSITION = Interpolation.linear

    }

    val texture  = Texture(Gdx.files.internal("copter_1.png"))
    val region = TextureRegion(texture,texture.width,texture.height)
    var bound = Rectangle()

    override fun draw(batch: Batch?, parentAlpha: Float) {
        bound.run {
            x = this@Helicopter.x
            y = this@Helicopter.y
            width = this@Helicopter.width
            height = this@Helicopter.height
        }
        if (batch != null)
            batch.draw(region,x,y,originX,originY,width,height,scaleX,scaleY,rotation)
    }



    override fun dispose(){
        clear()
        clearActions()
        clearListeners()
        texture.dispose()
    }


    override fun update(dl : Float) {
    }


}