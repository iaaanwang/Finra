import java.util.*;

class Point{

	double x, y;

	public Point(double a, double b) {
		this.x = a;
		this.y = b;
	}

	@Override
	public String toString() {
		return "[x: " + this.x + ", y: " + this.y + "]"; 
	}

}

class Rectangle{

	Point topLeft;
	Point bottomRight;

	public Rectangle(Point a, Point b) {

		this.topLeft = a;
		this.bottomRight = b;

	}

}

class TreeNode{
	int val;
	TreeNode left, right;

	public TreeNode(int v) {
		this.val = v;
	}
}

class ListNode{

	int val;
	ListNode next;

	public ListNode(int v) {this.val = v;}

}

class Node{

	int val;
	Node prev, next;

	public Node(int v) {this.val = v;}

}



class Order{

	String name;
	public Order(String n) {this.name = n;}

	@Override
	public String toString() {
		return this.name; 
	}

}

class OrderDependency{

	Order order, dependent;

	public OrderDependency(Order a, Order b) {
		this.order = a;
		this.dependent = b;
	}

}

class Score{

	int id;
	int grade;

	public Score(int id, int g) {

		this.id = id;
		this.grade = g;

	}

}




public class Amazon{

	public List<Integer> windowSum(List<Integer> nums, int k) {
		List<Integer> res = new ArrayList<>();
		int cnt = 0, sum = 0;
		for (int i = 0; i < nums.size(); i++) {
			sum += nums.get(i);
			if (++cnt == k) {
				res.add(sum);
				sum -= nums.get(i - k + 1);
				cnt--;
			}
		}
		return res;
	}





	public Point[] kNearestPoints(Point[] array, Point origin, int k) {

		// List<Point> res = new ArrayList<>();

		if (k <= 0) return new Point[0];
		if (k >= array.length) return array;

		Point[] res = new Point[k];

		PriorityQueue<Point> pq = new PriorityQueue<>(new Comparator<Point>() {

			public int compare(Point a, Point b) {
				double adis = (a.x - origin.x)*(a.x - origin.x) + (a.y - origin.y)*(a.y - origin.y);
				double bdis = (b.x - origin.x)*(b.x - origin.x) + (b.y - origin.y)*(b.y - origin.y);
				return Double.compare(bdis, adis);
			}

		});

		for (Point p : array) {
			pq.offer(p);
			if (pq.size() > k) {
				pq.poll();
			}
		}

		for (int i = 0; i < k ; i++) {
			res[i] = pq.poll();
		}

		return res;

	}





	public boolean rectangleOverlap(Rectangle a, Rectangle b) {

		return overlapHelper(a.topLeft, a.bottomRight, b.topLeft, b.bottomRight);

	}

	private boolean overlapHelper(Point al, Point ar, Point bl, Point br) {

		if (al.x > br.x || ar.x < bl.x) return false;

		if (al.y < br.y || ar.y > bl.y) return false;

		return true;
	}





	// Find Minimum Path sum from root to any leaf.

	public int minPathSum(TreeNode root) {
		if (root == null) return 0;

		// int l = minPathSum(root.left);
		// int r = minPathSum(root.right);

		if (root.left != null && root.right != null) return Math.min(minPathSum(root.left), minPathSum(root.right)) + root.val;

		if (root.left != null) return minPathSum(root.left) + root.val;

		return minPathSum(root.right) + root.val;

	}





	//Insert a value into ordered cycle list

	public ListNode insertCycleList(ListNode arb, int val) {

		ListNode newNode = new ListNode(val);

		if (arb == null) {
			newNode.next = newNode;
			return newNode;
		}

		ListNode cur = arb;

		while (true) {
			if (cur.val <= val && cur.next.val >= val) {
				break;
			}
			if (cur.val > cur.next.val && (val > cur.val || val < cur.next.val)) {
				break;
			}
			cur = cur.next;
			if (cur == arb) break;
		}

		newNode.next = cur.next;
		cur.next = newNode;

		return newNode;
	}





	//Order Dependency -> Topo-Sort

	public List<Order> getOrderList(List<OrderDependency> orderDependencies) {

		Map<Order, Set<Order>> orderMap = new HashMap<>();
		Map<Order, Integer> inDegree = new HashMap<>();

		for (OrderDependency od : orderDependencies) {

			Order ord = od.order;
			Order dep = od.dependent;

			if (!orderMap.containsKey(ord)) orderMap.put(ord, new HashSet<Order>());
			// orderMap.get(ord).add(dep);

			inDegree.putIfAbsent(dep, 0);
			inDegree.putIfAbsent(ord, 0);

			if (orderMap.get(ord).add(dep)) inDegree.put(dep, inDegree.get(dep) + 1);

		}

		Queue<Order> q = new LinkedList<>();

		for (Order o : inDegree.keySet()) {
			if (inDegree.get(o) == 0) q.offer(o);
		}

		List<Order> res = new ArrayList<>();

		while (!q.isEmpty()) {
			Order cur = q.poll();
			res.add(cur);
			if (!orderMap.containsKey(cur)) continue;
			for (Order dep : orderMap.get(cur)) {
				inDegree.put(dep, inDegree.get(dep) - 1);
				if (inDegree.get(dep) == 0) q.offer(dep);
			}
		}

		return res;

	}






	public String maxOccurString(List<String> strs) {

		int max = 0, idx = -1;
		Map<String, Integer> imap = new HashMap<>();

		for (int i = 0; i < strs.size(); i++) {
			// if (!imap.containsKey(strs.get(i))) imap.put(strs.get(i), 0);
			imap.put(strs.get(i), imap.getOrDefault(strs.get(i), 0) + 1);

			if (imap.get(strs.get(i)) > max) {
				idx = i;
				max = imap.get(strs.get(i));
			}
		}

		return idx >= 0 ? strs.get(idx) : null;

	}






	//High Five

	public Map<Integer, Double> getHighFive(List<Score> scores){

		Map<Integer, PriorityQueue<Integer>> imap = new HashMap<>();

		for (Score sc : scores) {
			int id = sc.id;
			int grade = sc.grade;
			if (!imap.containsKey(id)) imap.put(id, new PriorityQueue<Integer>());
			imap.get(id).offer(grade);
			if (imap.get(id).size() > 5) imap.get(id).poll();
		}

		Map<Integer, Double> res = new HashMap<>();

		for (Integer id : imap.keySet()) {

			double sum = 0;
			int sz = imap.get(id).size();

			for (Integer g : imap.get(id)) sum += g;

			res.put(id, sum / sz);

		}

		return res;

	}





	public int countCacheMiss(int[] array, int sz) {

		Node head = new Node(0), tail = new Node(0);
		head.next = tail;
		tail.prev = head;
		Map<Integer, Node> imap = new HashMap<>();
		int miss = 0;

		for (int a : array) {

			if (!imap.containsKey(a)) miss++;
			else {
				Node cur = imap.get(a);
				cur.prev.next = cur.next;
				cur.next.prev = cur.prev;
			}

			Node newNode = new Node(a);
			newNode.prev = head;
			newNode.next = head.next;
			head.next.prev = newNode;
			head.next = newNode;

			imap.put(a, newNode);

			if (imap.size() > sz) {
				Node oldNode = tail.prev;
				oldNode.prev.next = oldNode.next;
				oldNode.next.prev = oldNode.prev;
				imap.remove(oldNode.val);
			}
		}

		while (head != null) {
			System.out.print(" " + head.val);
			head = head.next;
		}

		System.out.println();

		return miss;


	}





	public static void main(String[] args) {

		Amazon test = new Amazon();


		System.out.println(test.windowSum(Arrays.asList(1,2,3,4,5,6,7),2));





		Point p1 = new Point(1,0);
		Point p2 = new Point(2,0);
		Point p3 = new Point(-1,0);
		Point p4 = new Point(0,1);
		Point p5 = new Point(1,1);
		Point p6 = new Point(1,-0.5);
		Point p7 = new Point(1,2);
		Point p8 = new Point(1.5,0);
		Point p9 = new Point(3,-3);
		Point p10 = new Point(2.5,1.5);

		// List<Point> array = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8);
		Point[] arr = new Point[]{p1, p2, p3, p4, p5, p6, p7, p8};
		// array.add(p1);
		// array.add(p2);
		// array.add(p3);
		// array.add(p4);
		// array.add(p5);
		// array.add(p6);
		// array.add(p7);
		// array.add(p8);

		// System.out.println(test.kNearestPoints(array, new Point(0,0), 6));
		System.out.println(Arrays.asList(test.kNearestPoints(arr, new Point(0,0), 1)));





		Rectangle a = new Rectangle(p1, p9);
		Rectangle b = new Rectangle(p7, p2);

		System.out.println(test.rectangleOverlap(a, b));






		ListNode ln1 = new ListNode(1);
		ListNode ln2 = new ListNode(3);
		ListNode ln3 = new ListNode(4);
		ListNode ln4 = new ListNode(5);
		ListNode ln5 = new ListNode(8);
		ListNode ln6 = new ListNode(10);

		ln1.next = ln2;
		ln2.next = ln3;
		ln3.next = ln4;
		ln4.next = ln5;
		ln5.next = ln6;
		ln6.next = ln1;

		ListNode cur = test.insertCycleList(ln3, 7);
		ListNode dummy = cur;

		do {
			System.out.println(cur.val);
			cur = cur.next;
		} while (dummy != cur);

		// System.out.println(test.insertCycleList(ln3, 1));





		Order o1 = new Order("1");
		Order o2 = new Order("2");
		Order o3 = new Order("3");
		Order o4 = new Order("4");
		Order o5 = new Order("5");
		Order o6 = new Order("6");
		Order o7 = new Order("7");

		OrderDependency od1 = new OrderDependency(o1,o2);
		OrderDependency od2 = new OrderDependency(o1,o3);
		OrderDependency od3 = new OrderDependency(o2,o3);
		OrderDependency od4 = new OrderDependency(o4,o1);
		OrderDependency od5 = new OrderDependency(o4,o3);
		OrderDependency od6 = new OrderDependency(o5,o6);
		OrderDependency od7 = new OrderDependency(o4,o5);
		OrderDependency od8 = new OrderDependency(o7,o6);
		// OrderDependency od9 = new OrderDependency(o8,o6);

		List<OrderDependency> odL = Arrays.asList(od1, od2, od3, od4, od5, od6, od7, od8);

		System.out.println(test.getOrderList(odL));





		List<String> strs = Arrays.asList("apple","banana","cat","dog","apple","ear","floor","banana");

		System.out.println(test.maxOccurString(strs));





		Score s1 = new Score(1,80);
		Score s2 = new Score(2,80);
		Score s3 = new Score(3,90);
		Score s4 = new Score(2,93);
		Score s5 = new Score(1,95);
		Score s6 = new Score(1,98);
		Score s7 = new Score(2,80);
		Score s8 = new Score(1,90);
		Score s9 = new Score(1,97);
		Score s10 = new Score(1,75);

		List<Score> scores = Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);

		System.out.println(test.getHighFive(scores));




		System.out.println(test.countCacheMiss(new int[]{1,3,5,3,1,5,1,4,5}, 3));

	}

}