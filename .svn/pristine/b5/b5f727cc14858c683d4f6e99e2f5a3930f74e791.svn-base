package com.baluobo.home.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.baluobo.R;
import com.baluobo.app.flux.Store;
import com.baluobo.app.router.MainRouter;
import com.baluobo.app.ui.BaseStatusBarActivity;
import com.baluobo.common.config.AppConfig;
import com.baluobo.common.module.ModuleID;
import com.baluobo.common.tools.FileUtils;
import com.baluobo.home.actions.LoadPicAction;
import com.baluobo.home.model.Banner;
import com.baluobo.home.router.HomeRouter;
import com.baluobo.home.router.HomeUI;
import com.baluobo.home.stores.LoadPicStore;
import com.baluobo.user.actions.SecurityAction;
import com.baluobo.user.model.User;
import com.baluobo.user.router.UserUI;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * desc: App的开机界面
 * Created by:chenliliang
 * Created on:16/5/4.
 */
public class StartActivity extends BaseStatusBarActivity {
    private Handler handler = new Handler();
    private ImageView imageView;
    private LoadPicStore store;
    private boolean downloadPic = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_layout);
        initDependencies();
        setupViews();
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        store = LoadPicStore.getInstance();
        dispatcher.register(store);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        imageView = (ImageView) findViewById(R.id.start_activity_pic);
        File picFileDir = FileUtils.getPicFile(appContext);
        if (picFileDir != null && picFileDir.isDirectory()){
            File[] fileList = picFileDir.listFiles();
            if (fileList != null && fileList.length > 0){
                List<Integer> fileNumberList = new ArrayList<>();
                for (int i = 0; i < fileList.length; i++){
                    File file = fileList[i];
                    String picFileName = file.getName();
                    String[] sepString = picFileName.split(FileUtils.LOAD_PIC_SEP);
                    if (sepString != null && sepString.length > 0){
                        String numString = sepString[sepString.length - 1];
                        try {
                            int number = Integer.parseInt(numString);
                            fileNumberList.add(number);
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                        }
                    }
                }

                if (fileNumberList != null && fileNumberList.size() > 0){
                    int max = 0;
                    for (int number : fileNumberList){
                        if (number > max){
                            max = number;
                        }
                    }

                    for (File picFile : fileList){
                        String picFileName = picFile.getName();
                        if (picFileName.endsWith(String.valueOf(max))){
                            String[] fileSep = picFileName.split(FileUtils.LOAD_PIC_SEP);
                            if (fileSep.length == 4){
                                String startTimeS = fileSep[0];
                                String endTimeS = fileSep[1];
                                try {
                                    long startTime = Long.parseLong(startTimeS);
                                    long endTime = Long.parseLong(endTimeS);
                                    long nowTime = Calendar.getInstance().getTimeInMillis();
                                    if (nowTime > startTime && nowTime < endTime){
                                        String md5 = fileSep[2];
                                        String fileMD5 = FileUtils.getFileMD5(picFile);
                                        if (md5.equals(fileMD5)){
                                            Bitmap bitmap = BitmapFactory.decodeFile(picFile.getPath());
                                            if (bitmap != null){
                                                imageView.setImageBitmap(bitmap);
                                            }
                                        }
                                    }
                                }catch (NumberFormatException e){
                                    e.printStackTrace();
                                }

                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private void deletePicFile(){
        File picFileDir = FileUtils.getPicFile(appContext);
        FileUtils.deleteFile(picFileDir);
    }

    @Override
    protected void onResume() {
        super.onResume();
        store.register(this);
        handler.postDelayed(runnable, 3000);
        appActionsCreator.checkLoadPic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        store.unregister(this);
    }

    @Subscribe
    public void onLoadPicStoreChange(Store.StoreChangeEvent event) {
        String type = event.getType();
        switch (type){
            case LoadPicAction.LOAD_PIC_ACTION_SUCCESS:
                Banner picInfo = store.getLoadPicInfo();
                if (picInfo.getActivityId() == 0){
                    deletePicFile();
                    return;
                }
                String fileName = FileUtils.getLoadPicName(picInfo.getStartTimeStamp(), picInfo.getEndTimeStamp(), picInfo.getImgMd5(), picInfo.getActivityId());
                File picFile = FileUtils.getLoadPic(appContext, fileName);
                if (picFile != null && picFile.exists() && picFile.isFile()){
                    String md5 = FileUtils.getFileMD5(picFile);
                    String fileMd5 = picInfo.getImgMd5();
                    if (!TextUtils.isEmpty(md5) && !TextUtils.isEmpty(fileMd5) && md5.equals(fileMd5)){

                    }else {
                        if (FileUtils.deleteNullFile(FileUtils.FILE_TYPE_LOAD_PIC, appContext, fileName)){
                            deletePicFile();
                            downloadLoadPic(picInfo.getActivityPic(), fileName);
                        }
                    }
                }else {
                    deletePicFile();
                    downloadLoadPic(picInfo.getActivityPic(), fileName);
                }
                break;
            case LoadPicAction.DOWNLOAD_LOAD_PIC_FINISH:
                downloadPic = false;
                break;
        }
    }

    private void downloadLoadPic(String filePath, String fileName){
        if (downloadPic){
            return;
        }
        downloadPic = true;
        appActionsCreator.downloadLoadPic(appContext, filePath, fileName);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            showGuide();
        }
    };

    private void showGuide(){
        if (AppConfig.NEW_VERSION_SHOW_GUIDE){
            AppConfig appConfig = AppConfig.getInstance(this);
            if (appConfig.hadShowGuide()){
                showPage(mUser);
            }else {
                HomeRouter.getInstance(this).showActivity(HomeUI.GuideActivity);
            }
        }else {
            showPage(mUser);
        }
        finish();
    }

    private void showPage(User user){
        if (user != null && !TextUtils.isEmpty(user.getHandPassword())){
            Bundle bundle = new Bundle();
            bundle.putString(SecurityAction.SECURITY_GESTURE_VERIFY_ACTION, SecurityAction.GESTURE_VERIFY_ACTION_OPEN);
            MainRouter.getInstance(this).showActivity(ModuleID.USER_MODULE_ID, UserUI.GestureVerify, bundle);
        }else {
            HomeRouter.getInstance(this).showActivity(HomeUI.MainActivity);
        }
    }

    @Override
    protected void onDestroy() {
        dispatcher.unregister(store);
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
