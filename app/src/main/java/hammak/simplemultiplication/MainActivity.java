package hammak.simplemultiplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {

    private TextView tvTaskL, tvTaskR, tvCounter, tvUserAnswer, tvRightAnswer;
    private EditText etAnswer;

    // Task numbers
    private int fNum, sNum, counter = 0;

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

        updateCounter(counter);
        setNewTask();
    }

    private void updateCounter(int counter) {
        tvCounter.setText(String.format(Locale.ENGLISH, "%d", counter));
        if (counter == 0)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter0));
        else if (counter == 5)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter5));
        else if (counter == 10)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter10));
        else if (counter == 12)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter12));
        else if (counter == 13)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter13));
        else if (counter == 20)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter20));
        else if (counter == 33)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter33));
        else if (counter == 50)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter50));
        else if (counter == 75)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter75));
        else if (counter == 79)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter79));
        else if (counter == 100)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter100));
        else if (counter == 255)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter255));
        else if (counter == 500)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter500));
        else if (counter == 666)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter666));
        else if (counter == 1618)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter1618));
        else if (counter == 2147483647)
            tvCounter.setBackgroundColor(ContextCompat.getColor(context, R.color.counter2147483647));
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
