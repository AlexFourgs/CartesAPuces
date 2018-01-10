package org.ucp.facial_recog;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Hello world!
 *
 */
public class formTracker
{
	private float[] shapeAlpha;
	private float[] shapeBeta;
	
	
	public formTracker() {
		this.shapeAlpha = new float[361];
		this.shapeBeta = new float[361];
	}

	public void  Train(Mat img , Point centerOfTheShape) {
		Mat grayImg = new Mat(img.size(), CvType.CV_8UC1);
		Mat resultImg = new Mat(img.size() , CvType.CV_8UC3);
		Imgproc.cvtColor(img, grayImg, Imgproc.COLOR_BGR2GRAY);
			
			
		Mat gradient = new Mat(img.size(), CvType.CV_32FC2);
		for(int colsCursor = 0 ; colsCursor <  img.cols() ; colsCursor++ ) {
			for(int rowCursor = 0 ; rowCursor <  img.rows() ; rowCursor++ ) {
				
				gradient.get(rowCursor,colsCursor)[0] = (
						 grayImg.get(rowCursor - 1, colsCursor - 1)[0] * -1.0 
						+ grayImg.get(rowCursor, colsCursor - 1)[0] * -2.0
						+ grayImg.get(rowCursor + 1, colsCursor - 1)[0] * -1.0
						+
//						grayImg.get(rowCursor-1, 	colsCursor) 	* 0 +
//						grayImg.get(rowCursor, 		colsCursor) 	* 0 +  // the midle case is null
//						grayImg.get(rowCursor +1, 	colsCursor) 	* 0 +
						grayImg.get(rowCursor - 1, colsCursor + 1)[0] * 1.0
						+ grayImg.get(rowCursor, colsCursor + 1)[0] * 2.0
						+ grayImg.get(rowCursor + 1, colsCursor + 1)[0] * 1.0)/255;
				
				gradient.get(rowCursor,colsCursor)[1] =( 
						 grayImg.get(rowCursor - 1, colsCursor - 1)[0] * -1.0 
//						+ grayImg.get(rowCursor, colsCursor - 1)[0] * -0.0
						+ grayImg.get(rowCursor + 1, colsCursor - 1)[0] * 1.0
						+
						grayImg.get(rowCursor-1, 	colsCursor)[0] 	* -2.0 +
//						grayImg.get(rowCursor, 		colsCursor)[0] 	* 0 +  // the midle case is null
						grayImg.get(rowCursor +1, 	colsCursor)[0] 	* 2.0 +
						grayImg.get(rowCursor - 1, colsCursor + 1)[0] * -1.0
//						+ grayImg.get(rowCursor, colsCursor + 1)[0] * 0.0
						+ grayImg.get(rowCursor + 1, colsCursor + 1)[0] * 1.0)/255;
				
				if(gradient.get(rowCursor, colsCursor)[0] != 0.0 || gradient.get(rowCursor, colsCursor)[1] != 0.0) {
					float alpha = (float) Math.atan2(gradient.get(rowCursor, colsCursor)[0] , gradient.get(rowCursor, colsCursor)[1]);
					 float ydiff =  (float) (centerOfTheShape.y - rowCursor) ; 
					 float xdiff =  (float) (centerOfTheShape.x - colsCursor) ; 
					 float beta = 0;
					 if(xdiff != 0  ) {
						
								beta  = (float) Math.atan2(ydiff ,xdiff);
						Imgproc.arrowedLine(resultImg, new Point(rowCursor , colsCursor ) ,new Point(centerOfTheShape.y , centerOfTheShape.x  )   , new Scalar(0, 100, 255)  );
						
					 
					
						
					
				}
					}
			}	
		}
		Imgcodecs.imwrite("out.png", resultImg);
	
		
	}
	
    public Point Track(Mat img) {
    	
    	
    	
		return null;
    }
    public static void main(String[] args) {
	Mat mat = Imgcodecs.imread("src/test.png");	
	
	}
}
