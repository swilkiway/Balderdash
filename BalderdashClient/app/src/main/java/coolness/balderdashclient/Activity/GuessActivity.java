package coolness.balderdashclient.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import coolness.balderdashclient.Model.Answer;
import coolness.balderdashclient.Model.Guess;
import coolness.balderdashclient.R;
import coolness.balderdashclient.Client.ServerProxy;
import coolness.balderdashclient.Client.User;

public class GuessActivity extends AppCompatActivity {
    static final int GUESS_CODE = 3;
    private TextView mQuestion;
    private RecyclerView mAnswersList;
    private RecyclerView.Adapter mAnswersAdaptor;
    private RecyclerView.LayoutManager mAnswersManager;
    private ArrayList<Answer> mAnswers;
    private Guess mGuess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        mGuess = new Guess();
        mQuestion = (TextView) findViewById(R.id.question_spot);
        mQuestion.setText(User.get().getQuestion().getMessage());
        mAnswersList = (RecyclerView) findViewById(R.id.answer_list);
        mAnswersManager = new LinearLayoutManager(this);
        mAnswersList.setLayoutManager(mAnswersManager);
        mAnswersAdaptor = new AnswerAdapter(mAnswers);
        mAnswersList.setAdapter(mAnswersAdaptor);
    }
    public class GuessTask extends AsyncTask<Guess, Guess, Guess> {
        @Override
        protected Guess doInBackground(Guess... gObj) {
            ServerProxy.submitGuess(gObj[0]);
            try {
                boolean done = false;
                while (!done) {
                    Thread.sleep(1000);
                    done = ServerProxy.testIfReady(GUESS_CODE).isReady();
                }
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Guess g) {
            Intent intent = new Intent(GuessActivity.this, DisplayActivity.class);
            startActivity(intent);
        }
    }
    public class AnswerAdapter extends RecyclerView.Adapter<AnswerHolder> {
        private ArrayList<Answer> mAnswers;
        public AnswerAdapter(ArrayList<Answer> r) {
            mAnswers = r;
        }
        @Override
        public AnswerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(GuessActivity.this);
            return new AnswerHolder(inflater.inflate(R.layout.answer_item, parent, false));
        }
        @Override
        public void onBindViewHolder(AnswerHolder holder, int position) {
            Answer answer = mAnswers.get(position);
            holder.bind(answer);
        }

        @Override
        public int getItemCount() {
            return mAnswers.size();
        }
    }
    public class AnswerHolder extends RecyclerView.ViewHolder {
        private Answer mAnswer;
        private TextView eTextView;
        private Button eButton;
        public AnswerHolder(View view) {
            super(view);
            eTextView = (TextView) view.findViewById(R.id.answer_text);
            eButton = (Button) view.findViewById(R.id.answer_button);
            eButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eButton.setEnabled(false);
                    mGuess.setMessage(mAnswer.getMessage());
                    mGuess.setUserName(User.get().getUserName());
                    GuessTask r = new GuessTask();
                    r.execute(mGuess);
                }
            });
        }
        public void bind(Answer answer) {
            mAnswer = answer;
            eTextView.setText(mAnswer.getMessage());
        }
    }
}


