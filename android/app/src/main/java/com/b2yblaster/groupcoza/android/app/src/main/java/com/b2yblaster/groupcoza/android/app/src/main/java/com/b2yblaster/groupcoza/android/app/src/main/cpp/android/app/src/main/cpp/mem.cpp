#include "mem.h"
#include <cstdio>
#include <cstring>
#include <dirent.h>
#include <unistd.h>
#include <sys/uio.h>

pid_t mem::find_pid(const char* name) {
    DIR* d = opendir("/proc");
    if (!d) return -1;
    dirent* e;
    while ((e = readdir(d))) {
        int pid = atoi(e->d_name);
        if (pid <= 0) continue;
        char path[64], comm[256];
        snprintf(path, sizeof(path), "/proc/%d/cmdline", pid);
        FILE* f = fopen(path, "r");
        if (!f) continue;
        fgets(comm, sizeof(comm), f);
        fclose(f);
        if (strstr(comm, name)) {
            closedir(d);
            return pid;
        }
    }
    closedir(d);
    return -1;
}

uintptr_t mem::get_base(pid_t pid, const char* lib) {
    char path[64];
    snprintf(path, sizeof(path), "/proc/%d/maps", pid);
    FILE* f = fopen(path, "r");
    if (!f) return 0;
    char line[512];
    while (fgets(line, sizeof(line), f)) {
        if (strstr(line, lib)) {
            uintptr_t base = strtoull(line, nullptr, 16);
            fclose(f);
            return base;
        }
    }
    fclose(f);
    return 0;
}

bool mem::read(pid_t pid, uintptr_t addr, void* buf, size_t sz) {
    iovec local  = { buf, sz };
    iovec remote = { reinterpret_cast<void*>(addr), sz };
    return process_vm_readv(pid, &local, 1, &remote, 1, 0) == (ssize_t)sz;
}

bool mem::write(pid_t pid, uintptr_t addr, const void* buf, size_t sz) {
    iovec local  = { const_cast<void*>(buf), sz };
    iovec remote = { reinterpret_cast<void*>(addr), sz };
    return process_vm_writev(pid, &local, 1, &remote, 1, 0) == (ssize_t)sz;
}
