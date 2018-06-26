package poliv.jr.com.login;

import org.greenrobot.eventbus.Subscribe;

import poliv.jr.com.lib.EventBus;
import poliv.jr.com.lib.GreenRobotEventBus;
import poliv.jr.com.login.events.LoginEvent;
import poliv.jr.com.login.ui.LoginView;

public class LoginPresenterImpl implements LoginPresenter {

    EventBus eventBus;
    LoginView loginView;
    LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
        this.eventBus = GreenRobotEventBus.getInstance();
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

        loginInteractor.checkAlreadyAuthenticated();
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()){
            case LoginEvent.onSignInError:
                onSignError(event.getErrorMessage());
                break;
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;

        }
    }

    private void onFailedToRecoverSession() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.enableInputs();
        }
    }

    private void onSignUpSuccess() {
        if(loginView != null)
            loginView.newUserSuccess();
    }

    private void onSignUpError(String errorMessage) {
        if (loginView != null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(errorMessage);
        }
    }

    private void onSignInSuccess() {
        if(loginView != null)
            loginView.navigateToMainScreen();
    }

    private void onSignError(String errorMessage) {
        if (loginView != null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(errorMessage);
        }
    }

    @Override
    public void validateLogin(String email, String password) {
        if(loginView != null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);

    }

    @Override
    public void registerNewUser(String email, String password) {
        if(loginView != null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email, password);

    }
}
