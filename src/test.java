// Author : Rohan Parikh
// Test

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class test{


	public static void main(String[] args) throws IOException, ClassNotFoundException{
		FileInputStream fileIn = new FileInputStream("tree.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        BKTree b = (BKTree) in.readObject();
        in.close();
        fileIn.close();
        ArrayList<String> s = b.search("decifer", 0);
		for(int i=0; i<s.size(); i++){
			System.out.println(s.get(i));
		}
		System.out.println("Number of comparisons are "+b.numberOfComparisons);
	}
}
