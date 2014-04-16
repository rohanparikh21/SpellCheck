// Author: Rohan Parikh
// Levenshtein distance implementation

public class LevenshteinDistance {
	
	// Recursive function for finding the levenshtein distance
	// len_s and len_t are the number of characters in string s and t respectively
	public static int LevenshteinDistance(String s, int len_s, String t, int len_t)
	{
	  int cost;
	  /* base case: empty strings */
	  if (len_s == 0) return len_t;
	  if (len_t == 0) return len_s;
	 
	  /* test if last characters of the strings match */
	  if (s.charAt(len_s-1) == t.charAt(len_t-1)) cost = 0;
	  else                          cost = 1;
	 
	  /* return minimum of delete char from s, delete char from t, and delete char from both */
	  return Math.min(Math.min(LevenshteinDistance(s, len_s - 1, t, len_t    ) + 1,
			  		 LevenshteinDistance(s, len_s    , t, len_t - 1) + 1),
	                 LevenshteinDistance(s, len_s - 1, t, len_t - 1) + cost);
	}
	
	// DP implementation of finding the levenshtein distance
	public static int levenshteinDistanceDP(String s, String t){
		
		int lengthS = s.length();
		int lengthT = t.length();
		
		int[][] d = new int[lengthS+1][lengthT+1];
		
		for(int i=0; i <= lengthS; i++){
			d[i][0] = i;
		}
		
		for(int j=0; j<=lengthT; j++){
			d[0][j] = j;
		}
		
		//printMatrix(d,lengthS,lengthT);
		
		for(int i=1; i<=lengthS; i++){
			for(int j=1; j<=lengthT; j++){
				if(s.charAt(i-1) == t.charAt(j-1)){
					d[i][j] = d[i-1][j-1];
				}
				else{
					d[i][j] = Math.min(Math.min(d[i-1][j],d[i][j-1]),d[i-1][j-1])+1;
				}
			}
		}
		
		
		//printMatrix(d,lengthS,lengthT);
		return d[lengthS][lengthT];
		
	}
	
	// To test the matrix during each step of matrix update
	public static void printMatrix(int[][] m, int columnL, int rowL){
		for(int i=0; i<=columnL; i++){
			for(int j=0; j<=rowL; j++){
				System.out.print(m[i][j]+"|");
			}
			System.out.println("\n");
		}
		
		System.out.println("--------------------------------------------");
	}	
	
	public static void main(String[] args){
		// Test
		System.out.println(LevenshteinDistance.levenshteinDistanceDP("sleek", "peek"));
	}

}
