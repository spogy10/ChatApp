package poliv.jr.com.chat;

public interface ChatInteractor {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void destroyChatListener();
    void subscribeForChatUpdates();
    void unSubscribeForChatUpdates();
}
