package com.vklikesapp.nat.vklikesapp.likes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vklikesapp.nat.vklikesapp.R;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.rest.model.response.PhotosResponse;

import java.util.List;


public class SelectPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface Listener{
        void clicked(int ownerId, int postId);
    }

    private Context context;
    private List<PhotosResponse.Item> itemList;
    private SharedPreferencesManager sharedPreferencesManager;
    private Listener listener;

    int checkPosition;

    public SelectPhotoAdapter(Context context, List<PhotosResponse.Item> profiles, SharedPreferencesManager sharedPreferencesManager) {
        this.context = context;
        this.itemList = profiles;
        this.sharedPreferencesManager = sharedPreferencesManager;
        checkPosition = sharedPreferencesManager.getPhotoId();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof UserHolder) {
            UserHolder userHolder = (UserHolder) holder;

            final PhotosResponse.Item item = itemList.get(position);
            userHolder.itemView.setOnClickListener(view -> {
                checkPosition = item.id;
                sharedPreferencesManager.setPhotoId(item.id);
                notifyDataSetChanged();
            });

            Glide.with(context.getApplicationContext()).load(item.photo604)
                    .into(userHolder.ava);

            userHolder.check.setVisibility(checkPosition == item.id ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (itemList != null) {
            return itemList.size();
        } else {
            return 0;
        }
    }

    protected class UserHolder extends RecyclerView.ViewHolder {
        protected ImageView ava;
        protected ImageView check;

        public UserHolder(ViewGroup view) {
            super(LayoutInflater.from(view.getContext()).inflate(R.layout.select_photo_adapter, view, false));
            ava = itemView.findViewById(R.id.ava);
            check = itemView.findViewById(R.id.check);
        }
    }

}
