package poliv.jr.com.chatapp.login.ui;

import android.app.Activity;
import android.os.Bundle;


import poliv.jr.com.chatapp.R;

public class LoginActivity extends Activity implements LoginView {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    @Override
    public void enableInputs() {

    }

    @Override
    public void disableInputs() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void handleSignUp() {

    }

    @Override
    public void handleSignIn() {

    }

    @Override
    public void navigateToMainScreen() {

    }

    @Override
    public void loginError(String error) {

    }

    @Override
    public void newUserSuccess() {

    }

    @Override
    public void newUserError(String error) {

    }
}
