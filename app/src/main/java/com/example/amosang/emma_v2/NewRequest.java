package com.example.amosang.emma_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewRequest extends AppCompatActivity {

    private MediaRecorder mR;
    private MediaPlayer m;
    private String path = "";
    private String fileTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);
        initializeUI();
    }

    private void initializeUI(){
        final EditText requestTitle = (EditText)findViewById(R.id.input_reqtitle);
        final TextView audioFile = (TextView)findViewById(R.id.text_audiofile);
        final Button addRequest = (Button)findViewById(R.id.btn_add);
        final Button cancelRequest = (Button)findViewById(R.id.btn_cancel);
        final Button recordAudio = (Button)findViewById(R.id.btn_record);
        final Button stopRecording = (Button)findViewById(R.id.btn_stop);
        final Button playRecording = (Button)findViewById(R.id.btn_play);
        final Button clearRecording = (Button)findViewById(R.id.btn_clear);
        final Button stopPlayback = (Button)findViewById(R.id.btn_stopPlay);

        addRequest.setVisibility(View.INVISIBLE);
        recordAudio.setVisibility(View.INVISIBLE);
        stopRecording.setVisibility(View.INVISIBLE);
        playRecording.setVisibility(View.INVISIBLE);
        clearRecording.setVisibility(View.INVISIBLE);
        stopPlayback.setVisibility(View.INVISIBLE);

        folderExists();

        requestTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 4) {
                    //addRequest.setVisibility(View.VISIBLE);
                    recordAudio.setVisibility(View.VISIBLE);


                } else {
                    //addRequest.setVisibility(View.INVISIBLE);
                    recordAudio.setVisibility(View.INVISIBLE);
                }
            }
        });




        recordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mR = new MediaRecorder();
                    mR.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mR.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mR.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    fileTitle = requestTitle.getText().toString();
                    path = getCacheDir()+"/reqAudio/"+fileTitle+".3gp";
                    audioFile.setText(path);
                    mR.setOutputFile(path);
                    mR.prepare();
                    mR.start();

                    Log.d("RECORDSTART", "RECORDING");
                } catch (Exception e) {
                    Log.d("EXCEPTION", e.toString());
                }
                recordAudio.setEnabled(false);
                stopRecording.setVisibility(View.VISIBLE);
                stopRecording.setEnabled(true);
            }
        });

        stopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("STOPRECORDING","STOPRECORDING");
                mR.stop();
                mR.release();
                mR = null;

                stopRecording.setEnabled(false);
                playRecording.setVisibility(View.VISIBLE);
                stopPlayback.setVisibility(View.VISIBLE);
                stopPlayback.setEnabled(false);
                clearRecording.setVisibility(View.VISIBLE);
                addRequest.setVisibility(View.VISIBLE);

            }
        });

        clearRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLEARRECORDING", "CLEARRECORDING");
                fileDelete(path);
                playRecording.setVisibility(View.INVISIBLE);
                clearRecording.setVisibility(View.INVISIBLE);
                stopRecording.setVisibility(View.INVISIBLE);
                stopPlayback.setVisibility(View.INVISIBLE);
                addRequest.setVisibility(View.INVISIBLE);
                recordAudio.setVisibility(View.VISIBLE);
                recordAudio.setEnabled(true);
            }
        });

        playRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PLAYRECORDING","PLAYRECORDING");
                m = new MediaPlayer();
                try{
                    m.setDataSource(path);
                }catch (IOException e){
                    Log.d("IOEXCEPTION",e.toString());
                }
                try{
                    m.prepare();
                }catch (IOException e){
                    Log.d("IOEXCEPTION",e.toString());
                }
                stopPlayback.setEnabled(true);
                m.start();
            }
        });

        stopPlayback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlayback.setEnabled(false);
                m.stop();
            }
        });


        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(audioFile.getText().length()>0){
                    Date date = new Date();
                    Log.d("DATE", date.toString());
                    String convDate = convert(date);
                    String getText = requestTitle.getText().toString();
                    String newRequest = getText.toString() + "|" + convDate.toString() + "|" + path + "\n";
                    try {
                        //output to file or database here
                        FileOutputStream fOut = openFileOutput("requests", MODE_APPEND);
                        Log.d("REQUESTOUTPUT", newRequest.toString());
                        fOut.write(newRequest.getBytes());
                    } catch (IOException e) {
                        Log.d("IOEXCEPTION", e.toString());
                    }
                    Intent main = new Intent();
                    main.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(main);
                }else{
                    Log.d("NORECORDING","NORECORDING");
                }
                /**if (!requestTitle.toString().equals("")) {
                    Date date = new Date();
                    Log.d("DATE", date.toString());
                    String convDate = convert(date);
                    String getText = requestTitle.getText().toString();
                    String newRequest = getText.toString() + "|" + convDate.toString() + "\n";
                    try {
                        //output to file or database here
                        FileOutputStream fOut = openFileOutput("requests", MODE_APPEND);
                        Log.d("REQUESTOUTPUT", newRequest.toString());
                        fOut.write(newRequest.getBytes());
                    } catch (IOException e) {
                        Log.d("IOEXCEPTION", e.toString());
                    }
                    Intent main = new Intent();
                    main.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(main);
                }**/
            }
        });

        cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("TESTTEST",path);
                fileDelete(path);
                Intent cancel = new Intent();
                cancel.setClass(getApplicationContext(), MainActivity.class);
                startActivity(cancel);

            }
        });
    }


    public void fileDelete(String fileLoc){
//        Log.d("PATH",fileLoc);
        File file = new File(fileLoc);
        if(file.exists()){
            Log.d("FEMA","File Exists");
            file.delete();
        }else{
            Log.d("FDEMA", "File Doesn't Exist");
        }
    }

    private String convert(Date input){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String datetime = sdf.format(input);
            return datetime;
        } catch (Exception e) {
            Log.d("PARSEEXCEPTION",e.toString());
        }
        return null;
    }

    public boolean folderExists(){
        //String path =  getApplicationContext().getFilesDir().getAbsolutePath();
        //String path = getCacheDir().toString();
        //Log.d("PATH",path);
        //File reqDir = new File(getCacheDir(),"reqAudio");
        //reqDir.mkdir();
        File f = new File(getCacheDir()+"/reqAudio");
        if(f.exists()&&f.isDirectory()){
            Log.d("PATHEXISTS","PATHEXISTS");
            return true;
        }else{
            Log.d("PATHDOESNTEXIST","PATHDOESNTEXIST");
            File reqDir = new File(getCacheDir(),"reqAudio");
            reqDir.mkdir();
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_request, menu);
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
