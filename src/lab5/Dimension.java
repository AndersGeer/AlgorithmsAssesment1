package lab5;

import java.io.File;

import edu.princeton.cs.introcs.Picture;
import edu.princeton.cs.introcs.StdOut;

public class Dimension 
{
	Picture pic;
	Dimension(String fileLocation)
	{
		pic = new Picture(fileLocation);
		StdOut.println("Width: " + pic.width() + " and height: " + pic.height());
	}
	
	public static void main(String[] args) {
		Dimension d = new Dimension("D:/Algorithms/Eclipse/Lab5_Pictures/Pictures/IMG_5194.jpg");
	}
	
	
}
