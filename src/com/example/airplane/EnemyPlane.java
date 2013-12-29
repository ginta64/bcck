package com.example.airplane;

import java.util.ArrayList;

import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.Texture;


public abstract class EnemyPlane {
	protected Sprite _SpiteTexture;
	protected Texture _texture;
	protected int speed;
	protected int slood;
	protected int _x, _y;
	protected int wayMove;
	protected int wayAp;
	protected boolean _isLife ;
	protected boolean _isAcross ;
	protected boolean _status ;
	protected boolean _isfire;
	protected boolean _isf;
	
	protected ArrayList<Dan> ListB = new ArrayList<Dan>();
	public EnemyPlane()
	{
		this._texture = null;
		this.speed = 10;
		this.slood = 100;
		this._y = 0;
		this._x = 0;
		this._isLife = true;
		wayMove = -1;
		wayAp = -1 ;
		_isAcross = true;
		this._status = false;
		this._isfire = false;
		this._isf = false;
	}

}
