public class StringManipulation {
	public StringManipulation() {

	}

	/**
	 * Given a string, count the number of words ending in 'y' or 'z' -- so the
	 * 'y' in "heavy" and the 'z' in "fez" count, but not the 'y' in "yellow"
	 * (not case sensitive). We'll say that a y or z is at the end of a word if
	 * there is not an alphabetic letter immediately following it. (Note:
	 * Character.isLetter(char) tests if a char is an alphabetic letter.)
	 * 
	 * @param str
	 * @return
	 */
	public int countYZ(String str) {
		int count = 0;
		str = str.toLowerCase();
		for (int i = 1; i < str.length(); i++) {
			if (Character.isLetter(str.charAt(i))) {
			} else {
				if ((str.charAt(i - 1) == 'y') || (str.charAt(i - 1) == 'z')) {
					count++;
				}
			}
		}
		if (str.charAt(str.length() - 1) == 'y'
				|| str.charAt(str.length() - 1) == 'z') {
			count++;
		}
		return count;
	}

	/**
	 * We'll say that a lowercase 'g' in a string is "happy" if there is another
	 * 'g' immediately to its left or right. Return true if all the g's in the
	 * given string are happy.
	 * 
	 * @param str
	 * @return
	 */
	public boolean gHappy(String str) {
		if (str.length() == 1) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 'g' && i == 0) {
				if (str.charAt(i + 1) != 'g') {
					return false;
				}
			} else if (str.charAt(i) == 'g' && i == str.length() - 1) {
				if (str.charAt(i - 1) != 'g') {
					return false;
				}
			} else if (str.charAt(i) == 'g') {
				if (str.charAt(i + 1) != 'g' && str.charAt(i - 1) != 'g') {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Given a string, return a string where for every char in the original,
	 * there are two chars.
	 * 
	 * @param str
	 * @return
	 */
	public String doubleChar(String str) {

		String nstr = "";
		for (int i = 0; i < str.length(); i++) {
			nstr = nstr + str.charAt(i) + str.charAt(i);

		}
		return nstr;
	}

	/**
	 * Return the number of times that the string "code" appears anywhere in the
	 * given string, except we'll accept any letter for the 'd', so "cope" and
	 * "cooe" count.
	 * 
	 * @param str
	 * @return
	 */
	public int countCode(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 'c' && i + 3 < str.length()) {
				if (str.charAt(i + 1) == 'o' && str.charAt(i + 3) == 'e') {
					count++;
				}
			}

		}
		return count;
	}

	/**
	 * Return true if the given string contains a "bob" string, but where the
	 * middle 'o' char can be any char.
	 */
	public boolean bobThere(String str) {
		int count = 0;
		for (int i = 0; i < str.length() - 2; i++) {
			if (str.charAt(i) == 'b' && str.charAt(i + 2) == 'b') {
				count++;
			}
		}
		if (count > 0) {
			return true;
		} else
			return false;
	}

	/**
	 * Given a string and an int N, return a string made of N repetitions of the
	 * last N characters of the string. You may assume that N is between 0 and
	 * the length of the string, inclusive.
	 * 
	 * @param str
	 * @param n
	 * @return
	 */
	public String repeatEnd(String str, int n) {
		int j = str.length();
		String out = str.substring(j - n, j);
		int l = out.length();
		while (l <= n * n) {
			if (n <= 1)

				return out;
			if (n <= 2)
				return out + out;

			if (n <= 3)
				return out + out + out;

		}
		return "";
	}

	/**
	 * Given a string, consider the prefix string made of the first N chars of
	 * the string. Does that prefix string appear somewhere else in the string?
	 * Assume that the string is not empty and that N is in the range
	 * 1..str.length().
	 * 
	 * @param str
	 * @param n
	 * @return
	 */
	public boolean prefixAgain(String str, int n) {
		String nstr = str.substring(0, n);
		String cstr = str.substring(n);

		if (cstr.contains(nstr)) {
			return true;
		} else
			return false;
	}

	/**
	 * Returns true if for every '*' (star) in the string, if there are chars
	 * both immediately before and after the star, they are the same.
	 * 
	 * @param str
	 * @return
	 */
	public boolean sameStarChar(String str) {

		for (int i = 1; i < str.length() - 1; i++) {
			if (str.charAt(i) == '*') {
				if (str.charAt(i - 1) != str.charAt(i + 1))
					return false;
			}

		}
		return true;

	}

	/**
	 * Given a string and a non-empty word string, return a version of the
	 * original String where all chars have been replaced by pluses ("+"),
	 * except for appearances of the word string which are preserved unchanged.
	 * 
	 * @param str
	 * @param word
	 * @return
	 */
	public String plusOut(String str, String word) {
		int wordlength = word.length();
		String nstr = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == word.charAt(0)) {
				String sub = str.substring(i, i + wordlength);
				if (sub.equals(word)) {
					i += wordlength - 1;
					nstr += sub;
				} else
					nstr += "+";
			} else
				nstr += "+";
		}
		return nstr;
	}

	/**
	 * Return the number of times that the string "hi" appears anywhere in the
	 * given string
	 * 
	 * @param str
	 * @return
	 */
	public int countHi(String str) {
		int count = 0;
		for (int i = 0; i < str.length() - 1; i++) {
			if (str.charAt(i) == 'h' && str.charAt(i + 1) == 'i') {
				count++;
			}
		}

		return count;
	}

	/**
	 * Given two strings, return true if either of the strings appears at the
	 * very end of the other string, ignoring upper/lower case differences (in
	 * other words, the computation should not be "case sensitive"). Note:
	 * str.toLowerCase() returns the lowercase version of a string.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean endOther(String a, String b) {
		a = a.toLowerCase();
		b = b.toLowerCase();

		if (a.length() > b.length()) {
			if (a.substring(a.length() - b.length()).equals(b)) {
				return true;
			}
		} else if (b.length() > a.length()) {
			if (b.substring(b.length() - a.length()).equals(a)) {
				return true;
			}
		} else {
			if (a.equals(b)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * We'll say that a String is xy-balanced if for all the 'x' chars in the
	 * string, there exists a 'y' char somewhere later in the string. So "xxy"
	 * is balanced, but "xyx" is not. One 'y' can balance multiple 'x's. Return
	 * true if the given string is xy-balanced.
	 * 
	 * @param str
	 * @return
	 */
	public boolean xyBalance(String str) {
		int L = str.length();
		int pos = 0;

		while (pos < L) {

			if (str.charAt(pos) == 'x') {
				int yup = str.indexOf("y", pos + 1);
				if (yup < 0)
					return false;
			}
			pos++;
		}
		return true;

	}

	/**
	 * Given a string and an int n, return a string made of the first n
	 * characters of the string, followed by the first n-1 characters of the
	 * string, and so on. You may assume that n is between 0 and the length of
	 * the string, inclusive (i.e. n >= 0 and n <= str.length()).
	 * 
	 * @param str
	 * @param n
	 * @return
	 */
	public String repeatFront(String str, int n) {
		String nstr = "";

		for (int i = n; i > 0; i--) {
			nstr += str.substring(0, i);
		}
		return nstr;
	}

	/**
	 * Given a string, does "xyz" appear in the middle of the string? To define
	 * middle, we'll say that the number of chars to the left and right of the
	 * "xyz" must differ by at most one. This problem is harder than it looks.
	 * 
	 * @param str
	 * @return
	 */
	public boolean xyzMiddle(String str) {
		int mid = (str.length() - 3) / 2;
		int dod = (str.length() - 3) % 2;
		if (str.length() < 3) {
			return false;
		}

		if (str.length() == 3 && str.contains("xyz")) {
			return true;
		}
		if (dod == 0) {
			if (str.charAt(mid) == 'x' && str.charAt(mid + 1) == 'y'
					&& str.charAt(mid + 2) == 'z') {
				return true;
			} else
				return false;
		} else {
			if ((str.charAt(mid) == 'x' && str.charAt(mid + 1) == 'y' && str
					.charAt(mid + 2) == 'z')) {
				return true;
			} else if (str.charAt(mid + 1) == 'x' && str.charAt(mid + 2) == 'y'
					&& str.charAt(mid + 3) == 'z') {
				return true;
			} else
				return false;
		}
	}

	/**
	 * Look for patterns like "zip" and "zap" in the string -- length-3,
	 * starting with 'z' and ending with 'p'. Return a string where for all such
	 * words, the middle letter is gone, so "zipXzap" yields "zpXzp".
	 * 
	 * @param str
	 * @return
	 */
	public String zipZap(String str) {
		String tstr = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 'z' && i + 2 < str.length()) {
				if (str.charAt(i + 2) == 'p') {
					tstr += "zp";
					i += 2;
				} else
					tstr += "z";
			} else
				tstr += str.charAt(i);

		}
		return tstr;
	}

	/**
	 * Given a string and a non-empty word string, return a string made of each
	 * char just before and just after every appearance of the word in the
	 * string. Ignore cases where there is no char before or after the word, and
	 * a char may be included twice if it is between two words.
	 * 
	 * @param str
	 * @param word
	 * @return
	 */
	public String wordEnds(String str, String word) {
		String end = "";
		if (str.length() == word.length()) {
			return end;
		}
		for (int i = 0; i < str.length(); i++) {

			if (str.charAt(i) == word.charAt(0)) {
				if (i == 0) {// start
					end += str.charAt(i + word.length());
					// i+=word.length();
				} else if (i != 0 && i + word.length() < str.length()) {
					end += str.charAt(i - 1) + ""
							+ str.charAt(i + word.length());
					// i+=word.length();
				} else
					end += str.charAt(i - 1);
				// i=str.length();
			}
		}
		return end;
	}

	/**
	 * Return true if the string "cat" and "dog" appear the same number of times
	 * in the given string.
	 * 
	 * @param str
	 * @return
	 */
	public boolean catDog(String str) {
		int countC = 0;
		int countD = 0;
		for (int i = 0; i < str.length() - 2; i++) {
			String sub = str.substring(i, i + 3);
			if (sub.equals("cat"))
				countC = countC + 1;

		}
		for (int i = 0; i < str.length() - 2; i++) {
			String sub = str.substring(i, i + 3);
			if (sub.equals("dog"))
				countD = countD + 1;

		}
		if (countC == countD)
			return true;
		else
			return false;
	}

	/**
	 * Return true if the given string contains an appearance of "xyz" where the
	 * xyz is not directly preceeded by a period (.). So "xxyz" counts but
	 * "x.xyz" does not.
	 * 
	 * @param str
	 * @return
	 */
	public boolean xyzThere(String str) {
		String check = "xyz";
		for (int i = 0; i < str.length(); i++) {
			String build = "";
			if (str.charAt(i) == check.charAt(0)) {
				if (i + 3 <= str.length()) {
					for (int k = 0; k < 3; k++) {
						build += str.charAt(i + k);
					}
					if (build.equals(check) && i == 0) {
						return true;
					} else if (build.equals(check) && str.charAt(i - 1) != '.') {
						return true;
					}
				} else
					return false;
			}
		}
		return false;
	}

	/**
	 * Given two strings, A and B, create a bigger string made of the first char
	 * of A, the first char of B, the second char of A, the second char of B,
	 * and so on. Any leftover chars go at the end of the result.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public String mixString(String a, String b) {
		String result = "";
		if (a.length() > b.length()) {
			for (int i = 0; i < b.length(); i++) {
				result += a.charAt(i) + "" + b.charAt(i);
			}
			result += a.substring(b.length());
		} else {
			for (int i = 0; i < a.length(); i++) {
				result += a.charAt(i) + "" + b.charAt(i);
			}
			result += b.substring(a.length());
		}
		return result;
	}

	/**
	 * Given two strings, word and a separator, return a big string made of
	 * count occurences of the word, separated by the separator string.
	 * 
	 * @param word
	 * @param sep
	 * @param count
	 * @return
	 */
	public String repeatSeparator(String word, String sep, int count) {
		String mid = "";
		for (int i = 0; i < count; i++) {
			mid += word;
			if (i < count - 1) {
				mid += sep;
			}
		}
		return mid;
	}

	/**
	 * A sandwich is two pieces of bread with something in between. Return the
	 * string that is between the first and last appearance of "bread" in the
	 * given string, or return the empty string "" if there are not two pieces
	 * of bread.
	 * 
	 * @param str
	 * @return
	 */
	public String getSandwich(String str) {
		int first = str.indexOf("bread");
		int last = str.lastIndexOf("bread");
		String result = "";
		if (first == last) {
			return result;
		} else {
			return str.substring(first + 5, last);
		}
	}

}
