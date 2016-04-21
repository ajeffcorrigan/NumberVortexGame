package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ScreenObject extends Sprite {

    public boolean isActionItem;
    public int actionCode;

    public ScreenObject() {
        super();
    }

    public ScreenObject(Texture texture, Vector2 startxy) {
        super(new Sprite(texture));
        this.isActionItem = false;
        setPosition(startxy.x,startxy.y);
        setBounds(getX(),getY(),getWidth(),getHeight());
    }

    public ScreenObject(Texture texture, Vector2 startxy, int act) {
        super(new Sprite(texture));
        this.isActionItem = true;
        this.actionCode = act;
        setPosition(startxy.x,startxy.y);
        setBounds(getX(),getY(),getWidth(),getHeight());
    }

    public void randomMoveXY(float delta) {
        this.setX(getX() - (delta * MathUtils.random(2)));
        this.setY(getY() + (delta * MathUtils.random(2)));
    }




}