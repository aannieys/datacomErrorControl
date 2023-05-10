// Name: Suphavadee Cheng (Individual)
// ID: 6488120
// Sec: 1

import java.util.Scanner;

public class Checksum {
	public static final int MAX_SIZE = 8;
	
	
	public static String Checksum_gen(String[] dataword, int num_blocks){
		
		int sumDe = addBi(dataword); // add all of the data together
		String sum = toBinary(sumDe); // convert back to binary

		String wrapped = wrap(sum,dataword[0].length()); // this function use for make a space so we will use it to split in further method 
		
		System.out.println("\nsum: "+wrapped+" ("+sumDe+")"); // show the sum in both binary and decimal number
		
		String wrappedsum = wrap(toBinary(addBiWrap(wrapped.split(" "))),dataword[0].length()); 
		// first, we use 'addBiWrap' function to add it with the extra bits
		// second, we convert the summation to be in binary bits
		// third, we use wrap to add zero (in the front) 
		
		System.out.println("wrapped sum: "+wrappedsum);
		
		String checksum = comp(wrappedsum);
		// convert 'wrappedsum' by using one's complement 
		System.out.println("actual checksum: "+checksum);
		
		String send = "";
		
		for(int i=0;i<dataword.length;i++) {
			send+=dataword[i]+" "; // add the checksum to dataword
		}
		return send+checksum;
	}
	
	
	
	public static int Checksum_check(String codeword, int num_blocks){
		
		String[] words = codeword.split(" "); // split the codeword into dataword+checksum 
		int sumDe = addBi(words); // add all of the data together 
		String sum = toBinary(sumDe); // convert back to binary

		String wrapped = wrap(sum,words[0].length()); // make space so we can split it as a word in further and add zero if it needed
		System.out.println( "\nadd: "+wrapped+" ("+sumDe+")");
		
		String wrappedsum = wrap(toBinary(addBiWrap(wrapped.split(" "))),words[0].length());
		// first, we use 'addBiWrap' function to add it with the extra bits
		// second, we convert the summation to be in binary bits
		// third, we use wrap to add zero (in the front) and it will seperate the word from the length of the first word bits
		
		System.out.println( "wrapped sum: "+wrappedsum);
		
		String checksum = comp(wrappedsum);
		System.out.println( "calcuated checksum: "+checksum);
		
		if(checksum.contains("1")) return -1; // if checksum has 1 that's mean it is ---> FAIL
	
		return 0; // PASS
	}

	public static String comp(String comp) { // One's Complement
		String c = "";
		
		for(int i=0;i<comp.length();i++) {
			if(comp.charAt(i)=='0') { // if the bit is 0 ---change-to---> 1
				c+="1";
			}else {                   // if the bit is 1 ---change-to---> 0
				c+="0";
			}
		}
		return c;
	}
	
	public static String wrap(String sum,int l) { // sum(binary), l(length)
		String addzero = ""; // we use this to add zero so it has all of the required bit 
		
		if(sum.length()<l) {                         // if the number of bit of sum is less than length of dataword
			                                         // that mean we have to add zero to make it full
			for(int i=0;i<l-sum.length();i++) {      // we will add more on the difference of length and the number of bit of sum 
				addzero+="0";
			}
			return addzero+sum;
			
		}else if(sum.length()==l) {                  // if the number of bit of sum has the same length of dataword
			return sum;                              // that mean we don't have to add anything
			
		}else {                                      // if the number of bit of sum is more than length of dataword
			for(int i=0;i<2*l-sum.length();i++) {    // we will add more on the difference of 2*length and the number of bit of sum 
				addzero+="0";
			}
			addzero+=sum;
			
		    String addspace = "";
			for(int i=0;i<2*l;i++) {
				addspace+=addzero.charAt(i);
				if(i==l-1) {
					addspace+=" ";                   // this loop is use for add the space to separate the word
				}
			}
			return addspace;
		
		}
	}
	
	public static int addBiWrap(String[] dataword){

		// this use to convert from binary to decimal and add it
		int mul=1; // mul: multiply - if bit is one, mul=1 | if bit is zero, mul=0
		int index=0; // this will use for doing the power
		int sum = 0; 
		
		for(int i=0;i<dataword.length;i++) {
			for(int j=dataword[i].length()-1;j>=0;j--) { // j will point to the index from back to front
				if(dataword[i].charAt(j)=='0') mul=0; // if that bit is zero, we will multiply with zero
				sum+=(Math.pow(2, index)*mul);
				mul=1; // set mul to 1 again
				index++;
			}
			index=0;
		}
		
		return sum;
	}
	
	public static int addBi(String[] dataword){

		// this use to convert from binary to decimal and add it
		int mul=1; // mul: multiply - if bit is one, mul=1 | if bit is zero, mul=0
		int index=0; // this will use for doing the power
		int sum = 0; 
		
		for(int i=0;i<dataword.length;i++) {
			for(int j=dataword[i].length()-1;j>=0;j--) { // j will point to the index from back to front
				if(dataword[i].charAt(j)=='0') mul=0; // if that bit is zero, we will multiply with zero
				sum+=(Math.pow(2, index)*mul);
				mul=1; // set mul to 1 again
				index++;
			}
			index=0;
		}
		
		return sum;
	}
	
	public static String toBinary(int sum){

		String binary = "";
		
		//check how many bits when convert from decimal to binary
		while(sum!=1) {
			binary += sum%2; //keep the value when it mod by 2
			sum = sum/2; // always divided by 2
		}	
		binary += "1"; //add the last digit which is one

		String toBinary = "";
		//print out binary number and the loop will be reverse
		for(int j=binary.length()-1;j>=0;j--) {
			toBinary+=(binary.charAt(j));     
		}
	
		return toBinary;
	}
	
	public static String get() { // this method will ask for the input
	    Scanner info = new Scanner(System.in);  // create a Scanner object

		String in = info.nextLine();
		
		return in; // send back the input
	}
	
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		boolean statusOP = true; // use to choose the option: generate and check | exit to turn to FALSE
		boolean statusG = true;  // use to generate from the type you choose | press 0 to turn to FALSE
		boolean statusC = true;  // use to check from the type you choose | press 0 to turn to FALSE
		System.out.println("::::: Checksum :::::\n");
		do {
			
			System.out.print("Please select 'g' to generate codeword or 'c' to check codeword ['e':exit program]: ");
			int func = in.next().charAt(0);
			
			switch (func) {
			case 'g':
				statusG = true;
				System.out.println("\n============ Generating Codeword ============\n");
				
				do{
					
					System.out.println("---------------------------------------------");
					System.out.println("  Press 1 to generate codeword");
					System.out.println("  Press 0 to change function");
					System.out.println("---------------------------------------------");
					
					System.out.print("Select No: ");
					int number = in.nextInt();			
					System.out.println();
					
					
					String codeword = "";
					if(number == 1) {
						System.out.print("Insert Dataword: ");
						String[] dataword = get().split(" ");

						if(dataword[0].contains("o")) {
							String Onedata = dataword[0].substring(1);
							System.out.println("Codeword: "+Onedata+" "+comp(Onedata)+"\n");
						}
						if(dataword.length!=1) {
							System.out.println(dataword.length+" Word");
							codeword = Checksum_gen(dataword,dataword.length);
							System.out.println("Codeword: "+codeword+"\n");
						}
						
					}else if(number == 0) {
						System.out.println("Change Function\n");
						statusG = false;
					}else {
						System.out.println("Invalid\n");
					}
					
				}while(statusG);
				
				break;
			case 'c':
				System.out.println("\n============= Checking Codeword =============\n");
				statusC = true;
				
				do{
					
					System.out.println("---------------------------------------------");
					System.out.println("  Press 1 to check codeword");
					System.out.println("  Press 0 to change function");
					System.out.println("---------------------------------------------");
					
					System.out.print("Select No: ");
					int number = in.nextInt();			
					System.out.println();
					
					
					if(number == 1) {
						System.out.print("Insert Codeword: ");
						
						String codeword = get();
						
						if(codeword.contains("o")) {
							System.out.println("Need more than one data");
						}
						if(codeword.length()!=1) {
							System.out.println(codeword.split(" ").length+" Word");
							int validity =  Checksum_check(codeword, codeword.length());

							if(validity == 0) System.out.println("\n PASS :)\n");
							else System.out.println("\n FAIL :(\n");
						}
						
					}else if(number == 0) {
						System.out.println("Change Function\n");
						statusC = false;
					}else {
						System.out.println("Invalid\n");
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
