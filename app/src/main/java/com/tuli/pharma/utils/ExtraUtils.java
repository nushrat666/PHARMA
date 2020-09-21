package com.tuli.pharma.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraUtils
{

    /**
     * reload page
     * @param activity current activity
     * @param refreshLayout refresh view object
     * @param nameOfClass name of start activity
     */
    public static void RELOAD_PAGE(Activity activity, final SwipeRefreshLayout refreshLayout, final Class<?> nameOfClass) {
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);

            (new Handler()).postDelayed(() -> {
                refreshLayout.setRefreshing(false);
                activity.startActivity(new Intent(activity, nameOfClass));
                activity.finish();
            }, 2500);
        });
    }

    /**
     * Check internet connection is enable or disable
     * @param context , application context
     * @return , true if network enable otherwise false
     */
    public static boolean isOnline(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null)
                return networkInfo.getState() == NetworkInfo.State.CONNECTED;

        }
        return false;
    }

    /**
     * Convert html text to plain text
     * @param text, html text
     * @return , plain text
     */
    public static Spanned HTML_TO_PLAIN_TEXT(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }

    public static void SHOW_KEYBOARD(EditText mEtSearch, Context context) {
        mEtSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void HIDE_KEYBOARD(EditText mEtSearch, Context context) {
        mEtSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);
    }

    /**
     * Disable Soft keyboard from activity in order to show the custom keyboard
     * @param activity - activity
     */
    public static void DISABLE_SOFT_KEYBOARD(Activity activity) {
        // hide soft keyboard
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    /**
     * Check keyboard is open or not
     * If keyboard is open hide view will be hide otherwise not
     * @param context, application context
     * @param view, root layout
     * @param hideView, hide view
     */
    public static void chckKeyboard(final Context context, final View view, final View hideView)
    {
        view.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int heightDiff = view.getRootView().getHeight() - view.getHeight();
            if (heightDiff > dpToPx(context, 200)) { // if more than 200 dp, it's probably a keyboard...
                // ... do something here
                hideView.setVisibility(View.GONE);
            }else hideView.setVisibility(View.VISIBLE);
        });
    }

    /**
     * Check keyboard is open or not
     * @param context application context
     * @param view view
     * @return true or false
     */
    public static boolean CHECK_KEYBOARD_IS_OPEN(final Context context,View view)
    {
        AtomicBoolean isOpen = new AtomicBoolean(false);

        view.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int heightDiff = view.getRootView().getHeight() - view.getHeight();
            if (heightDiff > dpToPx(context, 200)) { // if more than 200 dp, it's probably a keyboard...
                // ... do something here
                isOpen.set(true);
            }else isOpen.set(false);
        });

        return isOpen.get();
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    /**
     * Parse 4 digit verification code
     *
     * @param message sms message
     * @return only four numbers from massage string
     */
    public static String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    /**
     * Open gallery to pick an image
     */
    public static void PICK_IMAGE_FROM_GALLERY(Activity activity, int GALLERY_REQUEST_CODE){
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        activity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }


    //close top all activity and go to specific activity
    public static void CLOSE_ACTIVITY(Activity context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        context.finish();
    }

    public static void BACK_FROM_ACTIVITY(Activity activity)
    {
        activity.finish();
    }
}
