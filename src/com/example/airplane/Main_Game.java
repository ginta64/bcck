package com.example.airplane;

import java.util.ArrayList;
import java.util.Random;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

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
	
	//điểm kỉ lục
	int score = 0;
	
	//thoi gian
	long time = 0;
	
	//frame
	private ArrayList<AnimatedSprite.Frame> NoFrames;
	private ArrayList<Nframes> noFrames = new ArrayList<Nframes>();
	
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
		MIG29(arg0, arg1);
		try
		{
			for (int i = 0; i < PT.Dan.size(); i++) {

				if (PT.Dan.get(i).isf == false) {
					PT.Dan.get(i).onLoadScene(arg0);
					PT.Dan.get(i).isf = true;
				}
				PT.Dan.get(i).GetBulletSprite()
						.moveRelativeY(-PT.Dan.get(i).m_Speed);
				TestCMIG29(PT.Dan.get(i), arg0);
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
			if (StopTest == false) {
				
				TestColPTMig29(arg0);
			}
		} catch (Exception e) {

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
		//load button tro ve menu chinh
		scene.addEventListener(SpriteMainmenu);
		scene.getTopLayer().add(SpriteMainmenu);
		//load mang va diem ki luc	
		scene.getTopLayer().add(LabelLife);
		scene.getTopLayer().add(LabelScore);
		LabelScore.move(WIDTH - 90, 0);
		LabelLife.move(8, 0);
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
	boolean StopTest = false;
	long TimePT = -1;
	public void TestColPTMig29(E3Scene arg0) {
		for (EnemyPlane e : Mig29) {
			if (((Mig29) e)._SpiteTexture.collidesWith(PT.PlaneSprite)
					&& ((Mig29) e)._SpiteTexture.isVisible()
					&& PT.PlaneSprite.isVisible()) {
				((Mig29) e)._SpiteTexture.setVisible(false);
				PT.PlaneSprite.setVisible(false);
				NoEventF(arg0, PT.PlaneSprite.getRealX(),
						PT.PlaneSprite.getRealY());
				//tiengno.play();
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
}

