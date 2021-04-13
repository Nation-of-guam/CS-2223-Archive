package algs.days.day13;

public class BadHashString {
	final String val;
	
	public BadHashString (String s) {
		this.val = s;
	}
	
	// super bad!
	public int hashCode() {
		return val.charAt(0);
	}
	
	public String toString() { return val; }
}
