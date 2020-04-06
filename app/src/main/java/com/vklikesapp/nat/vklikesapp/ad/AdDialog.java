package com.vklikesapp.nat.vklikesapp.ad;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vklikesapp.nat.vklikesapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by john on 5/11/18.
 */

public class AdDialog extends BottomSheetDialogFragment {

    private class AdModel{
        public String description;
        public String image;
        public String link;
        public String title;
    }

    private LinearLayout mainView;
    private ProgressBar progressBar;

    public AdDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View child = getActivity().getLayoutInflater().inflate(R.layout.ad_layout, container, false);
        mainView = child.findViewById(R.id.main_container);
        progressBar = child.findViewById(R.id.progressBar);
        loadOtherLikesAndUsers();

        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            View bottomSheetInternal = d.findViewById(android.support.design.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        return child;
    }


    public void loadOtherLikesAndUsers() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("apps");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<AdModel> adModels = new ArrayList<>();

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> stringHashMap = (HashMap<String, String>) singleSnapshot.getValue();
                    if (stringHashMap != null) {
                        AdModel adModel = new AdModel();
                        adModel.description = stringHashMap.get("description");
                        adModel.image = stringHashMap.get("image");
                        adModel.link = stringHashMap.get("link");
                        adModel.title = stringHashMap.get("title");
                        adModels.add(adModel);
                    }
                }
                draw(adModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void downloadImage(ImageView imageView, String url){
        Glide.with(getActivity()).load(url)
                .into(imageView);
    }

    public void draw(ArrayList<AdModel> adModels){
        if (adModels == null) return;
        if (mainView == null) return;

        progressBar.setVisibility(View.GONE);
        mainView.removeAllViews();
        for (AdModel adModel : adModels){

            Activity activity = getActivity();
            if (activity == null){
                break;
            }

            View child = activity.getLayoutInflater().inflate(R.layout.ad_card, null);
            mainView.addView(child);
            TextView titleView = child.findViewById(R.id.ad_title);
            titleView.setText(adModel.title);
            TextView textViewDescription = child.findViewById(R.id.ad_description);
            textViewDescription.setText(adModel.description);
            ImageView imageView = child.findViewById(R.id.ad_image);
            downloadImage(imageView, adModel.image);
            child.setOnClickListener(view -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(adModel.link));
                startActivity(browserIntent);
            });
        }
    }

}
