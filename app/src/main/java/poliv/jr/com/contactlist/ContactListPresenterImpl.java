package poliv.jr.com.contactlist;

import org.greenrobot.eventbus.Subscribe;

import poliv.jr.com.contactlist.entities.User;
import poliv.jr.com.contactlist.events.ContactListEvent;
import poliv.jr.com.contactlist.ui.ContactListView;
import poliv.jr.com.lib.EventBus;
import poliv.jr.com.lib.GreenRobotEventBus;

public class ContactListPresenterImpl implements ContactListPresenter {

    EventBus eventBus;
    ContactListView contactListView;
    ContactListSessionInteractor contactListSessionInteractor;
    ContactListInteractor contactListInteractor;

    public ContactListPresenterImpl(ContactListView contactListView) {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.contactListView = contactListView;
        this.contactListSessionInteractor = new ContactListSessionInteractorImpl();
        this.contactListInteractor = new ContactListInteractorImpl();
    }

    @Override
    public void onPause() {
        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.unSubscribeForContactEvents();
    }

    @Override
    public void onResume() {
        contactListSessionInteractor.changeConnectionStatus(User.ONLINE);
        contactListInteractor.subscribeForContactEvents();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        contactListInteractor.destroyContactListListener();
        contactListView = null;
    }

    @Override
    public void signOff() {
        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.destroyContactListListener();
        contactListInteractor.unSubscribeForContactEvents();
        contactListSessionInteractor.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return contactListSessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {
        contactListInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {
        User user = event.getUser();
        switch (event.getEventType()){
            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
        }
    }

    private void onContactRemoved(User user) {
        if(contactListView != null){
            contactListView.onContactRemoved(user);
        }
    }

    private void onContactChanged(User user) {
        if(contactListView != null){
            contactListView.onContactChanged(user);
        }
    }

    private void onContactAdded(User user) {
        if(contactListView != null){
            contactListView.onContactAdded(user);
        }
    }
}