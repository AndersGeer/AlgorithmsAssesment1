package lab5;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdOut;

public class Thresholding {
	Picture pic;
	int width;
	int height;
	int[] pixelnumber;

	Thresholding(String fileLocation, int threshold) {
		pic = new Picture(fileLocation);

		width = pic.width();
		height = pic.height();
		int thresholdValue = threshold;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {

				// GreyScale
				Color col = pic.get(x, y);
				Color grey = Luminance.toGray(col);
				pic.set(x, y, grey);

				// Binary
				Color color = pic.get(x, y);
				if (Luminance.lum(color) <= thresholdValue) {
					pic.set(x, y, Color.BLACK);
				} else {
					pic.set(x, y, Color.WHITE);
				}

			}
		}
		pic.show();

		Map<Integer, Coordinate> coords = new HashMap<>();

		int currentPixel = 0;
		WeightedQuickUnionUF uf = new WeightedQuickUnionUF(pic.width() * pic.height());
		for (int x = width - 1; x >= 0; x--) {
			for (int y = height - 1; y >= 0; y--) {
				coords.put(currentPixel, new Coordinate(x, y));
				int thisSite = currentPixel;
				Color thisColor = pic.get(x, y);

				if (y != 0 && pic.get(x, y-1).equals(thisColor)) {
					
						int siteToUnion = currentPixel + 1;
						uf.union(thisSite, siteToUnion);
								
				}
				if (x != 0 && pic.get(x-1, y).equals(thisColor)) {

					int siteToUnion = currentPixel + (height);
					uf.union(thisSite, siteToUnion);
				}
				currentPixel++;
			}

		}



		

		redSquares(coords, uf);

		
		 
		 

		/*
		 * for (int i = 0; i < lowX.length; i++) { lowX[i] = width-1; } for (int
		 * i = 0; i < highX.length; i++) { highX[i] = 0; }
		 * 
		 * for (int i = 0; i < lowY.length; i++) { lowY[i] = height-1; } for
		 * (int i = 0; i < highY.length; i++) { highY[i] = 0; }
		 * 
		 * 
		 * 
		 * for (int x = 0; x < width; x++) { for (int y = 0; y < height; y++) {
		 * //Check if connected with each of the black pixels in the arrays for
		 * (int i = 0; i < lowY.length; i++) { int compareYPixel; if (x>0) {
		 * compareYPixel = (height*(x-1)+y)-1; } else { compareYPixel = y; }
		 * 
		 * if (uf.connected(lowY[i], compareYPixel)) { int thisY = height * (0 -
		 * x) + height + lowY[i] + 1; int compareY = ConvertToY(x,
		 * compareYPixel); if (compareY < thisY) { lowY[i] = compareYPixel; } }
		 * } for (int i = 0; i < highY.length; i++) { int compareYPixel; if
		 * (x>0) { compareYPixel = (height*(x-1)+y)-1; } else { compareYPixel =
		 * y; }
		 * 
		 * if (uf.connected(highY[i], compareYPixel)) { int thisY = height * (0
		 * - x) + height + highY[i] + 1; int compareY = ConvertToY(x,
		 * compareYPixel); if (compareY > thisY) { highY[i] = compareYPixel; } }
		 * }
		 * 
		 * 
		 * for (int i = 0; i < lowX.length; i++) { int compareXPixel; if (x>0) {
		 * compareXPixel = (height*(x-1)+y)-1; } else { compareXPixel = y; }
		 * 
		 * if (uf.connected(lowX[i], compareXPixel)) { int thisX =
		 * (height+lowX[i]-y+1)/height; int compareX =
		 * ConvertToX(y,compareXPixel);
		 * //height*(0-x)+height+pixelnumber[(height*(x-1)+y)-1]+1; if
		 * (compareX<thisX) { lowX[i] = compareXPixel; } } } for (int i = 0; i <
		 * highX.length; i++) { int compareXPixel; if (x>0) { compareXPixel =
		 * (height*(x-1)+y)-1; } else { compareXPixel = y; } if
		 * (uf.connected(highX[i], compareXPixel)) { int thisX =
		 * (height+highX[i]-y+1)/height; int compareX =
		 * ConvertToX(y,compareXPixel);
		 * //height*(0-x)+height+pixelnumber[(height*(x-1)+y)-1]+1; if
		 * (compareX>thisX) { highX[i] = compareXPixel; } } }
		 * 
		 * //Check if this pixel is more out to whatever side than the checked
		 * one //Change if needed
		 * 
		 * } }
		 */
		StdOut.println("Count is: " + (uf.count()));
	}

	private void redSquares(Map<Integer, Coordinate> coords, WeightedQuickUnionUF uf) {
		
		int[] highX = new int[uf.count()];
		int[] highY = new int[uf.count()];
		int[] lowY = new int[uf.count()];
		int[] lowX = new int[uf.count()];
		
		for (int i = 0; i < lowX.length; i++) {
			lowX[i] = -1;
			highX[i] = -1;
			lowY[i] = -1;
			highY[i] = -1;
		}
		
		
		int currentPixel;
		currentPixel = 0;
		for (int x = width - 1; x >= 0; x--) {
			for (int y = height - 1; y >= 0; y--) {
				boolean connectedWithSomething = false;
				Coordinate thisPixelCoords = coords.get(currentPixel);
				Coordinate compareLowX;
				Coordinate compareHighX;
				Coordinate compareLowY;
				Coordinate compareHighY;
				for (int i = 0; i < lowX.length; i++) {

					if (lowX[i] != -1 && uf.connected(lowX[i], currentPixel)) {

						compareLowX = coords.get(lowX[i]);
						compareHighX = coords.get(highX[i]);
						compareLowY = coords.get(lowY[i]);
						compareHighY = coords.get(highY[i]);
						if (compareLowX.getX() < thisPixelCoords.getX()) {
							lowX[i] = currentPixel;
						}
						if (compareHighX.getX() > thisPixelCoords.getX()) {
							highX[i] = currentPixel;
						}
						if (compareLowY.getY() > thisPixelCoords.getY()) {
							lowY[i] = currentPixel;
						}
						if (compareHighY.getY() > thisPixelCoords.getY()) {
							highY[i] = currentPixel;
						}
						connectedWithSomething = true;

					}
					compareLowX = null;
					compareHighX = null;
					compareLowY = null;
					compareHighY = null;

				}
				if (!connectedWithSomething) {
					for (int j = 0; j < lowX.length; j++) {
						if (lowX[j] == -1) {
							lowX[j] = currentPixel;
							highX[j] = currentPixel;
							lowY[j] = currentPixel;
							highY[j] = currentPixel;
							j = lowX.length;
						}
					}
				}
				currentPixel++;

			}
		}
		
		for (int j : lowX) 
		 { 
			 StdOut.println(j); 
		 } 
		 StdOut.println(); 
		 for (int j : highX) 
		 { 
			 StdOut.println(j); 
			 } 
		 StdOut.println(); 		 
		 for (int j : lowY) 
		 {
			 StdOut.println(j); 
		 } 
		 StdOut.println(); 
		 for (int j : highY) 
		 {
		 StdOut.println(j); 
		 }
	}

	public static void main(String[] args) {
		Thresholding t = new Thresholding("E:/Algorithms/Eclipse/Lab5_Pictures/Pictures/shapes.bmp", 128);

	}
}
