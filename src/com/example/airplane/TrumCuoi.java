package com.example.airplane;

import java.util.ArrayList;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.sprite.AnimatedSprite;
import com.e3roid.drawable.texture.TiledTexture;

public class TrumCuoi extends EnemyPlane {
	int Blood;
	 AnimatedSprite sprite;
	TiledTexture texture;
	private ArrayList<AnimatedSprite.Frame> BossFrames ;
	
	public TrumCuoi()
	{
		super();
		this._x =  150;
		this._y = -200;
		this.Blood = 50;
		
	}
	public void  setX(int x)
	{
		this._x = x;
	}
	public void setY(int y)
	{
		this._y = y;
	}
	public void  setW(int w)
	{
		this.wayMove = w;
	}
	
	public int  getX()
	{
		return this._x;
	}
	public int  getW()
	{
		return this.wayMove;
	}
	public int getY()
	{
		return this._y ;
	}
	
	public void  onLoadScene(E3Scene scene)
	{
		scene.getTopLayer().add(sprite);
	}

	public void onLoadResourcesB(Context context)
	{
		texture = new TiledTexture("Boss/Boss.png", 200,500, 0, 0, 75, 30, context);
		
		BossFrames = new ArrayList<AnimatedSprite.Frame>();
		BossFrames.add(new AnimatedSprite.Frame(0, 0));
		BossFrames.add(new AnimatedSprite.Frame(1, 0));	
	}
	public void onLoadScene()
	{
		sprite = new AnimatedSprite(texture, this._x, this._y);
		sprite.animate(200, BossFrames);
	}
	public void onUpdate(int x, int y)
	{
		this._x = x;
		this._y = y;
	}

}
