package gluka.morsecodeggc;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioTrack;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    String result ;
    ImageView image;
     EditText input;
     int buttonPressedCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

          input = (EditText)findViewById(R.id.input);
        Button transmit = (Button)findViewById(R.id.transmitButton);
        image = (ImageView)findViewById(R.id.imageColor);
        image.setBackgroundColor(Color.WHITE);

        transmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                result = ""+ input.getText();
                Log.d("print", "onClick: lenght: " + result.length());
                //gets morse code
                buttonPressedCount++;



                new MorseAsyncTask().execute();
            }
        });
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
            Intent intent = new Intent(this,About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class MorseAsyncTask extends AsyncTask<Void,Void,AudioTrack> {
        AudioTrack tone;//= AudioUtils.generateTone(440, 50);
        MorseCode m = new MorseCode();
        @Override
        protected AudioTrack doInBackground(Void...params) {


int pause = 0;
                try {

                    for (Signal temp : MorseCode.genOnOffSchedule(result, 1)) {
                        Log.d("print", "doInBackground: getOnSet: " + temp.getOnset());
                        //signals are for each letter, contains ditdahs

                        if (temp.isOn() && temp.getDuration() == 50) {
                            Log.d("print", "doInBackground: " + "on " + temp.getCharacterNumber());
                            tone = AudioUtils.generateTone(400, temp.getDuration());
                            tone.play();
                            input.setText(MorseCode.annotateMessage(temp.getCharacterNumber(), result));
                            image.setBackgroundColor(Color.BLUE);

                        }
                        else if (temp.isOn() && temp.getDuration() == 150) {

                            tone = AudioUtils.generateTone(400, temp.getDuration());
                            tone.play();
                            input.setText(MorseCode.annotateMessage(temp.getCharacterNumber(), result));
                            image.setBackgroundColor(Color.BLUE);

                        } else if (temp.isOn() == false || temp.getOnset()==200) {
                            Thread.sleep(200);
                            image.setBackgroundColor(Color.WHITE);

                            if (tone != null) {
                                tone.release();
                            }
                        }

                        if(pause==temp.getCharacterNumber()==false){
                            pause++;
                            Thread.sleep(250);
                        }

                    }

                    //used for queueing
                    if(buttonPressedCount>1){
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    Log.d("print", "onClick: " + "thread failed to sleep");
                }


                return tone;

        };

        @Override
        protected void onPostExecute(AudioTrack s) {
            super.onPostExecute(s);
        }
    }
}
