package coolness.balderdashclient.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import coolness.balderdashclient.R;
import coolness.balderdashclient.Model.ReadyRequest;
import coolness.balderdashclient.Model.ReadyResponse;
import coolness.balderdashclient.Model.Register;
import coolness.balderdashclient.Client.ServerProxy;
import coolness.balderdashclient.Client.User;

public class MainActivity extends AppCompatActivity {
    static final int EVERYONE_CODE = 0;
    private Button mSubmitButton;
    private Button mEveryoneButton;
    private EditText mUsername;
    private EditText mServerHost;
    private Register mRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRegister = new Register();
        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setEnabled(false);
        mEveryoneButton = (Button) findViewById(R.id.everyone_button);
        mEveryoneButton.setEnabled(false);
        mUsername = (EditText) findViewById(R.id.username);
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegister.setUserName(s.toString());
                if (mRegister.confirmRegister()) {
                    mSubmitButton.setEnabled(true);
                    mEveryoneButton.setEnabled(true);
                } else {
                    mSubmitButton.setEnabled(false);
                    mEveryoneButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mServerHost = (EditText) findViewById(R.id.server_host);
        mServerHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegister.setServerHost(s.toString());
                if (mRegister.confirmRegister()) {
                    mSubmitButton.setEnabled(true);
                    mEveryoneButton.setEnabled(true);
                } else {
                    mSubmitButton.setEnabled(false);
                    mEveryoneButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterTask r = new RegisterTask();
                r.execute(mRegister);
            }
        });
        mEveryoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadyRequest r = new ReadyRequest(0);
                StartTask s = new StartTask();
                s.execute(r);
            }
        });
    }
    public class RegisterTask extends AsyncTask<Register, Register, Register> {
        @Override
        protected Register doInBackground(Register... regObj) {
            ServerProxy.register(regObj[0]);
            try {
                Thread.sleep(1000);
                User.get(mRegister.getUserName(), mRegister.getServerHost());
                return null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Register rr) {
        }
    }
    public class StartTask extends AsyncTask<ReadyRequest, ReadyResponse, ReadyResponse> {
        @Override
        protected ReadyResponse doInBackground(ReadyRequest... readyObj) {
            ServerProxy.testIfReady(EVERYONE_CODE);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
            startActivity(intent);
            return null;
        }
    }
}
