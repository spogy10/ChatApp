package poliv.jr.com.contactlist;

public interface ContactListInteractor {
    void subscribeForContactEvents();
    void unSubscribeForContactEvents();
    void destroyContactListListener();
    void removeContact(String email);
}
