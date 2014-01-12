package com.example.airplane;

import com.e3roid.drawable.sprite.AnimatedSprite;
import com.e3roid.drawable.texture.TiledTexture;

public class Nframes {
	TiledTexture texture;
	AnimatedSprite sprite;
	long Time;
	public Nframes()
	{
		texture = null;
		sprite = null;
		Time = 0;
	}
	public Nframes(TiledTexture t, AnimatedSprite a, long ti)
	{
		this.texture  = t;
		this.sprite = a;
		this.Time = ti;
	}

}
