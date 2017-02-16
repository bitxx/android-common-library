package com.wj.library.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wj.library.util.LogUtil;

import java.util.Stack;

/**
 * activity管理
 * Created by wuj on 2016/6/1.
 * @version 1.0
 */
public class MyBaseAppManager {
    private static final String TAG = MyBaseAppManager.class.getName();

    private static Stack<AppCompatActivity> activityStack;
    private static MyBaseAppManager instance=null;

    /**
     * 双重锁定，避免多线程使用造成异常
     * @return
     */
    public static MyBaseAppManager getInstance(){
        if(instance==null){
            synchronized(MyBaseAppManager.class){
                if(null==instance){
                    instance=new MyBaseAppManager();
                }
            }
        }
        return instance;
    }

    /**
     *  获取指定的activity
     * @param cls
     * @return
     */
    public static AppCompatActivity getActivity(Class<?> cls){
        if(activityStack!=null)
            for(AppCompatActivity activity:activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 添加activity到堆栈	 */
    public void addActivity(AppCompatActivity activity){
        if(activityStack==null){
            activityStack=new Stack<>();
        }
        activityStack.add(activity);
    }
    /**
     * 获取当前activity（堆栈中最后一个压入）
     */
    public AppCompatActivity currentActivity(){
        AppCompatActivity activity=activityStack.lastElement();
        return activity;
    }
    /**
     * 结束当前activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        AppCompatActivity activity=activityStack.lastElement();
        finishActivity(activity);
    }
    /**
     * 结束指定的acitvity
     */
    public void finishActivity(AppCompatActivity activity){
        if(activity!=null&&activity.isFinishing()==false){
            activityStack.remove(activity);
            activity.finish();
            activity=null;
        }
    }

    /**
     * 某activity是否在最顶层
     * @param context
     * @param tag
     * @return
     */
    public static boolean isTopActivity(Context context,String tag)
    {
        boolean isTop = false;
        ActivityManager am = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.e(TAG,cn.getClassName()+" "+tag);
        if (cn.getClassName().contains(tag))
        {
            isTop = true;
        }
        return isTop;
    }

    /**
     * 结束指定类名的activity
     * 用for循环 而不用activity:activityStack,否则第一次使用可能会报错
     */
    public void finishActivity(Class<?> cls){

        if(activityStack==null||activityStack.size()==0)
            return;

        for(int i=0;i<activityStack.size();i++)
            if(activityStack.get(i).getClass().equals(cls) ){
                finishActivity(activityStack.get(i));
                break;
            }
    }
    /**
     * 结束所有的Activity
     */
    public void finishAllActivity(){
        LogUtil.e(TAG,activityStack.size()+"");
        for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {	}
    }


}
