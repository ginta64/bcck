package com.example.airplane;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button StartGame;
	private Button Exit;
	private Button Info;
	private TextView Record;
	
	Animation anm_alpha;
	Animation anm_xoay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StartGame = (Button)findViewById(R.id.xml_btnPlay);
		Exit = (Button)findViewById(R.id.xml_btnExit);
		Info = (Button)findViewById(R.id.xml_btnInfo);
		Record = (TextView)findViewById(R.id.xml_tvRecord);
		anm_alpha = AnimationUtils.loadAnimation(this,R.anim.rotate_slide);
		anm_xoay = AnimationUtils.loadAnimation(this,R.anim.slide_right_to_left);
		StartGame.startAnimation(anm_alpha);
		Info.startAnimation(anm_alpha);
		Exit.startAnimation(anm_alpha);
		
		StartGame.setOnClickListener(this);
		Info.setOnClickListener(this);
		Exit.setOnClickListener(this);
		
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				
				switch (index) {
					case 0:
						Exit.clearAnimation();
						Info.clearAnimation();
						StartGame.startAnimation(anm_xoay);
						break;
					case 1:
						StartGame.clearAnimation();
						Info.clearAnimation();
						Exit.startAnimation(anm_xoay);
						break;
					case 2:
						Exit.clearAnimation();
						StartGame.clearAnimation();
						Info.startAnimation(anm_xoay);
						break;
					default: break;
				}
				super.handleMessage(msg);
			}
		};
		Thread th = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(8000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					index++;
					if (index > 2)
						index = 0;
					Message msg = handler.obtainMessage();
					handler.sendMessage(msg);
				}
			}
		});
		th.start();
	}
int index = 0;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Information() {
		String str_info = "";
		
		str_info += "ĐH KHOA HỌC TỰ NHIÊN\n";
		str_info += "\n";
		str_info += "\n";
		str_info += "  THÀNH VIÊN: \n";
		str_info += "    1012382  Cao Xuân Tân\n";
		str_info += "    1012309  Trần Thanh Thiện Phúc \n";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Thông tin");
		builder.setMessage(str_info);
		builder.setNegativeButton("Đóng",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
	
	public void Exit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Thoát");
		builder.setMessage("Bạn muốn thoát khỏi trò chơi ?");
		builder.setNegativeButton("Đồng ý",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						MainActivity.this.finish();
						dialog.dismiss();
						 
						
					}
				});
		builder.setPositiveButton("Không",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});
		builder.show(); 
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i;
		int id = arg0.getId();
		switch (id) {
		case R.id.xml_btnPlay:
			
			i = new Intent(this, Main_Game.class);
			this.startActivity(i);

			break;
		case R.id.xml_btnInfo:
			Information();
			break;

		case R.id.xml_btnExit:
			Exit();
			break;

		default:
			break;
		}
		
	}

}
