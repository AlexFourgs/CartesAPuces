cmake_minimum_required(VERSION 2.8)
find_package(OpenCV REQUIRED)
find_package(SWIG REQUIRED)
find_package(Java REQUIRED)
find_package(JNI REQUIRED)
include(UseJava)
include(UseSWIG)
include_directories(${JNI_INCLUDE_DIRS})

set( CMAKE_SWIG_OUTDIR ${CMAKE_CURRENT_BINARY_DIR} )

# Build the C++ code into a dynamic library: example.dll (on Windows) or libexample.so (on Linux)
swig_add_module(
    Hough 
    java 
    facial_recog_hough.i 
    facial_recog_hough.cpp
)

# For convenience we copy the dynamic library to the current build folder
add_custom_command(
    TARGET Hough 
    POST_BUILD
    COMMAND ${CMAKE_COMMAND} -E copy_if_different $<TARGET_FILE:Hough> ${CMAKE_CURRENT_BINARY_DIR}
)

# Build the Java code into ExampleJNI.jar
add_jar(
    HoughJNI
    SOURCES
    ${CMAKE_SWIG_OUTDIR}/Hough.java
    ${CMAKE_SWIG_OUTDIR}/HoughJNI.java
)
add_dependencies( HoughJNI Hough )

# Finally build the demonstration code into Main.jar

set(CMAKE_JAVA_JAR_ENTRY_POINT main) 
add_jar(
    Main 
    SOURCES main.java
    ENTRY_POINT main
)
add_dependencies( Main ExampleJNI )

add_executable(Hough_exec
    main.cpp 
    facial_recog_hough.cpp
)
include_directories(${OpenCV_INCLUDE_DIRS})
target_link_libraries(Hough_exec ${OpenCV_LIBRARIES})
