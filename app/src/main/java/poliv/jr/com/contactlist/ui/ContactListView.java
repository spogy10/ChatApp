package poliv.jr.com.contactlist.ui;

import poliv.jr.com.contactlist.entities.User;

public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
