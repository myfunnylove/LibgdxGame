package com.locidnet.minigame.sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.sun.org.apache.xpath.internal.operations.Bool

/**
 * Created by myfunnylove on 12.12.2017.
 */
class Tube : Actor() ,ISprite {




    object Body {

        val HEIGHT = 25f
        val MAX_WIDTH = 350
        val INTERVAL = 130f

    }

    object Move{
        val SPEED = 5

    }


    val texture = Texture(Gdx.files.internal("ground.png"))
    val region = TextureRegion(texture,texture.width,texture.height)
    var bound = Rectangle()

    override fun draw(batch: Batch?, parentAlpha: Float) {

        bound.run {
            x = this@Tube.x
            y = this@Tube.y
            width = this@Tube.width
            height = this@Tube.height
        }
        if (batch != null)
            batch.draw(region,x,y,originX,originY,width,height,scaleX,scaleY,rotation)
    }

    override fun dispose() {
        texture.dispose()
        clear()
        clearActions()
        clearListeners()
    }

    override fun update(dl: Float) {

        y -= Move.SPEED
    }


    fun isCollided(helicopter: Helicopter) : Boolean = bound.overlaps(helicopter.bound)
}