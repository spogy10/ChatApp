package poliv.jr.com.contactlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import poliv.jr.com.contactlist.entities.User;
import poliv.jr.com.contactlist.events.ContactListEvent;
import poliv.jr.com.domain.FirebaseHelper;
import poliv.jr.com.lib.EventBus;
import poliv.jr.com.lib.GreenRobotEventBus;

public class ContactListRepositoryImpl implements ContactListRepository {

    private FirebaseHelper helper;

    private ChildEventListener contactListEventListener;

    public ContactListRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void signOff() {
        helper.signOff();
    }

    @Override
    public String getCurrentEmail() {
        return helper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail = helper.getAuthUserEmail();
        helper.getOneContactReference(currentUserEmail, email).removeValue();
        helper.getOneContactReference(email, currentUserEmail).removeValue();
    }

    @Override
    public void destroyContactListListener() {
        contactListEventListener = null;
    }

    @Override
    public void subscribeForContactListUpdates() {
        if(contactListEventListener == null){
            contactListEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildKey) {
                    String email = dataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = ((Boolean) dataSnapshot.getValue()).booleanValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvent.onContactAdded, user);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String email = dataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = ((Boolean) dataSnapshot.getValue()).booleanValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvent.onContactChanged, user);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.getKey();
                    email = email.replace("_", ".");
                    boolean online = ((Boolean) dataSnapshot.getValue()).booleanValue();
                    User user = new User(email, online, null);
                    postEvent(ContactListEvent.onContactRemoved, user);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
        }

        helper.getMyContactsReference().addChildEventListener(contactListEventListener);
    }

    @Override
    public void unSubscribeForContactListUpdates() {
        if (contactListEventListener != null){
            helper.getMyContactsReference().removeEventListener(contactListEventListener);
        }
    }

    @Override
    public void changeUserConnectionStatus(boolean online) {
        helper.changeUserConnectionsStatus(online);
    }

    private void postEvent(int type, User user) {
        ContactListEvent contactListEvent = new ContactListEvent();
        contactListEvent.setEventType(type);
        contactListEvent.setUser(user);
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(contactListEvent);
    }
}
