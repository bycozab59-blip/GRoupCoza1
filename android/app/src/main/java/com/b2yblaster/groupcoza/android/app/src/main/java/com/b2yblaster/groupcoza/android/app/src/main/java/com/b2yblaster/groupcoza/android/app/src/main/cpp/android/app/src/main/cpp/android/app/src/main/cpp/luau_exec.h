#pragma once
#include <cstdint>
#include <string>

namespace luau {
    void init(int pid, uintptr_t base);
    bool exec(const std::string& script);
}
