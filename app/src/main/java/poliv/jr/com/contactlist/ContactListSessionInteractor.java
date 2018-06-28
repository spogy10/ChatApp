package poliv.jr.com.contactlist;

public interface ContactListSessionInteractor {
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean online);
}
