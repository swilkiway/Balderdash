package coolness.balderdashclient.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import coolness.balderdashclient.Model.Answer;
import coolness.balderdashclient.R;
import coolness.balderdashclient.Client.ServerProxy;
import coolness.balderdashclient.Client.User;

public class AnswerActivity extends AppCompatActivity {
    static final int ANSWER_CODE = 2;
    private Button mSubmitButton;
    private TextView mQuestion;
    private EditText mAnswerType;
    private Answer mAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mAnswer = new Answer();
        mSubmitButton = (Button) findViewById(R.id.submit_a);
        mSubmitButton.setEnabled(false);
        mQuestion = (TextView) findViewById(R.id.question_spot);
        mQuestion.setText(User.get().getQuestion().getMessage());
        mAnswerType = (EditText) findViewById(R.id.enter_a);
        mAnswerType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAnswer.setMessage(s.toString());
                if (!mAnswer.getMessage().equals("")) {
                    mSubmitButton.setEnabled(true);
                } else {
                    mSubmitButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerTask r = new AnswerTask();
                r.execute(mAnswer);
            }
        });
    }
    public class AnswerTask extends AsyncTask<Answer, Answer, Answer> {
        @Override
        protected Answer doInBackground(Answer... aObj) {
            ServerProxy.submitAnswer(aObj[0]);
            try {
                boolean done = false;
                while (!done) {
                    Thread.sleep(1000);
                    done = ServerProxy.testIfReady(ANSWER_CODE).isReady();
                }
                User.get().setAnswers(ServerProxy.getAnswers());
                Thread.sleep(1000);
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Answer a) {
            Intent intent = new Intent(AnswerActivity.this, GuessActivity.class);
            startActivity(intent);
        }
    }
}

