/*
 * faceDetector.h
 *
 *  Created on: 11 janv. 2018
 *      Author: antoine
 */

#ifndef FACEDETECTOR_H_
#define FACEDETECTOR_H_
#include <map>
#include <vector>
#include <opencv/cv.hpp>
#include <iostream>
#include <math.h>
#include <fstream>
namespace std {

/*
 *
 */
class faceDetector {
public:
	typedef std::map<int, std::vector<float>> lookUpTable;
	typedef vector<vector<vector<float>>> histogram;
public:
	faceDetector() {
		for (int i = 0; i < 361; i++) {
			_lookUTableEllipse.insert(
					std::pair<int, std::vector<float>>(i,
							std::vector<float>()));
			_lookUTableEyes.insert(
					std::pair<int, std::vector<float>>(i,
							std::vector<float>()));
		}
		for (int i = 0; i < 2; i++) {
			_histo.push_back(std::vector<vector<float>>());
			for (int j = 0; j < 3; j++) {
				_histo[i].push_back(std::vector<float>());
				for (int k = 0 ; k < 256 ; k++){
					_histo[i][j].push_back(0);
				}

			}

		}
		_accuTable = cv::Mat();
	}
	virtual ~faceDetector();
	void resetLookuptable();


	cv::Mat getAccuTable() {
		return _accuTable;
	}

	void learnEllipsisShape(cv::Mat& img, cv::Point2d centerOfTheShape , string windowPrefix);
	void learnEyeShape(cv::Mat& img, cv::Point2d centerOfTheShape ,  string windowPrefix);
	void locateFace(cv::Mat& img  ,  string windowPrefix);
	void locateEye(cv::Mat& img , string windowPrefix);
	void compute_histogram(cv::Mat& img, string windowPrefix);
	void print_histogram();
	void drawImg(cv::Mat& img , string windowPrefix);
	void reset_histograms();

private:
	cv::Point recognizeShape(cv::Mat& img, faceDetector::lookUpTable &lookUpTable , string windowPrefix , bool ellipse = true);
	void createLookupTable(cv::Mat& img, cv::Point2d centerOfTheShape,
			faceDetector::lookUpTable &lookUpTable,  string windowPrefix);

	std::map<int, std::vector<float> > _lookUTableEllipse;
	std::map<int, std::vector<float> > _lookUTableEyes;
	cv::Mat _accuTable;
	cv::Point _facesCenter;
	cv::Point _leftEye;
	cv::Point _rightEye;
	histogram _histo;
};

} /* namespace cv */

#endif /* FACEDETECTOR_H_ */
