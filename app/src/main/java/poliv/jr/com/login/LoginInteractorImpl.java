package poliv.jr.com.login;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginRepository loginRespository;

    public LoginInteractorImpl(){
        this.loginRespository = new LoginRepositoryImpl();
    }


    @Override
    public void checkAlreadyAuthenticated() {
        loginRespository.checkAlreadyAuthenticated();
    }

    @Override
    public void doSignUp(String email, String password) {
        loginRespository.signUp(email, password);
    }

    @Override
    public void doSignIn(String email, String password) {
        loginRespository.signIn(email, password);
    }
}
