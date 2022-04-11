public class DecBuilder {
	// ---------------------------------------------------- definition of Decimal
	private class Dec {
		private int value;
		private Dec next, prev;
		
		public Dec (int data) {
			value = data;
			next = null;
			prev = null;
		}
	}
	
	// ---------------------------------------------------- most significant decimal
	private Dec MSB;
	
	// ---------------------------------------------------- constructors for DecBuilder
	public DecBuilder () {
		Dec temp = new Dec(0);
		Dec curr = temp;
		MSB = curr;
		for (int i = 1; i < 10; i++) {
			temp = new Dec(0);
			curr.next = temp;
			temp.prev = curr;
			curr = temp;
		}
		MSB.prev = curr;
		curr.next = MSB;
	}
	
	public DecBuilder (String data) {
		if (data.length() < 10) {
			int c = 10 - data.length();
			while (c > 0) {
				data = "0" + data;
				c--;
			}
		}
		Dec temp = new Dec(Integer.parseInt(data.substring(0, 1)));
		Dec curr = temp;
		MSB = curr;
		for (int i = 1; i < 10; i++) {
			temp = new Dec(Integer.parseInt(data.substring(i, i + 1)));
			curr.next = temp;
			temp.prev = curr;
			curr = temp;
		}
		curr.next = MSB;
		MSB.prev = curr;
	}
	
	public DecBuilder (HexBuilder data) {
		String input = getNumeric(data);
		if (input.length() < 10) {
			int c = 10 - input.length();
			while (c > 0) {
				input = "0" + input;
				c--;
			}
		}
		Dec temp = new Dec(Integer.parseInt(input.substring(0, 1)));
		Dec curr = temp;
		MSB = curr;
		for (int i = 1; i < 10; i++) {
			temp = new Dec(Integer.parseInt(input.substring(i, i + 1)));
			curr.next = temp;
			temp.prev = curr;
			curr = temp;
		}
		curr.next = MSB;
		MSB.prev = curr;
	}
	
	// ---------------------------------------------------- to String function
	public String toString () {
		String base10 = "";
		Dec curr = MSB;
		int i = 10;
		while (i > 0) {
			base10 = base10 + curr.value;
			curr = curr.next;
			i--;
		}
		return base10;
	}
	
	// ---------------------------------------------------- helper functions for converting Hex
	private String getNumeric (HexBuilder data) {
		long total = 0;
		long tempVal;
		for (int i = 0; i < 8; i++) {
			tempVal = (long)data.getValue(i);
			total += (tempVal * get16ToThe(7 - i));
		}
		Long x = new Long(total);
		return x.toString();
	}
	
	private int get16ToThe (int pow) {
		int total = 1;
		while (pow > 0) {
			total *= 16;
			pow--;
		}
		return total;
	}
}