#ifndef VOTE_MAP_H_
#define VOTE_MAP_H_
#include <opencv2/opencv.hpp>
#include <cmath>
#include <vector>
#include <map>

cv::Mat vote_table(cv::Mat img, std::map<int, std::vector<int>> teta_beta);

#endif
