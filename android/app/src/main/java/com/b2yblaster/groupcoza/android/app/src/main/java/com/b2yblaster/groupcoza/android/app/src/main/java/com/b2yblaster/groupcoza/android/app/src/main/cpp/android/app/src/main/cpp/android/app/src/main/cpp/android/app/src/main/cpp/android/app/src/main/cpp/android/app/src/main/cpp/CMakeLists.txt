cmake_minimum_required(VERSION 3.22.1)
project(groupcoza)

set(CMAKE_CXX_STANDARD 17)

add_library(groupcoza SHARED
    injector.cpp
    luau_exec.cpp
    mem.cpp
)

target_include_directories(groupcoza PRIVATE
    ${CMAKE_SOURCE_DIR}
)

target_link_libraries(groupcoza
    android
    log
)
