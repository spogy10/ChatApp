package poliv.jr.com.contactlist.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import poliv.jr.com.chatapp.R;
import poliv.jr.com.contactlist.entities.User;
import poliv.jr.com.contactlist.ui.OnItemClickListener;
import poliv.jr.com.domain.AvatarHelper;
import poliv.jr.com.lib.ImageLoader;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<User> contactList;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;

    public ContactListAdapter(List<User> contactList, ImageLoader imageLoader, OnItemClickListener clickListener) {
        this.contactList = contactList;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = contactList.get(position);
        holder.setClickListener(user, clickListener);

        String email = user.getEmail();
        boolean online = user.isOnline();
        String status = online ? "online" : "offline";
        int color = online? Color.GREEN : Color.RED;

        holder.txtUser.setText(email);
        holder.txtStatus.setText(status);
        holder.txtStatus.setTextColor(color);

        imageLoader.load(holder.imgAvatar, AvatarHelper.getAvatarUrl(email));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public int getPositionByUsername(String username){
        int position = 0;
        for(User user : contactList){
            if(user.getEmail().equals(username))
                break;
            position++;
        }
        return position;
    }

    private boolean alreadyInAdapter(User newUser){
        boolean alreadyInAdapter = false;
        for(User user : this.contactList){
            if(user.getEmail().equals(newUser.getEmail())){
                alreadyInAdapter = true;
                break;
            }
        }
        return alreadyInAdapter;
    }

    public void add(User user){
        if(!alreadyInAdapter(user)){
            this.contactList.add(user);
            this.notifyDataSetChanged();
        }
    }

    public void update(User user){
        int pos = getPositionByUsername(user.getEmail());
        contactList.set(pos, user);
        this.notifyDataSetChanged();
    }

    public void remove(User user){
        int pos = getPositionByUsername(user.getEmail());
        contactList.remove(pos);
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.txtUser)
        TextView txtUser;
        @BindView(R.id.txtStatus)
        TextView txtStatus;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        public void setClickListener(final User user, final OnItemClickListener listener){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(user);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(user);
                    return false;
                }
            });
        }
    }
}
