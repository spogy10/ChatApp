package poliv.jr.com.login;

import poliv.jr.com.lib.EventBus;
import poliv.jr.com.login.events.LoginEvent;
import poliv.jr.com.login.ui.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    EventBus eventBus;
    LoginView loginView;
    LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
        //todo: this.eventBus =
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
        eventBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        if(loginView != null){
            loginView.disableInputs();
            loginView.showProgress();
        }
    }

    @Override
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType())
    }

    @Override
    public void validateLogin(String email, String password) {

    }

    @Override
    public void registerNewUser(String email, String password) {

    }
}
