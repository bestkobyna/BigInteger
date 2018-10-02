package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		
		//clear space
				integer = integer.replaceAll(" ","");
				
		//test if there is no input
		if (integer.equals("")) {
			throw new IllegalArgumentException();
		}
		
		//Create a BigInteger 
		BigInteger ans = new BigInteger();
		
		//check sign
		if(integer.charAt(0) =='-') {
			ans.negative = true;
			integer = integer.substring(1);
		}else if(integer.charAt(0)=='+') {
			integer = integer.substring(1);
		}
		
		//check no significant zeros
		while((integer.charAt(0)=='0')&&(integer.length()!=1)){
			integer = integer.substring(1);
		}
		
		//Check the integer is 0 and return null list
		if(integer.equals("0")) {
			ans.negative = false;
			return ans;
		}
		
		//parse into BigInteger
		for(int i=0;i<integer.length();i++) {
			int a = 0;
			//test format
			if(Character.isDigit(integer.charAt(i))){
				a = Character.getNumericValue(integer.charAt(i));
			}else {
				throw new IllegalArgumentException();
			}
			
			//parse 
			ans.front = new DigitNode(a,ans.front);
			ans.numDigits++;
					
		}
		
		return ans; 
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		

		BigInteger res = new BigInteger();
	
		//null case test
		if(first.front==null&&second.front==null) {
			return res;
		}
		
		//initialize the var
		int sum = 0;
		int carrier = 0; 
		int borrower = 0;
		res.negative = first.negative; 
		DigitNode ptr1 = first.front;
		DigitNode ptr2 = second.front;
		
		//check which is bigger and decide who minus who 
		if(first.numDigits<second.numDigits) {
			ptr1 = second.front;
			ptr2 = first.front;
			res.negative = second.negative;
		}
		
		res.front = new DigitNode(0,null);
		DigitNode ptr3 = res.front;
		//case for doing adding
		
		//add case for both positive or negative 
		if((first.negative&&second.negative)||(!first.negative&&!second.negative)) {
			//case for have same digits 
			if(first.numDigits==second.numDigits) {
				//run until both ptr point to null
				while(ptr1!=null) {
					//check carrier 
					if(carrier == 1){
						sum = 1;
					}
					sum = sum+ptr1.digit + ptr2.digit;
					//check if large than 10 and set carrier 
					if(sum>=10) {
						sum = sum -10;
						carrier = 1;
					}else {
						carrier = 0;
					}//add digit with ptr3
					ptr3.next = new DigitNode(sum,null);
					ptr3 = ptr3.next;
					res.numDigits++;
					sum = 0;
					ptr1 = ptr1.next;
					ptr2 = ptr2.next;
				}
			
			//case that first and second not have same NumDigit
			}else {
				//run until one of the pointer point to null
				while(ptr1!=null) {
					//do adding 
					if(ptr2!=null) {
						sum = carrier+ptr1.digit + ptr2.digit;
						//check if large than 10 and set carrier 
						if(sum>=10) {
							sum = sum -10;
							carrier = 1;
						}else {
							carrier = 0;
						}
						//add digit with ptr3
						ptr3.next = new DigitNode(sum,null);
						ptr3 = ptr3.next;
						ptr2 = ptr2.next;
					//when ptr2 point to null, just copy the left digits from ptr1
					}else {
						if(ptr1.digit ==9&&carrier == 1) {
							ptr3.next = new DigitNode(0,null);
						}else {
							sum = ptr1.digit+carrier;
							ptr3.next = new DigitNode(sum,null);
							carrier = 0;
						}
						ptr3 = ptr3.next;
					}
					ptr1 = ptr1.next;
					res.numDigits++;
					sum = 0;	
				}
			}
		////check carrier 
		if(carrier==1) {
			ptr3.next = new DigitNode(1,null);
			ptr3 = ptr3.next;
			res.numDigits++;
		}
		res.negative = first.negative;
			
		//case that doing subtraction 
		//divide to two case, same NumDigit or not 
		}else {
			//case that not have same NumDigit
			if(first.numDigits!=second.numDigits) {
				while(ptr2!=null) {
					//check if need borrower
					if(ptr1.digit-borrower<ptr2.digit) {
						sum = ptr1.digit+10-borrower-ptr2.digit;
						borrower = 1;
					}else {
						sum = ptr1.digit-borrower-ptr2.digit;
						borrower = 0;
					}
					//add digit with ptr3
					ptr3.next = new DigitNode(sum,null);
					ptr3 = ptr3.next;
					ptr1 = ptr1.next;
					ptr2 = ptr2.next;
					res.numDigits++;
					sum = 0;
				}
				//copy the left of the digits from the ptr1
				while(ptr1!=null){
					if(ptr1.digit==0&&borrower==1) {
						ptr3.next = new DigitNode(9,null);
					}else {
						ptr3.next = new DigitNode(ptr1.digit-borrower,null);
						borrower = 0;
					}
					ptr3 = ptr3.next;
					ptr1 = ptr1.next;
				}
			}
			
			//case that they both have same NumDigit
			/*for not knowing which integer is bigger, we do the subtraction first - second
			  if first smaller than second, then will have nothing to borrow
			  in that case, reset and then do second - first */
			else {
				//try first - second 
				while(ptr2!=null) {
					//check if need borrower
					if(ptr1.digit - borrower<ptr2.digit) {
						sum = ptr1.digit+10-borrower-ptr2.digit;
						borrower = 1;
					}else {
						sum = ptr1.digit-borrower-ptr2.digit;
						borrower = 0;
					}
					
					//add digit use ptr3
					ptr3.next = new DigitNode(sum,null);
					ptr3 = ptr3.next;
					ptr1 = ptr1.next;
					ptr2 = ptr2.next;
					res.numDigits++;
					sum = 0;
					res.negative = first.negative;
				}
				//test if that fail 
				//if fail, try second - first
				if(borrower == 1) {
					//reset 
					borrower = 0;
					ptr1 = first.front;
					ptr2 = second.front;
					res.front = new DigitNode(0,null);
					ptr3 = res.front;
					
					while(ptr1!=null) {
						//check if need borrower
						if(ptr2.digit-borrower<ptr1.digit) {
							sum = ptr2.digit+10-borrower-ptr1.digit;
							borrower = 1;
						}else {
							sum = ptr2.digit-borrower-ptr1.digit;
							borrower = 0;
						}
						
						//add digit with ptr3
						ptr3.next = new DigitNode(sum,null);
						ptr3 = ptr3.next;
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
						sum = 0;
					}
					res.negative = second.negative;
				}
				
			//test case for getting 0011 after subtract
				
			}
		} 
		res.front = res.front.next;
		
		//remove all not significant zero
		ptr1 = res.front;
		ptr2 = res.front.next;
		while(ptr2!=null) {
			if (ptr2.digit != 0) {
				ptr1 = ptr2;
			}
			ptr2 = ptr2.next;
		}
		ptr1.next = null; 
		
		//if front is null, reset sign
		if (res.front.digit == 0&&res.front.next == null) {
			res.negative = false;
		}
		return res;
	}
	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	
	
	
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		
		//Biginteger for return
		BigInteger ans = new BigInteger();
		
		//null case test
		if(first.front==null||second.front==null) {
			return ans;
		}
		
		//initialize the var
		BigInteger temp = new BigInteger();
		ans.front = new DigitNode(0,null);
		DigitNode ptr1 = first.front;
		DigitNode ptr2 = second.front;
		DigitNode ptr3;
		int product;
		int carrier = 0;
		int court = 0;
		
		//test which is large, do integer first time second if first has more digits
		if (first.numDigits<second.numDigits) {
			ptr2 = first.front;
			ptr1 = second.front;
		}
		
		//run until ptr2 point to null
		while(ptr2!=null) {
			temp.numDigits = 0;
			
			//for perfect run time for case times 0
			if(ptr2.digit == 0) {
				court++;
				ptr2 = ptr2.next;
				continue;
			}
			
			//Set up ptr3 for temporary list 
			ptr3 = new DigitNode(0,null);
			temp.front = ptr3;
			
			//add 0 for different digit case
			for(int i=court; i>0; i--) {
				ptr3.next = new DigitNode(0,null);
				ptr3 = ptr3.next;
				temp.numDigits++;
			}
			
			//run until ptr1 point to null
			while(ptr1!=null){
				product = ptr1.digit*ptr2.digit+carrier;
				carrier = product/10;
				ptr3.next = new DigitNode(product%10,null);
				ptr3 = ptr3.next;
				ptr1 = ptr1.next;
				temp.numDigits++;
			}
			
			//check carrier 
			if(carrier!=0) {
				ptr3.next = new DigitNode(carrier,null);
				ptr3 = ptr3.next;
				temp.numDigits++;
			}
			
			temp.front = temp.front.next;
			ans = BigInteger.add(ans,temp);
			
			//reset 
			carrier = 0;
			ptr2 = ptr2.next;
			court ++;
			if (first.numDigits<second.numDigits) {
				ptr1 = second.front;
			}else {
				ptr1 = first.front;
			}
		}
		
		//implement NumDigits
		ans.numDigits = temp.numDigits;
		
		//check sign
		if(first.negative!=second.negative) {
			ans.negative = true;
		}
		
		//for the integer stored in front is 0, reset sign
		if (ans.front.digit == 0&&ans.front.next == null) {
			ans.negative = false;
		}
		
		return ans;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	
}
