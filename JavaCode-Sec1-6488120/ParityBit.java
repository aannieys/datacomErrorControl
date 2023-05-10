// Name: Suphavadee Cheng (Individual)
// ID: 6488120
// Sec: 1

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParityBit {
	public static final int MAX_SIZE = 8;
	
	public static String parity_gen(String[] dataword, String parity_type, int array_size) {
		// dataword:    An array of bit string of any size up to word_size
		// parity_type: Type of parity: one-dimensional-even, one-dimensional-odd, two-dimensional-even, two-dimensional-odd
		// array_size:  Size of the array of the dataword
		
		int n; //  odd or even
		if(parity_type.contains("Even")) { 
			n = 0; // 0 is represented as an even
		}else {
			n = 1; // 1 is represented as an odd
		}
		
		String codeword = ""; // to store codeword

		if(parity_type.contains("One-Dimensional")){ // this is type of "One-Dimensional"
			codeword = addParity(dataword, n, array_size); // this function will return the codeword
			                                               // we send 'dataword', n (this mention that it is even or odd), and size of array
			
		}else if(parity_type.contains("Two-Dimensional")) { // this is type of "Two-Dimensional"
			codeword = addParity(dataword, n, array_size); // this function will return the codeword
                                                           // we send 'dataword', n (this mention that it is even or odd), and size of array
			                                               // this will add parity at each of the row
			
			String[] codeword2 = codeword.split(" "); // 2 dimensional have multiple datawords 
			                                          // split the dataword and store in codeword2
			
			// this checkone2 will check each of column
			int[] checkone2 = new int[codeword2[0].length()]; 
			
			for(int i=0;i<array_size;i++) {
				for(int j=0;j<codeword2[i].length();j++) {
					if(codeword2[i].charAt(j)=='1') {
						checkone2[j]++; // if that column(i) and each of row(j) is 1, it will increase 1
					}
				}
			}
			
			for(int i=0;i<codeword2[0].length();i++) {
				if(n==0) { //even
					if(checkone2[i]%2==0) {  // if the position of row i, col j mod 2 equal 0 ---> add 0
						codeword+="0";
					}else {                  // if the position of row i, col j mod 2 equal 1 ---> add 1
						codeword+="1";
					}
				}else { //odd
					if(checkone2[i]%2==0) {  // if the position of row i, col j mod 2 equal 0 ---> add 1
						codeword+="1";
					}else {                  // if the position of row i, col j mod 2 equal 1 ---> add 0
						codeword+="0";
					}
				}
			}
		}
		return codeword;
	}
	
	public static int parity_check(String codeword, String parity_type, int array_size) {
		// codeword:    An array of bit string of any size up to word_size 
		// parity_type: Type of parity: one-dimensional-even, one-dimensional-odd, two-dimensional-even, two-dimensional-odd
		// array_size:  Size of the array of the dataword 
		
		int n; //  odd or even
		if(parity_type.contains("Even")) { 
			n = 0; // 0 is represented as an even
		}else {
			n = 1; // 1 is represented as an odd
		}
		
		String[] codewordChk = codeword.split(" "); // this will seperate codeword in to the dataword
		int checkone = 0; // this use to count one
	
		for(int i=0;i<array_size;i++) {

			for(int j=0;j<codewordChk[i].length();j++) {
				if(codewordChk[i].charAt(j)=='1') { 
					checkone++; // if that column(i) and each of row(j) is 1, it will increase 1
				}
			}
			if(n==0) { //even
				if(checkone%2!=0) { // if checkone has number of one which is not even it will return -1 ---> FAIL
					return -1;
				}
			}else { //odd
				if(checkone%2==0) { // if checkone has number of one which is not odd it will return -1 ---> FAIL
					return -1;
				}
			}
			checkone = 0;
		}

		return 0; // if it is success, it will return 0 ---> PASS
	}
	
	public static String addParity(String[] dataword, int n, int array_size) {
		
		String codeword = "";
		int checkone = 0;
		for(int i=0;i<array_size;i++) {
			codeword+=dataword[i];
			for(int j=0;j<dataword[i].length();j++) {
				if(dataword[i].charAt(j)=='1') {
					checkone++; // if that column(i) and each of row(j) is 1, it will increase 1
				}
			}
			if(n==0) { //even
				if(checkone%2==0) {  // if the position of row i, col j mod 2 equal 0 ---> add 0
					codeword+="0 ";
				}else {              // if the position of row i, col j mod 2 equal 1 ---> add 1
					codeword+="1 ";
				}
			}else { //odd
				if(checkone%2==0) {  // if the position of row i, col j mod 2 equal 0 ---> add 1
					codeword+="1 ";
				}else {              // if the position of row i, col j mod 2 equal 1 ---> add 0
					codeword+="0 ";
				}
			}
			checkone = 0;
		}
		
		return codeword;
	}
	
	
	public static String get() { // this method will ask for the input
	    Scanner info = new Scanner(System.in);  // create a Scanner object

		String in = info.nextLine();
		String[] data = in.split(" ");

		int length = data[0].length(); // the bits of the first dataword

		int check = 0; 
		for(int i=0;i<data.length;i++) {
			if(length == data[i].length()) {
				check++; // check the number of dataword that has the same bits. if it same, it will increase 1
			}
			if(length<data[i].length()) {
				length = data[i].length();
			}
		}
		
		// in the case input is not what exactly as we want
		if(check!=data.length && length > MAX_SIZE) { // if not every dataword has the same length and the bit in dataword is exceed 8 bits, it will print this message.
			System.out.println("XXX Not same bits & Too many bits XXX");
			return "";
		}else if(check!=data.length) {                     // if not every dataword has the same length, it will print this message.
			System.out.println("XXX Not same bits XXX");
			return "";
		}else if(length > MAX_SIZE) {                 // if the bit in dataword is exceed 8 bits, it will print this message.
			System.out.println("XXX Too many bits XXX");
			return "";
		}
		return in; // send back the input
	}
	
	public static String getCode() { // this method will ask for the input
	    Scanner info = new Scanner(System.in);  // create a Scanner object

		String in = info.nextLine();

		return in; // send back the input
	}
	
	public static String connect(String[] get) {
		String str = "";
		
		for(int i=0;i<get.length;i++) {
			str+=get[i]+" ";
		}

		return str;
	}

	
    public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		boolean statusOP = true; // use to choose the option: generate and check | exit to turn to FALSE
		boolean statusG = true;  // use to generate from the type you choose | press 0 to turn to FALSE
		boolean statusC = true;  // use to check from the type you choose | press 0 to turn to FALSE
		System.out.println("::::: ParityBit :::::\n");
		
		do {
			
			System.out.print("Please select 'g' to generate codeword or 'c' to check codeword ['e':exit program]: ");
			int func = in.next().charAt(0);
			
			switch (func) {
			case 'g':
				statusG = true;
				System.out.println("\n============ Generating Codeword ============\n");
				
				do{
					
					System.out.println("---------------------------------------------");
					System.out.println("  Press 1 to generate by one-dimensional-even");
					System.out.println("  Press 2 to generate by one-dimensional-odd");
					System.out.println("  Press 3 to generate by two-dimensional-even");
					System.out.println("  Press 4 to generate by two-dimensional-odd");
					System.out.println("  Press 0 to change function");
					System.out.println("---------------------------------------------");
					
					System.out.print("Select No: ");
					int number = in.nextInt();			
					System.out.println();
					
					
					String codeword = "";
					if(number == 1) {
						System.out.println("Type: One-Dimensional-Even");
						System.out.print("Insert Dataword: ");
						String[] dataword = get().split(" ");
						codeword = parity_gen(dataword,"One-Dimensional-Even", dataword.length);
						if(!codeword.equals(" ")) System.out.println("Codeword: "+codeword+"\n");
						
					}else if(number == 2) {
						System.out.println("Type: One-Dimensional-Odd");
						System.out.print("Insert Dataword: ");
						String[] dataword = get().split(" ");
						codeword = parity_gen(dataword,"One-Dimensional-Odd", dataword.length);
						if(!codeword.equals(" ")) System.out.println("Codeword: "+codeword+"\n");
						
						
					}else if(number == 3) {
						System.out.println("Type: Two-Dimensional-Even");
						System.out.print("Insert Dataword: ");
						String[] dataword = get().split(" ");
						codeword = parity_gen(dataword,"Two-Dimensional-Even", dataword.length);
						if(!codeword.equals(" ")) System.out.println("Codeword: "+codeword+"\n");
						
						
					}else if(number == 4) {
						System.out.println("Type: Two-Dimensional-Odd");
						System.out.print("Insert Dataword: ");
						String[] dataword = get().split(" ");
						codeword = parity_gen(dataword,"Two-Dimensional-Odd", dataword.length);
						if(!codeword.equals(" ")) System.out.println("Codeword: "+codeword+"\n");
						
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
					System.out.println("  Press 1 to check by one-dimensional-even");
					System.out.println("  Press 2 to check by one-dimensional-odd");
					System.out.println("  Press 3 to check by two-dimensional-even");
					System.out.println("  Press 4 to check by two-dimensional-odd");
					System.out.println("  Press 0 to change function");
					System.out.println("---------------------------------------------");
					
					System.out.print("Select No: ");
					int number = in.nextInt();			
					System.out.println();
					
					
					if(number == 1) {
						System.out.println("Type: One-Dimensional-Even");
						System.out.print("Insert Codeword: ");
						String codeword = getCode();
						
						int validity = parity_check(codeword, "One-Dimensional-Even", codeword.split(" ").length);
						
						if(validity == 0) System.out.println("\n PASS :)\n");
						else System.out.println("\n FAIL :(\n");
						
					}else if(number == 2) {
						System.out.println("Type: One-Dimensional-Odd");
						System.out.print("Insert Codeword: ");
						String codeword = getCode();
						
						int validity = parity_check(codeword, "One-Dimensional-Odd", codeword.split(" ").length);
						
						if(validity == 0) System.out.println("\n PASS :)\n");
						else System.out.println("\n FAIL :(\n");
						
					}else if(number == 3) {
						System.out.println("Type: Two-Dimensional-Even");
						System.out.print("Insert Codeword: ");
						String codeword = getCode();
						
						int validity = parity_check(codeword, "Two-Dimensional-Even", codeword.split(" ").length);
						
						if(validity == 0) System.out.println("\n PASS :)\n");
						else System.out.println("\n FAIL :(\n");
						
					}else if(number == 4) {
						System.out.println("Type: Two-Dimensional-Odd");
						System.out.print("Insert Codeword: ");
						String codeword = getCode();
						
						int validity = parity_check(codeword, "Two-Dimensional-Odd", codeword.split(" ").length);
						
						if(validity == 0) System.out.println("\n PASS :)\n");
						else System.out.println("\n FAIL :(\n");
						
					}else if(number == 0) {
						System.out.println("Change Function\n");
						statusC = false;
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
