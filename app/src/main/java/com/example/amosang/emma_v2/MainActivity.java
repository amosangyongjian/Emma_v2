package com.example.amosang.emma_v2;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ArrayList<UserReq> userReqs = new ArrayList<>();
    private ListView listView;
    private String newReqTitle = "";
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI(savedInstanceState);

    }



    private void initializeUI(Bundle savedInstanceState){

        //preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceSettings = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceEditor = preferenceSettings.edit();
        String userName = preferenceSettings.getString("userName","empty");
        String uID = preferenceSettings.getString("userID","empty");

        TextView welcomeUser = (TextView)findViewById(R.id.text_username);
        welcomeUser.setText("Welcome, "+userName);
        //set textview for username
        //id is set for requests

        listView = (ListView)findViewById(R.id.listview_requests);
        RequestAdapter ra;
        ra = new RequestAdapter(getApplicationContext(),R.layout.request_listadapter);
        listView.setAdapter(ra);
        Date date = new Date();
        UserReq u1 = new UserReq("Flowers", date,"filepath");
        UserReq u2 = new UserReq("Cars", date,"filepath2");
        userReqs.add(u1);
        userReqs.add(u2);
        ra.add(u1);
        ra.add(u2);

        //check database or file
        if(fileExists("requests")==true){
            try{
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput("requests")));
                String in = null;
                while((in=inputReader.readLine())!=null){
                    String[] split = in.split(Pattern.quote("|"));
                    String requestName = split[0];
                    String requestDate = split[1];
                    String requestPath = split[2];
                    Date convDate = convert(requestDate);
                    UserReq newRequest = new UserReq(requestName,convDate,requestPath);
                    userReqs.add(newRequest);
                    ra.add(newRequest);


                }
            }catch (IOException e){
                Log.d("IOEXCEPTION",e.toString());
            }
        }else{
            Log.d("FDEMA","File Doesn't Exist");
        }

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Log.d("POSITION",String.valueOf(position));
               open(position);

           }
       });




        /**
        if(newReqTitle!=""){
            Log.d("DEBUG",newReqTitle);
            UserReq u3 = new UserReq(newReqTitle,date);
            userReqs.add(u3);
            ra.add(u3);
        }**/

        Button newReq = (Button)findViewById(R.id.btn_add);
        newReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),NewRequest.class);
                startActivity(intent);
            }
        });

    }

    public void open(int position){

        String reqName = userReqs.get(position).getRequestTitle();
        Date reqDate = userReqs.get(position).getRequestDate();
        String audioFile = userReqs.get(position).getAudioPath();
        Intent openRequest = new Intent();
        openRequest.setClass(getApplicationContext(),RequestPage.class);
        Bundle data = new Bundle();
        data.putString("reqName",reqName);
        data.putString("reqDate",String.valueOf(reqDate));
        data.putString("audioFile",audioFile);
        openRequest.putExtras(data);
        startActivity(openRequest);



    }


    public boolean fileExists(String fname){
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + fname;
        Log.d("PATH",path);
        File file = new File(path);
        if(file.exists()){
            Log.d("FEMA","File Exists");
            Log.d("TEST","TEST");
            return true;
        }else{
            Log.d("FDEMA", "File Doesn't Exist");
            return false;
        }
    }

    private Date convert(String input){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = sdf.parse(input);

            return date;
        }catch(ParseException e){
            Log.d("PARSEEXCEPTION",e.toString());
        }
        return null;
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
}
