package com.example.airplane;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;

public class S33 extends EnemyPlane {

	Dan Ndan;
	
	public S33()
	{
		super();
		
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
		scene.getTopLayer().add(_SpiteTexture);
	}
	public void  onReLoadScene(E3Scene scene)
	{
		scene.getTopLayer().remove(_SpiteTexture);
	}
	
	public void onLoadResourcesD(Context context)
	{
		this._texture = new AssetTexture("s33/s33_1.png", context);
	}
	public void onLoadResourcesL(Context context)
	{
		this._texture = new AssetTexture("s33/s33_2.png", context);
	}
	public void onLoadResourcesR(Context context)
	{
		this._texture = new AssetTexture("s33/s33_4.png", context);
	}
	public void onLoadResourcesRD(Context context)
	{
		this._texture = new AssetTexture("s33/s33_5.png", context);
	}
	public void onLoadResourcesLD(Context context)
	{
		this._texture = new AssetTexture("s33/s33_3.png", context);
	}
	public void onLoadScene()
	{
		this._SpiteTexture = new Sprite(this._texture, this._x, this._y);
	}
	public void onUpdate(int x, int y)
	{
		this._x = x;
		this._y = y;
	}
}
