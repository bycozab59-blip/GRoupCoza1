#include "luau_exec.h"
#include "mem.h"
#include <android/log.h>
#include <cstring>

#define TAG "GRoupCoza"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,  TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

static int       g_pid  = -1;
static uintptr_t g_base = 0;

// FILL: scan these sigs in libroblox.so after dumping
// luaL_loadbuffer: 48 89 5C 24 ? 57 48 83 EC 20
// lua_pcall:       48 83 EC 28 E8 ? ? ? ? 48 83 C4 28
static uintptr_t off_luaL_loadbuffer = 0x0; // FILL
static uintptr_t off_lua_pcall       = 0x0; // FILL
static uintptr_t off_lua_getstate    = 0x0; // FILL

using luaL_loadbuffer_t = int(*)(void*, const char*, size_t, const char*);
using lua_pcall_t       = int(*)(void*, int, int, int);
using lua_getstate_t    = void*(*)();

void luau::init(int pid, uintptr_t base) {
    g_pid  = pid;
    g_base = base;
    LOGI("luau init pid=%d base=0x%lx", pid, base);
}

bool luau::exec(const std::string& script) {
    if (g_pid < 0 || !g_base) {
        LOGE("not attached");
        return false;
    }

    // FILL: resolve lua_State* from Roblox's ScriptContext
    // then call luaL_loadbuffer + lua_pcall via shellcode or pipe
    // placeholder returns true so UI doesn't break during dev
    LOGI("exec: %s", script.c_str());
    return true;
}
