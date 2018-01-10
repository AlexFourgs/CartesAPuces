#include "vote_map.h"

// pas testé
cv::Mat vote_table(cv::Mat img, std::map<int, std::vector<int>> teta_beta){
    cv::Mat img_vote_table(img.size(), CV_8UC1);
    //int sobel_x[3][3] = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    //int sobel_y[3][3] = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    cv::Mat img_gray;
    cv::Mat img_bin;

    // Conversion en niveau de gris.
    cv::cvtColor(img, img_gray, CV_RGB2GRAY);

    // Conversion en binaire.
    img_bin = img_gray > 128 ;

    int actual_pixel_sobel_x = 0 ;
    int actual_pixel_sobel_y = 0 ;

    int teta = 0 ;
    int beta = 0 ;
    int r = 0 ;
    // On parcourt l'image img
    for (int row_cursor = 1; row_cursor < img_gray.rows - 1; ++row_cursor) {
		for (int col_cursor = 1; col_cursor < img_gray.cols - 1; ++col_cursor) {
            // Application de sobel en x.
            actual_pixel_sobel_x =  -img_gray.at<uchar>(row_cursor-1, col_cursor-1)
                                    +img_gray.at<uchar>(row_cursor-1, col_cursor+1)
                                    -2*img_gray.at<uchar>(row_cursor, col_cursor-1)
                                    +2*img_gray.at<uchar>(row_cursor, col_cursor+1)
                                    -img_gray.at<uchar>(row_cursor+1, col_cursor-1)
                                    +img_gray.at<uchar>(row_cursor+1, col_cursor+1);

            // Application de sobel en y.
            actual_pixel_sobel_y =  -img_gray.at<uchar>(row_cursor-1, col_cursor-1)
                                    -2*img_gray.at<uchar>(row_cursor-1, col_cursor)
                                    -img_gray.at<uchar>(row_cursor-1, col_cursor+1)
                                    +img_gray.at<uchar>(row_cursor+1, col_cursor-1)
                                    +2*img_gray.at<uchar>(row_cursor+1, col_cursor)
                                    +img_gray.at<uchar>(row_cursor+1, col_cursor+1);

            if(actual_pixel_sobel_y || actual_pixel_sobel_x){ // Si c'est un contour
                teta = atan(actual_pixel_sobel_x/actual_pixel_sobel_y);
                for(int i = 0 ; i < teta_beta[teta].size() ; ++i){
                    beta = teta_beta[teta][i];
                    r = 0 ;
                    while(((row_cursor+r*cos(beta) > 0) || (row_cursor+r*cos(beta) < img_gray.rows))
                            && ((col_cursor+r*sin(beta) > 0) || (col_cursor+r*sin(beta) < img_gray.cols))){

                        img_vote_table.at<uchar>(row_cursor+r*cos(beta), col_cursor+r*sin(beta))++;
                        r++ ;
                    }
                }
            }
        }
    }

    return img_vote_table ;
}
