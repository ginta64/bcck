package com.example.airplane;

import android.content.Context;

import com.badlogic.gdx.math.Vector2;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class background {

	Texture background1;

	Sprite bkSprite1;

	Vector2 vt1;

	public background() {
		background1 = null;

		bkSprite1 = null;

		vt1 = new Vector2(0, 0);

	}
	public background(Texture t, Sprite s, Vector2 v)
	{
		background1  = t;
		bkSprite1 = s;
		vt1 = v;
	}
	public void onLoadScene(E3Scene scene) {
		scene.getTopLayer().add(bkSprite1);

	}

	public void onLoadResourcesD(Context context) {
		this.background1 = new AssetTexture("sky2.png", context);

	}

	public void onLoadScene() {
		this.bkSprite1 = new Sprite(this.background1, (int) vt1.x, (int) vt1.y);
		

	}
}
