#pragma once
#include <sys/types.h>
#include <cstdint>
#include <cstddef>

namespace mem {
    pid_t     find_pid(const char* name);
    uintptr_t get_base(pid_t pid, const char* lib);
    bool      read(pid_t pid, uintptr_t addr, void* buf, size_t sz);
    bool      write(pid_t pid, uintptr_t addr, const void* buf, size_t sz);
}
