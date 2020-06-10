package com.zhengsr.zdiffupdate;

/**
 * @author by  zhengshaorui on 2019/9/6
 * Describe:
 */
public class UpdateJni {
    static {
        System.loadLibrary("diffUpdate");
    }

    /**
     * 升级方法
     * @param oldPath
     * @param patch
     * @param newApkPath
     */
    public static native void diffUpdate(String oldPath,String patch,String newApkPath);
}
