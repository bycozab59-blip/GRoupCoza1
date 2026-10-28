#include "mem.h"
#include "luau_exec.h"
#include <jni.h>
#include <android/log.h>
#include <string>

#define TAG "GRoupCoza"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,  TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

static pid_t     g_pid  = -1;
static uintptr_t g_base = 0;

extern "C" {

JNIEXPORT jboolean JNICALL
Java_com_b2yblaster_groupcoza_ExecutorService_attach(JNIEnv*, jobject) {
    g_pid = mem::find_pid("com.roblox.client");
    if (g_pid < 0) {
        LOGE("Roblox not found");
        return JNI_FALSE;
    }
    g_base = mem::get_base(g_pid, "libroblox.so");
    if (!g_base) {
        LOGE("libroblox.so not found");
        return JNI_FALSE;
    }
    LOGI("Attached pid=%d base=0x%lx", g_pid, g_base);
    luau::init(g_pid, g_base);
    return JNI_TRUE;
}

JNIEXPORT jboolean JNICALL
Java_com_b2yblaster_groupcoza_ExecutorService_execute(JNIEnv* env, jobject, jstring jscript) {
    if (g_pid < 0 || !g_base) return JNI_FALSE;
    const char* s = env->GetStringUTFChars(jscript, nullptr);
    bool ok = luau::exec(std::string(s));
    env->ReleaseStringUTFChars(jscript, s);
    return ok ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNICALL
Java_com_b2yblaster_groupcoza_ExecutorService_detach(JNIEnv*, jobject) {
    g_pid  = -1;
    g_base = 0;
    LOGI("Detached");
}

}
