// Name: Suphavadee Cheng (Individual)
// ID: 6488120
// Sec: 1

import java.util.Scanner;

public class CRC {
	public static final int MAX_SIZE = 8;

	//                                    {"CRC-8",      "CRC-16",             "CRC-32"};	
	public static final String[] BITs = {"111010101","11000000000000101","100000100110000010001110110110111"}; 
	
	public static String CRC_gen(String dataword, String CRC_type){
		
		String originalData = dataword;
		String divisor = ""; // use to store the divisor
		
		if(CRC_type.equals("CRC-8")) {
			divisor = BITs[0];                      // if type of CRC-8  --> divisor = 111010101
		}else if(CRC_type.equals("CRC-16")) {
			divisor = BITs[1];                      // if type of CRC-16 --> divisor = 11000000000000101
		}else if(CRC_type.equals("CRC-32")){
			divisor = BITs[2];                      // if type of CRC-32 --> divisor = 100000100110000010001110110110111
		}
		
		int loop = dataword.length(); // the loop will run following the number of bits in dataword
		
		for(int i=0;i<divisor.length()-1;i++) {
			dataword+='0';                                 // add zero for divisor length - 1 bits
		}
		
		String a = dataword.substring(0,divisor.length()); // dataword in range divisor length
		String b = divisor;                                // divisor
		String c = "";                                     // remainder in each loop
		
		
		for(int i=0;i<loop;i++) {             
			for(int j=0;j<divisor.length();j++) {         // each loop will run from the number of divisor bits

				if(a.charAt(0)!='0') {                    // if the first bit is not zero, c will add string of the bits from a do the XOR with b
					c+=(a.charAt(j)^b.charAt(j));
				}else {                                   // but if the first bit is zero, c will add string of the bits from a do the XOR with 0
					c+=(a.charAt(j)^'0');
				}
			}

			if(dataword.length()!=i+divisor.length()) {  // check that if the number of divisor bits add with 
                										 // the current loop isn't reach the number of dataword bits
				a = c.substring(1,c.length())+dataword.charAt(i+divisor.length()); 
			} else {                                     // this is in case it reach the number of dataword bits
				a = c.substring(1,c.length());
			}

			c="";                                   // set c to empty again
		}
		
		
		return originalData+a; // a is CRC
		
	}
	
	
	public static int CRC_check(String codeword, String CRC_type){
		String divisor = "";
		
		if(CRC_type.equals("CRC-8")) {
			divisor = BITs[0];                      // if type of CRC-8  --> divisor = 111010101
		}else if(CRC_type.equals("CRC-16")) {
			divisor = BITs[1];                      // if type of CRC-16 --> divisor = 11000000000000101
		}else if(CRC_type.equals("CRC-32")){
			divisor = BITs[2];                      // if type of CRC-32 --> divisor = 100000100110000010001110110110111
		}
		
		int loop = codeword.length()+1-divisor.length();   // the loop will run following the number of bits in dataword, 
		                                                   // we find by use the codeword minus with length of divisor and plus one
	
		String a = codeword.substring(0,divisor.length()); 
		String b = divisor;                                // divisor
		String c = "";                                     // remainder in each loop
		
		for(int i=0;i<loop;i++) {
			for(int j=0;j<divisor.length();j++) {         // each loop will run from the number of divisor bits
				if(a.charAt(0)!='0') {                    // if the first bit is not zero, c will add string of the bits from a do the XOR with b
					c+=(a.charAt(j)^b.charAt(j));
				}else {                                   // but if the first bit is zero, c will add string of the bits from a do the XOR with 0
					c+=(a.charAt(j)^'0');
				}
			}
			
			if(codeword.length()!=i+divisor.length()) {  // check that if the number of divisor bits add with 
				                                         // the current loop isn't reach the number of codeword bits
				a = c.substring(1,c.length())+codeword.charAt(i+divisor.length()); // move the position of a and add the bit from codeword
			} else {                                     // this is in case it reach the number of codeword bits
				a = c.substring(1,c.length());
			}
			c="";
		}
		
		System.out.println("Syndrome: "+a);
		
		if(a.contains("1")) return -1; // check that if the bit in a has 1 it will return -1 ---> FAIL

		return 0; // otherwise it will return 0 ---> PASS
	}
	
	public static String get() { // this method will ask for the input
	    Scanner info = new Scanner(System.in);  // create a Scanner object

		String in = info.nextLine();
		String[] data = in.split(" ");
	
		return in; // send back the input
	}
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		boolean statusOP = true; // use to choose the option: generate and check | exit to turn to FALSE
		boolean statusG = true;  // use to generate from the type you choose | press 0 to turn to FALSE
		boolean statusC = true;  // use to check from the type you choose | press 0 to turn to FALSE
		System.out.println("::::: CRC :::::\n");
		
		do {
			
			System.out.print("Please select 'g' to generate codeword or 'c' to check codeword ['e':exit program]: ");
			int func = in.next().charAt(0);
			
			switch (func) {
			case 'g':
				statusG = true;
				System.out.println("\n============ Generating Codeword ============\n");
				
				do{
					
					System.out.println("---------------------------------------------");
					System.out.println("  Press 1 to generate by CRC-8");
					System.out.println("  Press 2 to generate by CRC-16");
					System.out.println("  Press 3 to generate by CRC-32");
					System.out.println("  Press 0 to change function");
					System.out.println("---------------------------------------------");
					
					System.out.print("Select No: ");
					int number = in.nextInt();			
					System.out.println();
					
					
					String codeword = "";
					if(number == 1) {
						System.out.println("Type: CRC-8");
						System.out.print("Insert Dataword: ");
						String dataword = get();
						codeword = CRC_gen(dataword,"CRC-8");
						System.out.println("Codeword: "+codeword+"\n");
						
					}else if(number == 2) {
						System.out.println("Type: CRC-16");
						System.out.print("Insert Dataword: ");
						String dataword = get();
						codeword = CRC_gen(dataword,"CRC-16");
						System.out.println("Codeword: "+codeword+"\n");
						
						
					}else if(number == 3) {
						System.out.println("Type: CRC-32");
						System.out.print("Insert Dataword: ");
						String dataword = get();
						codeword = CRC_gen(dataword,"CRC-32");
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
					System.out.println("  Press 1 to check by CRC-8");
					System.out.println("  Press 2 to check by CRC-16");
					System.out.println("  Press 3 to check by CRC-32");
					System.out.println("  Press 0 to change function");
					System.out.println("---------------------------------------------");
					
					System.out.print("Select No: ");
					int number = in.nextInt();			
					System.out.println();
					
					
					if(number == 1) {
						System.out.println("Type: CRC-8");
						System.out.print("Insert Codeword: ");
						
						String codeword = get();
						int validity = CRC_check(codeword, "CRC-8");
						
						if(validity == 0) System.out.println("\n PASS :)\n");
						else System.out.println("\n FAIL :(\n");
						
					}else if(number == 2) {
						System.out.println("Type: CRC-16");
						System.out.print("Insert Codeword: ");
						String codeword = get();
						
						int validity = CRC_check(codeword, "CRC-16");
						
						if(validity == 0) System.out.println("\n PASS :)\n");
						else System.out.println("\n FAIL :(\n");
						
					}else if(number == 3) {
						System.out.println("Type: CRC-32");
						System.out.print("Insert Codeword: ");
						String codeword = get();
						
						int validity = CRC_check(codeword, "CRC-32");
						
						if(validity == 0) System.out.println("\n PASS :)\n");
						else System.out.println("\n FAIL :(\n");
						
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
