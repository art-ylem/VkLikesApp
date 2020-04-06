package com.vklikesapp.nat.vklikesapp.likes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vklikesapp.nat.vklikesapp.R;
import com.vklikesapp.nat.vklikesapp.model.Profile;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class LikesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<Profile> profiles;

    public LikesAdapter(Context context, List<Profile> profiles) {
        this.context = context;
        this.profiles = profiles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(parent);
    }

    public void updateAdapter(List<Profile> profiles){
        this.profiles = profiles;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof UserHolder) {
            UserHolder userHolder = (UserHolder) holder;

            final Profile profile = profiles.get(position);

            userHolder.itemView.setOnClickListener(view -> {
                String url = "https://vk.com/id" + profile.getId();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            });

            Glide.with(context.getApplicationContext()).load(profile.getPhoto())
                    .into(userHolder.ava);

            switch (position) {
                case 0: {
                    userHolder.medal.setImageDrawable(context.getResources().getDrawable(R.drawable.first_medal));
                    userHolder.medal.setVisibility(View.VISIBLE);
                }
                break;
                case 1: {
                    userHolder.medal.setImageDrawable(context.getResources().getDrawable(R.drawable.second_medal));
                    userHolder.medal.setVisibility(View.VISIBLE);
                }
                break;
                case 2: {
                    userHolder.medal.setImageDrawable(context.getResources().getDrawable(R.drawable.third_medal));
                    userHolder.medal.setVisibility(View.VISIBLE);
                }
                break;
                default: {
                    userHolder.medal.setVisibility(View.GONE);
                }
            }

            userHolder.name.setText(profile.getLastName() + " " + profile.getFirstName());
            userHolder.number.setText(profile.getLikesForYou() + "");

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
        protected TextView number;
        protected CircleImageView ava;
        protected ImageView medal;

        public UserHolder(ViewGroup view) {
            super(LayoutInflater.from(view.getContext()).inflate(R.layout.like_adapter, view, false));
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
            ava = (CircleImageView) itemView.findViewById(R.id.ava);
            medal = (ImageView) itemView.findViewById(R.id.medal);
        }
    }

}
