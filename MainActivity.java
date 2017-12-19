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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//Used to help create preferences from activities and extends the android preference object.
import android.preference.PreferenceManager;
//used to access and modify preference data, modifications to the preferences go through a shared
// preference editor object to ensure the values are in a consistent when committing them to storage
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //sets class variables used throughout the main activity.
    String strName = "";
    String strEmail = "";
    int intCookieTotal = 0;
    final int MAXCOOKIES = 200;
    boolean BOOLFLAG =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //These three lines enable the action bar on the home screen if it was turned off,
            //sets the logo from the mipmap, and finally displays the logo to the action bar.
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

            //Holds my constant strings used within toast messages depending on what the user is
            // trying to do.
            final String STRSCRATCH = "Previous user information must have been stored in " +
                    "order to utilize this button.";
            final String STRNEEDINFO = "Please fill all information fields before continuing.";
            final String STRMAXCOOKIES = "Please enter a more accurate cookie total for your week.";


            //Links the java userName to the edit text on the main activity editTextName, and holds
            // memory space for userName.
            final EditText userName = (EditText)findViewById(R.id.editTextName);

            //Links the java userEmail to the edit text on the main activity editTextEmail, and
            //holds memory space for userEmail.
            final EditText userEmail = (EditText)findViewById(R.id.editTextEmail);

            //Links the java usercookietotal to the edit text on the main activity editTextamount,
            // and holds memory space for usercookietotal.
            final EditText userCookieTotal = (EditText)findViewById(R.id.editTextAmount);

            //Links btnPreviousrank to the main activity buttonPreviousRank, and holds memory
            //space for the button object.
            Button btnPreviousRank = (Button)findViewById(R.id.buttonPreviousRank);

            //Links btnRegister to the main activity buttonRegister, and holds memory
            //space for the button object.
            Button btnRegister = (Button)findViewById(R.id.buttonRegister);

            //Creates a shared preference object, these are used to point to a file containing key
            //value pairs and provides simple methods to read and write them.
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


            //Sets an on click listener for button register.
            btnRegister.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    //Once the button is clicked Boolflag turns true.
                    BOOLFLAG =true;

                    //The application will try to change user input to strings and set them to other
                    //variable names.
                    try {
                        //strName is gathered from the entered user name on the editTextName
                        strName = userName.getText().toString();

                        //strEmail is gathered from the entered user email on the editTextEmail
                        strEmail = userEmail.getText().toString();

                        //intAge is gathered from the entered user age on the editTextAmount
                        intCookieTotal = Integer.parseInt(userCookieTotal.getText().toString());
                    }
                    //this will catch the number format error which occurs when an empty edit text
                    //is converted into a string.
                    catch(NumberFormatException numberFormErr) {

                        //The catch will then throw this toast warning the user to enter all info
                        //required for the application to work.
                        Toast.makeText(MainActivity.this, STRNEEDINFO, Toast.LENGTH_LONG).show();

                        //Boolflag is changed to false preventing the next activity from starting.
                        BOOLFLAG = false;
                    }

                    //Used for modifying values in a shared preference object. Changes made with the
                    //editor are batched, and are not copied back to the original shared preference
                    //unless they are committed or applied.
                    SharedPreferences.Editor editPref = sharedPref.edit();

                    //The editor editpref writes keys to the shared preferences these are
                    //instantiated on the fly. Shared preferences store keys in this instance we
                    //are putting strings and an integer linked to a key.
                    editPref.putString("keyName",strName);
                    editPref.putString("keyEmail",strEmail);
                    editPref.putInt("keyCookieAmt", intCookieTotal);

                    //If the cookie total is greater than 200 then...
                    if(intCookieTotal > MAXCOOKIES){

                        //This toast pops up and tells the user to enter a more accurate cookie
                        //count
                        Toast.makeText(MainActivity.this, STRMAXCOOKIES, Toast.LENGTH_LONG).show();

                        //This sets int cookie total to 0.
                        intCookieTotal = 0;

                        //The shared preferences for key cookie amount is edited to 0.
                        editPref.putInt("keyCookieAmt", intCookieTotal);

                        //This sets the bool flag to false.
                        BOOLFLAG = false;
                    }

                    //The editore writes the keys to persistent storage so if the application is
                    //started over the data will still remain in the shared preferences
                    editPref.commit();


                    //if boolflag does not equal bool flag AKA if it is currently false then the
                    //start activity intent will not occur. Otherwise the user is taken to the
                    //results activity.
                    if(BOOLFLAG) {
                        startActivity(new Intent(MainActivity.this, ResultsActivity.class));
                    }

                }
            });
            //Btn previous rank is set to an on click listener.
            btnPreviousRank.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    //Once clicked the application checks to see if there is a keyname stored in the
                    //shared preferences, and since this application does not let you continue
                    //without filling every edit text it is safe to assume key email and key cookie
                    //amt are filled if key name is. If key name is there it grabs it.
                    String strUserName = sharedPref.getString("keyName", "");


                    //If strUserName is not empty after getting the string from key name then...
                    if (!strUserName.isEmpty()) {

                        //the user is taken to a screen with past information from prior application
                        //use.
                        startActivity(new Intent(MainActivity.this, ResultsActivity.class));
                    }
                    //otherwise..
                    else{
                        //This toast message tells the user that they have no previous data to look
                        //at.
                        Toast.makeText(MainActivity.this, STRSCRATCH, Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
}
