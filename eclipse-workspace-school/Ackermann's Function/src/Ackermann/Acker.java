package Ackermann;

public class Acker {
	public static int Ack(int i, int j) {
		return (i==0)?(j+1):(j==0?Ack(i-1,1):(Ack(i-1,Ack(i,j-1))));
	}
	
	public static int fib(int i) {
		return i==0||i==1?1:fib(i-1)+fib(i-2);
	}
	
	public static void main(String[] args) {
		/*String printVal = "";
		for (int i = 0; i<10; i++) {
			printVal += Ack(0,i) + ", ";
		}
		printVal += "\n";
		for (int i = 0; i<10; i++) {
			printVal += Ack(1,i) + ", ";
		}
		printVal += "\n";
		for (int i = 0; i<10; i++) {
			printVal += Ack(2,i) + ", ";
		}
		printVal += "\n";
		for (int i = 0; i<5; i++) {
			printVal += Ack(3,i) + ", ";
		}
		printVal += "\n";
		for (int i = 0; i<1; i++) {
			printVal += Ack(4,i) + ", ";
		}
		printVal += "\n";
		System.out.print(printVal);*/
		String printVal = "";
		for (int i = 30; i<35; i++) {
			printVal += fib(i) + ", ";
		}
		System.out.print(printVal);
	}
}
