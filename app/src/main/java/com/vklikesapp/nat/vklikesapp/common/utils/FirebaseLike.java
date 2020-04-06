package com.vklikesapp.nat.vklikesapp.common.utils;

import android.content.Context;
import android.util.Log;

import com.bs.vklibrary.model.CurrentUser;
import com.bs.vklibrary.model.Profile;
import com.bs.vklibrary.model.request.UsersGetRequestModel;
import com.bs.vklibrary.net.LikesApi;
import com.bs.vklibrary.net.NetworkManager;
import com.bs.vklibrary.net.RestClient;
import com.bs.vklibrary.net.UsersApi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sergey on 25/01/18.
 */

public class FirebaseLike {
    private static int MAX_LIKES = 100;
    private static final int DELAY_TIME = 250;
    private static final int DELAY_BEETWIN_LIKES = 1000;


    public interface Listener{
        void orderLikeCount(long count);
        void profiles(List<Profile> profiles);
    }

    List<Profile> profiles = new ArrayList<>();

    NetworkManager networkManager;

    com.bs.vklibrary.SharedPreferencesManager sharedPreferencesManager;

    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    LikesApi likesApi;
    UsersApi usersApi;

    public FirebaseLike(Context context) {
        networkManager = new NetworkManager(context);
        sharedPreferencesManager = new com.bs.vklibrary.SharedPreferencesManager(context);
        RestClient mRestClient = new RestClient();
        likesApi = mRestClient.createService(LikesApi.class);
        usersApi = mRestClient.createService(UsersApi.class);
    }


    public void loadOtherLikesAndUsers() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Long> usersForLike = new HashMap<>();
                HashMap<String, Long> usersNotLiked = new HashMap<>();
                HashMap<String, Long> initialUsers = new HashMap<>();

                long currentProfileCount = 0;
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    HashMap<String, Long> dataFromSrever = (HashMap<String, Long>) singleSnapshot.getValue();
                    Long count  = dataFromSrever.get("count");
                    Long photo  = dataFromSrever.get("photo");
                    String id = singleSnapshot.getKey();

                    initialUsers.put(id, photo);

                    if (id.equals(String.valueOf(CurrentUser.userId))) {
                        currentProfileCount = count;
                        usersNotLiked.put(id, count);
                    }
                    else {
                        usersForLike.put(id, count);
                    }
                }

                //чистим от уже лайканых
                HashMap<Integer, Integer> integerIntegerHashMap = sharedPreferencesManager.getStoredLikesId();
                if (integerIntegerHashMap != null){
                    for (int id : integerIntegerHashMap.keySet()){
                        if (usersForLike.containsKey(String.valueOf(id))){
                            usersNotLiked.put(String.valueOf(id), usersForLike.get(String.valueOf(id)));
                            usersForLike.remove(String.valueOf(id));
                        }
                    }
                }

                int maxCount = usersForLike.size() >= MAX_LIKES ? MAX_LIKES : usersForLike.size();
                int i = 0;

                ArrayList<String> listForRemove = new ArrayList<>();
                ArrayList<String> users = new ArrayList<>();

                ArrayList<String> shuffleUsersForLike = new ArrayList<>();
                shuffleUsersForLike.addAll(usersForLike.keySet());
                Collections.shuffle(shuffleUsersForLike);

                for (String id : shuffleUsersForLike){
                    usersForLike.put(id, usersForLike.get(id) - 1);
                    users.add(id);

                    if(usersForLike.get(id).equals(0L)){
                        listForRemove.add(id);
                    }

                    if (++i >= maxCount){
                        break;
                    }
                }

                for (String id : listForRemove){
                    usersForLike.remove(id);
                }

                if (listener != null){
                    listener.orderLikeCount(currentProfileCount);
                }

                for (String id : usersNotLiked.keySet()){
                    usersForLike.put(id, usersNotLiked.get(id));
                }


                //final check
                HashMap<String, Long> finalUsers = new HashMap<>();
                for (String id : users){
                    finalUsers.put(id, initialUsers.get(id));
                }

                loadProfiles(users, finalUsers);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                int e = 0;
                e++;
            }
        });
    }

    private void loadProfiles(ArrayList<String> userIds, HashMap<String, Long> usersHashMap){
        networkManager
                .getNetworkObservable()
                .flatMap(aBoolean ->
                        usersApi.get(new UsersGetRequestModel(userIds, "crop_photo").toMap())
                                .doOnNext(listFull -> setLikesToUsers(listFull.response, usersHashMap))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileAva -> {
                }, error -> error.printStackTrace());

    }

    private void setLikesToUsers(List<Profile> profiles, HashMap<String, Long> usersHashMap) {
        for (Profile profile : profiles){
            long value = usersHashMap.get(String.valueOf(profile.getId()));
            String photo = String.valueOf(value);
            profile.setPhotoForLike(photo);
        }

        this.profiles = profiles;
        sleep(DELAY_TIME);

//        for (Profile profile : profiles){
//            if (profile.getCropProfile() == null || profile.getCropProfile().getPhoto() == null){
//                removeId(profile.getId());
//            }
//        }

        if (listener != null){
            listener.profiles(profiles);
        }
//        if (profiles != null && profiles.size() > 0) {
//            addLikes(profiles.get(0));
//        }
    }


    private void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //ACHTUNG!!!!!!!!!!!!!!!!!! ЧИСТИТ БАЗУ
    public void removeId(int ownerId){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("users").child(String.valueOf(ownerId));


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Long count = null;
                if (dataSnapshot.getValue() != null){
//                    count = dataSnapshot.getValue(Long.class) - 1;
//                    if (count == 0){
//
//                    }
                    count = null;
                }

                Log.e("likeDELETE","" + ownerId);

                ref.setValue(count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR","Произошла ошибка");
            }
        });
    }
}
