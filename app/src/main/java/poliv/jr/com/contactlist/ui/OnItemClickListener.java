package poliv.jr.com.contactlist.ui;

import poliv.jr.com.contactlist.entities.User;

public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
