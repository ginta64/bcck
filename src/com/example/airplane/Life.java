package com.example.airplane;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;


public class Life extends Items {
	public Life()
	{
		super();
		style = 4;
		
	}
	public void onLoadScene(E3Scene scene) {
		scene.getTopLayer().add(Itemsprite);

	}

	public void onLoadResourcesD(Context context) {
		this.ItemTexture = new AssetTexture("life.png", context);

	}

	public void onLoadScene() {
		this.Itemsprite = new Sprite(this.ItemTexture, (int) vt.x, (int) vt.y);
		

	}
	

}
