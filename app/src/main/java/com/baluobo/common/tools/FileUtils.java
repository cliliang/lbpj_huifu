package com.baluobo.common.tools;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class FileUtils {
    public static String APK_DIR = "apk_file";
    public static String LOAD_PIC = "load_pic";
    public static String LOAD_PIC_SEP = "_";
    public static int FILE_TYPE_APK = 1;
    public static int FILE_TYPE_LOAD_PIC = 2;
    public static boolean createDir(Context context, String path) {
        if (path == null || path.length() == 0 || context == null) {
            return false;
        }
        File dir = new File(context.getFilesDir().getPath() + "/" + path);
        if (dir.exists() && dir.isDirectory()) {
            return true;
        } else {
            return dir.mkdirs();
        }
    }
    public static String getStorePath(Context context){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            File file = context.getExternalFilesDir(null);
            if (file != null){
                return file.getPath();
            }
        }
        return context.getFilesDir().getPath();
    }
    public static File getApkFile(Context context, String fileName){
        return new File(getStorePath(context) + "/" + APK_DIR + "/" + fileName);
    }

    public static File getLoadPic(Context context, String fileName){
        return new File(getStorePath(context) + "/" + LOAD_PIC + "/" + fileName);
    }

    public static File getPicFile(Context context){
        return new File(getStorePath(context) + "/" + LOAD_PIC);
    }

    public static String getLoadPicName(long startTime, long endTime, String md5, int id){
        return startTime + LOAD_PIC_SEP + endTime + LOAD_PIC_SEP + md5 + LOAD_PIC_SEP + id;
    }

    public static boolean deleteNullFile(int type, Context context, String fileName){
        Log.i("chen", "delete error package");
        boolean deleteSuccess = false;
        File file = null;
        if (type == FILE_TYPE_APK){
            file = getApkFile(context, fileName);
        }else if (type == FILE_TYPE_LOAD_PIC){
            file = getLoadPic(context, fileName);
        }
        if (file != null && file.exists() && file.isFile()){
            deleteSuccess = file.delete();
        }
        return deleteSuccess;
    }

    public static void writeFile(int type, Context context, InputStream inputStream, String fileName){
        if (context == null || inputStream == null || fileName == null){
            return;
        }
        File file = null;
        if (type == FILE_TYPE_APK){
            file = getApkFile(context, fileName);
        }else if (type == FILE_TYPE_LOAD_PIC){
            file = getLoadPic(context, fileName);
        }
        try {
            if (file != null && !file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buff = new byte[100];
            int rc;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                outputStream.write(buff, 0, rc);
            }
            inputStream.close();
            outputStream.close();
            return;
        }catch (IOException e){
            Log.e("chen","write wrong message:" + e.getMessage());
        }
        return;
    }

    public static String writeFile(Context context, byte[] data, String dir, String fileName) {
        if (context == null || data == null || dir == null) {
            return null;
        }
        if (fileName == null) {
            String randomName = String.valueOf((int) (Math.random() * 100000));
            int difference = 6 - randomName.length();
            for (int i = 0; i < difference; i++) {
                randomName = "0" + randomName;
            }
            fileName = System.currentTimeMillis() + randomName + ".png";
        }
        File file = new File(context.getFilesDir().getPath() + "/" + dir + "/" + fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file);
            output.write(data, 0, data.length);
            output.flush();
            output.close();
        } catch (IOException e) {
            Log.e(FileUtils.class.getName(), "写入文件失败 - " + file.getPath() + " error:" + e.getMessage());
        }
        return file.getPath();
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 删除文件夹及其下所有文件
     * @param file
     */
    public static void deleteFile(File file){
        if (file == null){
            return;
        }
        if(file.isFile()){//表示该文件不是文件夹
            file.delete();
        }else{
            File[] childFiles = file.listFiles();
            if (childFiles != null && childFiles.length > 0){
                for (File childFile : childFiles){
                    childFile.delete();
                }
            }
            file.delete();
        }
    }
}