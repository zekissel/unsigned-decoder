public class BinBuilder {
	// ------------------------------------------------ definition of Bit
	private class Bit {
		private int value;
		private Bit next, prev;
		
		public Bit (int data) {
			value = data;
			next = null;
			prev = null;
		}
	}
	
	// ------------------------------------------------ most significant bit class variable
	private Bit MSB;
	
	// ------------------------------------------------ constructors for BinBuilder
	public BinBuilder () {
		Bit temp = new Bit(0);
		Bit curr = temp;
		MSB = curr;
		for (int i = 1; i < 32; i++) {
			temp = new Bit(0);
			curr.next = temp;
			temp.prev = curr;
			curr = temp;
		}
		MSB.prev = curr;
		curr.next = MSB;
	}
	
	public BinBuilder (String data) {
		if (data.length() < 32) {
			int c = 32 - data.length();
			while (c > 0) {
				data = "0" + data;
				c--;
			}
		}
		Bit temp = new Bit(Integer.parseInt(data.substring(0, 1)));
		Bit curr = temp;
		MSB = curr;
		for (int i = 1; i < 32; i++) {
			temp = new Bit(Integer.parseInt(data.substring(i, i + 1)));
			curr.next = temp;
			temp.prev = curr;
			curr = temp;
		}
		curr.next = MSB;
		MSB.prev = curr;
	}
	
	public BinBuilder (DecBuilder data) {
		Bit temp, curr, LSB;
		String tempVal = data.toString();
		long total = Long.parseLong(tempVal);
		long remainder = 0;
		
		remainder = total % 2;
		total /= 2;
		temp = new Bit((int)remainder);
		curr = temp;
		LSB = curr;
		for (int i = 1; i < 32; i++) {
			remainder = total % (long)2;
			total /= 2;
			temp = new Bit((int)remainder);
			curr.prev = temp;
			temp.next = curr;
			curr = temp;
		}
		curr.prev = LSB;
		LSB.next = curr;
		MSB = curr;
		
	}
	
	// ------------------------------------------------ to String function
	public String toString () {
		String base2 = "";
		Bit curr = MSB;
		int i = 32;
		while (i > 0) {
			base2 = base2 + curr.value;
			curr = curr.next;
			i--;
		}
		return base2;
	}
	
	// ------------------------------------------------ used by HexBuilder to get nibbles at a time
	public String getFront () {
		String nib = "";
		Bit curr = MSB;
		for (int i = 0; i < 4; i++ ) {
			Integer x = new Integer(curr.value);
			nib = nib + x.toString();
			curr = curr.next;
		}
		return nib;
	}
	
	public void rotateFront () {
		for (int i = 0; i < 4; i++) {
			MSB = MSB.next;
		}
	}
}