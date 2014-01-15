package com.example.airplane;

import android.content.Context;

import com.badlogic.gdx.math.Vector2;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.Texture;

public class Items {
	Texture ItemTexture;
	Sprite Itemsprite;
	Vector2 vt;
	int style;
	public Items()
	{
		Itemsprite = null;
		ItemTexture = null;
		vt= new Vector2(0, 0);
		style = -1;
	}
	
	public void onLoadScene(E3Scene scene) {
		
	}

	public void onLoadResourcesD(Context context) {
		
	}

	public void onLoadScene() {
	}

}
