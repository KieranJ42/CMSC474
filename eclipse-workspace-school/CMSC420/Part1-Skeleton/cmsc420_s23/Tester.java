package cmsc420_s23;

public class Tester {
	/*public static void main(String[] args) {
		WtLeftHeap<Integer,String> heap = new WtLeftHeap<Integer,String>();
		
		WtLeftHeap<Integer,String>.Locator loc1 = heap.insert(4,"dog");
		WtLeftHeap<Integer,String>.Locator loc2 = heap.insert(3,"cat");
		WtLeftHeap<Integer,String>.Locator loc3 = heap.insert(2,"alpaca");
		WtLeftHeap<Integer,String>.Locator loc4 = heap.insert(17,"sheep");
		WtLeftHeap<Integer,String>.Locator loc5 = heap.insert(1,"cow");
		WtLeftHeap<Integer,String>.Locator loc6 = heap.insert(7,"moose");
		WtLeftHeap<Integer,String>.Locator loc7 = heap.insert(1,"hippo");
		try {
			heap.updateKey(loc7, 14);
			heap.updateKey(loc3, 15);
			heap.updateKey(loc1, 6);
			heap.updateKey(loc5, 4);
			heap.updateKey(loc4, 24);
			heap.updateKey(loc6, 13);
			heap.updateKey(loc1, 34);
			heap.updateKey(loc2, 4);
			heap.updateKey(loc7, 1);
			heap.updateKey(loc2, 9);
		} catch(Exception ex) {}
		System.out.println(loc1);
		System.out.println(loc2);
		System.out.println(loc3);
		System.out.println(loc4);
		System.out.println(loc5);
		System.out.println(loc6);
		System.out.println(loc7);
		System.out.println(heap.list());
	}*/
	
	public static void main(String[] args) {
		WtLeftHeap<Integer,String> heap = new WtLeftHeap<Integer,String>();
		WtLeftHeap<Integer,String>.Locator BWI = heap.insert(88,"BWI");
		WtLeftHeap<Integer,String>.Locator LAX = heap.insert(61,"LAX");
		WtLeftHeap<Integer,String>.Locator IAD = heap.insert(47,"IAD");
		WtLeftHeap<Integer,String>.Locator DCA = heap.insert(33,"DCA");
		WtLeftHeap<Integer,String>.Locator JFK = heap.insert(26,"JFK");
		WtLeftHeap<Integer,String>.Locator ATL = heap.insert(52,"ATL");
		WtLeftHeap<Integer,String>.Locator PVD = heap.insert(13,"PVD");
		
		WtLeftHeap<Integer,String> heap2 = new WtLeftHeap<Integer,String>();
		WtLeftHeap<Integer,String>.Locator SEA = heap2.insert(96,"SEA");
		WtLeftHeap<Integer,String>.Locator CLT = heap2.insert(82,"CLT");
		WtLeftHeap<Integer,String>.Locator BOS = heap2.insert(72,"BOS");
		WtLeftHeap<Integer,String>.Locator ORD = heap2.insert(37,"ORD");
		WtLeftHeap<Integer,String>.Locator DFW = heap2.insert(55,"DFW");
		WtLeftHeap<Integer,String>.Locator DEN = heap2.insert(23,"DEN");
		WtLeftHeap<Integer,String>.Locator MIA = heap2.insert(11,"MIA");
		
		try {
			heap2.extract();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		heap.mergeWith(heap2);
		
		System.out.println(heap.list());
		System.out.println(heap2.list());
		System.out.println(heap.size());
		
		try {
			heap.extract();
			heap.extract();
			heap.extract();
			heap.extract();
			heap.extract();
			heap.extract();
			heap.extract();
			heap.extract();
			heap.extract();
			heap.extract();
			heap.extract();
			heap.extract();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(heap.list());
		try {
			heap.updateKey(MIA,32);
			System.out.println(heap.list());
			heap.updateKey(MIA,5);
			System.out.println(heap.list());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
