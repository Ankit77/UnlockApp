package com.unlockphone;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Directions.HideType;
import com.nightonke.blurlockview.Directions.ShowType;
import com.nightonke.blurlockview.Eases.EaseType;
import com.nightonke.blurlockview.Password;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        BlurLockView.OnPasswordInputListener,
        BlurLockView.OnLeftButtonClickListener {
    private BlurLockView blurLockView;
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageView1 = (ImageView) findViewById(R.id.image_1);

        blurLockView = (BlurLockView) findViewById(R.id.blurlockview);

        // Set the view that need to be blurred
        blurLockView.setBlurredView(imageView1);

        // Set the password
        blurLockView.setCorrectPassword("1234");
        blurLockView.setTitle(getString(R.string.app_name));
        blurLockView.setLeftButton("");
        blurLockView.setRightButton("Back");
        blurLockView.setTypeface(getTypeface());
        blurLockView.setType(getPasswordType(), false);

        blurLockView.setOnLeftButtonClickListener(this);
        blurLockView.setOnPasswordInputListener(this);

        imageView1.setOnClickListener(this);

    }

    private Password getPasswordType() {
//        if ("PASSWORD_NUMBER".equals(getIntent().getStringExtra("PASSWORD_TYPE")))
//            return Password.NUMBER;
//        else if ("PASSWORD_NUMBER".equals(getIntent().getStringExtra("PASSWORD_TYPE")))
//            return Password.TEXT;
        return Password.NUMBER;
    }

    private Typeface getTypeface() {
        return Typeface.DEFAULT;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_1:
                blurLockView.show(
                        getIntent().getIntExtra("SHOW_DURATION", 1000),
                        ShowType.FADE_IN,
                        EaseType.Linear);
                break;
        }
    }

    @Override
    public void onClick() {

    }

    @Override
    public void correct(String inputPassword) {
        Toast.makeText(this,
                "CorrectPassword",
                Toast.LENGTH_SHORT).show();
        blurLockView.hide(
                getIntent().getIntExtra("HIDE_DURATION", 1000),
                HideType.FADE_OUT,
                EaseType.Linear);
    }

    @Override
    public void incorrect(String inputPassword) {
        Toast.makeText(this,
                "Incorrect",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void input(String inputPassword) {

    }
}
