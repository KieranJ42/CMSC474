package cmsc420_s23;

public class Tester {
	
	public static void main(String[] args) {
		AdjustableStack<String> stack = new AdjustableStack<String>();
		System.out.println(stack);
		AdjustableStack<String>.Locator l1 = stack.push("Hello");
		AdjustableStack<String>.Locator l2 = stack.push(", ");
		stack.push("World");
		System.out.println(stack);
		stack.promote(l1);
		System.out.println(stack);
		AdjustableStack<String>.Locator l3 = stack.push("Hola");
		System.out.println(stack);
		stack.demote(l3);
		System.out.println(stack);
		System.out.println(stack.getDepth(l3));
		System.out.println(stack.getDepth(l2));
	}
	
}
