/*
 * main.cpp
 *
 *  Created on: 10 janv. 2018
 *      Author: antoine
 */
#include <iostream>
#include "faceDetector.h"
using namespace cv;
using namespace std;

int main(int argc, char **argv) {

    VideoCapture cap(0); // open the default camera
    if(!cap.isOpened())  // check if we succeeded
        return -1;

    namedWindow("image",1);
    namedWindow("hough",cv::WINDOW_AUTOSIZE);


    Mat ellipsis = cv::imread("ellipseLI.png" );
    Mat eye = cv::imread("eyeI.png" );

    cv::Mat accu;

    faceDetector detector;
    cv::Point centerOfTheShapeEllipsis(  ellipsis.rows / 2  , ellipsis.cols / 2);
    cv::Point centerOfTheShapeEye(  eye.rows / 2  , eye.cols / 2);

    detector.learnEllipsisShape(ellipsis ,centerOfTheShapeEllipsis ,"ellipsis");
    detector.learnEyeShape(eye ,centerOfTheShapeEye ,"eye");

    Mat frame;
    detector.locateFace(ellipsis , "face");

    int count = 0;
    for(;;)
    {

        cap >> frame; // get a new frame from camera
        imshow("image", frame);

        detector.locateFace(frame, "face");

        detector.locateEye(frame , "Eye");
        detector.drawImg(frame , "final");
        detector.compute_histogram(frame , "hist");
        detector.print_histogram();

#ifndef DEMO
        count++;
       if(count > 20 ) break;
#endif

    }
    // the camera will be deinitialized automatically in VideoCapture destructor
    return 0;

}



