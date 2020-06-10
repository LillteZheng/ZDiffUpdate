#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_zhengsr_zduffupdate_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C"{
//引入bspatch.c里的main方法
extern int execute_update(int argc,char * argv[]);
}

extern "C" JNIEXPORT void JNICALL
Java_com_zhengsr_zdiffupdate_UpdateJni_diffUpdate(JNIEnv *env, jclass instance, jstring oldapk_,
                                                 jstring patch_, jstring output_) {
    const char *oldapk = env->GetStringUTFChars(oldapk_, 0);
    const char *patch = env->GetStringUTFChars(patch_, 0);
    const char *output = env->GetStringUTFChars(output_, 0);


    int argc = 4;
    char *argv[4] ={"", const_cast<char *>(oldapk),const_cast<char *>(output),const_cast<char *>(patch)};
    execute_update(argc,argv);

    env->ReleaseStringUTFChars(oldapk_, oldapk);
    env->ReleaseStringUTFChars(patch_, patch);
    env->ReleaseStringUTFChars(output_, output);
}