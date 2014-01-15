package com.example.airplane;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Win extends Activity implements OnClickListener {

	int DIEM = 0;
	//private Button btnRepeatGame;
	private Button btnMainMenu;
	private Button btnExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.win);

		//btnRepeatGame = (Button) findViewById(R.id.x_btnChoilai);
		btnMainMenu = (Button) findViewById(R.id.xml_btnMainMenu);
		btnExit = (Button) findViewById(R.id.xml_btnExit2);

		//btnRepeatGame.setOnClickListener(this);
		btnExit.setOnClickListener(this);
		btnMainMenu.setOnClickListener(this);

		Bundle bun = getIntent().getExtras();

		if (bun != null) {
			DIEM = bun.getInt("diem");
		}

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent i;
		switch (id) {
		case R.id.xml_btnExit2:
			Thoat();
			break;
		case R.id.xml_btnMainMenu:
			i = new Intent(Win.this, MainActivity.class);
			this.startActivity(i);
			this.finish();
			break;

		default:
			break;
		}
	}
	public void Thoat() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Thoát");
		builder.setMessage("Bạn muốn thoát khỏi trò chơi ?");
		builder.setNegativeButton("Đồng ý",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						Win.this.finish();
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

}
