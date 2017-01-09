package hammak.simplemultiplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

import static android.content.SharedPreferences.*;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    private TextView tvTaskL, tvTaskR, tvCounter, tvUserAnswer, tvRightAnswer;
    private EditText etAnswer;

    private SharedPreferences pref;
    private static final String COUNTER_SAVED_STATE_NAME = "counter";

    private int fNum, sNum, counter;

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();

        // TextViews
        tvTaskL = (TextView) findViewById(R.id.tvTaskL);
        tvTaskR = (TextView) findViewById(R.id.tvTaskR);
        tvCounter = (TextView) findViewById(R.id.tvCounter);
        tvUserAnswer = (TextView) findViewById(R.id.tvUserAnswer);
        tvRightAnswer = (TextView) findViewById(R.id.tvRightAnswer);

        // EditTexts
        etAnswer = (EditText) findViewById(R.id.etAnswer);

        // Buttons
        Button bAnswer = (Button) findViewById(R.id.bAnswer);

        // OnClickListeners
        bAnswer.setOnClickListener(this);

        pref = getPreferences(MODE_PRIVATE);
        counter = pref.getInt(COUNTER_SAVED_STATE_NAME, 0);

        updateCounter(counter);
        setNewTask();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Saves counter state
        pref = getPreferences(MODE_PRIVATE);
        Editor ed = pref.edit();
        ed.putInt(COUNTER_SAVED_STATE_NAME, counter);
        ed.apply();
    }

    private void updateCounter(int counter) {
        tvCounter.setText(String.format(Locale.ENGLISH, "%d", counter));
    }

    private void setNewTask(){
        // Generates new task numbers from 0 to 9
        fNum = (int) (Math.random() * 10);
        sNum = (int) (Math.random() * 10);

        // Sets textViews's values to generated numbers
        tvTaskL.setText(String.format(Locale.ENGLISH, "%d", fNum));
        tvTaskR.setText(String.format(Locale.ENGLISH, "%d", sNum));
    }

    private boolean checkAnswers(int answer) {
        boolean result = false;

        // Checks answer
        if ( (fNum * sNum) == answer)
            result = true;

        return result;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bAnswer:
                if (Objects.equals(etAnswer.getText().toString(), ""))
                    break;

                // Gets an answer and sets it to tvUserAnswer, also sets right answer to
                // tvRightAnswer
                int answer;
                answer = Integer.parseInt((etAnswer.getText().toString()));
                tvRightAnswer.setText(String.format(Locale.ENGLISH, "%d", (fNum * sNum)));
                tvUserAnswer.setText(String.format(Locale.ENGLISH, "%d", answer));

                // If answer is correct - adding +1 to counter and updating it, also changes text
                // color of user answer to green (from app theme)
                if (checkAnswers(answer)) {
                    counter ++;
                    updateCounter(counter);
                    tvUserAnswer.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                }
                // Else sets counter to zero and updating it, also changes text
                // color of user answer to red (from app theme)
                else{
                    counter = 0;
                    updateCounter(counter);
                    tvUserAnswer.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                }

                etAnswer.setText("");

                setNewTask();
                break;
        }
    }
}
