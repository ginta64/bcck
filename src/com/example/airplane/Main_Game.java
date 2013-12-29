package com.example.airplane;

import java.util.ArrayList;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.os.SystemClock;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.e3roid.E3Activity;
import com.e3roid.E3Engine;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Shape;
import com.e3roid.drawable.controls.StickController;
import com.e3roid.drawable.sprite.TextSprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.event.ControllerEventListener;
import com.e3roid.event.SceneUpdateListener;




public class Main_Game extends E3Activity implements ControllerEventListener,
SceneUpdateListener{
	private AssetTexture Mainmenu;
	
	com.e3roid.drawable.Sprite SpriteMainmenu;
	
	TextSprite LabelScore; // điểm kỉ lục
	TextSprite LabelLife; // mạng
	
	private final static int WIDTH = 320;
	private final static int HEIGHT = 480;
	
	private AssetTexture controlBaseTexture;// nền các hướng
	private AssetTexture controlKnobTexture;//nút điều khiển
	
	// 2 background chạy cùng với nhau 
	background bk = new background(null, null, new Vector2(0, 0));
	background bk1 = new background(null, null, new Vector2(0, -480));
	
	//phi thuyen chinh
	PhiThuyen PT;
	private int speedB = 50;//khoảng cách giữa các đạn
	private int limit = 10;

	//enemy
	private ArrayList<EnemyPlane> Mig29 = new ArrayList<EnemyPlane>();
	
	@Override
	public void onUpdateScene(E3Scene arg0, long arg1) {
		// TODO Auto-generated method stub
		bk.bkSprite1.moveRelativeY(5);
		bk1.bkSprite1.moveRelativeY(5);

		if (bk.bkSprite1.getRealY() >= 480) {
			bk.bkSprite1.setVisible(false);
			bk.bkSprite1.moveRelativeY(-960);
			bk.bkSprite1.setVisible(true);

		}
		if (bk1.bkSprite1.getRealY() >= 480) {
			bk1.bkSprite1.setVisible(false);
			bk1.bkSprite1.moveRelativeY(-960);
			bk1.bkSprite1.setVisible(true);

		}
		
		if (PT.VP == 0) {
			Dan b;
			b = new Dan(speedB, PT.PlaneSprite.getRealX()
					+ PT.PlaneSprite.getWidth() / 2 - 18,
					PT.PlaneSprite.getRealY(), true, null, null, 0);
			b.onLoadResourcesD(getApplicationContext());
			b.onLoadScene();
			PT.Dan.add(b);
		}
		
		try
		{
			for (int i = 0; i < PT.Dan.size(); i++) {

				if (PT.Dan.get(i).isf == false) {
					PT.Dan.get(i).onLoadScene(arg0);
					PT.Dan.get(i).isf = true;
				}
				PT.Dan.get(i).GetBulletSprite()
						.moveRelativeY(-PT.Dan.get(i).m_Speed);
			}
			for (Dan b : PT.Dan) {
				if (b.m_bulletSpite.getRealY() < limit) {
					b.m_bulletSpite.setVisible(false);
					PT.Dan.remove(b);

				}
			}
		}
		catch (Exception e) {

		}
		
	}

	@Override
	public void onControlUpdate(StickController controller, int relativeX,
			int relativeY, boolean hasChanged) {
		// TODO Auto-generated method stub
		if (hasChanged) {
			int dir = controller.getDirection();
			if (dir == StickController.LEFT) {

			} else if (dir == StickController.RIGHT) {

			} else if (dir == StickController.UP) {

			} else if (dir == StickController.DOWN) {

			}
		}

		int x = PT.PlaneSprite.getRealX() + (relativeX / 5);
		int y = PT.PlaneSprite.getRealY() + (relativeY / 5);

		if (x > 0 && y > 0 && x < getWidth() - PT.PlaneSprite.getWidth() + 30
				&& y < getHeight() - PT.PlaneSprite.getHeight() - 30) {
			PT.PlaneSprite.move(x, y);
		}
	}

	@Override
	public E3Engine onLoadEngine() {
		// TODO Auto-generated method stub
		E3Engine engine = new E3Engine(this, WIDTH, HEIGHT);
		engine.requestFullScreen();
		return engine;
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		//load phím điều khiển
		controlBaseTexture = new AssetTexture("ControlBase.png", this);
		controlKnobTexture = new AssetTexture("controller_knob.png", this);
		//load phi thuyen
		PT = new PhiThuyen( WIDTH / 2 - 30, (getHeight() / 2) + 100,
				null, null, true);
		PT.onLoadResourcesD(getApplicationContext());
		Mainmenu = new AssetTexture("menu.png",this);
	
	}

	@Override
	public E3Scene onLoadScene() {
		// TODO Auto-generated method stub
		E3Scene scene = new E3Scene();
		scene.addEventListener(this);

		scene.registerUpdateListener(1, this);
		
		bk.onLoadResourcesD(getApplicationContext());
		bk.onLoadScene();

		bk1.onLoadResourcesD(getApplicationContext());
		bk1.onLoadScene();

		bk.onLoadScene(scene);
		bk1.onLoadScene(scene);
		
		StickController controller = new StickController(controlBaseTexture,
				controlKnobTexture, 0, getHeight()
						- controlBaseTexture.getHeight(), scene, this);
		controller.setAlpha(0.4f);
		scene.addHUD(controller);
		scene.addEventListener(controller);
		
		PT.onLoadScene();
		PT.onLoadScene(scene);
		
		SpriteMainmenu = new com.e3roid.drawable.Sprite(Mainmenu, WIDTH / 2 - 10, HEIGHT - 35){
			@Override
			public boolean onTouchEvent(E3Scene scene, Shape shape,
					MotionEvent motionEvent, int localX, int localY) {
				// TODO Auto-generated method stub
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					
					Main_Game.this.finish();
				}
				return false;
			}
		};
		
		scene.addEventListener(SpriteMainmenu);
		scene.getTopLayer().add(SpriteMainmenu);
			
		return scene;
	}
	
}
