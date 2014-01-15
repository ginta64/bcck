package com.example.airplane;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;


public class Cap1 extends Items{
	public Cap1()
	{
		super();
		this.style = 2;
	}
	public void onLoadScene(E3Scene scene) {
		scene.getTopLayer().add(Itemsprite);

	}

	public void onLoadResourcesD(Context context) {
		this.ItemTexture = new AssetTexture("fire.png", context);

	}

	public void onLoadScene() {
		this.Itemsprite = new Sprite(this.ItemTexture, (int) vt.x, (int) vt.y);
		

	}

}
