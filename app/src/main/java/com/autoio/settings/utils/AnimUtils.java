package com.autoio.settings.utils;

import android.view.View;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.orhanobut.logger.Logger;

/**
 * Created by wuhongyun on 17-8-11.
 */

public class AnimUtils {

    /**
     * 折叠展开布局
     * @param expandableLayout 需要折叠展开的布局view
     * @param arrow 附带需要旋转的arrow
     */
    public static void toggleContent(ExpandableLayout expandableLayout, View arrow) {
        if (expandableLayout.isExpanded()){

            expandableLayout.collapse();
            //500ms
            arrowRight(arrow);
            Logger.i("arrowRight");

        }else{

            expandableLayout.expand();
            arrowDown(arrow);
            Logger.i("arrowDown");
        }

    }

    /**
     * 动画效果，箭头由向下到向右
     * @param view 需要做动画的view
     */
    private  static void arrowRight(View view) {
        view.animate().rotation(0).setDuration(500).start();
    }

    /**
     * 箭头向下
     * @param view 需要做动画的view
     */
    private static void arrowDown(View view) {

        view.animate().rotation(90).setDuration(500).start();
    }
}
