package com.unlockphone;

import android.animation.Animator;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Directions.HideType;
import com.nightonke.blurlockview.Directions.ShowType;
import com.nightonke.blurlockview.Eases.EaseType;
import com.nightonke.blurlockview.Password;

import io.codetail.animation.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        BlurLockView.OnPasswordInputListener {
    private BlurLockView blurLockView;
    private ImageView imageView1;
    private FrameLayout flForgotPassword;
    final static int SLOW_DURATION = 400;
    final static int FAST_DURATION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageView1 = (ImageView) findViewById(R.id.image_1);
        flForgotPassword = (FrameLayout) findViewById(R.id.view_stack);
        blurLockView = (BlurLockView) findViewById(R.id.blurlockview);
        // Set the view that need to be blurred
        blurLockView.setBlurredView(imageView1);
        // Set the password
        blurLockView.setCorrectPassword("1234");
        blurLockView.setTitle("Enter Four Digit Pin");
        blurLockView.setLeftButton("Forgot Pin?");
        blurLockView.setRightButton("Back");
        blurLockView.setTypeface(getTypeface());
        blurLockView.setType(getPasswordType(), false);

        blurLockView.setOnPasswordInputListener(this);

        imageView1.setOnClickListener(this);

        final GestureDetector detector = new GestureDetector(this, tapDetector);
        blurLockView.getRightButton().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });


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
            case R.id.left_button:
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
                break;
            case R.id.right_button:
                Toast.makeText(getApplicationContext(), "hello1", Toast.LENGTH_LONG).show();
                break;
        }
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


    private float hypo(View view, MotionEvent event) {
        Point p1 = new Point((int) event.getX(), (int) event.getY());
        Point p2 = new Point(view.getWidth() / 2, view.getHeight() / 2);

        return (float) Math.sqrt(Math.pow(p1.y - p2.y, 2) + Math.pow(p1.x - p2.x, 2));
    }

    private GestureDetector.OnGestureListener tapDetector =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View nextView = flForgotPassword;
                    nextView.bringToFront();
                    nextView.setVisibility(View.VISIBLE);

                    final float finalRadius =
                            (float) Math.hypot(nextView.getWidth() / 2f, nextView.getHeight() / 2f) + hypo(
                                    nextView, e);

                    Animator revealAnimator =
                            ViewAnimationUtils.createCircularReveal(nextView, (int) nextView.getWidth(), (int) nextView.getHeight(), 0,
                                    finalRadius, View.LAYER_TYPE_HARDWARE);

                    revealAnimator.setDuration(MainActivity.SLOW_DURATION);
                    revealAnimator.setInterpolator(new FastOutLinearInInterpolator());
                    revealAnimator.start();

                    return true;
                }
            };
}
