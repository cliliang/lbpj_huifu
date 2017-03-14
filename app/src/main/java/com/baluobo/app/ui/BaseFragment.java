package com.baluobo.app.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baluobo.R;
import com.baluobo.app.AppContext;
import com.baluobo.app.flux.AppActionsCreator;
import com.baluobo.app.flux.Dispatcher;
import com.baluobo.common.db.LuoBoDBM;
import com.baluobo.user.model.User;

import java.util.Locale;
import java.util.TimerTask;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class BaseFragment extends Fragment {
    private final String FRAGMENT_SAVE_DATA = "fragment_save_data";

    public User mUser;
    public Dispatcher dispatcher;

    public AppContext appContext;
    public AppActionsCreator appActionsCreator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = (AppContext)getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUser = appContext.getUser();
    }

    public void setupView(View view){

    }

    public void updateUserToken(int uid, String oldToken, String newToken){
        if (TextUtils.isEmpty(newToken)){
            return;
        }
        if (oldToken.equals(newToken)){
            return;
        }
        mUser.setToken(newToken);
        appContext.setUser(mUser);
        LuoBoDBM.getInstance(getActivity()).updateUserToken(uid, newToken);
        appActionsCreator.refreshToken(uid, newToken);
    }

    protected void initDependencies(){
        if (dispatcher == null){
            dispatcher = Dispatcher.get();
        }
        appActionsCreator = AppActionsCreator.getInstance(dispatcher, appContext);
    }

    Bundle savedState;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Restore State Here
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched();
        }
    }

    protected void onFirstTimeLaunched() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save State Here
        saveStateToArguments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Save State Here
        saveStateToArguments();
    }

    ////////////////////
    // Don't Touch !!
    ////////////////////

    private void saveStateToArguments() {
        if (getView() != null)
            savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            if (b != null){
                b.putBundle(FRAGMENT_SAVE_DATA, savedState);
            }
        }
    }

    ////////////////////
    // Don't Touch !!
    ////////////////////

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        if (b != null){
            savedState = b.getBundle(FRAGMENT_SAVE_DATA);
            if (savedState != null) {
                restoreState();
                return true;
            }
        }
        return false;
    }

    /////////////////////////////////
    // Restore Instance State Here
    /////////////////////////////////

    private void restoreState() {
        if (savedState != null) {
            // For Example
            //tv1.setText(savedState.getString(text));
            onRestoreState(savedState);
        }
    }

    protected void onRestoreState(Bundle savedInstanceState) {

    }

    //////////////////////////////
    // Save Instance State Here
    //////////////////////////////

    private Bundle saveState() {
        Bundle state = new Bundle();
        // For Example
        //state.putString(text, tv1.getText().toString());
        onSaveState(state);
        return state;
    }

    protected void onSaveState(Bundle outState) {

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
