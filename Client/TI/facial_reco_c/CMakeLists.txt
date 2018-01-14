cmake_minimum_required(VERSION 2.8)
find_package(OpenCV REQUIRED)


option(GRAPHIC  ON)
option(DEMO  ON)



add_executable(Hough_exec
    main.cpp
    faceDetector.cpp 
    
)

include_directories(${OpenCV_INCLUDE_DIRS})
target_link_libraries(Hough_exec ${OpenCV_LIBRARIES})


if(${GRAPHIC})
  target_compile_definitions(Hough_exec PRIVATE GRAPHIC)
  
endif(${GRAPHIC})

if(${GRAPHIC})
  target_compile_definitions(Hough_exec PRIVATE GRAPHIC)
  
endif(${GRAPHIC})
