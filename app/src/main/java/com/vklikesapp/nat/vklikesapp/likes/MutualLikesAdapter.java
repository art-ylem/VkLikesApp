package com.vklikesapp.nat.vklikesapp.likes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vklikesapp.nat.vklikesapp.R;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.mvp.view.LikesMutualView;

import java.util.ArrayList;
import java.util.List;


public class MutualLikesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LikesMutualView likesMutualView;
    private List<String> profiles;
    private int likesAmount;

    public MutualLikesAdapter(Context context, LikesMutualView likesMutualView, SharedPreferencesManager sharedPreferencesManager) {
        this.context = context;
        this.likesMutualView = likesMutualView;

        profiles = new ArrayList<>();
        profiles.add("ad");

        if (sharedPreferencesManager.getPaymentable() == 1) {
            profiles.add("pay");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof UserHolder) {
            UserHolder userHolder = (UserHolder) holder;


            switch (profiles.get(position)) {
                case "status": {
                    userHolder.title.setText("Статус");
                    userHolder.likes.setText("До завершения заказа осталось: " + likesAmount + " лайков");
                    userHolder.relativeLayout.setVisibility(View.GONE);
                    userHolder.divider.setVisibility(View.GONE);
                }
                break;
                case "free": {
                    userHolder.title.setText("Бесплатные лайки");
                    userHolder.likes.setText("Вы получите 1 балл");
                    userHolder.icon.setImageResource(R.drawable.ic_favorite);
                    userHolder.relativeLayout.setOnClickListener(view ->{
                        likesMutualView.likesOrder(1);
                        likesMutualView.hideFree();
                    });
                }
                break;
                case "ad": {
                    userHolder.title.setText("Заработать баллы");
                    userHolder.likes.setText("Вы получите 1 балл");
                    userHolder.icon.setImageResource(R.drawable.ic_camera_roll);
                    userHolder.relativeLayout.setOnClickListener(view -> {
                        if (likesMutualView != null){
                            likesMutualView.loadRewardedVideoAd();
                        }
                    });
                }
                break;
                case "pay": {
                    userHolder.title.setText("Купить баллы");
                    userHolder.likes.setText("От 1 до 100 баллов");
                    userHolder.icon.setImageResource(R.drawable.ic_add_to_queue_black);
                    userHolder.relativeLayout.setOnClickListener(view -> {
                        PayActivity.startActivity(view.getContext());
                    });
                }
                break;
            }

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

    public void updateStatus(int likesAmount){
        this.likesAmount = likesAmount;
        if (!profiles.contains("status")){
            profiles.add(0, "status");
        }
        notifyDataSetChanged();
    }

    public void updateFree(){
        profiles.add(0, "free");
        notifyDataSetChanged();
    }

    public void hideFree(){
        if (profiles.contains("free")){
            profiles.remove("free");
        }
        notifyDataSetChanged();
    }

    protected class UserHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView likes;
        protected ImageView icon;
        protected View divider;
        protected RelativeLayout relativeLayout;

        public UserHolder(ViewGroup view) {
            super(LayoutInflater.from(view.getContext()).inflate(R.layout.like_mutual_adapter, view, false));
            title = (TextView) itemView.findViewById(R.id.title);
            likes = (TextView) itemView.findViewById(R.id.likes);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            divider = itemView.findViewById(R.id.divider);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.buttonPanel);
        }
    }

}
