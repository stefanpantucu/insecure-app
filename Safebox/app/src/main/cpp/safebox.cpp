#include <jni.h>
#include <string.h>

#ifdef __cplusplus
extern "C" {
#endif

static const int correct_flag[] = {
        67, 84, 70, 123, 110, 97, 116, 105, 118, 101, 95, 118, 97, 108, 105, 100, 97, 116, 105, 111, 110, 125
};
static const int flag_len = sizeof(correct_flag) / sizeof(correct_flag[0]);

JNIEXPORT jboolean JNICALL
Java_com_example_safebox_Native_validateFlag(JNIEnv *env, jobject obj, jstring jflag) {
    const char *flag = env->GetStringUTFChars(jflag, nullptr);
    if (flag == NULL) return JNI_FALSE;

    int input_len = strlen(flag);
    if (input_len != flag_len) {
        env->ReleaseStringUTFChars(jflag, flag);
        return JNI_FALSE;
    }

    for (int i = 0; i < flag_len; i++) {
        if ((int)flag[i] != correct_flag[i]) {
            env->ReleaseStringUTFChars(jflag, flag);
            return JNI_FALSE;
        }
    }

    env->ReleaseStringUTFChars(jflag, flag);
    return JNI_TRUE;
}

#ifdef __cplusplus
}
#endif
