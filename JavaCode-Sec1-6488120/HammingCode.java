// Name: Suphavadee Cheng (Individual)
// ID: 6488120
// Sec: 1

import java.util.Scanner;

public class HammingCode {
	
	public static String Hamming_gen(String dataword) {
		
		int numR = numRedundancy(dataword); // find the amount of no. redundant
		
		String s = addPosOfRedundant(dataword,numR); //add the position of r, so it will be fill in the further

		String[] keep = setOfBitOfEachR(s, numR); // this keep the value of set of bit that is calculated to get value of r

		String[] r = new String[numR];               // this keep the value of r
		
		for(int i=0;i<numR;i++) {
			r[i] = findRedundant(keep[i]);        // find the redundant from the set of bits that is kept above
		}
		
		String codeword = "";  // keep the codeword
		int i = 0;             // use for power of 2
		
		for(int pos=0;pos<s.length();pos++) {
			if(pos+1==Math.pow(2, i)) {
				codeword += r[i];                 // add the bit of redundant
				i++;
			}else {
				codeword += s.charAt(pos);        // add the bit in dataword
			}
		}

		return reverseStr(codeword);              // reverse back
	}
	
	public static int Hamming_check(String codeword) {

		int numR = numRedundancy(codeword);              // find the amount of no. redundant
		
		String[] keep = setOfBitOfEachR(reverseStr(codeword), numR); // this keep the value of set of bit that is calculated to get value of r

		int checkone = 0;
		int[] invalid = new int[numR];
		for(int i=0;i<numR;i++) {                        // run the loop from the amount if redundant 
			for(int j=0;j<keep[i].length();j++) {        // run the loop from each length of set of bit from each 'r'
				if(keep[i].charAt(j) == '1') {         
					checkone++;
				}
			}
			if(checkone%2!=0) { // if checkone has number of one which is not even it will return -1 ---> FAIL
				invalid[i] = 1;
			}
			checkone = 0;
		}
		
		int errPos = 0;
		for(int i=0;i<numR;i++){	
			errPos+=(Math.pow(2,i )*invalid[i]);
		}
		return errPos;  // if it is success, it will return 0 ---> PASS
	}
	
	public static String[] setOfBitOfEachR(String s, int numR) {
		int go = 0;   // this will start when it is in the position of r (1,2,4,8,16,...)
		int next = 1; // this use to indicate the next set of
		
		String[] keep = new String[numR];
		// this loop use to find the set of bits that is used to calculate 'r'
		for(int i=0;i<numR;i++) {                     // run from the amount of no. redundant
			int posR = (int) Math.pow(2, i);          // posR: keep the position of R (1,2,4,8,16,...)
			keep[i] = "";
			for(int pos=1;pos<=s.length();pos++) {    // this will run the position of the string
				if(pos==posR) {                       // if position is equal 2 power of i that's mean it's position of redundant
 					go = 1;                           // 'go' indicates that you will start to keep the set of bit
				}
				if(go>=1) {                           
					if(pos%posR==0) {                 // we mod this to indicate that every range of posR it will come to this condition
						next++;                       // increase 'next' to indicate that we will skip the next set of bit
					}
					go++;                             // increase 'go' inside the condition because we want 
					                                  // to start this condition only when it's reach the position posR
				}
				if(next%2==0) {                       // next mod 2 use to indicate when to use or when to skip the bit  
					keep[i] += s.charAt(pos-1);       // add that bit in 'keep' attribute 
				}
			}	
			go = 0;       // set 'go' to 0
			next = 1;     // set 'next' to 1
		}
		return keep;
	}
	
	public static String reverseStr(String str) {
	    String reverse = "";                  

	    for(int i=str.length()-1;i>=0;i--) { // start from the last bit to the first bit 
	    	reverse += str.charAt(i);        // keep it in attribute named 'reverse'
	    }
	    return reverse;
	}
	
	public static String findRedundant(String word) {
		int checkone = 0;
		for(int i=0;i<word.length();i++) {
			if(word.charAt(i)=='1') {
				checkone++;  // if that bit is 1, it will increase by 1
			}
		}
		if(checkone%2==0) {  // if the amount of no.1 is even, it bit of R will be 0
			return "0";
		}else {              // if the amount of no.1 is even, it bit of R will be 1
			return "1";
		}	
	}
	
	public static String addPosOfRedundant(String word,int numR) {
		word = reverseStr(word);                             // reverse the string first to start the first position at index 0
		String codeword = "";
		int numWord = 0;                                     // this will count for the index of number of dataword
		int i = 0;                                           // this use for get the power of 2
		for(int pos=0;pos<word.length()+numR;pos++) {
			if(pos+1==Math.pow(2, i)) {                      // if the position is in the 2 power of i (0,1,2,4,...) 
				codeword += "x";                             // it will add 'x' instead
				i++;
			}else {                                          // otherwise, it will add the next character in dataword
				codeword += word.charAt(numWord);
				numWord++;
			}
		}
		return codeword;
	}
	
	public static int numRedundancy(String dataword) {
		int d = dataword.length()-1; 
		int pos = 1;                          // position
		int pow = 0;                          // power
		int num_r = 0;                        // count the number of r 
		
		while(d>=0) {                         // check until the 'd' is less than 0
			if(pos==Math.pow(2,pow)) {        // if the position is in the 2 power of 'pow' 
				num_r++;                      // it will increase the amount of r
				pow++;                        // it will increase the power
			}else {                           // otherwise,
				d--;                          // it will decrese 'd' which is indicate the remainder of d
			}
			pos++;                     
		}
		return num_r;	
	}
	
	public static String get() {
		Scanner info = new Scanner(System.in);  // Create a Scanner object
		String in = info.nextLine();
		return in;
	}
	
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		boolean statusOP = true;
		boolean statusG = true;
		boolean statusC = true;
		System.out.println("::::: Hamming Code :::::\n");
		
		do {
			
			System.out.print("Please select 'g' to generate codeword or 'c' to check codeword ['e':exit program]: ");
			int func = in.next().charAt(0);
			
			switch (func) {
			case 'g':
				statusG = true;
				System.out.println("\n============ Generating Codeword ============\n");
				
				do{
					
					System.out.println("---------------------------------------------");
					System.out.println("  Press 1 to generate by Hamming code");
					System.out.println("  Press 0 to change function");
					System.out.println("---------------------------------------------");
					
					System.out.print("Select No: ");
					int number = in.nextInt();			
					System.out.println();
					
					
					String codeword = "";
					if(number == 1) {
			
						System.out.print("Insert Dataword: ");
						String dataword = get();
						codeword = Hamming_gen(dataword);
						System.out.println("Codeword: "+codeword+"\n");
						
						
					}else if(number == 0) {
						System.out.println("Change Function\n");
						statusG = false;
					}else {
						System.out.println("Invalid");
					}
					
				}while(statusG);
				
				break;
			case 'c':
				System.out.println("\n============= Checking Codeword =============\n");
				statusC = true;
				
				do{
					
					System.out.println("---------------------------------------------");
					System.out.println("  Press 1 to check by Hamming code");
					System.out.println("  Press 0 to change function");
					System.out.println("---------------------------------------------");
					
					System.out.print("Select No: ");
					int number = in.nextInt();			
					System.out.println();
					
					
					if(number == 1) {
						System.out.print("Insert Codeword: ");
						
						String codeword = get();
						int validity = Hamming_check(codeword);
						
						if(validity == 0) System.out.println("\n PASS :)\n");
						else System.out.println("\n FAIL :( at position "+validity+"\n");
						
				
					}else if(number == 0) {
						System.out.println("Change Function\n");
						statusC = false;
					}else {
						System.out.println("Invalid");
					}
					
				}while(statusC);
				
				break;
			
			case 'e':
				statusOP = false;
				break;
				
			default:
				System.out.println("Invalid Function");
			}
			
				
		}while(statusOP);

	}
}
