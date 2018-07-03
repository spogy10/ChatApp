package poliv.jr.com.addcontact;

import poliv.jr.com.addcontact.events.AddContactEvent;

public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}
