package com.vklikesapp.nat.vklikesapp.analitycs;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.LoginEvent;

/**
 *
 * Created by d.melehov on 03.12.16.
 *
 */

public class Analitycs {

    public final static String OPEN_INFO_SOURCE_MUTUAL_FRIEND = "mutual_friend";
    public final static String OPEN_INFO_SOURCE_LIKE_COUNTER = "like_counter";
    public final static String OPEN_INFO_SOURCE_WHO_REMOVED = "who_removed";

    /**
     * Диалог подписаться на группу в вконтакте
     */
    public static void onEventJoinGroup(String action) {
        Answers.getInstance().logCustom(new CustomEvent("join_group_vk")
                .putCustomAttribute("action", action));
    }

    /**
     * Поставил лайк
     */
    public static void onEventLikeToUser() {
        Answers.getInstance().logCustom(new CustomEvent("LikeDone"));
    }

    /**
     * Поставил лайк
     */
    public static void onEventJoinGroup(int id) {
        Answers.getInstance().logCustom(new CustomEvent("added_to_group")
                .putCustomAttribute("action", id));
    }

    /**
     * open information
     */
    public static void onEventOpenInformation(String source) {
        Answers.getInstance().logCustom(new CustomEvent("open_information")
        .putCustomAttribute("source", source));
    }

    /**
     * Log out
     */
    public static void onEventLogOut() {
        Answers.getInstance().logCustom(new CustomEvent("Log out"));
    }

    /**
     * Log in
     */
    public static void onEventLogIn() {
        Answers.getInstance().logLogin(new LoginEvent());
    }

    /**
     * Общие друзья
     */
    public static void onEventOpenScreenFromNavDrawer(String title) {
        Answers.getInstance().logCustom(new CustomEvent("StartScreenNavD")
                .putCustomAttribute("Screen", title));
    }

    /**
     * Information
     */
    public static void onEventInforamtion(String title) {
        Answers.getInstance().logCustom(new CustomEvent("Information")
                .putCustomAttribute("Event", title));
    }

    /**
     * Rate Dialog
     */
    public static void onEventRateDialog(String title) {
        Answers.getInstance().logCustom(new CustomEvent("Rate Dialog")
                .putCustomAttribute("Event", title));
    }

}
