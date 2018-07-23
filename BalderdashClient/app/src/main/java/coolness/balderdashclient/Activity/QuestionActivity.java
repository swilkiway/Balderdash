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

import coolness.balderdashclient.Model.Question;
import coolness.balderdashclient.R;
import coolness.balderdashclient.Model.ReadyResponse;
import coolness.balderdashclient.Client.ServerProxy;
import coolness.balderdashclient.Client.User;

public class QuestionActivity extends AppCompatActivity {
    static final int QUESTION_CODE = 1;
    private Button mSubmitButton;
    private EditText mQuestionType;
    private Question mQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mQuestion = new Question();
        mSubmitButton = (Button) findViewById(R.id.submit_q);
        mSubmitButton.setEnabled(false);
        mQuestionType = (EditText) findViewById(R.id.enter_q);
        mQuestionType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mQuestion.setMessage(s.toString());
                if (!mQuestion.getMessage().equals("")) {
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
                QuestionTask r = new QuestionTask();
                r.execute(mQuestion);
            }
        });
    }
    public class QuestionTask extends AsyncTask<Question, Question, Question> {
        @Override
        protected Question doInBackground(Question... qObj) {
            ServerProxy.submitQuestion(qObj[0]);
            ReadyResponse rr = null;
            try {
                boolean done = false;
                while (!done) {
                    Thread.sleep(1000);
                    rr = ServerProxy.testIfReady(QUESTION_CODE);
                    done = rr.isReady();
                }
                User.get().setQuestion(rr.getQuestion());
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Question rr) {
            Intent intent = new Intent(QuestionActivity.this, AnswerActivity.class);
            startActivity(intent);
        }
    }
}
