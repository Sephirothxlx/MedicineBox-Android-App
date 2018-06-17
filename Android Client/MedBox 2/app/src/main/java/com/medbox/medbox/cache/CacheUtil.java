package com.medbox.medbox.cache;

/**
 * Created by Sephiroth on 17/3/7.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Environment;

public class CacheUtil {

    private static File CacheRoot;

    public static void writeJson(Context c, String json, String fileName,
                                 boolean append) {

        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            File ff = new File(CacheRoot, fileName);
            boolean boo = ff.exists();
            fos = new FileOutputStream(ff, append);
            os = new ObjectOutputStream(fos);
            if (append && boo) {
                FileChannel fc = fos.getChannel();
                fc.truncate(fc.position() - 4);
            }
            os.writeObject(json);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<String> readNotificationsAll(Context c, String fileName) {

        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<String> result = new ArrayList<String>();
        File des = new File(CacheRoot, fileName);
        try {
            fis = new FileInputStream(des);
            ois = new ObjectInputStream(fis);
            while (fis.available() > 0) {
                String temp = (String) ois.readObject();
                int i = temp.lastIndexOf('&');
                result.add(temp.substring(0, i));
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static List<String> readNotificationsByTime(Context c, String fileName, int difference) {
        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<String> result = new ArrayList<String>();
        File des = new File(CacheRoot, fileName);
        try {
            fis = new FileInputStream(des);
            ois = new ObjectInputStream(fis);
            while (fis.available() > 0) {
                String temp = (String) ois.readObject();
                int i = temp.lastIndexOf('&');
                String time = temp.substring(i, temp.length());
                DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date nDate = dateFormat2.parse(time);
                Date date = new Date();
                if ((date.getTime() - nDate.getTime()) / (1000 * 60 * 60) <= difference)
                    result.add(temp.substring(0, i));
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {

        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (ois != null) {
            try {
                ois.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    public String readAccount(Context c){
        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        String result = "";
        File des = new File(CacheRoot, "account");
        try {
            fis = new FileInputStream(des);
            ois = new ObjectInputStream(fis);
            while (fis.available() > 0) {
                result = (String) ois.readObject();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public void clear(Context c,String fileName){
        CacheRoot = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ? c
                .getExternalCacheDir() : c.getCacheDir();
        File des = new File(CacheRoot, fileName);
        des.delete();
    }
}