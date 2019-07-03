# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
#设置编译 native library 需要最小的 cmake 版本
cmake_minimum_required(VERSION 3.4.1)

#定位到.so文件目录
#set(distribution_DIR ${CMAKE_SOURCE_DIR}/libs)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
#将指定的源文件编译为名为 libnative-lib.so 的动态库
add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp/native-lib.cpp)


# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
#查找本地 log 库
find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

#set_target_properties(sodium
#        PROPERTIES IMPORTED_LOCATION
#        ${distribution_DIR}/libsodium.so )
#set_target_properties( sodium
#        PROPERTIES IMPORTED_LOCATION
#        ../libs/armeabi-v7a/libsodium.so )

add_library( sodium
        SHARED
        IMPORTED )
set_target_properties( # Specifies the target library.
        sodium

        # Specifies the parameter you want to define.
        PROPERTIES IMPORTED_LOCATION

        # Provides the path to the library you want to import.${PROJECT_SOURCE_DIR}
        ${PROJECT_SOURCE_DIR}/libs/${ANDROID_ABI}/libsodium.so )

include_directories(libs/include)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
#将预构建的库添加到自己的原生库
target_link_libraries( # Specifies the target library.
                       native-lib
                        sodium

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}
                        test-lib
                        mobinfo-lib)
