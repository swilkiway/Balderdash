package coolness.balderdashclient.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import coolness.balderdashclient.Client.ServerProxy;
import coolness.balderdashclient.Client.User;
import coolness.balderdashclient.Model.Answer;
import coolness.balderdashclient.Model.Guess;
import coolness.balderdashclient.Model.Question;
import coolness.balderdashclient.R;

public class DisplayActivity extends AppCompatActivity {
    private TextView mQuestion;
    private RecyclerView mAnswersList;
    private RecyclerView.Adapter mAnswersAdapter;
    private RecyclerView.LayoutManager mAnswersManager;
    private Answer[] mAnswers;
    private Button mNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        mQuestion = (TextView) findViewById(R.id.question_spot);
        mQuestion.setText(User.get().getQuestion().getMessage());
        mAnswers = User.get().getAnswers();
        mAnswersList = (RecyclerView) findViewById(R.id.answer_list);
        mAnswersManager = new LinearLayoutManager(this);
        mAnswersList.setLayoutManager(mAnswersManager);
        mAnswersAdapter = new AnswerAdapter(mAnswers);
        mAnswersList.setAdapter(mAnswersAdapter);
        mNext = (Button) findViewById(R.id.next_q);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextTask n = new NextTask();
                n.execute(new Question());
            }
        });
    }
    public class NextTask extends AsyncTask<Question, Question, Question> {
        @Override
        protected Question doInBackground(Question... qObj) {
            try {
                Question q = ServerProxy.getNextQuestion();
                if (q == null) {
                    User.get().setQuestion(null);
                    User.get().setAnswers(null);
                    Thread.sleep(500);
                    Intent intent = new Intent(DisplayActivity.this, QuestionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    User.get().setQuestion(q);
                    Thread.sleep(500);
                    Intent intent = new Intent(DisplayActivity.this, AnswerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public class AnswerAdapter extends RecyclerView.Adapter<AnswerHolder> {
        private Answer[] mAnswers;
        public AnswerAdapter(Answer[] a) {
            mAnswers = a;
        }
        @Override
        public AnswerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(DisplayActivity.this);
            return new AnswerHolder(inflater.inflate(R.layout.answer_item, parent, false));
        }
        @Override
        public void onBindViewHolder(AnswerHolder holder, int position) {
            Answer answer = mAnswers[position];
            holder.bind(answer);
        }

        @Override
        public int getItemCount() {
            return mAnswers.length;
        }
    }
    public class AnswerHolder extends RecyclerView.ViewHolder {
        private Answer mAnswer;
        private TextView eTextView;
        public AnswerHolder(View view) {
            super(view);
            eTextView = (TextView) view.findViewById(R.id.answer_text);
        }
        public void bind(Answer answer) {
            mAnswer = answer;
            eTextView.setText(mAnswer.getMessage());
        }
    }
}



