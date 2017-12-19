package edu.wmich.lab5erouse9494;
/*
*************************************
* Programmer: Ethan Rouse
* Spring 2017
* Due date: 04/21/17
* Date completed: 04/21/17
*************************************
* Program Explanation:
*************************************
*/
import android.content.Intent;
//Allows the use of the animation drawable or framed activity.
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//Used to help create preferences from activities and extends the android preference object.
import android.preference.PreferenceManager;
//used to access and modify preference data, modifications to the preferences go through a shared
//preference editor object to ensure the values are in a consistent when committing them to storage
import android.content.SharedPreferences;
import android.view.View;
//Allows the use of the animation utils or anim folder for tweens.
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        //Holds all of my constant string information used based on user information within the
        //shared preference file.
        final String strCasual = " , you are a casual cookie fiend who may enjoy eating ";
        final String strModerate = " , you are a moderate cookie fiend that enjoys eating ";
        final String strCookieAddict =" , you may be slightly addicted to cookies since you eat ";
        final String strCookieMonster = " , your voice is sounding a bit gruff and we are pretty " +
                "sure we heard you sing a song about eating ";
        final String strCasDeals = " cookies a week . You do not qualify for any deals , but if " +
                "your intake of cookies increases be sure to let us know and we will send you " +
                "great deals to your email ";
        final String strModDeals = " cookies a week . You qualify for a $5.00 voucher to your " +
                "nearest supermarket to indulge in more cookie sweetness . This voucher has been " +
                "sent to your email ";
        final String strAddDeals = " cookies a week . You qualify for a $10.00 voucher off any " +
                "cookie and milk purchase . This voucher has been sent to your email ";
        final String strMonDeals = " cookies you may need to slow it down a bit . We have sent " +
                "you NOM NOM Anonymous help line information to your email ";


        //This is a list of all of the constant integers used for calculations to bring up certain
        //images and text based on user information within the shared preference file..
        final int HIGHEND = 100;
        final int MIDDLEHIGH = 30;
        final int MIDDLELOW = 10;

        //This holds memory space for the back button and links the java to the XML buttonBack.
        final Button btnBack =(Button)findViewById(R.id.buttonBack);

        //These three lines enable the action bar on the home screen if it was turned off,
        //sets the logo from the mipmap, and finally displays the logo to the action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Links the java imgCookie to the xml imgViewCookie and holds the memory space for the
        //imageview.
        ImageView imgCookie = (ImageView)findViewById(R.id.imgViewCookie);

        //Links the java txtResult to the xml textViewresult and holds the memory space for the
        //textview.
        TextView txtResult = (TextView) findViewById(R.id.textViewResult);

        //Creates a shared preference object, these are used to point to a file containing key value
        //pairs and provides simple methods to read and write them.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //These strings and integer are instantiated on the fly and are gotten from the shared
        //preferences file with the key name as the reference, and grabs the string or int and sets
        //the value of the variable to the value from the preference. Otherwise it is left as an
        //empty string or 0.
        String strUserName = sharedPref.getString("keyName", "");
        String strUserEmail = sharedPref.getString("keyEmail", "");
        int intCookieAmt = sharedPref.getInt("keyCookieAmt", 0);


        //If the amout of cookies is greater or equal to 100 then...
        if(intCookieAmt >= HIGHEND){

            //imgCookie is set to the monster drawable resource
            imgCookie.setImageResource(R.drawable.monster);

            //imgCookie starts the scale animation where the image pops out of the cookie fading
            //into view. The scale animation actually does not use a scale it uses an alpha and
            //translate.
            imgCookie.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale));

            //The text result is set the string which says they eat too many cookies and they need
            //help, so the company sends them information on the anonymous group for cookie
            //monsters. --PS they get him to switch to greenery from cookies so they know their
            //stuff
            txtResult.setText(strUserName + strCookieMonster + intCookieAmt + strMonDeals +
                    strUserEmail + " .");
        }
        //else...If the amout of cookies is greater or equal to 30 then...
        else if(intCookieAmt>= MIDDLEHIGH){

            //imgCookie is set to the animation drawable resource
            imgCookie.setBackgroundResource(R.drawable.animation);

            //As soon as the activity starts the images in the frame-animation list appear one after
            //another in the location the imageview cookie is at this is set to repeat for eternity
            //so you may want to turn it off manually. --could probably stop it with a for
            //statement.
            AnimationDrawable frameAnimation = (AnimationDrawable) imgCookie.getBackground();
            frameAnimation.start();

            //Sets the text to let the guest know based on their cookie consumption they will get a
            //10 dollar off a purchase of cookies and milk.
            txtResult.setText(strUserName + strCookieAddict + intCookieAmt + strAddDeals +
                    strUserEmail+ " .");

        }
        //else...If the cookie amount is greater than or equal to 10 then...
        else if(intCookieAmt>= MIDDLELOW){

            //imgcookie is set to the smore drawable
            imgCookie.setImageResource(R.drawable.smore);

            //the drawable starts the rotation animation tween.
            imgCookie.startAnimation(AnimationUtils.loadAnimation(this,R.anim.rotation));

            //The text informs the user they get a 5 dollar voucher for buying cookies to help their
            //craving.
            txtResult.setText(strUserName + strModerate + intCookieAmt + strModDeals +
                    strUserEmail+ " .");
        }
        //otherwise...
        else{

            //The image cookie is set to the drawable Oreo
            imgCookie.setImageResource(R.drawable.oreo);

            //The oreo starts an alpha animation fading into existence...would have preferred the
            //other way but the oreo would show up after fading out.
            imgCookie.startAnimation(AnimationUtils.loadAnimation(this,R.anim.alpha));

            //sets the text to let the user know they do not eat enough cookies and cannot give the
            //user vouchers because of it.
            txtResult.setText(strUserName + strCasual + intCookieAmt + strCasDeals +
                    strUserEmail+ " .");
        }

        //This is an interesting piece of java which sets the back button to invisible...
        btnBack.setVisibility(View.INVISIBLE);

        //this runs a post-delayed method as a new run-able method under the public void run which
        //starts to run when the page is loaded.
        btnBack.postDelayed(new Runnable() {
            public void run() {

                //after 5 seconds the button becomes visible.
                btnBack.setVisibility(View.VISIBLE);
            }
        }, 5000);

        //once the back button is clicked it takes the user from the results activity to the main
        //activity.
        btnBack.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultsActivity.this, MainActivity.class));
            }
        });

    }
}
