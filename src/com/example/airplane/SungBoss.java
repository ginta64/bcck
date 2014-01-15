package com.example.airplane;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class SungBoss {
	Texture texture;
	Sprite sprite;
	int x, y;
	int blood = 100;
	public SungBoss() {
		
	}

	public SungBoss(Texture texture, Sprite sprite, int x, int y) {
		this.texture = texture;
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}

	public void onLoadScene(E3Scene scene) {
		scene.getTopLayer().add(sprite);
	}
	public void onLoadResourcesD(Context context) {
		texture = new AssetTexture("den.png", context);
	}
	public void onLoadResourcesB(Context context) {
		texture = new AssetTexture("sung.png", context);
	}
	public void onLoadResourcesY1(Context context) {
		texture = new AssetTexture("dy1.png", context);
	}
	public void onLoadResourcesY2(Context context) {
		texture = new AssetTexture("dy2.png", context);
	}

	

	public void onLoadScene() {
		sprite = new Sprite(texture, this.x, this.y);
	}

}
