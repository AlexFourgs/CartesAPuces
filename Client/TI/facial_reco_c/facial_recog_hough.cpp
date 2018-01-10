#include "facial_recog_hough.h"

void hough_Transform(cv::Mat img, cv::Point2d centerOfTheShape) {
	cv::Mat tmp(img.size(), CV_8UC1);
	cv::Mat result(img.size(), CV_8UC1);
	cv::cvtColor(img, tmp, cv::COLOR_BGR2GRAY);

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

			if (col_grad != 0.0 || row_grad != 0.0) {
				float alpha = atan2(col_grad, row_grad);
				float ydiff = (float) (centerOfTheShape.y - row_cursor);
				float xdiff = (float) (centerOfTheShape.x - col_cursor);
				float beta = 0;
				if (xdiff != 0) {

					beta = atan2(ydiff, xdiff);
					cv::arrowedLine(result, cv::Point(row_cursor, col_cursor),
							cv::Point(centerOfTheShape.y, centerOfTheShape.x),
							(0, 100, 255));

				}
			}

		}
	}

}
