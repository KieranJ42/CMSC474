package cmsc420_s23;

public class MyTester {
	public static void main(String[] args) {
		SMkdTree<Airport> tree = new SMkdTree<Airport>(5, new Rectangle2D(new Point2D(0,0),new Point2D(10,10)));
		try {
			tree.insert(new Airport("abc","def",4,4));
			tree.insert(new Airport("def","ghi",6,6));
			tree.insert(new Airport("ghi","jkl",4,6));
			tree.insert(new Airport("jkl","mno",6,4));
			tree.insert(new Airport("mno","pqr",2,2));
			tree.insert(new Airport("abc","def",2,8));
			tree.insert(new Airport("def","ghi",8,2));
			tree.insert(new Airport("ghi","jkl",8,8));
			tree.insert(new Airport("jkl","mno",8,4));
			tree.insert(new Airport("mno","pqr",4,8));
			tree.insert(new Airport("abc","def",8,6));
			tree.insert(new Airport("def","ghi",6,8));
			tree.insert(new Airport("ghi","jkl",6,2));
			tree.insert(new Airport("jkl","mno",2,6));
			tree.insert(new Airport("mno","pqr",4,2));
			tree.insert(new Airport("mno","pqr",2,4));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failure");
			e.printStackTrace();
		}
		System.out.println(tree);
	}
}
