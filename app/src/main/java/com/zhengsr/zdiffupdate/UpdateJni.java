package com.zhengsr.zdiffupdate;

/**
 * @author by  zhengshaorui on 2019/9/6
 * Describe:
 */
public class UpdateJni {
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 升级方法
     *
     * @param oldPath    老的apk 路径
     * @param patch      对比生成的 patch 的路径
     * @param newApkPath 新 apk 的路径
     */
    public static native void diffUpdate(String oldPath, String patch, String newApkPath);
}
