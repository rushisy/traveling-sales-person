package traveling_sales_person;

import java.awt.Color;

public class Tour {
	private int size; // keeps track of list length
	private Node head; // first node of list

	/**
	 * default constructor
	 */
	public Tour() {
		head = null;
		size = 0;
	}

	/**
	 * testing environment
	 * 
	 * @param a point a to connect to b
	 * @param b point b to connect to c
	 * @param c point c to connect to d
	 * @param d point d to connect to a
	 */
	public Tour(Point a, Point b, Point c, Point d) {
		head = new Node(a);
		head.next = new Node(b);
		head.next.next = new Node(c);
		head.next.next.next = new Node(d);
		head.next.next.next.next = head;
		size = 4;
	}

	/**
	 * outputs the total length of list
	 * 
	 * @return int the length of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * outputs the total distance elapsed
	 * 
	 * @return double the total distance
	 */
	public double distance() {
		try {
			double distance = 0;
			Node position = head;
			do {
				distance += position.data.distanceTo(position.next.data); // distance between current node and one after
				position = position.next;
			} while (position != head);
			return distance;
		} catch (Exception e) {
			return 0.0;
		}
	}

	/**
	 * prints the coordinates of the graphed points
	 */
	public void show() {
		try {
			String output = "";
			Node position = head;
			do {
				output += position.data.toString() + "\n"; // coordinates of current node
				position = position.next;
			} while (position != head);
			System.out.println(output);
		} catch (Exception e) {
			System.out.println("No points to show!");
		}
	}

	/**
	 * connects the dots from node to node thus creating a visual representation
	 */
	public void draw() {
		try {
			Node position = head;

			do {

				position.data.drawTo(position.next.data); // draw from current node to one after
				position = position.next;
			} while (position != head);
		} catch (Exception e) {
			System.out.println("No points to draw!");
		}
	}

	/**
	 * adds a point after the point it is closest to
	 * 
	 * @param p the point to add
	 */
	public void insertNearest(Point p) {
		try {
			if (size == 0) { // no elements in list
				head = new Node(p);
				head.next = head;
				size++;
				return;
			}

			Node insert = null;
			Node position = head;
			double smallest = Double.MAX_VALUE;
			do {
				if (position.data.distanceTo(p) < smallest) { // find shortest path from current to input
					smallest = position.data.distanceTo(p);
					insert = position; // update position to insert input
				}
				position = position.next;
			} while (position != head);

			Node add = new Node(p); // adding the input value
			add.next = insert.next;
			insert.next = add;
			size++;
		} catch (Exception e) {
		}
	}

	/**
	 * adds a point after the point which results in the lowest increase in total
	 * distance
	 * 
	 * @param p the point to add
	 */
	public void insertSmallest(Point p) {
		try {
			if (size == 0) { // no elements in list
				head = new Node(p);
				head.next = head;
				size++;
				return;
			}

			Node insert = null;
			Node position = head;
			double smallest = Double.MAX_VALUE;

			// ______________ > x \
			// a --> o --> o / ::: > o --> o --> a // need to subtract ::: from list length
			// and add other two lengths

			do {
				if (position.data.distanceTo(p) + position.next.data.distanceTo(p)
						- position.data.distanceTo(position.next.data) < smallest) { // find shortest path
					smallest = position.data.distanceTo(p) + position.next.data.distanceTo(p)
							- position.data.distanceTo(position.next.data); // current to next is default of list
																			// (remove)
					insert = position; // update position to insert input
				}
				position = position.next;
			} while (position != head);

			Node add = new Node(p); // adding the input value
			add.next = insert.next;
			insert.next = add;
			size++;
		} catch (Exception e) {
		}
	}

	private class Node {
		private Point data;
		private Node next;

		/**
		 * preferred constructor
		 * 
		 * @param data the point to assign the node
		 */
		public Node(Point data) {
			this.data = data;
		}
	}

	public static void main(String[] args) {
		StdDraw.setXscale(0, 600);
		StdDraw.setYscale(0, 600);

		Tour squareTour = new Tour(new Point(100.0, 100.0), new Point(500.0, 100.0), new Point(500.0, 500.0),
				new Point(100.0, 500.0));
		System.out.printf("Tour distance =  %.4f\n", squareTour.distance());
		System.out.printf("Number of points = %d\n", squareTour.size());
		squareTour.show();
		squareTour.draw();
	}
}