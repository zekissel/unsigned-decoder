public class HexBuilder {
	// ---------------------------------------------------- definition of Nibble
	private class Nib {
		private char letter;
		private int digit;
		private Nib next, prev;
		private boolean isDigit;
		
		// ---------------------------------------- including constructors for letters, numbers, and nibbles
		public Nib (char data) {
			letter = Character.toUpperCase(data);
			isDigit = false;
			
			next = null;
			prev = null;
		}
		
		public Nib (int data) {
			digit = data;
			isDigit = true;
			
			next = null;
			prev = null;
		}
		
		public Nib (String data) {
			int nibble = Integer.parseInt(data);
			switch (nibble) {
				case 0: digit = 0; isDigit = true; break;
				case 1: digit = 1; isDigit = true; break;
				case 10: digit = 2; isDigit = true; break;
				case 11: digit = 3; isDigit = true; break;
				case 100: digit = 4; isDigit = true; break;
				case 101: digit = 5; isDigit = true; break;
				case 110: digit = 6; isDigit = true; break;
				case 111: digit = 7; isDigit = true; break;
				case 1000: digit = 8; isDigit = true; break;
				case 1001: digit = 9; isDigit = true; break;
				case 1010: letter = 'A'; isDigit = false; break;
				case 1011: letter = 'B'; isDigit = false; break;
				case 1100: letter = 'C'; isDigit = false; break;
				case 1101: letter = 'D'; isDigit = false; break;
				case 1110: letter = 'E'; isDigit = false; break;
				case 1111: letter = 'F'; isDigit = false; break;
				default: throw new IllegalArgumentException("Invalid nibble: " + nibble);
			}
			
			next = null;
			prev = null;
		}
	}
	
	// ---------------------------------------------------- most significant nibble
	private Nib MSB;
	
	// ---------------------------------------------------- constructors for HexBuilder
	public HexBuilder () {
		Nib temp = new Nib(0);
		Nib curr = temp;
		MSB = curr;
		for (int i = 1; i < 8; i++) {
			temp = new Nib(0);
			curr.next = temp;
			temp.prev = curr;
			curr = temp;
		}
		MSB.prev = curr;
		curr.next = MSB;
	}
	
	public HexBuilder (String data) {
		if (data.length() < 8) {
			int c = 8 - data.length();
			while (c > 0) {
				data = "0" + data;
				c--;
			}
		}
		
		Nib temp;
		Nib curr;
		if (data.substring(0, 1).matches("^[a-fA-F]+$")) {
			temp = new Nib(data.charAt(0));
		} else {
			temp = new Nib(Character.getNumericValue(data.charAt(0)));
		}
		curr = temp;
		MSB = curr;
		for (int i = 1; i < 8; i++) {
			if (data.substring(i, i + 1).matches("^[a-fA-F]+$")) {
				temp = new Nib(data.charAt(i));
			} else {
				temp = new Nib(Character.getNumericValue(data.charAt(i)));
			}
			curr.next = temp;
			temp.prev = curr;
			curr = temp;
		}
		curr.next = MSB;
		MSB.prev = curr;
	}
	
	public HexBuilder (BinBuilder data) {
		String nibble = data.getFront();
		Nib temp = new Nib(nibble);
		Nib curr = temp;
		MSB = curr;
		data.rotateFront();
		for (int i = 1; i < 8; i++) {
			nibble = data.getFront();
			temp = new Nib(nibble);
			curr.next = temp;
			temp.prev = curr;
			curr = temp;
			data.rotateFront();
		}
		curr.next = MSB;
		MSB.prev = curr;
	}
	
	// ---------------------------------------------------- to String function
	public String toString () {
		String base16 = "";
		Nib curr = MSB;
		int i = 8;
		while (i > 0) {
			if (curr.isDigit) {
				Integer x = new Integer(curr.digit);
				base16 = base16 + x.toString();
			} else {
				base16 = base16 + curr.letter;
			}
			curr = curr.next;
			i--;
		}
		return base16;
	}
	
	// ---------------------------------------------------- helper functions for DecBuilder
	public int getValue (int i) {
		Nib curr = MSB;
		while (i > 0) {
			curr = curr.next;
			i--;
		}
		if (curr.isDigit) {
			return curr.digit;
		} else {
			switch (curr.letter) {
				case 'A': return 10;
				case 'B': return 11;
				case 'C': return 12;
				case 'D': return 13;
				case 'E': return 14;
				case 'F': return 15;
			}
		}
		return 0;
	}
}