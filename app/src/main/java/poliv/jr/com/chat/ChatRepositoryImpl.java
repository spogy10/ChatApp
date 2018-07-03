package poliv.jr.com.chat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import poliv.jr.com.chat.entities.ChatMessage;
import poliv.jr.com.chat.events.ChatEvent;
import poliv.jr.com.domain.FirebaseHelper;
import poliv.jr.com.lib.EventBus;
import poliv.jr.com.lib.GreenRobotEventBus;

public class ChatRepositoryImpl implements ChatRepository {

    private String receiver;
    private FirebaseHelper helper;
    private ChildEventListener chatEvenListener;

    public ChatRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void sendMessage(String msg) {
        String keySender = helper.getAuthUserEmail().replace(".", "_");
        ChatMessage chatMessage = new ChatMessage(keySender, msg);
        DatabaseReference chatReference = helper.getChatsReference(receiver);
        chatReference.push().setValue(chatMessage);
    }

    @Override
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public void destroyChatListener() {
        chatEvenListener = null;
    }

    @Override
    public void subscribeForChatUpdates() {
        if(chatEvenListener == null){
            chatEvenListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String msgSender = chatMessage.getSender();
                    msgSender = msgSender.replace("_", ".");

                    String currentUserEmail = helper.getAuthUserEmail();
                    chatMessage.setSentByMe(msgSender.equals(currentUserEmail));

                    ChatEvent chatEvent = new ChatEvent(chatMessage);
                    EventBus eventBus = GreenRobotEventBus.getInstance();
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            helper.getChatsReference(receiver).addChildEventListener(chatEvenListener);
        }
    }

    @Override
    public void unSubscribeForChatUpdates() {
        if(chatEvenListener != null){
            helper.getChatsReference(receiver).removeEventListener(chatEvenListener);
        }
    }

    @Override
    public void changeUserConnectionStatus(boolean online) {
        helper.changeUserConnectionsStatus(online);
    }
}
