package com.example.amosang.emma_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class NewUser extends AppCompatActivity {

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;


    private static final int PREFERENCE_MODE_PRIVATE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        initializeUI();
    }

    private void initializeUI(){
        Button confirm = (Button)findViewById(R.id.btn_go);
        //preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceSettings = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceEditor = preferenceSettings.edit();
        String uName = preferenceSettings.getString("userName","empty");
        String uID = preferenceSettings.getString("userID","empty");

        if(uName.equals("empty")&&uID.equals("empty")){
            Log.d("SP","SPEMPTY");
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText username = (EditText) findViewById(R.id.input_username);
                    if (username.getText().toString().trim().length() > 0) {
                        String in_username = username.getText().toString();
                        //generate random alphabet
                        Random r = new Random();
                        int c = r.nextInt(26) + (byte)'a';
                        char i = (char)c;
                        int random = randomInteger();
                        String combined = i+String.valueOf(random);
                        //random alphanumberic figures
                        preferenceEditor.putString("userName",in_username);
                        preferenceEditor.putString("userID", combined);
                        preferenceEditor.commit();
                        //Log.d("TEST", String.valueOf(preferenceEditor.commit()));
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
            }else{

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        }

        /**
        if(fileExists("user")==true){
            Log.d("User", "User Exists");
            try{
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput("user")));
                String in = null;
                while((in=inputReader.readLine())!=null){
                    String[] split = in.split(Pattern.quote("|"));
                    String username = split[0];
                    String userid = split[1];
                    Log.d("User Details",username+" "+userid);
                    showUser(username, userid);
                }
            }catch (IOException e){
                Log.d("IOEXCEPTION", e.toString());
            }

        }else{
            Log.d("User","User Doesn't Exist");
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText username = (EditText)findViewById(R.id.input_username);
                    if(username.getText().toString().trim().length()>0){
                        String in_username = username.getText().toString();

                        //int count = username.getText().toString().trim().length();
                        //generate random alphabet
                        Random r = new Random();
                        int c = r.nextInt(26) + (byte)'a';
                        char i = (char)c;
                        int random = randomInteger();
                        String combined = i+String.valueOf(random);
                        //random alphanumberic figures
                        String final_combined = username.getText()+"|"+combined;
                        //check in database if it exists and redo it needed
                        Log.d("Combined",in_username+"|"+combined);
                        try {
                            //output to file or database here
                            FileOutputStream fOut = openFileOutput("user", MODE_APPEND);
                            fOut.write(final_combined.getBytes());
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        } catch (IOException e) {
                            Log.d("IOEXCEPTION", e.toString());
                        }
                    }else{
                        Log.d("Empty","Empty");
                    }
                }
            });

        }

    }**/

    public void showUser(String name, String id){
        Bundle data = new Bundle();
        data.putString("userName",name);
        data.putString("userID", id);
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        intent.putExtras(data);
        startActivity(intent);
    }

    public int randomInteger() {
        int min = 100000000;
        int max = 999999998;
        Random rand = new Random();
        // nextInt excludes the top value so we have to add 1 to include the top value
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }



    public boolean fileExists(String fname){
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + fname;
        Log.d("PATH",path);
        File file = new File(path);
        if(file.exists()){
            Log.d("FE","File Exists");
            return true;
        }else{
            Log.d("FDE", "File Doesn't Exist");
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_user, menu);
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
}
