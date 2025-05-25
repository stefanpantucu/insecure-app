#include <jni.h>
#include <string.h>

static const int correct_flag[] = {
    70, 108, 111, 114, 105, 110, 32, 83, 97, 108, 97, 109
};
static const int flag_len = sizeof(correct_flag) / sizeof(correct_flag[0]);

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_safebox_utils_Native_validateFlag(JNIEnv *env, jobject thiz, jstring jflag) {
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