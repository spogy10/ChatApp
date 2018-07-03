package poliv.jr.com.chat;

import org.greenrobot.eventbus.Subscribe;

import poliv.jr.com.chat.entities.ChatMessage;
import poliv.jr.com.chat.events.ChatEvent;
import poliv.jr.com.chat.ui.ChatView;
import poliv.jr.com.contactlist.entities.User;
import poliv.jr.com.lib.EventBus;
import poliv.jr.com.lib.GreenRobotEventBus;

public class ChatPresenterImpl implements ChatPresenter {

    EventBus eventBus;
    ChatView chatView;
    ChatInteractor chatInteractor;
    ChatSessionInteractor chatSessionInteractor;

    public ChatPresenterImpl(ChatView chatView) {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.chatView = chatView;
        this.chatInteractor = new ChatInteractorImpl();
        this.chatSessionInteractor = new ChatSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        chatInteractor.unSubscribeForChatUpdates();
        chatSessionInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {
        chatInteractor.subscribeForChatUpdates();
        chatSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatInteractor.destroyChatListener();
        chatView = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        this.chatInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if(chatView != null){
            ChatMessage msg = event.getMessage();
            chatView.onMessageReceived(msg);
        }
    }
}
