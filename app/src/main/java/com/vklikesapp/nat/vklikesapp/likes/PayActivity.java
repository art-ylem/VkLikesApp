package com.vklikesapp.nat.vklikesapp.likes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vklikesapp.nat.vklikesapp.MyApplication;
import com.vklikesapp.nat.vklikesapp.R;
import com.vklikesapp.nat.vklikesapp.common.utils.SharedPreferencesManager;
import com.vklikesapp.nat.vklikesapp.model.Profile;
import com.vklikesapp.nat.vklikesapp.mvp.presenter.UsersPresenter;
import com.vklikesapp.nat.vklikesapp.mvp.view.UsersView;

import org.solovyev.android.checkout.ActivityCheckout;
import org.solovyev.android.checkout.BillingRequests;
import org.solovyev.android.checkout.Checkout;
import org.solovyev.android.checkout.EmptyRequestListener;
import org.solovyev.android.checkout.Inventory;
import org.solovyev.android.checkout.ProductTypes;
import org.solovyev.android.checkout.Purchase;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends MvpAppCompatActivity implements UsersView {

    @InjectPresenter
    UsersPresenter usersPresenter;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, PayActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        MyApplication.getApplicationComponent().inject(this);
        ButterKnife.bind(this);

        mCheckout.start();

        mCheckout.createPurchaseFlow(new PurchaseListener());

        mInventory = mCheckout.makeInventory();
        mInventory.load(Inventory.Request.create()
                        .loadAllPurchases()
                        .loadSkus(ProductTypes.IN_APP, "test1")
                        .loadSkus(ProductTypes.IN_APP, "100_pay")
                        .loadSkus(ProductTypes.IN_APP, "no_ad")
                , new InventoryCallback());
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3})
    public void onViewClicked(View view) {
        mCheckout.whenReady(new Checkout.EmptyListener() {
            @Override
            public void onReady(BillingRequests requests) {
                switch (view.getId()) {
                    case R.id.button1:
                        requests.purchase(ProductTypes.IN_APP, "test1", null, mCheckout.getPurchaseFlow());
                        break;
                    case R.id.button2:
                        requests.purchase(ProductTypes.IN_APP, "100_pay", null, mCheckout.getPurchaseFlow());
                        break;
                    case R.id.button3:
                        requests.purchase(ProductTypes.IN_APP, "no_ad", null, mCheckout.getPurchaseFlow());
                        break;
                }
            }
        });



    }

    @Override
    public void isLikeDone(int id) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void finishActivity() {

    }

    private class PurchaseListener extends EmptyRequestListener<Purchase> {
        @Override
        public void onSuccess(Purchase purchase) {
            if (purchase == null){
                //Toast.makeText(PayActivity.this, "null", Toast.LENGTH_SHORT).show();
                return;
            }

            //Toast.makeText(PayActivity.this, purchase.sku, Toast.LENGTH_SHORT).show();

            switch (purchase.sku){
                case "test1":
                    usersPresenter.likesOrder(30);
                    break;
                case "100_pay":
                    usersPresenter.likesOrder(100);
                    break;
                case "no_ad":
                    sharedPreferencesManager.setContainAd();
                    break;
            }
        }

        @Override
        public void onError(int response, Exception e) {
            // handle errors here
            Toast.makeText(PayActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private class InventoryCallback implements Inventory.Callback {
        @Override
        public void onLoaded(Inventory.Products products) {
            //Toast.makeText(PayActivity.this, products.size() + " Loaded", Toast.LENGTH_SHORT).show();
            // your code here
        }
    }

    private final ActivityCheckout mCheckout = Checkout.forActivity(this, MyApplication.get().getBilling());
    private Inventory mInventory;

    @Override
    protected void onDestroy() {
        mCheckout.stop();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCheckout.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 2){
            HashMap<String, Long> stringLongHashMap = new HashMap<>();

            Profile currentUser = sharedPreferencesManager.getUserProfile();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("users").child(String.valueOf(currentUser.getId()));
            stringLongHashMap.put("count", 5L);
            stringLongHashMap.put("photo", (long) sharedPreferencesManager.getPhotoId());
            myRef.setValue(stringLongHashMap);
        }
        return true;
    }
}
