package com.example.airplane;

import java.util.ArrayList;
import java.util.Random;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.view.MotionEvent;
import com.badlogic.gdx.math.Vector2;
import com.e3roid.E3Activity;
import com.e3roid.E3Engine;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Shape;
import com.e3roid.drawable.controls.StickController;
import com.e3roid.drawable.sprite.AnimatedSprite;
import com.e3roid.drawable.sprite.TextSprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.TiledTexture;
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
	private ArrayList<EnemyPlane> s33 = new ArrayList<EnemyPlane>();
	//điểm kỉ lục
	int score = 0;
	//vat pham
	Items i;
	//thoi gian
	long time = 0;
	long TimeExit = 0;
	long timesr = 0;
	long timeitems = 0;
	//frame hieu ung no
	private ArrayList<AnimatedSprite.Frame> NoFrames;
	private ArrayList<Nframes> noFrames = new ArrayList<Nframes>();
	
	//Boss
	TrumCuoi boss = new TrumCuoi();
	//sung tren boss
	SungBoss sung1 = new SungBoss();
	SungBoss sung2 = new SungBoss();
	SungBoss sung3 = new SungBoss();
	SungBoss sung4 = new SungBoss();
	SungBoss dy1 = new SungBoss();
	SungBoss dy2 = new SungBoss();
	
	ArrayList<Dan> DanBoss = new ArrayList<Dan>();
	
	//am thanh
	Sound nhacnen;
	Sound tiengno;
	Sound tiengthuong;
	Sound tiengsung;

	boolean b_nhacnen = false;
	boolean b_tiengno = false;
	boolean b_tiengthuong = false;
	boolean b_tiengsung = false;
	int flag = 0;
	//pause
	boolean t = false;
	com.e3roid.drawable.Sprite buttonPauseSprite;
	private AssetTexture buttonPauseTexture;
	@Override
	public void onUpdateScene(E3Scene arg0, long arg1) {
		if (t == false){
		// TODO Auto-generated method stub	
		Intent i1;
		if (PT.m_Life <= 0 && boss.Blood > 0) {
			if (TimeExit == 0) {
				TimeExit = SystemClock.elapsedRealtime();
			}
			if (SystemClock.elapsedRealtime() - TimeExit >= 5000) {

				SharedPreferences settings = getSharedPreferences("AppConfig",
						MODE_PRIVATE);
				int integerValue = settings.getInt("IntegerKey", 0);

				if (score > integerValue) {
					SharedPreferences.Editor editor = settings.edit();
					editor.putInt("IntegerKey", score);
					editor.commit();
				}
				nhacnen.stop();
				TimeExit = 0;
				i1 = new Intent(Main_Game.this, GameOver.class);
				this.startActivity(i1);
				Main_Game.this.finish();
			}
		}
		if (PT.m_Life > 0 && boss.Blood <= 0) {
			if (TimeExit == 0) {
				TimeExit = SystemClock.elapsedRealtime();
			}
			if (SystemClock.elapsedRealtime() - TimeExit >= 5000) {
				SharedPreferences settings = getSharedPreferences("AppConfig",
						MODE_PRIVATE);
				int integerValue = settings.getInt("IntegerKey", 0);

				if (score > integerValue) {
					SharedPreferences.Editor editor = settings.edit();
					editor.putInt("IntegerKey", score);
					editor.commit();
				}

				TimeExit = 0;
				nhacnen.stop();
				i1 = new Intent(Main_Game.this, Win.class);
				this.startActivity(i1);
				Main_Game.this.finish();
			}
		}
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
		
		tiengsung.play();
		
		if (PT.VP == 0) {
			Dan b;
			b = new Dan(speedB, PT.PlaneSprite.getRealX()
					+ PT.PlaneSprite.getWidth() / 2 - 18,
					PT.PlaneSprite.getRealY(), true, null, null, 0);
			b.onLoadResourcesD(getApplicationContext());
			b.onLoadScene();
			PT.Dan.add(b);
		}else {
			if (PT.VP == 2) {
				Dan _bullet1, b2;
				_bullet1 = new Dan(speedB, PT.PlaneSprite.getRealX() - 5,
						PT.PlaneSprite.getRealY() + 40, true, null, null, 0);
				_bullet1.onLoadResourcesD(getApplicationContext());
				_bullet1.onLoadScene();
				b2 = new Dan(speedB, PT.PlaneSprite.getRealX() + 45,
						PT.PlaneSprite.getRealY() + 40, true, null, null, 0);
				b2.onLoadResourcesD(getApplicationContext());
				b2.onLoadScene();

				PT.Dan2.add(_bullet1);
				PT.Dan2.add(b2);

			} else {

				Dan _bullet1, b2, b3;
				_bullet1 = new Dan(speedB, PT.PlaneSprite.getRealX() - 5,
						PT.PlaneSprite.getRealY() + 40, true, null, null, 0);
				_bullet1.onLoadResourcesD(getApplicationContext());
				_bullet1.onLoadScene();
				b2 = new Dan(speedB, PT.PlaneSprite.getRealX()
						+ PT.PlaneSprite.getWidth() / 2 - 18,
						PT.PlaneSprite.getRealY(), true, null, null, 0);
				b2.onLoadResourcesD(getApplicationContext());
				b2.onLoadScene();

				b3 = new Dan(speedB, PT.PlaneSprite.getRealX() + 45,
						PT.PlaneSprite.getRealY() + 40, true, null, null, 0);
				b3.onLoadResourcesD(getApplicationContext());
				b3.onLoadScene();

				PT.Dan3.add(_bullet1);
				PT.Dan3.add(b2);
				PT.Dan3.add(b3);
			}
		}
		timeitems++;
		if (timeitems == 50) {
			timeitems = 0;
			Random r = new Random();
			int x = 10 + (int) (Math.random() * ((WIDTH - 10) + 1));
			int lv = r.nextInt(3);

			if (lv == 0) {
				i = new Cap1();
				i.vt = new Vector2(x, 0);
				i.onLoadResourcesD(getApplicationContext());
				i.onLoadScene();
				i.onLoadScene(arg0);
				flag = 1;
			} else {
				if (lv == 1) {
					i = new Cap2();
					i.vt = new Vector2(x, 0);
					i.onLoadResourcesD(getApplicationContext());
					i.onLoadScene();
					i.onLoadScene(arg0);
					flag = 1;
				} else {
					i = new Life();
					i.vt = new Vector2(x, 0);
					i.onLoadResourcesD(getApplicationContext());
					i.onLoadScene();
					i.onLoadScene(arg0);
					flag = 1;

				}
			}

		}
		if (flag == 1) {
			i.Itemsprite.moveRelativeY(10);

			if (PT.PlaneSprite.collidesWith(i.Itemsprite)
					&& i.Itemsprite.isVisible() && PT.PlaneSprite.isVisible()) {
				i.Itemsprite.setVisible(false);
				if (i.style != 4) {
					PT.VP = i.style;
					tiengthuong.play();
				} else {
					if (i.style == 4) {
						PT.m_Life += 1;
						tiengthuong.play();
						LabelLife.setText("Mạng :" + " x" + PT.m_Life);
					}
				}
			}
			if (i.Itemsprite.getRealY() > HEIGHT) {
				flag = 0;
				i.Itemsprite.setVisible(false);
			}
		}
		for (Nframes no : noFrames) {
			if (SystemClock.elapsedRealtime() - no.Time >= 2000) {
				no.sprite.setVisible(false);

			}
		}
		MIG29(arg0, arg1);
		S333(arg0, arg1);
		
		for (Dan b : DanBoss) {
			if (b.daep == false) {
				b.onLoadScene(arg0);
				b.daep = true;
			}
			if (b.m_bulletSpite.getRealY() < 1000
					&& b.m_bulletSpite.isVisible()) {
				b.m_bulletSpite.moveRelativeY(b.m_Speed);
			}
			if (b.m_bulletSpite.getRealY() >= 1000) {
				b.m_bulletSpite.setVisible(false);
			}
		}

		if (time == 0) {
			time = SystemClock.elapsedRealtime();
		}
		if (SystemClock.elapsedRealtime() - time > 60000) {
			Boss(arg0, arg1);
			TestColPT(DanBoss, arg0);
		}
		
		try {
			for (EnemyPlane e : Mig29) {
				if ((((Mig29) e)._isfire = true)
						&& TestLocal(((Mig29) e)._SpiteTexture.getRealX(),
								((Mig29) e)._SpiteTexture.getRealY())
						&& TestLocal(
								((Mig29) e).Ndan.m_bulletSpite.getRealX(),
								((Mig29) e).Ndan.m_bulletSpite.getRealY())) {

					((Mig29) e)._SpiteTexture.setVisible(false);
					((Mig29) e).Ndan.m_bulletSpite.setVisible(false);

					((Mig29) e)._isLife = false;
					((Mig29) e).Ndan.isd = true;
				}
			}
			for (EnemyPlane e : s33) {
				if ((((S33) e)._isfire = true)
						&& TestLocal(((S33) e)._SpiteTexture.getRealX(),
								((S33) e)._SpiteTexture.getRealY())
						&& TestLocal(
								((S33) e).Ndan.m_bulletSpite.getRealX(),
								((S33) e).Ndan.m_bulletSpite.getRealY())) {

					((S33) e)._SpiteTexture.setVisible(false);
					((S33) e).Ndan.m_bulletSpite.setVisible(false);

					((S33) e)._isLife = false;
					((S33) e).Ndan.isd = true;
				}
			}
			for (int i = 0; i < PT.Dan.size(); i++) {

				if (PT.Dan.get(i).isf == false) {
					PT.Dan.get(i).onLoadScene(arg0);
					PT.Dan.get(i).isf = true;
				}
				PT.Dan.get(i).GetBulletSprite()
						.moveRelativeY(-PT.Dan.get(i).m_Speed);
				TestCMIG29(PT.Dan.get(i), arg0);
				TestCS33(PT.Dan.get(i), arg0);
				if (SystemClock.elapsedRealtime() - time > 60000) {
					TestColBoss(PT.Dan.get(i), arg0);
					TestBulletBoss(PT.Dan.get(i), arg0);
				}
			}
			for (int i = 0; i < PT.Dan2.size(); i += 2) {

				if (PT.Dan2.get(i).isf == false) {
					PT.Dan2.get(i).onLoadScene(arg0);
					PT.Dan2.get(i).isf = true;
				}
				if (PT.Dan2.get(i + 1).isf == false) {
					PT.Dan2.get(i + 1).onLoadScene(arg0);
					PT.Dan2.get(i + 1).isf = true;
				}
				if (PT.Dan2.get(i).isd == false) {

					PT.Dan2.get(i).GetBulletSprite()
							.moveRelativeY(-PT.Dan2.get(i).m_Speed);
					TestCMIG29(PT.Dan2.get(i), arg0);
					TestCS33(PT.Dan2.get(i), arg0);
					if (SystemClock.elapsedRealtime() - time > 60000) {
						TestColBoss(PT.Dan2.get(i), arg0);
						TestColBoss(PT.Dan2.get(i), arg0);
					}
				}
				if (PT.Dan2.get(i + 1).isd == false) {

					PT.Dan2.get(i + 1).GetBulletSprite()
							.moveRelativeY(-PT.Dan2.get(i + 1).m_Speed);
					TestCMIG29(PT.Dan2.get(i + 1), arg0);
					TestCS33(PT.Dan2.get(i + 1), arg0);
					if (SystemClock.elapsedRealtime() - time > 60000) {
						TestColBoss(PT.Dan2.get(i + 1), arg0);
						TestBulletBoss(PT.Dan2.get(i + 1), arg0);
					}
				}
			}

			for (int i = 0; i < PT.Dan3.size(); i += 3) {

				if (PT.Dan3.get(i).isf == false) {
					PT.Dan3.get(i).onLoadScene(arg0);
					PT.Dan3.get(i).isf = true;
				}
				if (PT.Dan3.get(i + 1).isf == false) {
					PT.Dan3.get(i + 1).onLoadScene(arg0);
					PT.Dan3.get(i + 1).isf = true;
				}
				if (PT.Dan3.get(i + 2).isf == false) {
					PT.Dan3.get(i + 2).onLoadScene(arg0);
					PT.Dan3.get(i + 2).isf = true;
				}

				if (PT.Dan3.get(i).isd == false) {

					PT.Dan3.get(i).GetBulletSprite()
							.moveRelativeY(-PT.Dan3.get(i).m_Speed);
					TestCMIG29(PT.Dan3.get(i), arg0);
					TestCS33(PT.Dan3.get(i), arg0);
					if (SystemClock.elapsedRealtime() - time > 60000) {
						TestColBoss(PT.Dan3.get(i), arg0);
						TestBulletBoss(PT.Dan3.get(i), arg0);
					}
				}
				if (PT.Dan3.get(i + 1).isd == false) {

					PT.Dan3.get(i + 1).GetBulletSprite()
							.moveRelativeY(-PT.Dan3.get(i + 1).m_Speed);
					TestCMIG29(PT.Dan3.get(i + 1), arg0);
					TestCS33(PT.Dan3.get(i + 1), arg0);
					if (SystemClock.elapsedRealtime() - time > 60000) {
						TestColBoss(PT.Dan3.get(i + 1), arg0);
						TestBulletBoss(PT.Dan3.get(i + 1), arg0);
					}
				}
				if (PT.Dan3.get(i + 2).isd == false) {

					PT.Dan3.get(i + 2).GetBulletSprite()
							.moveRelativeY(-PT.Dan3.get(i + 2).m_Speed);
					TestCMIG29(PT.Dan3.get(i + 2), arg0);
					TestCS33(PT.Dan3.get(i + 2), arg0);
					if (SystemClock.elapsedRealtime() - time > 60000) {
						TestColBoss(PT.Dan3.get(i + 2), arg0);
						TestBulletBoss(PT.Dan3.get(i + 2), arg0);
					}
				}
			}
			if (SystemClock.elapsedRealtime() - TimePT >= 3000 && TimePT != -1
					&& PT.m_Life > 0) {
				PT.PlaneSprite.setVisible(true);
				PT.VP = 0;
				PT.PlaneSprite.move(WIDTH / 2 - 30, (getHeight() / 2) + 100);

			}
			if (SystemClock.elapsedRealtime() - TimePT >= 6000 && TimePT != -1) {
				TimePT = -1;
				StopTest = false;
			}
			
			if (StopTest == false) {
				TestColPTMig29(arg0);
				TestColPTS33(arg0);
				TestColPTMig29B(arg0);
				TestColPTS33B(arg0);
				BossPT(arg0);
			}	
		}
		 catch (Exception e) {
		}
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
		//load phim pause
		buttonPauseTexture = new AssetTexture("pause.png", this);
		//bk.onLoadResourcesD(getApplicationContext());
		//load phi thuyen
		PT = new PhiThuyen( WIDTH / 2 - 30, (getHeight() / 2) + 100,
				null, null, true);
		PT.onLoadResourcesD(getApplicationContext());
		Mainmenu = new AssetTexture("menu.png",this);
		//load label mang va diem ki luc
		String text = "Điểm :" + "  0" + score;
		LabelScore = new TextSprite(text, 15, Color.YELLOW, Color.TRANSPARENT,
				Typeface.DEFAULT_BOLD, this);
		String text1 = "Mạng :" + "  x" + PT.m_Life;
		LabelLife = new TextSprite(text1, 15, Color.WHITE, Color.TRANSPARENT,
				Typeface.DEFAULT_BOLD, this);
		//////
		NoFrames = new ArrayList<AnimatedSprite.Frame>();
		NoFrames.add(new AnimatedSprite.Frame(0, 0));
		NoFrames.add(new AnimatedSprite.Frame(1, 0));
		NoFrames.add(new AnimatedSprite.Frame(2, 0));
		NoFrames.add(new AnimatedSprite.Frame(3, 0));
		NoFrames.add(new AnimatedSprite.Frame(4, 0));
		NoFrames.add(new AnimatedSprite.Frame(5, 0));
		NoFrames.add(new AnimatedSprite.Frame(6, 0));
		NoFrames.add(new AnimatedSprite.Frame(7, 0));
		NoFrames.add(new AnimatedSprite.Frame(8, 0));
		NoFrames.add(new AnimatedSprite.Frame(9, 0));
		NoFrames.add(new AnimatedSprite.Frame(10, 0));
		NoFrames.add(new AnimatedSprite.Frame(11, 0));
		NoFrames.add(new AnimatedSprite.Frame(12, 0));
		NoFrames.add(new AnimatedSprite.Frame(13, 0));
		NoFrames.add(new AnimatedSprite.Frame(14, 0));
		NoFrames.add(new AnimatedSprite.Frame(15, 0));
	
		sung1.x = 225;
		sung1.y = -100;

		sung2.x = sung1.x + 35;
		sung2.y = -100;

		sung3.x = 225;
		sung3.y = sung1.y + 110;

		sung4.x = sung1.x + 35;
		sung4.y = sung2.y + 110;

		dy1.x = sung1.x + 12;
		dy1.y = sung1.y + 40;

		dy2.x = dy1.x;
		dy2.y = dy1.y + 20;

		sung1.onLoadResourcesB(getApplicationContext());
		sung1.onLoadScene();
		sung2.onLoadResourcesB(getApplicationContext());
		sung2.onLoadScene();
		sung3.onLoadResourcesB(getApplicationContext());
		sung3.onLoadScene();
		sung4.onLoadResourcesB(getApplicationContext());
		sung4.onLoadScene();
		dy1.onLoadResourcesY1(getApplicationContext());
		dy1.onLoadScene();
		dy2.onLoadResourcesY2(getApplicationContext());
		dy2.onLoadScene();
	}

	@Override
	public E3Scene onLoadScene() {
		// TODO Auto-generated method stub
		E3Scene scene = new E3Scene();
		scene.addEventListener(this);

		scene.registerUpdateListener(1, this);
		
		nhacnen = new Sound(getApplicationContext(),
				R.raw.nhacnen);
		tiengno = new Sound(getApplicationContext(),
				R.raw.no1);
		tiengthuong = new Sound(getApplicationContext(),
				R.raw.thuong);
		tiengsung = new Sound(getApplicationContext(),
				R.raw.tiengno);
		
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
		buttonPauseSprite = new com.e3roid.drawable.Sprite(buttonPauseTexture, WIDTH - 80, HEIGHT - 40) {
			@Override
			public boolean onTouchEvent(E3Scene scene, Shape shape,
					MotionEvent motionEvent, int localX, int localY) {
				// TODO Auto-generated method stub
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					if(t == false)
						t =true;
					else
						t = false;
				}
				return false;
			}
		};

		scene.addEventListener(buttonPauseSprite);

		scene.getTopLayer().add(buttonPauseSprite);
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
		//load button tro ve menu chinh
		scene.addEventListener(SpriteMainmenu);
		scene.getTopLayer().add(SpriteMainmenu);
		//load mang va diem ki luc	
		scene.getTopLayer().add(LabelLife);
		scene.getTopLayer().add(LabelScore);
		LabelScore.move(WIDTH - 90, 0);
		LabelLife.move(8, 0);
		
		boss.onLoadResourcesB(getApplicationContext());
		nhacnen.play();
		return scene;
	}
	int k=0;
	int MIN = 2;
	int MAX = 4;
	int l1 = 0, l2 = 0;
	int limit1 = 10;
	int dh = 200;
	
	public void MIG29(E3Scene arg0, long arg1) {
		k++;
		if (k == 50 ) {
			k = 0;
			Random ran = new Random();
			int sl = MIN + (int) (Math.random() * ((MAX - MIN) + 1));

			if (PT.PlaneSprite.getRealX() >= (getWidth() / 2)) {
				int kq = ran.nextInt(2);
				if (kq == 0) {

					int move = ran.nextInt(3);
					int x = ran.nextInt(getWidth() / 2);
					for (int i = 0; i < sl; i++) {

						Mig29 m = new Mig29();
						m.Ndan = new Dan(limit1 + 5, x, dh, true, null,
								null, 0);
						m.Ndan.onLoadResources(getApplicationContext());
						m.Ndan.onLoadScene();

						m.setX(x);
						m.setY(0 - l1);
						m.onLoadResourcesD(getApplicationContext());
						m.onLoadScene();
						if (((Mig29) m).wayMove == -1) {
							((Mig29) m).setW(move);
						}
						if (((Mig29) m).wayAp == -1) {
							((Mig29) m).wayAp = kq;
						}
						Mig29.add(m);
						l1 += 30;
					}
				} else {
					Random ra = new Random();
					int move = ra.nextInt(3);
					int wayap = ra.nextInt(2);
					int y = ra.nextInt(getHeight() / 2);
					for (int i = 0; i < sl; i++) {

						Mig29 m = new Mig29();
						m.setX(0 - l1);
						m.setY(y);

						m.Ndan = new Dan(limit1 + 5, 0, y, true, null,
								null, 0);
						m.Ndan.onLoadResources(getApplicationContext());
						m.Ndan.onLoadScene();

						if (((Mig29) m).wayMove == -1) {
							((Mig29) m).setW(move);
						}
						if (((Mig29) m).wayAp == -1) {
							((Mig29) m).wayAp = kq;
						}
						if (wayap == 0) {
							((Mig29) m)._isAcross = true;
							m.onLoadResourcesR(getApplicationContext());
							m.onLoadScene();

						} else {
							((Mig29) m)._isAcross = false;
							m.onLoadResourcesRD(getApplicationContext());
							m.onLoadScene();
						}

						Mig29.add(m);
						l1 += 30;
					}

				}

			} else {
				Random r = new Random();
				int kq = r.nextInt(5 - 3) + 3;

				if (kq == 4) {
					int move = ran.nextInt(3);
					int x = r.nextInt(getWidth() - (getWidth() / 2))
							+ (getWidth() / 2);
					for (int i = 0; i < sl; i++) {
						Mig29 m = new Mig29();
						m.Ndan = new Dan(limit1 + 5, x, dh, true, null,
								null, 0);
						m.Ndan.onLoadResources(getApplicationContext());
						m.Ndan.onLoadScene();

						m.setX(x);
						m.setY(0 - l2);
						m.onLoadResourcesD(getApplicationContext());
						m.onLoadScene();
						if (((Mig29) m).wayMove == -1) {
							((Mig29) m).setW(move);
						}
						if (((Mig29) m).wayAp == -1) {
							((Mig29) m).wayAp = kq;
						}

						Mig29.add(m);
						l2 += 30;
					}
				} else {
					if (kq == 3) {

						Random ra = new Random();
						int move = ra.nextInt(3);
						int wayap = ra.nextInt(2);
						int y = ra.nextInt((getHeight() / 2));
						for (int i = 0; i < sl; i++) {

							Mig29 m = new Mig29();

							m.Ndan = new Dan(limit1 + 5, getWidth(), y,
									true, null, null, 0);
							m.Ndan.onLoadResources(getApplicationContext());
							m.Ndan.onLoadScene();

							m.setX(320 + l2);
							m.setY(y);
							
							if (((Mig29) m).wayMove == -1) {
								((Mig29) m).setW(move);
							}
							if (((Mig29) m).wayAp == -1) {
								((Mig29) m).wayAp = kq;
							}
							if (wayap == 0) {
								((Mig29) m)._isAcross = true;
								m.onLoadResourcesL(getApplicationContext());
								m.onLoadScene();
							} else {
								((Mig29) m)._isAcross = false;
								m.onLoadResourcesLD(getApplicationContext());
								m.onLoadScene();
							}
							Mig29.add(m);
							l2 += 30;
						}

					}
				}

			}
		}
		for (EnemyPlane e : Mig29) {

			if (((Mig29) e)._isLife == true) {
				if (((Mig29) e)._isf == false) {
					((Mig29) e).onLoadScene(arg0);

					((Mig29) e)._isf = true;
				}
				if (((Mig29) e).wayAp == 0) {
					if (((Mig29) e).getY() >= dh) {
						if (((Mig29) e).Ndan.vt == 0) {
							((Mig29) e).Ndan.SetX(0);
							((Mig29) e).Ndan.SetY(0);
							((Mig29) e).Ndan.vt = 1;
						}
						((Mig29) e).Ndan.onLoadScene(arg0);
						if (PT.PlaneSprite.getRealX() > getWidth() / 2) {
							((Mig29) e).Ndan.moveR(((Mig29) e).Ndan.m_Speed,
									((Mig29) e).Ndan.m_Speed);
							((Mig29) e).Ndan.m_X += ((Mig29) e).Ndan.m_Speed;
							((Mig29) e).Ndan.m_Y += ((Mig29) e).Ndan.m_Speed;
							((Mig29) e)._isfire = true;

						} else {
							((Mig29) e).Ndan.moveL(((Mig29) e).Ndan.m_Speed,
									((Mig29) e).Ndan.m_Speed);
							((Mig29) e).Ndan.m_X += ((Mig29) e).Ndan.m_Speed;
							((Mig29) e).Ndan.m_Y += ((Mig29) e).Ndan.m_Speed;
							((Mig29) e)._isfire = true;

						}

						if (((Mig29) e).wayMove == 0) {
							((Mig29) e)._SpiteTexture.moveRelativeY(e.speed);
						} else {
							if (((Mig29) e).wayMove == 1) {

								if (((Mig29) e)._status == false
										&& ((Mig29) e)._SpiteTexture.isVisible()) {
									((Mig29) e)
											.onLoadResourcesLD(getApplicationContext());

									((Mig29) e)._SpiteTexture.onRemove();
									((Mig29) e).onLoadScene();

									((Mig29) e).onLoadScene(arg0);
									((Mig29) e)._status = true;
								}
								((Mig29) e)._SpiteTexture.moveRelative(-e.speed,
										e.speed);

								((Mig29) e).setX(((Mig29) e).getX() - e.speed);
								((Mig29) e).setY(((Mig29) e).getY() + e.speed);

							} else {
								if (((Mig29) e).wayMove == 2) {

									if (((Mig29) e)._status == false
											&& ((Mig29) e)._SpiteTexture
													.isVisible()) {

										((Mig29) e)
												.onLoadResourcesRD(getApplicationContext());
										((Mig29) e)._SpiteTexture.onRemove();
										((Mig29) e).onLoadScene();
										((Mig29) e).onLoadScene(arg0);
										((Mig29) e)._status = true;
									}
									((Mig29) e)._SpiteTexture.moveRelative(
											e.speed, e.speed);
									((Mig29) e)
											.setX(((Mig29) e).getX() + e.speed);
									((Mig29) e)
											.setY(((Mig29) e).getY() + e.speed);
								}
							}
						}

					} else {
						((Mig29) e)._SpiteTexture.moveRelativeY(e.speed);
						((Mig29) e).setY(((Mig29) e).getY() + e.speed);
						((Mig29) e).setX(((Mig29) e).getX());

					}
				} else {
					if (((Mig29) e).wayAp == 1) {

						if (((Mig29) e).getX() >= dh) {

							if (((Mig29) e).Ndan.vt == 0) {
								((Mig29) e).Ndan.SetX(0);
								((Mig29) e).Ndan.SetY(0);
								((Mig29) e).Ndan.vt = 1;
							}
							((Mig29) e).Ndan.onLoadScene(arg0);

							if (PT.PlaneSprite.getRealX() >= getWidth() / 2) {
								((Mig29) e).Ndan.moveR(
										((Mig29) e).Ndan.m_Speed,
										((Mig29) e).Ndan.m_Speed);
								((Mig29) e).Ndan.m_X += ((Mig29) e).Ndan.m_Speed;
								((Mig29) e).Ndan.m_Y += ((Mig29) e).Ndan.m_Speed;
								((Mig29) e)._isfire = true;
							} else {
								((Mig29) e).Ndan.moveL(
										((Mig29) e).Ndan.m_Speed,
										((Mig29) e).Ndan.m_Speed);
								((Mig29) e).Ndan.m_X += ((Mig29) e).Ndan.m_Speed;
								((Mig29) e).Ndan.m_Y += ((Mig29) e).Ndan.m_Speed;
								((Mig29) e)._isfire = true;
							}

							if (((Mig29) e).wayMove == 0) {

								if (((Mig29) e)._status == false
										&& ((Mig29) e)._SpiteTexture.isVisible()) {
									((Mig29) e)
											.onLoadResourcesD(getApplicationContext());

									((Mig29) e)._SpiteTexture.onRemove();
									((Mig29) e).onLoadScene();

									((Mig29) e).onLoadScene(arg0);
									((Mig29) e)._status = true;
								}

								((Mig29) e)._SpiteTexture.moveRelativeY(e.speed);
								((Mig29) e).setX(((Mig29) e).getX());
								((Mig29) e).setY(((Mig29) e).getY() + e.speed);

							} else {
								if (((Mig29) e).wayMove == 1) {

									if (((Mig29) e)._status == false
											&& ((Mig29) e)._SpiteTexture
													.isVisible()) {
										((Mig29) e)
												.onLoadResourcesLD(getApplicationContext());
										((Mig29) e)._SpiteTexture.onRemove();
										((Mig29) e).onLoadScene();
										((Mig29) e).onLoadScene(arg0);
										((Mig29) e)._status = true;
									}

									((Mig29) e)._SpiteTexture.moveRelative(
											-e.speed, e.speed);
								} else {
									if (((Mig29) e).wayMove == 2) {
										if (((Mig29) e)._status == false
												&& ((Mig29) e)._SpiteTexture
														.isVisible()) {

											((Mig29) e)
													.onLoadResourcesRD(getApplicationContext());
											((Mig29) e)._SpiteTexture.onRemove();
											((Mig29) e).onLoadScene();
											((Mig29) e).onLoadScene(arg0);
											((Mig29) e)._status = true;
										}
										((Mig29) e)._SpiteTexture.moveRelative(
												e.speed, e.speed);
										((Mig29) e).setX(((Mig29) e).getX()
												+ e.speed);
										((Mig29) e).setY(((Mig29) e).getY()
												+ e.speed);
									}

								}
							}
						} else {
							if (((Mig29) e)._isAcross == true) {
								((Mig29) e)._SpiteTexture.moveRelativeX(e.speed);
								((Mig29) e).setX(((Mig29) e).getX() + e.speed);
								((Mig29) e).setY(((Mig29) e).getY());

							} else {
								((Mig29) e)._SpiteTexture.moveRelative(e.speed,
										e.speed);

								((Mig29) e).setX(((Mig29) e).getX() + e.speed);
								((Mig29) e).setY(((Mig29) e).getY() + e.speed);

							}

						}

					} else {
						if (((Mig29) e).wayAp == 4) {
							if (((Mig29) e).getY() >= dh) {

								if (((Mig29) e).Ndan.vt == 0) {
									((Mig29) e).Ndan.SetX(0);
									((Mig29) e).Ndan.SetY(0);
									((Mig29) e).Ndan.vt = 1;
								}
								((Mig29) e).Ndan.onLoadScene(arg0);
								if (PT.PlaneSprite.getRealX() <= getWidth() / 2) {
									((Mig29) e).Ndan.moveL(
											((Mig29) e).Ndan.m_Speed,
											((Mig29) e).Ndan.m_Speed);
									((Mig29) e).Ndan.m_X += ((Mig29) e).Ndan.m_Speed;
									((Mig29) e).Ndan.m_Y += ((Mig29) e).Ndan.m_Speed;
									((Mig29) e)._isfire = true;
								} else {
									((Mig29) e).Ndan.moveD(
											((Mig29) e).Ndan.m_Speed,
											((Mig29) e).Ndan.m_Speed);
									((Mig29) e).Ndan.m_X += ((Mig29) e).Ndan.m_Speed;
									((Mig29) e).Ndan.m_Y += ((Mig29) e).Ndan.m_Speed;
									((Mig29) e)._isfire = true;
								}

								if (((Mig29) e).wayMove == 0) {
									((Mig29) e)._SpiteTexture
											.moveRelativeY(e.speed);
								} else {
									if (((Mig29) e).wayMove == 1) {

										if (((Mig29) e)._status == false
												&& ((Mig29) e)._SpiteTexture
														.isVisible()) {
											((Mig29) e)
													.onLoadResourcesLD(getApplicationContext());

											((Mig29) e)._SpiteTexture.onRemove();
											((Mig29) e).onLoadScene();

											((Mig29) e).onLoadScene(arg0);
											((Mig29) e)._status = true;
										}
										((Mig29) e)._SpiteTexture.moveRelative(
												-e.speed, e.speed);

										((Mig29) e).setX(((Mig29) e).getX()
												- e.speed);
										((Mig29) e).setY(((Mig29) e).getY()
												+ e.speed);
									} else {
										if (((Mig29) e).wayMove == 2) {

											if (((Mig29) e)._status == false
													&& ((Mig29) e)._SpiteTexture
															.isVisible()) {

												((Mig29) e)
														.onLoadResourcesRD(getApplicationContext());
												((Mig29) e)._SpiteTexture
														.onRemove();
												((Mig29) e).onLoadScene();
												((Mig29) e).onLoadScene(arg0);
												((Mig29) e)._status = true;
											}
											((Mig29) e)._SpiteTexture
													.moveRelative(e.speed,
															e.speed);
											((Mig29) e).setX(((Mig29) e).getX()
													+ e.speed);
											((Mig29) e).setY(((Mig29) e).getY()
													+ e.speed);
										}
									}
								}

							} else {
								((Mig29) e)._SpiteTexture.moveRelativeY(e.speed);
								((Mig29) e).setY(((Mig29) e).getY() + e.speed);
								((Mig29) e).setX(((Mig29) e).getX());
							}

						} else {
							if (((Mig29) e).wayAp == 3) {
								if (PT.PlaneSprite.getRealX() < getWidth() / 2) {

									((Mig29) e).Ndan.onLoadScene(arg0);
									((Mig29) e).Ndan.moveL(
											((Mig29) e).Ndan.m_Speed,
											((Mig29) e).Ndan.m_Speed);
									((Mig29) e).Ndan.m_X += ((Mig29) e).Ndan.m_Speed;
									((Mig29) e).Ndan.m_Y += ((Mig29) e).Ndan.m_Speed;
									((Mig29) e)._isfire = true;
								} else {
									((Mig29) e).Ndan.moveD(
											((Mig29) e).Ndan.m_Speed,
											((Mig29) e).Ndan.m_Speed);
									((Mig29) e).Ndan.m_X += ((Mig29) e).Ndan.m_Speed;
									((Mig29) e).Ndan.m_Y += ((Mig29) e).Ndan.m_Speed;
									((Mig29) e)._isfire = true;
								}

								if (((Mig29) e).getX() <= 100) {

									if (((Mig29) e).Ndan.vt == 0) {
										((Mig29) e).Ndan.SetX(0);
										((Mig29) e).Ndan.SetY(0);
										((Mig29) e).Ndan.vt = 1;
									}

									if (((Mig29) e).wayMove == 0) {

										if (((Mig29) e)._status == false
												&& ((Mig29) e)._SpiteTexture
														.isVisible()) {
											((Mig29) e)
													.onLoadResourcesD(getApplicationContext());

											((Mig29) e)._SpiteTexture.onRemove();
											((Mig29) e).onLoadScene();

											((Mig29) e).onLoadScene(arg0);
											((Mig29) e)._status = true;
										}

										((Mig29) e)._SpiteTexture
												.moveRelativeY(e.speed);
										((Mig29) e).setX(((Mig29) e).getX());
										((Mig29) e).setY(((Mig29) e).getY()
												+ e.speed);

									} else {
										if (((Mig29) e).wayMove == 1) {

											if (((Mig29) e)._status == false
													&& ((Mig29) e)._SpiteTexture
															.isVisible()) {
												((Mig29) e)
														.onLoadResourcesLD(getApplicationContext());
												((Mig29) e)._SpiteTexture
														.onRemove();
												((Mig29) e).onLoadScene();
												((Mig29) e).onLoadScene(arg0);
												((Mig29) e)._status = true;
											}

											((Mig29) e)._SpiteTexture
													.moveRelative(-e.speed,
															e.speed);

										} else {
											if (((Mig29) e).wayMove == 2) {
												if (((Mig29) e)._status == false
														&& ((Mig29) e)._SpiteTexture
																.isVisible()) {

													((Mig29) e)
															.onLoadResourcesRD(getApplicationContext());
													((Mig29) e)._SpiteTexture
															.onRemove();
													((Mig29) e).onLoadScene();
													((Mig29) e)
															.onLoadScene(arg0);
													((Mig29) e)._status = true;
												}
												((Mig29) e)._SpiteTexture
														.moveRelative(e.speed,
																e.speed);
											}
										}
									}

								} else {
									if (((Mig29) e)._isAcross == true) {
										((Mig29) e)._SpiteTexture
												.moveRelativeX(-e.speed);
										((Mig29) e).setX(((Mig29) e).getX()
												- e.speed);
										((Mig29) e).setY(((Mig29) e).getY());

									} else {
										((Mig29) e)._SpiteTexture.moveRelative(
												-e.speed, e.speed);
										((Mig29) e).setX(((Mig29) e).getX()
												- e.speed);
										((Mig29) e).setY(((Mig29) e).getY()
												+ e.speed);

									}

								}

							}
						}
					}
				}
			}
		}
	}
	private boolean TestLocal(int _x, int _y) {
		if (_x < -30 || _x > 700 || _y > 600)
			return true;
		return false;
	}

	public void TestCMIG29(Dan PTb, E3Scene arg0) {
		for (EnemyPlane e : Mig29) {
			if (((Mig29) e)._SpiteTexture.collidesWith(PTb.m_bulletSpite)
					&& PTb.m_bulletSpite.isVisible()
					&& ((Mig29) e)._SpiteTexture.isVisible()) {

				PTb.m_bulletSpite.setVisible(false);

				((Mig29) e)._SpiteTexture.setVisible(false);

				PTb.isd = true;
				score += 10;
				LabelScore.setText("Điểm :" + " " + String.valueOf(score));
				NoEventF(arg0, ((Mig29) e)._SpiteTexture.getRealX(),
						((Mig29) e)._SpiteTexture.getRealY());
				//tiengno.play();
			}
		}
	}
	private void NoEventF(E3Scene arg0, int x, int y) {
		Nframes no = new Nframes();
		no.texture = new TiledTexture("No.png", 60, 80, 0, 0, 4, 3, this);
		no.sprite = new AnimatedSprite(no.texture, x, y);
		no.sprite.animate(30, NoFrames);
		arg0.getTopLayer().add(no.sprite);
		no.Time = SystemClock.elapsedRealtime();
		noFrames.add(no);
	}
	private void NoEvent(E3Scene arg0, int x, int y) {
		Nframes no = new Nframes();
		no.texture = new TiledTexture("No.png", 60, 80, 0, 0, 4, 3, this);
		no.sprite = new AnimatedSprite(no.texture, x, y+60);
		no.sprite.animate(30, NoFrames);
		arg0.getTopLayer().add(no.sprite);
		no.Time = SystemClock.elapsedRealtime();
		noFrames.add(no);
	}
	boolean StopTest = false;
	long TimePT = -1;
	public void TestColPTMig29(E3Scene arg0) {
		for (EnemyPlane e : Mig29) {
			if (((Mig29) e)._SpiteTexture.collidesWith(PT.PlaneSprite)) {
				((Mig29) e)._SpiteTexture.setVisible(false);
				PT.PlaneSprite.setVisible(false);
				NoEventF(arg0, PT.PlaneSprite.getRealX(),
						PT.PlaneSprite.getRealY());
				tiengno.play();
				if (PT.m_Life > 0) {
					PT.m_Life -= 1;

					LabelLife.setText("Mạng :" + " x" + PT.m_Life);
					PT.PlaneSprite.move(1000, 1000);
					StopTest = true;
					if (TimePT == -1) {
						TimePT = SystemClock.elapsedRealtime();
					}
				}
			}
		}
	}
	public void TestColPTMig29B(E3Scene arg0) {
		for (EnemyPlane e : Mig29) {
			if (((Mig29) e).Ndan.m_bulletSpite.collidesWith(PT.PlaneSprite)) {
				((Mig29) e).Ndan.m_bulletSpite.setVisible(false);
				PT.PlaneSprite.setVisible(false);
				NoEventF(arg0, PT.PlaneSprite.getRealX(),
						PT.PlaneSprite.getRealY());
				tiengno.play();
				if (PT.m_Life > 0) {
					PT.m_Life -= 1;

					LabelLife.setText("Mạng :" + " x" + PT.m_Life);
					PT.PlaneSprite.move(1000, 1000);
					StopTest = true;
					if (TimePT == -1) {
						TimePT = SystemClock.elapsedRealtime();
					}
				}
			}
		}
	}
	int k2 = 0 ;//so luong quan dich
	int l3=0, l4 = 0;
	public void S333(E3Scene arg0, long arg1) {
		k2++;
		if (k2 == 50 ) {
			k2 = 0;
			Random ran = new Random();
			int sl = MIN + (int) (Math.random() * ((MAX - MIN) + 1));

			if (PT.PlaneSprite.getRealX() >= (getWidth() / 2)) {
				int kq = ran.nextInt(2);
				if (kq == 0) {

					int move = ran.nextInt(3);
					int x = ran.nextInt(getWidth() / 2);
					for (int i = 0; i < sl; i++) {

						S33 m = new S33();
						m.Ndan = new Dan(limit1 + 5, x, dh, true, null,
								null, 0);
						m.Ndan.onLoadResources(getApplicationContext());
						m.Ndan.onLoadScene();

						m.setX(x);
						m.setY(0 - l1);
						m.onLoadResourcesD(getApplicationContext());
						m.onLoadScene();
						if (((S33) m).wayMove == -1) {
							((S33) m).setW(move);
						}
						if (((S33) m).wayAp == -1) {
							((S33) m).wayAp = kq;
						}
						s33.add(m);
						l3 += 30;
					}
				} else {
					Random ra = new Random();
					int move = ra.nextInt(3);
					int wayap = ra.nextInt(2);
					int y = ra.nextInt(getHeight() / 2);
					for (int i = 0; i < sl; i++) {

						S33 m = new S33();
						m.setX(0 - l1);
						m.setY(y);

						m.Ndan = new Dan(limit1 + 5, 0, y, true, null,
								null, 0);
						m.Ndan.onLoadResources(getApplicationContext());
						m.Ndan.onLoadScene();

						if (((S33) m).wayMove == -1) {
							((S33) m).setW(move);
						}
						if (((S33) m).wayAp == -1) {
							((S33) m).wayAp = kq;
						}
						if (wayap == 0) {
							((S33) m)._isAcross = true;
							m.onLoadResourcesR(getApplicationContext());
							m.onLoadScene();

						} else {
							((S33) m)._isAcross = false;
							m.onLoadResourcesRD(getApplicationContext());
							m.onLoadScene();
						}

						s33.add(m);
						l3 += 30;
					}

				}

			} else {
				Random r = new Random();
				int kq = r.nextInt(5 - 3) + 3;

				if (kq == 4) {
					int move = ran.nextInt(3);
					int x = r.nextInt(getWidth() - (getWidth() / 2))
							+ (getWidth() / 2);
					for (int i = 0; i < sl; i++) {
						S33 m = new S33();
						m.Ndan = new Dan(limit1 + 5, x, dh, true, null,
								null, 0);
						m.Ndan.onLoadResources(getApplicationContext());
						m.Ndan.onLoadScene();

						m.setX(x);
						m.setY(0 - l2);
						m.onLoadResourcesD(getApplicationContext());
						m.onLoadScene();
						if (((S33) m).wayMove == -1) {
							((S33) m).setW(move);
						}
						if (((S33) m).wayAp == -1) {
							((S33) m).wayAp = kq;
						}

						s33.add(m);
						l4 += 30;
					}
				} else {
					if (kq == 3) {

						Random ra = new Random();
						int move = ra.nextInt(3);
						int wayap = ra.nextInt(2);
						int y = ra.nextInt((getHeight() / 2));
						for (int i = 0; i < sl; i++) {

							S33 m = new S33();

							m.Ndan = new Dan(limit1 + 5, getWidth(), y,
									true, null, null, 0);
							m.Ndan.onLoadResources(getApplicationContext());
							m.Ndan.onLoadScene();

							m.setX(320 + l2);
							m.setY(y);
							
							if (((S33) m).wayMove == -1) {
								((S33) m).setW(move);
							}
							if (((S33) m).wayAp == -1) {
								((S33) m).wayAp = kq;
							}
							if (wayap == 0) {
								((S33) m)._isAcross = true;
								m.onLoadResourcesL(getApplicationContext());
								m.onLoadScene();
							} else {
								((S33) m)._isAcross = false;
								m.onLoadResourcesLD(getApplicationContext());
								m.onLoadScene();
							}
							s33.add(m);
							l4 += 30;
						}

					}
				}

			}
		}
		for (EnemyPlane e : s33) {

			if (((S33) e)._isLife == true) {
				if (((S33) e)._isf == false) {
					((S33) e).onLoadScene(arg0);

					((S33) e)._isf = true;
				}
				if (((S33) e).wayAp == 0) {
					if (((S33) e).getY() >= dh) {
						if (((S33) e).Ndan.vt == 0) {
							((S33) e).Ndan.SetX(0);
							((S33) e).Ndan.SetY(0);
							((S33) e).Ndan.vt = 1;
						}
						((S33) e).Ndan.onLoadScene(arg0);
						if (PT.PlaneSprite.getRealX() > getWidth() / 2) {
							((S33) e).Ndan.moveR(((S33) e).Ndan.m_Speed,
									((S33) e).Ndan.m_Speed);
							((S33) e).Ndan.m_X += ((S33) e).Ndan.m_Speed;
							((S33) e).Ndan.m_Y += ((S33) e).Ndan.m_Speed;
							((S33) e)._isfire = true;

						} else {
							((S33) e).Ndan.moveL(((S33) e).Ndan.m_Speed,
									((S33) e).Ndan.m_Speed);
							((S33) e).Ndan.m_X += ((S33) e).Ndan.m_Speed;
							((S33) e).Ndan.m_Y += ((S33) e).Ndan.m_Speed;
							((S33) e)._isfire = true;

						}

						if (((S33) e).wayMove == 0) {
							((S33) e)._SpiteTexture.moveRelativeY(e.speed);
						} else {
							if (((S33) e).wayMove == 1) {

								if (((S33) e)._status == false
										&& ((S33) e)._SpiteTexture.isVisible()) {
									((S33) e)
											.onLoadResourcesLD(getApplicationContext());

									((S33) e)._SpiteTexture.onRemove();
									((S33) e).onLoadScene();

									((S33) e).onLoadScene(arg0);
									((S33) e)._status = true;
								}
								((S33) e)._SpiteTexture.moveRelative(-e.speed,
										e.speed);

								((S33) e).setX(((S33) e).getX() - e.speed);
								((S33) e).setY(((S33) e).getY() + e.speed);

							} else {
								if (((S33) e).wayMove == 2) {

									if (((S33) e)._status == false
											&& ((S33) e)._SpiteTexture
													.isVisible()) {

										((S33) e)
												.onLoadResourcesRD(getApplicationContext());
										((S33) e)._SpiteTexture.onRemove();
										((S33) e).onLoadScene();
										((S33) e).onLoadScene(arg0);
										((S33) e)._status = true;
									}
									((S33) e)._SpiteTexture.moveRelative(
											e.speed, e.speed);
									((S33) e)
											.setX(((S33) e).getX() + e.speed);
									((S33) e)
											.setY(((S33) e).getY() + e.speed);
								}
							}
						}

					} else {
						((S33) e)._SpiteTexture.moveRelativeY(e.speed);
						((S33) e).setY(((S33) e).getY() + e.speed);
						((S33) e).setX(((S33) e).getX());

					}
				} else {
					if (((S33) e).wayAp == 1) {

						if (((S33) e).getX() >= dh) {

							if (((S33) e).Ndan.vt == 0) {
								((S33) e).Ndan.SetX(0);
								((S33) e).Ndan.SetY(0);
								((S33) e).Ndan.vt = 1;
							}
							((S33) e).Ndan.onLoadScene(arg0);

							if (PT.PlaneSprite.getRealX() >= getWidth() / 2) {
								((S33) e).Ndan.moveR(
										((S33) e).Ndan.m_Speed,
										((S33) e).Ndan.m_Speed);
								((S33) e).Ndan.m_X += ((S33) e).Ndan.m_Speed;
								((S33) e).Ndan.m_Y += ((S33) e).Ndan.m_Speed;
								((S33) e)._isfire = true;
							} else {
								((S33) e).Ndan.moveL(
										((S33) e).Ndan.m_Speed,
										((S33) e).Ndan.m_Speed);
								((S33) e).Ndan.m_X += ((S33) e).Ndan.m_Speed;
								((S33) e).Ndan.m_Y += ((S33) e).Ndan.m_Speed;
								((S33) e)._isfire = true;
							}

							if (((S33) e).wayMove == 0) {

								if (((S33) e)._status == false
										&& ((S33) e)._SpiteTexture.isVisible()) {
									((S33) e)
											.onLoadResourcesD(getApplicationContext());

									((S33) e)._SpiteTexture.onRemove();
									((S33) e).onLoadScene();

									((S33) e).onLoadScene(arg0);
									((S33) e)._status = true;
								}

								((S33) e)._SpiteTexture.moveRelativeY(e.speed);
								((S33) e).setX(((S33) e).getX());
								((S33) e).setY(((S33) e).getY() + e.speed);

							} else {
								if (((S33) e).wayMove == 1) {

									if (((S33) e)._status == false
											&& ((S33) e)._SpiteTexture
													.isVisible()) {
										((S33) e)
												.onLoadResourcesLD(getApplicationContext());
										((S33) e)._SpiteTexture.onRemove();
										((S33) e).onLoadScene();
										((S33) e).onLoadScene(arg0);
										((S33) e)._status = true;
									}

									((S33) e)._SpiteTexture.moveRelative(
											-e.speed, e.speed);
								} else {
									if (((S33) e).wayMove == 2) {
										if (((S33) e)._status == false
												&& ((S33) e)._SpiteTexture
														.isVisible()) {

											((S33) e)
													.onLoadResourcesRD(getApplicationContext());
											((S33) e)._SpiteTexture.onRemove();
											((S33) e).onLoadScene();
											((S33) e).onLoadScene(arg0);
											((S33) e)._status = true;
										}
										((S33) e)._SpiteTexture.moveRelative(
												e.speed, e.speed);
										((S33) e).setX(((S33) e).getX()
												+ e.speed);
										((S33) e).setY(((S33) e).getY()
												+ e.speed);
									}

								}
							}
						} else {
							if (((S33) e)._isAcross == true) {
								((S33) e)._SpiteTexture.moveRelativeX(e.speed);
								((S33) e).setX(((S33) e).getX() + e.speed);
								((S33) e).setY(((S33) e).getY());

							} else {
								((S33) e)._SpiteTexture.moveRelative(e.speed,
										e.speed);

								((S33) e).setX(((S33) e).getX() + e.speed);
								((S33) e).setY(((S33) e).getY() + e.speed);

							}

						}

					} else {
						if (((S33) e).wayAp == 4) {
							if (((S33) e).getY() >= dh) {

								if (((S33) e).Ndan.vt == 0) {
									((S33) e).Ndan.SetX(0);
									((S33) e).Ndan.SetY(0);
									((S33) e).Ndan.vt = 1;
								}
								((S33) e).Ndan.onLoadScene(arg0);
								if (PT.PlaneSprite.getRealX() <= getWidth() / 2) {
									((S33) e).Ndan.moveL(
											((S33) e).Ndan.m_Speed,
											((S33) e).Ndan.m_Speed);
									((S33) e).Ndan.m_X += ((S33) e).Ndan.m_Speed;
									((S33) e).Ndan.m_Y += ((S33) e).Ndan.m_Speed;
									((S33) e)._isfire = true;
								} else {
									((S33) e).Ndan.moveD(
											((S33) e).Ndan.m_Speed,
											((S33) e).Ndan.m_Speed);
									((S33) e).Ndan.m_X += ((S33) e).Ndan.m_Speed;
									((S33) e).Ndan.m_Y += ((S33) e).Ndan.m_Speed;
									((S33) e)._isfire = true;
								}

								if (((S33) e).wayMove == 0) {
									((S33) e)._SpiteTexture
											.moveRelativeY(e.speed);
								} else {
									if (((S33) e).wayMove == 1) {

										if (((S33) e)._status == false
												&& ((S33) e)._SpiteTexture
														.isVisible()) {
											((S33) e)
													.onLoadResourcesLD(getApplicationContext());

											((S33) e)._SpiteTexture.onRemove();
											((S33) e).onLoadScene();

											((S33) e).onLoadScene(arg0);
											((S33) e)._status = true;
										}
										((S33) e)._SpiteTexture.moveRelative(
												-e.speed, e.speed);

										((S33) e).setX(((S33) e).getX()
												- e.speed);
										((S33) e).setY(((S33) e).getY()
												+ e.speed);
									} else {
										if (((S33) e).wayMove == 2) {

											if (((S33) e)._status == false
													&& ((S33) e)._SpiteTexture
															.isVisible()) {

												((S33) e)
														.onLoadResourcesRD(getApplicationContext());
												((S33) e)._SpiteTexture
														.onRemove();
												((S33) e).onLoadScene();
												((S33) e).onLoadScene(arg0);
												((S33) e)._status = true;
											}
											((S33) e)._SpiteTexture
													.moveRelative(e.speed,
															e.speed);
											((S33) e).setX(((S33) e).getX()
													+ e.speed);
											((S33) e).setY(((S33) e).getY()
													+ e.speed);
										}
									}
								}

							} else {
								((S33) e)._SpiteTexture.moveRelativeY(e.speed);
								((S33) e).setY(((S33) e).getY() + e.speed);
								((S33) e).setX(((S33) e).getX());
							}

						} else {
							if (((S33) e).wayAp == 3) {
								if (PT.PlaneSprite.getRealX() < getWidth() / 2) {

									((S33) e).Ndan.onLoadScene(arg0);
									((S33) e).Ndan.moveL(
											((S33) e).Ndan.m_Speed,
											((S33) e).Ndan.m_Speed);
									((S33) e).Ndan.m_X += ((S33) e).Ndan.m_Speed;
									((S33) e).Ndan.m_Y += ((S33) e).Ndan.m_Speed;
									((S33) e)._isfire = true;
								} else {
									((S33) e).Ndan.moveD(
											((S33) e).Ndan.m_Speed,
											((S33) e).Ndan.m_Speed);
									((S33) e).Ndan.m_X += ((S33) e).Ndan.m_Speed;
									((S33) e).Ndan.m_Y += ((S33) e).Ndan.m_Speed;
									((S33) e)._isfire = true;
								}

								if (((S33) e).getX() <= 100) {

									if (((S33) e).Ndan.vt == 0) {
										((S33) e).Ndan.SetX(0);
										((S33) e).Ndan.SetY(0);
										((S33) e).Ndan.vt = 1;
									}

									if (((S33) e).wayMove == 0) {

										if (((S33) e)._status == false
												&& ((S33) e)._SpiteTexture
														.isVisible()) {
											((S33) e)
													.onLoadResourcesD(getApplicationContext());

											((S33) e)._SpiteTexture.onRemove();
											((S33) e).onLoadScene();

											((S33) e).onLoadScene(arg0);
											((S33) e)._status = true;
										}

										((S33) e)._SpiteTexture
												.moveRelativeY(e.speed);
										((S33) e).setX(((S33) e).getX());
										((S33) e).setY(((S33) e).getY()
												+ e.speed);

									} else {
										if (((S33) e).wayMove == 1) {

											if (((S33) e)._status == false
													&& ((S33) e)._SpiteTexture
															.isVisible()) {
												((S33) e)
														.onLoadResourcesLD(getApplicationContext());
												((S33) e)._SpiteTexture
														.onRemove();
												((S33) e).onLoadScene();
												((S33) e).onLoadScene(arg0);
												((S33) e)._status = true;
											}

											((S33) e)._SpiteTexture
													.moveRelative(-e.speed,
															e.speed);

										} else {
											if (((S33) e).wayMove == 2) {
												if (((S33) e)._status == false
														&& ((S33) e)._SpiteTexture
																.isVisible()) {

													((S33) e)
															.onLoadResourcesRD(getApplicationContext());
													((S33) e)._SpiteTexture
															.onRemove();
													((S33) e).onLoadScene();
													((S33) e)
															.onLoadScene(arg0);
													((S33) e)._status = true;
												}
												((S33) e)._SpiteTexture
														.moveRelative(e.speed,
																e.speed);
											}
										}
									}

								} else {
									if (((S33) e)._isAcross == true) {
										((S33) e)._SpiteTexture
												.moveRelativeX(-e.speed);
										((S33) e).setX(((S33) e).getX()
												- e.speed);
										((S33) e).setY(((S33) e).getY());

									} else {
										((S33) e)._SpiteTexture.moveRelative(
												-e.speed, e.speed);
										((S33) e).setX(((S33) e).getX()
												- e.speed);
										((S33) e).setY(((S33) e).getY()
												+ e.speed);

									}

								}

							}
						}
					}
				}
			}
		}
	}
	public void TestColPTS33(E3Scene arg0) {
		for (EnemyPlane e : s33) {
			if (((S33) e)._SpiteTexture.collidesWith(PT.PlaneSprite)
					&& ((S33) e)._SpiteTexture.isVisible()
					&& PT.PlaneSprite.isVisible()) {
				((S33) e)._SpiteTexture.setVisible(false);
				PT.PlaneSprite.setVisible(false);
				NoEventF(arg0, PT.PlaneSprite.getRealX(),
						PT.PlaneSprite.getRealY());
				tiengno.play();
				if (PT.m_Life > 0) {
					PT.m_Life -= 1;

					LabelLife.setText("Mạng :" + " x" + PT.m_Life);
					PT.PlaneSprite.move(1000, 1000);
					StopTest = true;
					if (TimePT == -1) {
						TimePT = SystemClock.elapsedRealtime();
					}
				}
			}
		}
	}
	public void TestColPTS33B(E3Scene arg0) {
		for (EnemyPlane e : s33) {
			if (((S33) e).Ndan.m_bulletSpite.collidesWith(PT.PlaneSprite)
					&& ((S33) e).Ndan.m_bulletSpite.isVisible()
					&& PT.PlaneSprite.isVisible()) {
				((S33) e)._SpiteTexture.setVisible(false);
				PT.PlaneSprite.setVisible(false);
				NoEventF(arg0, PT.PlaneSprite.getRealX(),
						PT.PlaneSprite.getRealY());
				tiengno.play();
				if (PT.m_Life > 0) {
					PT.m_Life -= 1;

					LabelLife.setText("Mạng :" + " x" + PT.m_Life);
					PT.PlaneSprite.move(1000, 1000);
					StopTest = true;
					if (TimePT == -1) {
						TimePT = SystemClock.elapsedRealtime();
					}
				}
			}
		}
	}
	public void TestCS33(Dan PTb, E3Scene arg0) {
		for (EnemyPlane e : s33) {
			if (((S33) e)._SpiteTexture.collidesWith(PTb.m_bulletSpite)
					&& PTb.m_bulletSpite.isVisible()
					&& ((S33) e)._SpiteTexture.isVisible()) {

				PTb.m_bulletSpite.setVisible(false);

				((S33) e)._SpiteTexture.setVisible(false);

				PTb.isd = true;
				score += 10;
				LabelScore.setText("Điểm :" + " " + String.valueOf(score));
				NoEventF(arg0, ((S33) e)._SpiteTexture.getRealX(),
						((S33) e)._SpiteTexture.getRealY());
				//tiengno.play();
			}
		}
	}
	
	int tocdo = 7;
	long T = 0;
	long T1 = 0;
	public void Boss(E3Scene arg0, long arg1) {
		if (boss._isf == false) {
			boss.onLoadScene();
			boss.onLoadScene(arg0);
			sung1.onLoadScene(arg0);
			sung2.onLoadScene(arg0);
			sung3.onLoadScene(arg0);
			sung4.onLoadScene(arg0);
			dy1.onLoadScene(arg0);
			dy2.onLoadScene(arg0);

			boss._isf = true;
		}
		if (boss.sprite.getRealY() <= 0) {
			boss.sprite.moveRelativeY(tocdo - 3);
			sung1.sprite.moveRelativeY(tocdo - 3);
			sung2.sprite.moveRelativeY(tocdo - 3);
			sung3.sprite.moveRelativeY(tocdo - 3);
			sung4.sprite.moveRelativeY(tocdo - 3);
			dy1.sprite.moveRelativeY(tocdo - 3);
			dy2.sprite.moveRelativeY(tocdo - 3);

		} else {

			boss.sprite.moveRelativeX(tocdo);
			sung1.sprite.moveRelativeX(tocdo);
			sung2.sprite.moveRelativeX(tocdo);
			sung3.sprite.moveRelativeX(tocdo);
			sung4.sprite.moveRelativeX(tocdo);
			dy1.sprite.moveRelativeX(tocdo);
			dy2.sprite.moveRelativeX(tocdo);
			if (T == 0) {
				T = SystemClock.elapsedRealtime();
			}
			if (T1 == 0) {
				T1 = SystemClock.elapsedRealtime();
			}
			if (SystemClock.elapsedRealtime() - T1 >= 2000) {
				if (dy1.blood > 0 && boss.Blood > 0) {
					Dan b = new Dan();
					b.m_X = dy1.sprite.getRealX();
					b.m_Y = dy1.sprite.getRealY();
					b.onLoadResourcesT(getApplicationContext());
					b.onLoadScene();
					b.m_Speed = 30;
					T1 = 0;
					DanBoss.add(b);
				}
				if (dy2.blood > 0 && boss.Blood > 0) {
					Dan b = new Dan();
					b.m_X = dy2.sprite.getRealX();
					b.m_Y = dy2.sprite.getRealY();
					b.onLoadResourcesT(getApplicationContext());
					b.onLoadScene();
					b.m_Speed = 30;
					T1 = 0;
					DanBoss.add(b);
				}
			}

			if (SystemClock.elapsedRealtime() - T >= 5000) {
				if (sung1.blood > 0 && boss.Blood > 0) {
					Dan b1 = new Dan();
					b1.m_X = sung1.sprite.getRealX();
					b1.m_Y = sung1.sprite.getRealY();
					b1.onLoadResourcesX(getApplicationContext());
					b1.onLoadScene();
					b1.m_Speed = 20;
					DanBoss.add(b1);
				}
				if (sung2.blood > 0 && boss.Blood > 0) {
					Dan b2 = new Dan();
					b2.m_X = sung2.sprite.getRealX();
					b2.m_Y = sung2.sprite.getRealY();
					b2.onLoadResourcesX(getApplicationContext());
					b2.onLoadScene();
					b2.m_Speed = 20;
					DanBoss.add(b2);
				}
				if (sung3.blood > 0 && boss.Blood > 0) {
					Dan b3 = new Dan();
					b3.m_X = sung3.sprite.getRealX();
					b3.m_Y = sung3.sprite.getRealY();
					b3.onLoadResourcesX(getApplicationContext());
					b3.onLoadScene();
					b3.m_Speed = 20;
					DanBoss.add(b3);
				}
				if (sung4.blood > 0 && boss.Blood > 0) {
					Dan b4 = new Dan();
					b4.m_X = sung4.sprite.getRealX();
					b4.m_Y = sung4.sprite.getRealY();
					b4.onLoadResourcesX(getApplicationContext());
					b4.onLoadScene();
					b4.m_Speed = 20;
					DanBoss.add(b4);
				}
				T = 0;
			}
			if (boss.sprite.getRealX() >= WIDTH - 100) {
				tocdo = -tocdo;
			}
			if (boss.sprite.getRealX() <= 0) {
				tocdo = -tocdo;
			}
		}
	}
	private void BossPT(E3Scene arg0) {
		if (boss._SpiteTexture.collidesWith(PT.PlaneSprite)
				&& boss._SpiteTexture.isVisible()
				&& PT.PlaneSprite.isVisible()) {
			boss.Blood -= 25;
			PT.PlaneSprite.setVisible(false);
			NoEventF(arg0, PT.PlaneSprite.getRealX(),
					PT.PlaneSprite.getRealY());
			tiengno.play();
			if (PT.m_Life > 0) {
				PT.m_Life -= 1;
				LabelLife.setText("Mạng :" + " x" + PT.m_Life);
				PT.PlaneSprite.move(1000, 1000);
				StopTest = true;
				if (TimePT == -1) {
					TimePT = SystemClock.elapsedRealtime();
				}
			}
		}
	}
	public void TestColBoss(Dan PTb, E3Scene arg0) {
		if (dy1.sprite.collidesWith(PTb.m_bulletSpite)
				&& PTb.m_bulletSpite.isVisible() && dy1.sprite.isVisible()) {
			boss.Blood -= 1;
			PTb.m_bulletSpite.setVisible(false);
			PTb.isd = true;
			NoEvent(arg0, dy1.x, dy1.y + 80);
			tiengno.play();
			if (boss.Blood <= 0) {
				boss.sprite.setVisible(false);
				dy1.sprite.setVisible(false);
				dy2.sprite.setVisible(false);
				sung1.sprite.setVisible(false);
				sung2.sprite.setVisible(false);
				sung3.sprite.setVisible(false);
				sung4.sprite.setVisible(false);
				score += 100;
				LabelScore.setText("Điểm :" + " " + String.valueOf(score));

			}
		}
		if (dy2.sprite.collidesWith(PTb.m_bulletSpite)
				&& PTb.m_bulletSpite.isVisible() && dy2.sprite.isVisible()) {
			boss.Blood -= 1;
			PTb.m_bulletSpite.setVisible(false);
			PTb.isd = true;
			NoEvent(arg0, dy2.x, dy2.y + 80);
			tiengno.play();
			if (boss.Blood <= 0) {
				boss.sprite.setVisible(false);
				dy1.sprite.setVisible(false);
				dy2.sprite.setVisible(false);
				sung1.sprite.setVisible(false);
				sung2.sprite.setVisible(false);
				sung3.sprite.setVisible(false);
				sung4.sprite.setVisible(false);
				score += 100;
				LabelScore.setText("Điểm :" + " " + String.valueOf(score));

			}
		}
		if (sung1.sprite.collidesWith(PTb.m_bulletSpite)
				&& PTb.m_bulletSpite.isVisible() && sung1.sprite.isVisible()
				&& sung1.blood > 0) {
			sung1.blood -= 10;
			PTb.m_bulletSpite.setVisible(false);
			PTb.isd = true;
			NoEvent(arg0, sung1.x, sung1.y + 80);
			tiengno.play();
			if (sung1.blood <= 0) {

				sung1.onLoadResourcesD(getApplicationContext());
				sung1.sprite.onRemove();
				sung1.x = sung1.sprite.getRealX();
				sung1.y = sung1.sprite.getRealY();
				sung1.onLoadScene();
				sung1.onLoadScene(arg0);
				score += 25;
				LabelScore.setText("Điểm :" + " " + String.valueOf(score));

			}
		}
		if (sung2.sprite.collidesWith(PTb.m_bulletSpite)
				&& PTb.m_bulletSpite.isVisible() && sung2.sprite.isVisible()
				&& sung2.blood > 0) {
			sung2.blood -= 10;
			PTb.m_bulletSpite.setVisible(false);
			PTb.isd = true;
			NoEvent(arg0, sung2.x, sung2.y + 80);
			tiengno.play();
			if (sung2.blood <= 0) {
				sung2.onLoadResourcesD(getApplicationContext());
				sung2.sprite.onRemove();
				sung2.x = sung2.sprite.getRealX();
				sung2.y = sung2.sprite.getRealY();
				sung2.onLoadScene();
				sung2.onLoadScene(arg0);
				score += 25;
				LabelScore.setText("Điểm :" + " " + String.valueOf(score));
			}
		}
		if (sung3.sprite.collidesWith(PTb.m_bulletSpite)
				&& PTb.m_bulletSpite.isVisible() && sung3.sprite.isVisible()
				&& sung3.blood > 0) {
			sung3.blood -= 10;
			PTb.m_bulletSpite.setVisible(false);
			PTb.isd = true;
			NoEvent(arg0, sung3.x, sung3.y + 80);
			tiengno.play();
			if (sung3.blood <= 0) {
				sung3.onLoadResourcesD(getApplicationContext());
				sung3.sprite.onRemove();
				sung3.x = sung3.sprite.getRealX();
				sung3.y = sung3.sprite.getRealY();
				sung3.onLoadScene();
				sung3.onLoadScene(arg0);
				score += 25;
				LabelScore.setText("Điểm :" + " " + String.valueOf(score));
			}
		}
		if (sung4.sprite.collidesWith(PTb.m_bulletSpite)
				&& PTb.m_bulletSpite.isVisible() && sung4.sprite.isVisible()
				&& sung4.blood > 0) {
			sung4.blood -= 10;
			PTb.m_bulletSpite.setVisible(false);
			PTb.isd = true;
			NoEvent(arg0, sung4.x, sung4.y + 80);
			tiengno.play();
			if (sung4.blood <= 0) {
				sung4.onLoadResourcesD(getApplicationContext());
				sung4.sprite.onRemove();
				sung4.x = sung4.sprite.getRealX();
				sung4.y = sung4.sprite.getRealY();
				sung4.onLoadScene();
				sung4.onLoadScene(arg0);
				score += 25;
				LabelScore.setText("Điểm :" + " " + String.valueOf(score));
			}
		}
	}
	public void TestBulletBoss(Dan b, E3Scene arg0) {
		try {
			for (Dan no : DanBoss) {

				if (no.m_bulletSpite.collidesWith(b.m_bulletSpite)
						&& no.m_bulletSpite.isVisible()
						&& b.m_bulletSpite.isVisible()) {
					no.m_bulletSpite.setVisible(false);
					b.m_bulletSpite.setVisible(false);
					b.isd = true;
					NoEventF(arg0, no.m_bulletSpite.getRealX(),
							no.m_bulletSpite.getRealY());
					tiengno.play();
				}
			}
		} catch (Exception e) {
		}
	}
	public int TestColPT(ArrayList<Dan> bullet, E3Scene arg0) {

		for (Dan b : DanBoss) {
			if (b.m_bulletSpite.collidesWith(PT.PlaneSprite)
					&& b.m_bulletSpite.isVisible()
					&& PT.PlaneSprite.isVisible()) {
				b.m_bulletSpite.setVisible(false);
				PT.PlaneSprite.setVisible(false);
				NoEventF(arg0, PT.PlaneSprite.getRealX(),
						PT.PlaneSprite.getRealY());
				tiengno.play();
				if (PT.m_Life > 0) {
					PT.m_Life -= 1;
					LabelLife.setText("Mạng :" + " x" + PT.m_Life);
					PT.PlaneSprite.move(1000, 1000);
					StopTest = true;
					if (TimePT == -1) {
						TimePT = SystemClock.elapsedRealtime();
					}
					return 1;
				} else {
					return 0;
				}
			}
		}
		return -1;
	}
}


