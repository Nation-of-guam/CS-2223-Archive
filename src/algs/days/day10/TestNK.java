package algs.days.day10;

import junit.framework.Assert;
import junit.framework.TestCase;


public class TestNK extends TestCase {
	
	public void testUpToK() {	
		NK nk = new NK(6);
		
		nk.add(3);
		nk.add(7);
		nk.add(1);
		nk.add(4);
		nk.add(2);
		nk.add(5);
		
		Assert.assertTrue (nk.contains(4));
		
		nk.add(9);
	
		Assert.assertTrue (nk.contains(9));
		nk.add(8);
		Assert.assertTrue (nk.contains(8));
		
		nk.remove(5);
		Assert.assertTrue (nk.contains(8));
		nk.remove(3);
		Assert.assertTrue (nk.contains(8));
		Assert.assertTrue (nk.contains(9));
	}
	
	public void testAddSeq() {
		NK nk = new NK(6);
		int[] toAdd = new int[50];
		for (int i = 0; i < toAdd.length; i++) {
			toAdd[i] = i+1;
		}
		for (int i : toAdd) {
			nk.add(i);
		}
		
		for (int i : toAdd) {
			Assert.assertTrue (nk.contains(i));
		}
		
		for (int i : toAdd) {
			System.out.println(nk.N);
			Assert.assertTrue (nk.remove(i));
		}
		
		Assert.assertTrue (nk.isEmpty());
	}
	
	public void testAddSeqInReverse() {
		NK nk = new NK(6);
		
		for (int i = 1; i <= 50; i++) {
			nk.add(i);
		}
		for (int i = 50; i >= 1; i--) {
			Assert.assertTrue (nk.remove(i));
		}
		
		Assert.assertTrue (nk.isEmpty());
	}
	
	public void testAddRemoveJosephus() {
		NK nk = new NK(6);
		int val = 1;
		
		// eventually will stop
		while (!nk.contains(val)) {
			nk.add(val);
			val = (val + 5) % 197;
		}
		
		Assert.assertEquals (197, nk.size());
		
		for (int i = 0; i < 197; i++) {
			if (nk.contains(i)) {
				nk.remove(i);
			}
		}
		
		Assert.assertTrue (nk.isEmpty());
	}
	
}
