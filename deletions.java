package hgRNAs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class deletions {
	private static final Random RANDOM = new Random();

	public static void main(String[] args) {
		// System.out.println(hgRNA());
//		String hg = spacer(10);
//		String hgPAM = addPam(hg);
//		
//		String hgdel = delete(hgPAM, 6, 2);
//
//		System.out.println(hgPAM);
//		System.out.println(hgdel);
		
		//String[] a = run(hgPAM, 13);

		//System.out.println(Arrays.toString(a));
		
		//String kalhor = "AGCTGCGTGGTGGGG";
		
//		for (int i = 0; i < 10; i++) {
//			System.out.println(insert(kalhor,6,1)+"TTA");
//		}
		
		
		
		
		for (int i = 0; i< 10; i++) {
			System.out.println(delete(spacer(14),random(6,10),random(1,5)));
		}
		
	}
	
	
	/**
	 * Generates a random int
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		return RANDOM.nextInt(max) + min;
	}

	/**
	 * 
	 * @param s
	 * @param cells
	 * @return
	 */
	public static String[] run(String s, int cells) {
		String[] array = new String[cells];
		array[0] = s;

		for (int i = 1; i < cells; i++) {
			s = delete(s,random(6,10), random(1,4));
			array[i] = s;
		}
		return array;
	}

	/**
	 * Deletes a random bp with a specific length x positions 
	 * away from the end of the PAM site
	 * 
	 * For example: delete(TCCTCCTGCTGGG, 6, 1) will return TCCTCCTCTGGG
	 * 
	 * @param spacer
	 * @return
	 */
	public static String delete(String s, int x, int length) {
		int position = s.length() - x;

		if (position < 0) {
			return "0";
		}
		return s.substring(0, position) + s.substring(position + length,s.length());
	}
	
	
	

	/**
	 * Inserts a random bp with a specific length  x positions 
	 * away from the end of the PAM site
	 * 
	 * For example: insert(GTGCCGTTAAGGG, 6, 1) will return GTGCCGTCTAAGGG
	 * 
	 * @param spacer
	 * @param position
	 * @return
	 */
	public static String insert(String s, int x, int length) {
		String ch = spacer(length); //using spacer program again since it just generates random bp
		int position = s.length() - x; 
		if (position < 0) {
			return "0";
		}
		return s.substring(0, position) + ch + s.substring(position);
	}

	/**
	 * Adds the PAM site to the spacer sequence.
	 * 
	 * For example: Given the spacer sequence GCGTCGAGAT, addPAM(GCGTCGAGAT) will
	 * return GCGTCGAGATGGG
	 * 
	 * @param spacer
	 * @return
	 */
	public static String addPam(String spacer) {
		return spacer + "GGG";
	}

	/**
	 * Creates a string of random basepairs of a certain length. This will be the
	 * spacer sequence for the hgRNA
	 * 
	 * For example, generate(10) returns CTAACGCAGC
	 * 
	 * @param length
	 * @return
	 */
	public static String spacer(int length) {
		String bp = "AGCT";
		String hg = "";
		Random rd = new Random();

		for (int i = 0; i < length; i++) {
			hg = hg + bp.charAt(rd.nextInt(bp.length()));
		}
		return hg;
	}

	/**
	 * This is one of the hgRNAs in the Kalhor paper.
	 * 
	 * @param length
	 * @param bp
	 * @return
	 */
	public static String hgRNA() {
		String hgRNA = "AGCTGCGTGGTGGGGTTAG";
		return hgRNA;
	}

}
