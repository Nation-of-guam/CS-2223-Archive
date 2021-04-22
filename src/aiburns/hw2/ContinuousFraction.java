package aiburns.hw2;

import java.util.Iterator;

public class ContinuousFraction {

	class ContinuousNode {
		int             value;
		ContinuousNode  next;
		
		ContinuousNode(int v) {
			this.value = v;
		}
	}

	protected ContinuousFraction(){}

	/** Use linked list. */
	ContinuousNode first;

	private int floor(double d){
		return (int) Math.floor(d);
	}

	public ContinuousFraction(Iterator<Integer> it) {
		Iterator<Integer> iter = it;
		if (!iter.hasNext()){
			return;
		}
		int i = iter.next();
		ContinuousNode temp = new ContinuousNode(i);
		first = temp;

		while (iter.hasNext()){
			i = iter.next();
			temp.next = new ContinuousNode(i);
			temp = temp.next;
		}
	}
	
	public ContinuousFraction(double d) {
		if (d == (int) d){
			first = new ContinuousNode((int) d);
			return;
		}
		double iterativeDouble = d;
		ContinuousNode temp = new ContinuousNode(floor(d));
		first = temp;
		iterativeDouble = 1/(iterativeDouble - temp.value);
		ContinuousFraction comparison = new ContinuousFraction();
		comparison.first = first;
		while (comparison.value() != d && iterativeDouble != 0 ){
			temp.next = new ContinuousNode(floor(iterativeDouble));
			if ((iterativeDouble - temp.next.value) == 0){
				return;
			}
			temp = temp.next;
			iterativeDouble = 1/(iterativeDouble - temp.value);
		}
	}
	
	public double value() {
		if (first == null){
			return 0;
		} else if (first.next == null) {
			return first.value;
		}

		double sum = first.value;
		sum += (1/denominatorValue(first.next));
		return sum;
	}

	private static double denominatorValue(ContinuousNode node){
		if (node == null){
			return 0;
		} else if (node.next == null){
			return node.value;
		}
		return node.value + (1/(denominatorValue(node.next)));
	}

	public String toString() {
		return "" + value();
	}
	
	public static void main(String[] args) {
		// Print out the ever popular PI!
		ContinuousFraction frac = new ContinuousFraction(Math.PI);
		System.out.println(frac);

		// look at tables of square roots for patterns.
		for (int i = 2; i < 20; i++) {
			frac = new ContinuousFraction(Math.sqrt(i));
			System.out.println(i + "\t" + frac);
		}
	}
}
