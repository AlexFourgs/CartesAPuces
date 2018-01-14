/*
 * faceDetector.cpp
 *
 *  Created on: 11 janv. 2018
 *      Author: antoine
 */

#include "faceDetector.h"

namespace std {

faceDetector::~faceDetector() {
	// TODO Auto-generated destructor stub
}

void faceDetector::resetLookuptable() {
	for (auto it : _lookUTableEllipse) {
		it.second.clear();
	}
}
void faceDetector::learnEllipsisShape(cv::Mat& img,
		cv::Point2d centerOfTheShape, string windowPrefix) {
	createLookupTable(img, centerOfTheShape, _lookUTableEllipse, windowPrefix);
}
void faceDetector::learnEyeShape(cv::Mat& img, cv::Point2d centerOfTheShape,
		string windowPrefix) {
	createLookupTable(img, centerOfTheShape, _lookUTableEyes, windowPrefix);
}

void faceDetector::locateFace(cv::Mat& img, string windowPrefix) {
	_facesCenter = recognizeShape(img, _lookUTableEllipse, windowPrefix);
}

void faceDetector::reset_histograms() {
	for (int i = 0; i < 2; i++) {
		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 256; k++) {
				_histo[i][j][k] = 0;
			}
		}

	}
}
void faceDetector::compute_histogram(cv::Mat& img, string windowPrefix) {
	reset_histograms();
	float max_left[3] = { -INFINITY, -INFINITY, -INFINITY };
	float max_right[3] = { -INFINITY, -INFINITY, -INFINITY };
	for (int col_cursor = _leftEye.x - 30; col_cursor < _leftEye.x + 30;
			col_cursor++) {
		for (int row_cursor = _leftEye.y - 30; row_cursor < _leftEye.y + 30;
				row_cursor++) {
			cv::Vec3b pixel = img.at<cv::Vec3b>(row_cursor, col_cursor);
			_histo[0][0][pixel[0]]++;
			_histo[0][1][pixel[1]]++;
			_histo[0][2][pixel[2]]++;

			max_left[0] =
					(_histo[0][0][pixel[0]] > max_left[0]) ?
							_histo[0][0][pixel[0]] : max_left[0];
			max_left[1] =
					(_histo[0][1][pixel[1]] > max_left[1]) ?
							_histo[0][1][pixel[1]] : max_left[1];
			max_left[2] =
					(_histo[0][2][pixel[2]] > max_left[2]) ?
							_histo[0][2][pixel[2]] : max_left[2];
		}
	}
	for (int col_cursor = _rightEye.x - 30; col_cursor < _rightEye.x + 30;
			col_cursor++) {
		for (int row_cursor = _rightEye.y - 30; row_cursor < _rightEye.y + 30;
				row_cursor++) {
			cv::Vec3b pixel = img.at<cv::Vec3b>(row_cursor, col_cursor);
			_histo[1][0][pixel[0]]++;
			_histo[1][1][pixel[1]]++;
			_histo[1][2][pixel[2]]++;
			max_right[0] =
					(_histo[1][0][pixel[0]] > max_right[0]) ?
							_histo[1][0][pixel[0]] : max_right[0];
			max_right[1] =
					(_histo[1][1][pixel[1]] > max_right[1]) ?
							_histo[1][1][pixel[1]] : max_right[1];
			max_right[2] =
					(_histo[1][2][pixel[2]] > max_right[2]) ?
							_histo[1][2][pixel[2]] : max_right[2];
		}
	}

#ifdef GRAPHIC
	cv::Mat hist_left(cv::Size(512 , 120) , CV_8UC3 , cv::Scalar(0,0,0));
	cv::Mat hist_right(cv::Size(512 , 120) , CV_8UC3 , cv::Scalar(0,0,0));
	for(int i = 0; i < 3; i++) {
		for(int j = 0; j < 255; j++) {
			int nb_pixel= _histo[0][i][j];
			cv::Point pointstart( 2*j , (40 * (i+1) ) - 39 * (float)( nb_pixel / max_right[i]) );
			cv::Point pointend( 2*j+2 + 2 , (40 * (i+1)) + 10 );
			switch(i) {
				case 0:
				cv::rectangle(hist_left ,pointstart , pointend,cv::Scalar(255 , 0 , 0 ) , -1 );
				break;

				case 1:
				cv::rectangle(hist_left ,pointstart , pointend,cv::Scalar(0 , 255 , 0 ) , -1 );
				break;
				case 2:
				cv::rectangle(hist_left ,pointstart , pointend,cv::Scalar( 0 , 0 , 255 ) , -1 );
				break;
			}

		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 265; j++) {
				int nb_pixel= _histo[1][i][j];
				cv::Point pointstart( 2*j , (40 * (i+1) ) - 39 * (float)( nb_pixel / max_right[i]) );
				cv::Point pointend( 2*j+2 + 2 , (40 * (i+1)) + 10 );
				switch(i) {
					case 0:
					cv::rectangle(hist_right ,pointstart , pointend,cv::Scalar(255 , 0 , 0 ) , -1 );
					break;

					case 1:
					cv::rectangle(hist_right ,pointstart , pointend,cv::Scalar(0 , 255 , 0 ) , -1 );
					break;
					case 2:
					cv::rectangle(hist_right ,pointstart , pointend,cv::Scalar( 0 , 0 , 255 ) , -1 );
					break;
				}

			}
		}
		cv::imshow(windowPrefix+"_left_eye_histoRGB" , hist_left);
		cv::imshow(windowPrefix+"_right_eye_histoRGB" , hist_right);
	}
#endif

}
void faceDetector::print_histogram() {

	std::ofstream fichier("histo.txt", ios::out|ios::app); // ouverture en écriture avec effacement du fichier ouvert

	if (fichier)

	{

		fichier << "R";

		for (int i = 0; i < 256; i++) {
			fichier << "," << _histo[0][2][i];
		}
		fichier << std::endl << "G";
		for (int i = 0; i < 256; i++) {
			fichier << "," << _histo[0][1][i];
		}
		fichier << std::endl << "B";
		for (int i = 0; i < 256; i++) {
			fichier << "," << _histo[0][0][i];
		}
		fichier << std::endl;
	}


	else
		cerr << "Impossible d'ouvrir le fichier !" << endl;

}

void faceDetector::drawImg(cv::Mat& img, string windowPrefix) {
	cv::Mat eye_img;
	img.copyTo(eye_img);
	cv::circle(eye_img, _leftEye, 10, cv::Scalar(255, 0, 0), 1);
	cv::circle(eye_img, _rightEye, 10, cv::Scalar(255, 0, 0), 1);
	cv::circle(eye_img, _facesCenter, 10, cv::Scalar(255, 255, 0), -1);
	cv::ellipse(eye_img, _facesCenter, cv::Size(75.0, 150), 0, 0, 360,
			cv::Scalar(255, 0, 0), 1, 8);
	cv::imshow(windowPrefix + "finalOutput", eye_img);
}

void faceDetector::locateEye(cv::Mat& img, string windowPrefix) {

	int windows_size = min(img.cols / 6, img.rows / 6);
	int left_x = max(_facesCenter.x - windows_size - 1, 1);
	int left_y = max(_facesCenter.y - windows_size - 1, 1);
	int left_w = min(img.cols - _facesCenter.x - 2, windows_size);
	int left_h = min(img.rows - _facesCenter.y - 2, windows_size);
	int right_x = max(_facesCenter.x, 1);
	int right_y = max(_facesCenter.y - windows_size - 1, 1);
	int right_w = min(img.cols - _facesCenter.x - 2, windows_size);
	int right_h = min(img.rows - _facesCenter.y - 2, windows_size);
	cv::Rect left_eye_rect(left_x, left_y, left_w, left_h);
	cv::Rect right_eye_rect(right_x, right_y, right_w, right_h);

	cv::Mat left_eye_mat = img(left_eye_rect);
	cv::Mat right_eye_mat = img(right_eye_rect);
	cv::Point left_eye = recognizeShape(left_eye_mat, _lookUTableEyes,
			windowPrefix + "_left_eye", false);
	cv::Point right_eye = recognizeShape(right_eye_mat, _lookUTableEyes,
			windowPrefix + "_right_eye", false);

	_leftEye = cv::Point(left_x + left_eye.x, left_eye.y + left_y);
	_rightEye = cv::Point(right_x + right_eye.x, right_eye.y + right_y);

#if GRAPHIC

	cv::Mat eye_img;
	img.copyTo(eye_img);
	cv::circle(eye_img , _leftEye , 10 , cv::Scalar(255,0,0) , -1);
	cv::circle(eye_img , _rightEye , 10 , cv::Scalar(255,0,0) , -1);
	cv::circle(left_eye_mat , left_eye , 10 , cv::Scalar(255,0,0) , -1);
	cv::circle(right_eye_mat , right_eye , 10 , cv::Scalar(255,0,0) , -1);
	cv::imshow(windowPrefix+"_output" , eye_img);
	cv::imshow(windowPrefix+"_left_eye_mat" , left_eye_mat);
	cv::imshow(windowPrefix+"_right_eye_mat" , right_eye_mat);
#endif

}

void faceDetector::createLookupTable(cv::Mat& img, cv::Point2d centerOfTheShape,
		faceDetector::lookUpTable& lookUpTable, string windowPrefix) {

	cv::Mat tmp(img.size(), CV_8UC1);
	cv::cvtColor(img, tmp, cv::COLOR_BGR2GRAY);

#ifdef GRAPHIC
	cv::Mat res(img.size(), CV_8UC3);
#endif

	int count = 0;

	for (int row_cursor = 1; row_cursor < tmp.rows - 1; ++row_cursor) {

		for (int col_cursor = 1; col_cursor < tmp.cols - 1; ++col_cursor) {
			//compute the row derivative
			float row_grad = tmp.at<uchar>(row_cursor - 1, col_cursor - 1)
					* -1.0 + tmp.at<uchar>(row_cursor, col_cursor - 1) * -2.0
					+ tmp.at<uchar>(row_cursor + 1, col_cursor - 1) * -1.0
					+
					//					tmp.at<uchar>(row_cursor-1, 	col_cursor) 	* 0 +
					//					tmp.at<uchar>(row_cursor, 		col_cursor) 	* 0 +  // the midle case is null
					//					tmp.at<uchar>(row_cursor +1, 	col_cursor) 	* 0 +
					tmp.at<uchar>(row_cursor - 1, col_cursor + 1) * 1.0
					+ tmp.at<uchar>(row_cursor, col_cursor + 1) * 2.0
					+ tmp.at<uchar>(row_cursor + 1, col_cursor + 1) * 1.0;

			//compute the col derivative
			float col_grad = tmp.at<uchar>(row_cursor - 1, col_cursor - 1) * -1

					//					+ tmp.at<uchar>(row_cursor, 		col_cursor-1) 	* 0
					+ tmp.at<uchar>(row_cursor + 1, col_cursor - 1) * 1
					+ tmp.at<uchar>(row_cursor - 1, col_cursor) * -2
					//					+ tmp.at<uchar>(row_cursor, col_cursor) 	* 0
					+ tmp.at<uchar>(row_cursor + 1, col_cursor) * 2
					+ tmp.at<uchar>(row_cursor - 1, col_cursor + 1) * -1
					//					+ tmp.at<uchar>(row_cursor, 		col_cursor+1) 	* 0
					+ tmp.at<uchar>(row_cursor + 1, col_cursor + 1) * 1;

			col_grad = col_grad / 255;
			row_grad = row_grad / 255;

			if ((col_grad != 0.0 || row_grad != 0.0)) {

				count++;
				float theta = atan2(-col_grad, row_grad);
				float ydiff = (float) (centerOfTheShape.y - col_cursor);
				float xdiff = (float) (centerOfTheShape.x - row_cursor);
				float beta = 0;

				beta = atan2(-ydiff, xdiff);

				int roundTheta = (int) floor((theta * 180) / (float) M_PI)
						+ 180;
				int roundBeta = (int) floor((beta * 180) / (float) M_PI);

				if (lookUpTable[roundTheta].size() < 100)
					lookUpTable[roundTheta].push_back(beta);

				float tangeantx = 100 * cos(beta);

				float tangeanty = -100 * sin(beta);
#ifdef GRAPHIC

				cv::arrowedLine(res, cv::Point(col_cursor, row_cursor),
						cv::Point(col_cursor + tangeanty,
								row_cursor + tangeantx),
						cv::Scalar(count % 255, 100, 255));
#endif
			}

		}
	}
#ifdef GRAPHIC
	cv::Mat hist(cv::Size(720 , 40) , CV_8UC1 , cv::Scalar(255));
	for(int i = 0; i < lookUpTable.size(); i++) {
		int nb_class = lookUpTable[i].size();
		cv::Point pointstart( 2 * i , 3 * nb_class );
		cv::Point pointend( 2 * i + 2 , 0 );

		cv::rectangle(hist ,pointstart , pointend,cv::Scalar(0 , 0 , 255 ) , -1 );

	}

	cv::imshow(windowPrefix+"_BetaLUT" ,res);
	cv::imshow(windowPrefix+"_histogramLUT" ,hist);
#endif
}

cv::Point faceDetector::recognizeShape(cv::Mat& imgorig,
		faceDetector::lookUpTable& lookUpTable, string windowPrefix,
		bool ellipse) {
	float witdh_height_ratio = (float) imgorig.cols / (float) imgorig.rows;
	float desired_height = 120;
	float witdh_ratio = (float) imgorig.cols
			/ (desired_height * witdh_height_ratio);
	float height_ratio = (float) imgorig.rows / desired_height;
	cv::Mat img;
	cv::resize(imgorig, img,
			cv::Size(desired_height * witdh_height_ratio, desired_height));

	cv::Mat accuTable = cv::Mat(img.size(), CV_32FC1, cv::Scalar(float(0)));
#ifdef GRAPHIC
	cv::Mat contour = cv::Mat(img.size(), CV_8UC1, cv::Scalar(uchar(0)));
	cv::Mat res(img.size(), CV_8UC3, cv::Scalar(0, 0, 0));
#endif

	// Conversion en niveau de gris.
	cv::Mat img_gray;
	cv::cvtColor(img, img_gray, CV_RGB2GRAY);

	int actual_pixel_sobel_x;
	int actual_pixel_sobel_y;

	float theta = 0;
	float beta = 0;
	int r = 0;
	float max = -INFINITY;
	// On parcourt l'image img
	for (int row_cursor = 1; row_cursor < img_gray.rows - 1; ++row_cursor) {
		for (int col_cursor = 1; col_cursor < img_gray.cols - 1; ++col_cursor) {
			// Application de sobel en x.
			float row_grad = img_gray.at<uchar>(row_cursor - 1, col_cursor - 1)
					* -1.0
					+ img_gray.at<uchar>(row_cursor, col_cursor - 1) * -2.0
					+ img_gray.at<uchar>(row_cursor + 1, col_cursor - 1) * -1.0
					+
					//					tmp.at<uchar>(row_cursor-1, 	col_cursor) 	* 0 +
					//					tmp.at<uchar>(row_cursor, 		col_cursor) 	* 0 +  // the midle case is null
					//					tmp.at<uchar>(row_cursor +1, 	col_cursor) 	* 0 +
					img_gray.at<uchar>(row_cursor - 1, col_cursor + 1) * 1.0
					+ img_gray.at<uchar>(row_cursor, col_cursor + 1) * 2.0
					+ img_gray.at<uchar>(row_cursor + 1, col_cursor + 1) * 1.0;

			//compute the col derivative
			float col_grad = img_gray.at<uchar>(row_cursor - 1, col_cursor - 1)
					* -1

					//					+ tmp.at<uchar>(row_cursor, 		col_cursor-1) 	* 0
					+ img_gray.at<uchar>(row_cursor + 1, col_cursor - 1) * 1
					+ img_gray.at<uchar>(row_cursor - 1, col_cursor) * -2
					//					+ tmp.at<uchar>(row_cursor, col_cursor) 	* 0
					+ img_gray.at<uchar>(row_cursor + 1, col_cursor) * 2
					+ img_gray.at<uchar>(row_cursor - 1, col_cursor + 1) * -1
					//					+ tmp.at<uchar>(row_cursor, 		col_cursor+1) 	* 0
					+ img_gray.at<uchar>(row_cursor + 1, col_cursor + 1) * 1;

			row_grad = row_grad / 255.0;
			col_grad = col_grad / 255.0;

			if (ellipse) {
				if (abs(col_grad) < 0.1 || abs(row_grad) < 0.1) {
					row_grad = 0;

				}
				if (abs(col_grad) < 0.1 || abs(row_grad) < 0.1) {
					col_grad = 0;
				}

			} else {
				if (abs(row_grad) < 0.3) {
					row_grad = 0;

				}
				if (abs(col_grad) < 0.3) {
					col_grad = 0;
				}

			}

			if (row_grad != 0
					|| col_grad != 0) { // Si c'est un contour
#ifdef GRAPHIC
							contour.at<uchar>(row_cursor , col_cursor) = (abs(col_grad * 255) +abs(row_grad * 255))/2;
							float tangeantx = 10 * cos(theta);
							float tangeanty = -10 * sin(theta);

							cv::arrowedLine(res, cv::Point(col_cursor, row_cursor),
									cv::Point(col_cursor + tangeanty,
											row_cursor + tangeantx), (255, 100, 255));
#endif
				theta = atan2(-col_grad, row_grad);

				float roundTheta = (int) floor((theta * 180) / (float) M_PI)
						+ 180;
//	std::cout << "for theta " << roundTheta << " "<< lookUpTable[roundTheta].size() <<"  angles"  <<endl;

				for (int i = 0; i < lookUpTable[roundTheta].size(); ++i) {
					beta = lookUpTable[roundTheta][i];
					r = 0;

					while (((row_cursor + r * cos(beta) > 0)
							&& (row_cursor + r * cos(beta) < img_gray.rows))
							&& ((col_cursor - r * sin(beta) > 0)
									&& (col_cursor - r * sin(beta)
											< img_gray.cols)) && r < 100) {

						int xcoord = row_cursor + (int) r * cos(beta);
						int ycoord = col_cursor - (int) r * sin(beta);
//	                    	std::cout<<  xcoord << " " << ycoord << std::endl;
						accuTable.at<float>(xcoord, ycoord)++;max
						= (abs(accuTable.at<float>(xcoord, ycoord)) > max) ?
								abs(accuTable.at<float>(xcoord, ycoord)) : max;
						r++;
					}
				}
			}
		}
	}

	double mininarray = 0, maxinarray = 0;
	cv::Point maxloc, minloc;
	accuTable = accuTable / max;
	cv::minMaxLoc(accuTable, &mininarray, &maxinarray, &minloc, &maxloc);

#ifdef GRAPHIC
	cv::circle(res, maxloc, 10, cv::Scalar(0, 0, 255));
	cv::imshow(windowPrefix+"_grad", res);
	res = cv::Scalar(0, 0, 0);
	cv::imshow(windowPrefix+"_contour", contour);
	contour = cv::Scalar(0, 0, 0);
	cv::imshow(windowPrefix+"_hough", accuTable);
#endif
	return cv::Point(maxloc.x * witdh_ratio, maxloc.y * height_ratio);
}

} /* namespace cv */
