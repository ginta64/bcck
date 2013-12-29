package com.example.airplane;

import java.util.ArrayList;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;


public class PhiThuyen {
	
	Sprite PlaneSprite;
	Texture PlaneTexture;
	int x, y;
	boolean m_isLife;
	public ArrayList<Dan> Dan = new ArrayList<Dan>();
	public ArrayList<Dan> Dan2 = new ArrayList<Dan>();
	public ArrayList<Dan> Dan3 = new ArrayList<Dan>();
	
	int VP ;
	int m_Life = 5;
	public PhiThuyen()
	{
		x = 300;
		y = 400;
		PlaneTexture = null;
		m_isLife = true;
		PlaneSprite  = null;
		VP = 0;
		
	}
	public PhiThuyen(int _x, int _y, Texture texture, Sprite sprite, boolean islife)
	{
		this.x  = _x;
		this.y = _y;
		this.PlaneTexture = texture;
		this.PlaneSprite = sprite;
		this.m_isLife = islife;
		 m_Life = 5;
	}
	
	public void  onLoadScene(E3Scene scene)
	{
		scene.getTopLayer().add(PlaneSprite);
	}
	public void onLoadResourcesD(Context context)
	{
		PlaneTexture = new AssetTexture("pt2.png", context);
	}
	public void onLoadScene()
	{
		this.PlaneSprite = new Sprite(PlaneTexture, this.x, this.y);
	}

}
