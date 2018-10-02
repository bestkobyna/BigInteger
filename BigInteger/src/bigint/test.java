package bigint;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class test {
	public static void main (String arg[]) {
		Random rand = new Random();
		int court = 0;
		for(int i = 10; i >0; i--) {
			int  n = 1000-rand.nextInt(2010);
			int  m = 1000-rand.nextInt(2010);
			String sum = n+m+"";
			String product = n*m+"";
			BigInteger bign = BigInteger.parse(""+n);
			BigInteger bigm = BigInteger.parse(""+m);
			BigInteger add = BigInteger.add(bign, bigm);
			BigInteger times = BigInteger.multiply(bign,bigm);
			
			//System.out.println(n + " + "+ m);
			//System.out.println(n+m +" ?= " + add);
			//System.out.println(sum.equals(add.toString()));
			if (numdigit(product)==times.numDigits){
				court++;
			}else {
				
				System.out.println(n + " + "+ m);
				System.out.println(n+m +" ?= " + add);
				System.out.println(sum.equals(add.toString()));
				System.out.println(numdigit(product)+"  "+times.numDigits);
		
				//System.out.println(product.equals(times.toString()));
			}
			
		}
		System.out.println(court);
	}
	public static int numdigit(String i) {
		return i.length();
		
	}
}
