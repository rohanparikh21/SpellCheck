// Author : Rohan Parikh
// Implementation of a BK Tree for use in spell checking


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


public class BKTree implements Serializable{
	
	public int numberOfComparisons = 0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Node class implementation
	private class Node implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String key;
		Hashtable<Integer,Node> children = new Hashtable<Integer,Node>();
		
		public Node(String k){
			key = k;
		}
		
		public void AddChildren(int dist, String word){
			children.put(dist, new Node(word));
		}
		
	}
	// Root node of the BK Tree
	public Node root = null;
	
	// Insert function for the BK Tree
	public void insert(String word){
		
		// Case when root is null
		if(root == null){
			root = new Node(word);
			return;
		}
		
		Node currentNode = root;
		
		int distance = LevenshteinDistance.levenshteinDistanceDP(root.key, word);
		
		// Loop till you find an empty child
		while(currentNode.children.containsKey(distance)){
			
			if(distance == 0){
				return;
			}
			else{
				currentNode = currentNode.children.get(distance);
				distance = LevenshteinDistance.levenshteinDistanceDP(currentNode.key, word);
			}
		}
		
		currentNode.AddChildren(distance, word);	
	}
	
	// Search of a word
	public ArrayList<String> search(String word, int tolerance){
		
		numberOfComparisons = 0;
		ArrayList<String> r = new ArrayList<String>();
		recursiveSearch(root, r, word, tolerance);
		return r;
	}
	
	// Recursive search
	public void recursiveSearch(Node n, ArrayList<String> r,  String word, int tolerance){
		
		numberOfComparisons++;
		int distance = LevenshteinDistance.levenshteinDistanceDP(n.key, word);
		int maxD = distance + tolerance;
		int minD = distance - tolerance;
		
		if(distance <= tolerance){
			r.add(n.key);
		}
		
		for(int i=minD; i<= maxD; i++){
			if(n.children.containsKey(i)){
				recursiveSearch(n.children.get(i),r,word,tolerance);
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		long startTime = System.nanoTime();
		BKTree B = new BKTree();
		Scanner reader = new Scanner(new FileInputStream("DSAdict.txt"));
		while(reader.hasNextLine()){
			B.insert(reader.next().toLowerCase());
		}
		// Create a file to store the BK Tree
		FileOutputStream fileOut = new FileOutputStream("tree.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
        	out.writeObject(B);
        	out.close();
		fileOut.close();
		long endTime = System.nanoTime();
		System.out.println("Time to make the Data Structure : "+ (endTime-startTime)/1000000+"ms");
		
		
	}

}
