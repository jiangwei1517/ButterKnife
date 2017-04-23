package com.jiangwei.api;

import java.lang.reflect.Method;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * author: jiangwei18 on 17/4/19 11:26 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class BFProxy {
    private static final String PROXY = "$$BindId";
    private static final String PROXYF = "$$BindIdf";
    private static final String ID_VIEW_HOLDER_PACKAGE = "com.butterknife.luffy.bindid.";
    private static final String ID_VIEW_HOLDER_PACKAGEF = "com.butterknife.luffy.bindidf.";
    private static final String ID_ACTIVITY_PACKAGEF = "com.butterknife.luffy.bindid.";
    private static final String CLICK_ACTIVITY_PACKAGEF = "com.butterknife.luffy.bindclick.BindViewClick$$BindClick";
    private static final String VIEW_FRAGMENT_PACKAGE = "com.butterknife.luffy.bindidf.";
    private static final String VIEW_ACTIVITY_CLASS = "com.butterknife.luffy.bindview.BindContentView$$BindView";
    private static final String BIND_VIEW_FOR_VIEW_HOLDER = "bindViewForViewHolder";
    private static final String BIND_VIEW_FOR_FRAGMENT = "bindViewForFragment";
    private static final String INJECT_FRAGMENT = "com.butterknife.luffy.bindviewf.BindContentView$$BindViewf";

    // ActivityView注入
    public static void injectActivity(Activity activity) {
        // contentView
        try {
            Class<?> clazz = Class.forName(VIEW_ACTIVITY_CLASS);
            Method bindMethod = clazz.getDeclaredMethod("setContentView", Object.class);
            bindMethod.invoke(clazz.newInstance(), activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // bind ActivityId
    public static void injectActivityId(Activity activity) {
        // id
        try {
            Class<?> clazz = Class.forName(ID_ACTIVITY_PACKAGEF + activity.getClass().getSimpleName() + PROXY);
            Method bindMethod = clazz.getDeclaredMethod("bindViewForActivity", Object.class);
            bindMethod.invoke(clazz.newInstance(), activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // bind ActivityClick
    public static void injectActivityClick(Activity activity) {
        // click
        try {
            Class<?> clazz = Class.forName(CLICK_ACTIVITY_PACKAGEF);
            Method bindMethod = clazz.getDeclaredMethod("bindClick", Object.class);
            bindMethod.invoke(clazz.newInstance(), activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // bind FragmentView
    public static View injectFragment(android.app.Fragment fragment) {
        // contentView
        try {
            Class<?> clazz = Class.forName(INJECT_FRAGMENT);
            Method bindMethod = clazz.getDeclaredMethod("setContentView", Object.class);
            Object o = bindMethod.invoke(clazz.newInstance(), fragment);
            return (View) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // bind FragmentId
    public static void injectFragmentId(android.app.Fragment fragment, View view) {
        try {
            Class<?> clazzBindId = Class.forName(VIEW_FRAGMENT_PACKAGE + fragment.getClass().getSimpleName() + PROXYF);
            Method bindMethodId = clazzBindId.getDeclaredMethod(BIND_VIEW_FOR_FRAGMENT, Object.class, Object.class);
            bindMethodId.invoke(clazzBindId.newInstance(), view, fragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // bind FragmentClick
    public static void injectFragmentClick(android.app.Fragment fragment, View view) {
        Class<?> clazzClick = null;
        try {
            clazzClick = Class.forName("com.butterknife.luffy.bindclickf.BindViewClickf$$BindClickf");
            Method bindClick = clazzClick.getDeclaredMethod("bindClickf", Object.class, Object.class);
            bindClick.invoke(clazzClick.newInstance(), fragment, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // bind SupportFragmentView
    public static View injectSupportFragment(Fragment fragment) {
        // contentView
        try {
            Class<?> clazz = Class.forName(INJECT_FRAGMENT);
            Method bindMethod = clazz.getDeclaredMethod("setContentView", Object.class);
            Object o = bindMethod.invoke(clazz.newInstance(), fragment);
            return (View) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // bind SupportFragmentId
    public static void injectSupportFragmentId(Fragment fragment, View view) {
        try {
            Class<?> clazzBindId = Class.forName(VIEW_FRAGMENT_PACKAGE + fragment.getClass().getSimpleName() + PROXYF);
            Method bindMethodId = clazzBindId.getDeclaredMethod(BIND_VIEW_FOR_FRAGMENT, Object.class, Object.class);
            bindMethodId.invoke(clazzBindId.newInstance(), view, fragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // bind SupportFragmentClick
    public static void injectSupportFragmentClick(Fragment fragment, View view) {
        Class<?> clazzClick = null;
        try {
            clazzClick = Class.forName("com.butterknife.luffy.bindclickf.BindViewClickf$$BindClickf");
            Method bindClick = clazzClick.getDeclaredMethod("bindClickf", Object.class, Object.class);
            bindClick.invoke(clazzClick.newInstance(), fragment, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Activity 内部类 ViewHolder注入
    public static void injectView(Object holder, View view) {
        try {
            Class<?> clazz =
                    Class.forName(ID_VIEW_HOLDER_PACKAGE + holder.getClass().getEnclosingClass().getSimpleName() + "$"
                            + holder.getClass().getSimpleName() + PROXY);
            Method bindMethod = clazz.getDeclaredMethod(BIND_VIEW_FOR_VIEW_HOLDER, Object.class, Object.class);
            bindMethod.invoke(clazz.newInstance(), view, holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fragment 内部类 ViewHolder注入
    public static void injectViewF(Object holder, View view) {
        try {
            Class<?> clazz =
                    Class.forName(ID_VIEW_HOLDER_PACKAGEF + holder.getClass().getEnclosingClass().getSimpleName() + "$"
                            + holder.getClass().getSimpleName() + PROXYF);
            Method bindMethod = clazz.getDeclaredMethod(BIND_VIEW_FOR_VIEW_HOLDER, Object.class, Object.class);
            bindMethod.invoke(clazz.newInstance(), view, holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
