package com.example.airplane;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class Dan {
	int m_Speed;
	int m_X;
	int m_Y;
	boolean m_isLife;
	Sprite m_bulletSpite;
	Texture m_bulletTexture;
	int style = 0;
	int vt;
    boolean isd ;
	boolean daep = false;
	boolean isf  = false;

	public Dan()
	{
	}
	public Dan(int speed, int x, int y, boolean life, Sprite sprite,
			Texture texture, int t) {
		this.m_Speed = speed;
		this.m_X = x;
		this.m_Y = y;
		this.m_isLife = life;
		this.m_bulletSpite = sprite;
		this.m_bulletTexture = texture;
		this.vt = t;
		isd = false;
	}
	
	public int GetSpeed() {
		return this.m_Speed;
	}

	public int GetX() {
		return this.m_X;
	}

	public int GetY() {
		return this.m_Y;
	}

	public boolean GetisLife() {
		return this.m_isLife;
	}

	public Sprite GetBulletSprite() {
		return this.m_bulletSpite;
	}

	public Texture GetBulletTexture() {
		return this.m_bulletTexture;
	}

	public void SetX(int x) {
		this.m_X = x;
	}

	public void SetY(int y) {
		this.m_Y = y;
	}

	public void SetSpeed(int speed) {
		this.m_Speed = speed;
	}

	public void SetisLife(boolean islife) {
		this.m_isLife = islife;
	}
	public void SetbulletSprite(Sprite sprite) {
		this.m_bulletSpite = sprite;
	}

	public void SetbulletTexture(Texture texture) {
		this.m_bulletTexture = texture;
	}

	public void onLoadScene(E3Scene scene) {
		scene.getTopLayer().add(m_bulletSpite);
	}

	public void onLoadResourcesD(Context context) {
		m_bulletTexture = new AssetTexture("Dan.png", context);

	}
	public void onLoadResources(Context context) {
		m_bulletTexture = new AssetTexture("DanE.png", context);

	}
	public void onLoadResourcesX(Context context) {
		m_bulletTexture = new AssetTexture("xoay.png", context);

	}
	public void onLoadResourcesT(Context context) {
		m_bulletTexture = new AssetTexture("xoay1.png", context);

	}
	
	
	

	public void onLoadScene() {
		this.m_bulletSpite = new Sprite(this.m_bulletTexture, this.m_X,
				this.m_Y);
	}

	public void moveR(int x, int y) {
		this.m_bulletSpite.moveRelative(x, y);
	}

	public void moveL(int x, int y) {
		this.m_bulletSpite.moveRelative(-x, y);
	}

	public void moveD(int x, int y) {
		this.m_bulletSpite.moveRelativeY(y);
	}
	public void moveQ(int x, int y)
	{
		this.m_bulletSpite.moveRelative(x, y);
	}


}
