package edu.wmich.lab5erouse9494;
/*
*************************************
* Programmer: Ethan Rouse
* Spring 2017
* Due date: 04/21/17
* Date completed: 04/21/17
*************************************
* Program Explanation: This splash activity takes 5 seconds before it opens the main activity
* during the 5 seconds a tween animation is ran on the page to help the load screen aesthetic.
*************************************
*/
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//Allows the use of the animation utils or anim folder for tweens.
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
//Allows the java utility timer to be used.
import java.util.Timer;
//Allows the timer task to run
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //ImageView XML is linked to the java imgSmoreTrain and holds its memory space.
        ImageView imgSmoreTrain = (ImageView) findViewById(R.id.imageViewSmore);

        //The imgSmoreTrain starts the set animation from the anim folder in the res file. The
        //image starts transparent and 0x0 and becomes opaque and 200dpx200dp in about 5 seconds.
        imgSmoreTrain.startAnimation(AnimationUtils.loadAnimation(this,R.anim.set));

        //This starts the timer task.
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                //This finish is to prevent users from hitting the back button on the phone to end
                //up at the splash screen again, instead the application will finish and end
                //operation.
                finish();

                //once the timer task is complete this opens the main activity.
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        };
        //creates a timer object opening and sets the task to 5 seconds until it runs the code
        //within the timer task.
        Timer opening = new Timer();
        opening.schedule(task,5000);
    }
}
