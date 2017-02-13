package com.wj.demo.ui.activity.hongbao;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.wj.library.util.LogUtil;

import java.util.List;

/**
 * Created by idea_wj on 2017/2/13.
 */

public class HongbaoService extends AccessibilityService {

    private String[] filter = new String[]{"查看红包","领取红包"};

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("HELLO","HELLO");
        return super.onStartCommand(intent, flags, startId);

    }

    //监听窗口变化的回调
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo info = event.getSource(); //树形结构
        if(info == null){
            return;
        }
        startClick(info);
    }

    private void startClick(AccessibilityNodeInfo info) {
        List<AccessibilityNodeInfo> list = findByText(info);
        if(list == null)
            return;
        AccessibilityNodeInfo nodeInfo = list.get(list.size()-1);  //最后一个红包
        if("已拆开".equals(nodeInfo.getText())){
            return;
        }
        boolean isChceked = nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK); //模拟点击,由父类点击
        if(!isChceked){
            reClick(info);
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        //可以用来配置accessibilityservice具体要监听的内容，也可以通过config.xml来进行配置
    }

    //拿子view实现点击
    private void reClick(AccessibilityNodeInfo info) {
        int childCount = info.getChildCount();
        if(childCount == 0){
            info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }else{
            for(int i=0;i<childCount;i++){
                AccessibilityNodeInfo nodeInfo = info.getChild(i);
                if(nodeInfo == null)
                    continue;
                if (nodeInfo.getChildCount() > 0) {
                    reClick(nodeInfo);
                }else {
                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }

    private List<AccessibilityNodeInfo> findByText(AccessibilityNodeInfo info) {
        for(String text:filter){
           List<AccessibilityNodeInfo> list =  info.findAccessibilityNodeInfosByText(text);
            if(list != null&&!list.isEmpty()){
                return list;

            }
        }
        return null;

    }

    //中断服务的回调
    @Override
    public void onInterrupt() {

    }
}
