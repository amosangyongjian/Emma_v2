package com.example.amosang.emma_v2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestPage extends AppCompatActivity {

    private MediaPlayer m;
    private String reqName;
    private String reqDate;
    private String audioFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page);
        initializeUI();
    }


    private void initializeUI(){

        Button playRequest = (Button)findViewById(R.id.btn_playReq);
        final Button stopRequest = (Button)findViewById(R.id.btn_stopReq);
        stopRequest.setEnabled(false);
        Bundle data = getIntent().getExtras();
        reqName = data.getString("reqName");
        //Date reqDate = convert(data.getString("reqDate"));
        reqDate = data.getString("reqDate");
        audioFile = data.getString("audioFile");
        TextView requestName = (TextView)findViewById(R.id.text_reqtitle);
        TextView requestDate = (TextView)findViewById(R.id.text_reqdate);
        TextView reqFP = (TextView)findViewById(R.id.text_filepath);

        requestName.setText(reqName);
        requestDate.setText(String.valueOf(reqDate));
        reqFP.setText(audioFile);

        playRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PLAYRECORDING", "PLAYRECORDING");
                m = new MediaPlayer();
                try {
                    m.setDataSource(audioFile);
                } catch (IOException e) {
                    Log.d("IOEXCEPTION", e.toString());
                }
                try {
                    m.prepare();
                } catch (IOException e) {
                    Log.d("IOEXCEPTION", e.toString());
                }
                stopRequest.setEnabled(true);
                m.start();
            }
        });

        stopRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRequest.setEnabled(false);
                m.stop();
            }
        });


    }




    private Date convert(String input){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date datetime = sdf.parse(input);
            return datetime;
        } catch (Exception e) {
            Log.d("PARSEEXCEPTION", e.toString());
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_request_page, menu);
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
