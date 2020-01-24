package org.apache.camel.dataformat.bindy;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.camel.dataformat.bindy.UnicodeHelper.Method;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("javadoc")
public class StringHelperTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(StringHelperTest.class);	

	private static final String UCSTR = cps2String(
		0x1f645, // FACE WITH NO GOOD GESTURE; Basiszeichen (Geste)
		0x1f3ff, // EMOJI MODIFIER FITZPATRICK TYPE-6; Hautfarbe für #1
		0x200d,  // ZERO WIDTH JOINER [ZWJ]; Steuerzeichen zum Verbinden
		0x2642,  // MALE SIGN; Geschlecht für #1
		0xfe0f   // VARIATION SELECTOR-16 [VS16]; Darstellung als Piktogramm für #4				
	);

	@Test
	public void testLengthCPs() {
		final UnicodeHelper lh = new UnicodeHelper("a",Method.CODEPOINTS);
		Assert.assertEquals(1,lh.length());
		
		final UnicodeHelper lh2 = new UnicodeHelper(new String(Character.toChars(0x1f600)),Method.CODEPOINTS);
		Assert.assertEquals(1,lh2.length());

		final UnicodeHelper lh3 = new UnicodeHelper(UCSTR,Method.CODEPOINTS);
		Assert.assertEquals(5,lh3.length());

		final UnicodeHelper lh4 = new UnicodeHelper("a" + UCSTR + "A",Method.CODEPOINTS);
		Assert.assertEquals(7,lh4.length());
		
		final UnicodeHelper lh5 = new UnicodeHelper("k\u035fh",Method.CODEPOINTS);
		Assert.assertEquals(3,lh5.length());
	}	

	@Test
	public void testLengthGrapheme() {

		final UnicodeHelper lh = new UnicodeHelper("a",Method.GRAPHEME);
		Assert.assertEquals(1,lh.length());
		
		final UnicodeHelper lh2 = new UnicodeHelper(new String(Character.toChars(0x1f600)),Method.GRAPHEME);
		Assert.assertEquals(1,lh2.length());

		final UnicodeHelper lh3 = new UnicodeHelper(UCSTR,Method.GRAPHEME);
		Assert.assertEquals(1,lh3.length());

		final UnicodeHelper lh4 = new UnicodeHelper("a" + UCSTR + "A",Method.GRAPHEME);
		Assert.assertEquals(3,lh4.length());
		
		final UnicodeHelper lh5 = new UnicodeHelper("k\u035fh",Method.GRAPHEME);
		Assert.assertEquals(2,lh5.length());
	}
	
	@Test
	public void testSubstringCPs() throws FileNotFoundException, IOException {

		final UnicodeHelper lh = new UnicodeHelper("a",Method.CODEPOINTS);
		Assert.assertEquals("a",lh.substring(0));

		final UnicodeHelper lh2 = new UnicodeHelper(new String(Character.toChars(0x1f600)),Method.CODEPOINTS);
		Assert.assertEquals(new String(Character.toChars(0x1f600)),lh2.substring(0));

		final UnicodeHelper lh3 = new UnicodeHelper(UCSTR,Method.CODEPOINTS);
		Assert.assertEquals(UCSTR,lh3.substring(0));

		final UnicodeHelper lh4 = new UnicodeHelper("a" + UCSTR + "A",Method.CODEPOINTS);
		Assert.assertEquals(UCSTR + "A",lh4.substring(1));
		Assert.assertEquals(new String(Character.toChars(0x1f3ff)) + "\u200d\u2642\ufe0fA",lh4.substring(2));
		
		final UnicodeHelper lh5 = new UnicodeHelper("k\u035fh",Method.CODEPOINTS);
		Assert.assertEquals("\u035fh",lh5.substring(1));
	}	

	@Test
	public void testSubstringGrapheme() throws FileNotFoundException, IOException {

		final UnicodeHelper lh = new UnicodeHelper("a",Method.GRAPHEME);
		Assert.assertEquals("a",lh.substring(0));

		final UnicodeHelper lh2 = new UnicodeHelper(new String(Character.toChars(0x1f600)),Method.GRAPHEME);
		Assert.assertEquals(new String(Character.toChars(0x1f600)),lh2.substring(0));

		final UnicodeHelper lh3 = new UnicodeHelper(UCSTR,Method.GRAPHEME);
		Assert.assertEquals(UCSTR,lh3.substring(0));

		final UnicodeHelper lh4 = new UnicodeHelper("a" + UCSTR + "A",Method.GRAPHEME);
		Assert.assertEquals(UCSTR + "A",lh4.substring(1));
		Assert.assertEquals("A",lh4.substring(2));
		
		final UnicodeHelper lh5 = new UnicodeHelper("k\u035fh",Method.GRAPHEME);
		Assert.assertEquals("h",lh5.substring(1));
	}	
	
	@Test
	public void testSubstringCPs2() {

		final UnicodeHelper lh = new UnicodeHelper("a",Method.CODEPOINTS);
		Assert.assertEquals("a",lh.substring(0,1));
		
		final UnicodeHelper lh2 = new UnicodeHelper(new String(Character.toChars(0x1f600)),Method.CODEPOINTS);
		Assert.assertEquals(new String(Character.toChars(0x1f600)),lh2.substring(0, 1));	

		final UnicodeHelper lh3 = new UnicodeHelper(UCSTR,Method.CODEPOINTS);
		Assert.assertEquals(new String(Character.toChars(0x1f645)),lh3.substring(0,1));

		final UnicodeHelper lh4 = new UnicodeHelper("a" + UCSTR + "A",Method.CODEPOINTS);
		Assert.assertEquals("a",lh4.substring(0,1));
		Assert.assertEquals(new String(Character.toChars(0x1f645)),lh4.substring(1,2));
		Assert.assertEquals(new String(Character.toChars(0x1f3ff)),lh4.substring(2,3));
		Assert.assertEquals("a" + new String(Character.toChars(0x1f645)),lh4.substring(0,2));
		
		final UnicodeHelper lh5 = new UnicodeHelper("k\u035fh",Method.CODEPOINTS);
		Assert.assertEquals("k",lh5.substring(0,1));
		Assert.assertEquals("\u035f",lh5.substring(1,2));		
	}		
	
	@Test
	public void testSubstringGrapheme2() {

		final UnicodeHelper lh = new UnicodeHelper("a",Method.GRAPHEME);
		Assert.assertEquals("a",lh.substring(0,1));
		
		final UnicodeHelper lh2 = new UnicodeHelper(new String(Character.toChars(0x1f600)),Method.GRAPHEME);
		Assert.assertEquals(new String(Character.toChars(0x1f600)),lh2.substring(0, 1));	

		final UnicodeHelper lh3 = new UnicodeHelper(UCSTR,Method.GRAPHEME);
		Assert.assertEquals(UCSTR,lh3.substring(0,1));

		final UnicodeHelper lh4 = new UnicodeHelper("a" + UCSTR + "A",Method.GRAPHEME);
		Assert.assertEquals("a",lh4.substring(0,1));
		Assert.assertEquals(UCSTR,lh4.substring(1,2));
		Assert.assertEquals("A",lh4.substring(2,3));
		Assert.assertEquals("a" + UCSTR,lh4.substring(0,2));
		
		final UnicodeHelper lh5 = new UnicodeHelper("k\u035fh",Method.GRAPHEME);
		Assert.assertEquals("k\u035f",lh5.substring(0,1));
		Assert.assertEquals("h",lh5.substring(1,2));		
	}
	
	@Test
	public void testIndexOf() {
		final UnicodeHelper lh = new UnicodeHelper("a",Method.CODEPOINTS);
		Assert.assertEquals(-1,lh.indexOf("b"));
		
		final UnicodeHelper lh2 = new UnicodeHelper(
			"a" + new String(Character.toChars(0x1f600)) + "a" + UCSTR + "A" + "k\u035fh" + "z",
			Method.CODEPOINTS);
		
		Assert.assertEquals(1,lh2.indexOf(new String(Character.toChars(0x1f600))));
		
		Assert.assertEquals(3,lh2.indexOf(UCSTR));
		
		Assert.assertEquals(10,lh2.indexOf("\u035f"));
		
		expectIllegalArgumentException(() -> {
			lh2.indexOf(Character.toString(Character.toChars(0x1f600)[0])); // UTF-16  surrogates are no codepoints.
		});
	}
	
	@Test
	public void testIndexOf2() {
		final UnicodeHelper lh = new UnicodeHelper("a",Method.GRAPHEME);
		Assert.assertEquals(-1,lh.indexOf("b"));
		
		final UnicodeHelper lh2 = new UnicodeHelper(
			"a" + new String(Character.toChars(0x1f600)) + "a" + UCSTR + "A" + "k\u035fh" + "z",
			Method.GRAPHEME);
		
		Assert.assertEquals(1,lh2.indexOf(new String(Character.toChars(0x1f600))));
		
		Assert.assertEquals(3,lh2.indexOf(UCSTR));
		
		expectIllegalArgumentException(() -> {
			lh2.indexOf("\u035f"); // Codepoint of dangling combing char is not a "unicode char".
		});
	}	
	
	private void expectIllegalArgumentException(final Runnable r)
	{
		try {
			r.run();
			Assert.assertTrue("We do not expect to reach here -- missing IllegalArgumentException.",false);
			
		} catch (final IllegalArgumentException e) {
			LOG.debug("Caught expected IllegalArgumentException",e);
			
		}
	}
	
	private static String cps2String(final int... cps)
	{
		final StringBuilder buf = new StringBuilder();
		for (int cp : cps) {
			buf.append(Character.toChars(cp));
		}
		final String result = buf.toString();
		
		if (LOG.isDebugEnabled()) {
			final String cpStr = Arrays.stream(cps).boxed()
				.map((i) -> "0x" + Integer.toString(i, 16))
				.collect(Collectors.joining(", "));
			LOG.debug("Built string '{}' from CPs [ {} ].",result, cpStr);
		}
		
		return result;
	}
}
