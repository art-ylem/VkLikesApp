package com.vklikesapp.nat.vklikesapp.common;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.vklikesapp.nat.vklikesapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sergey on 23/10/17.
 */

public class BaseAlertFragment extends DialogFragment {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.number_progress_bar)
    NumberProgressBar numberProgressBar;
    Unbinder unbinder1;

    public interface Listener {
        void onClickDialog();
    }

    Unbinder unbinder;


    private Listener listener;

    public static void show(FragmentManager fm) {
        BaseAlertFragment fragment = new BaseAlertFragment();
        fragment.show(fm, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alert_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        unbinder1 = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
