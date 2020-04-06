package com.vklikesapp.nat.vklikesapp.likes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.vklibrary.model.Profile;
import com.bumptech.glide.Glide;
import com.vklikesapp.nat.vklikesapp.R;

import java.util.ArrayList;
import java.util.List;


public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Listener{
        void clicked(int ownerId, int postId);
    }

    private Context context;
    private List<Profile> profiles;
    private Listener listener;
    private String url;

    public UsersAdapter(Context context, List<Profile> profiles, String url) {
        this.context = context;
        this.profiles = profiles;
        this.url = url;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(parent);
    }

    public void updateAdapter(List<Profile> profiles){
        if (profiles == null) profiles = new ArrayList<>();

        List<Profile> profilesNew = new ArrayList<>();
        profilesNew.addAll(profiles);

        for (Profile profile : profiles){
            if (profile.getCropProfile() == null || profile.getCropProfile().getPhoto() == null){
                profilesNew.remove(profile);
            }
        }

        this.profiles = profilesNew;

        notifyDataSetChanged();
    }


    public void removeItem(int id){
        List<Profile> profilesNew = new ArrayList<>();

        for (Profile profile : profiles){
            if (profile.getId() != id){
                profilesNew.add(profile);
            }
        }
        profiles.clear();
        profiles.addAll(profilesNew);
        notifyDataSetChanged();
    }

    public boolean isEmpty(){
        return profiles.isEmpty();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof UserHolder) {
            UserHolder userHolder = (UserHolder) holder;

            final Profile profile = profiles.get(position);

            View.OnClickListener onClickListener = view -> {
                if (!TextUtils.isEmpty(this.url)) {
                    if (listener != null)
                        listener.clicked(profile.getCropProfile().getPhoto().getOwnerId(), Integer.parseInt(profile.getPhotoForLike()));
                    String url = this.url + profile.getId();

                    url += "?z=photo" + profile.getCropProfile().getPhoto().getOwnerId() + "_" + profile.getPhotoForLike();
                    Log.e("GGGG", url);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                    profiles.remove(profile);
                    notifyDataSetChanged();
                }
            };

            userHolder.itemView.setOnClickListener(onClickListener);
            userHolder.button.setOnClickListener(onClickListener);

            if (profile.getCropProfile() != null && profile.getCropProfile().getPhoto() != null) {
                Glide.with(context.getApplicationContext()).load(profile.getCropProfile().getPhoto().getPhoto130())
                        .into(userHolder.ava);
            }

            userHolder.name.setText(profile.getFirstName());
        }
    }

    @Override
    public int getItemCount() {
        if (profiles != null) {
            return profiles.size();
        } else {
            return 0;
        }
    }

    protected class UserHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected ImageView ava;
        protected TextView button;

        public UserHolder(ViewGroup view) {
            super(LayoutInflater.from(view.getContext()).inflate(R.layout.users_adapter, view, false));
            name = itemView.findViewById(R.id.name);
            ava = itemView.findViewById(R.id.ava);
            button = itemView.findViewById(R.id.like_button);
        }
    }

}
