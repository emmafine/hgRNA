package hgRNAs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public class Sim2 {
	private static final Random RANDOM = new Random();

	// Make sure to change both of these if changing one
	static int cells = 4;
	static double cellsDouble = 4.0;

	// Make sure to change both of these if changing one
	static int replicates = 5;
	static double repDouble = 5.0;

	// Arrays that will be used
	static String[] array = new String[cells];
	static int[] stats = new int[replicates];
	static int[] newArray = new int[replicates];

	public static void main(String[] args) throws IOException {
		int barcodeLength = 3;
		int min = 1;
		int max = 3;

		// System.out.println(Arrays.toString(fullStatsMeanPerc(3, min, max)));

//		for (int i = 1; i < hgRNAs; i++) {
//			write("thisIsNew.txt", fullStatsMeanPerc(i, min, max));
//		}

		
		// write("testing1.txt", fullStatsMeanPerc(hgRNAs, min, max));

	}

	/**
	 * Saves to a file
	 * 
	 * @param filename
	 * @param ds
	 * @throws IOException
	 */
	public static void write(String filename, String[] x) throws IOException {
		BufferedWriter outputWriter = null;
		outputWriter = new BufferedWriter(new FileWriter(filename));
		for (int i = 0; i < x.length; i++) {
			outputWriter.flush();
			outputWriter.write(x[i] + "");
			outputWriter.newLine();
		}
		outputWriter.flush();
		outputWriter.close();
	}

	/**
	 * Calculates all the means.
	 * 
	 * @param hgRNAs
	 * @param min
	 * @param max
	 * @return
	 */
	public static String[] fullStatsMeanPerc(int hgRNAs, int min, int max) {
		String[] calculates = new String[max];

		for (int i = 1; i < max; i++) {
			if (mean(unique(run(hgRNAs, min, i))) <= 0) {
				calculates[i] = "0";
			} else {
				calculates[i] = ((mean(unique(run(hgRNAs, min, i))) / cellsDouble) * 100) + "";
			}
		}
		return calculates;

	}

	/**
	 * Reports the max
	 * 
	 * @param array
	 * @return
	 */
	public static int max(int[] array) {
		int largest = -1;
		for (int i = 0; i < replicates; i++) {
			if (largest < array[i]) {
				largest = array[i];
			}
		}
		return largest;
	}

	/**
	 * Reports the min
	 * 
	 * @param array
	 * @return
	 */
	public static int min(int[] array) {
		int smallest = 1000;
		for (int i = 0; i < replicates; i++) {
			if (smallest > array[i]) {
				smallest = array[i];
			}
		}
		return smallest;
	}

	/**
	 * Calculate median
	 * 
	 * @param array
	 * @return
	 */
	public static double median(int[] array) {
		Arrays.sort(array);

		if (replicates % 2 != 0) {
			return (double) array[replicates / 2];
		} else {
			return (double) (array[(replicates - 1) / 2] + array[replicates / 2]) / 2.0;
		}
	}

	/**
	 * Calculates the mean of cells uniquely labeled
	 * 
	 * For example: 
	 * 
	 * @param array
	 * @return
	 */
	public static double mean(int[] array) {
		int sum = 0;
		for (int i = 0; i < replicates; i++)
			sum += array[i];

		return (double) sum / (double) replicates;
	}

	/**
	 * Calculates average
	 * 
	 * @param array
	 * @return
	 */
	public static double average(int[] array) {
		int sum = 0;
		double average = 0;
		for (int value : array) {
			sum += value;
			average = sum / repDouble;
		}
		return average;
	}

	/**
	 * Returns the number of unique cells
	 *
	 * 
	 * Say that unique takes in the array [5, 5, 5, 4, 5, 3] and we have 5 cells
	 * Generates a new array of size 6.
	 * Generates a second array of size 6.
	 * 
	 * 
	 * for (0 < 6){
	 *  newArray[0] = 5 - 5 = 0
	 *  second[0] = 5 - 0 = 5
	 * }
	 * 
	 * 
	 * for (3 < 6){
	 *  newArray[3] = 5 - 4 = 1
	 *  second[3] = 5 - 1 = 4
	 * }
	 *  
	 * returns [5, 5, 5, 4, 
	 * 
	 * 
	 * Imagine we have 50,000 cells. If we have a 3 hgRNAs with a length of 32
	 * bits (i.e. Shannon entropy score of 5), then we will have 32^3 = 32,768 cells
	 * approximately labeled (with uniform distribution). This means that 17,232
	 * (b/c 50,000 - 32,768) cells don't receive a label, they will eventually have
	 * to be relabeled with the same barcode. This means that the number of cells
	 * with a unique label is actually only 15,536 (32,768 - 17,232)
	 * 
	 * @param array
	 * @return
	 */
	public static int[] unique(int[] array) {
		int[] newArray = new int[replicates]; 
		int[] second = new int[replicates]; 
		for (int i = 0; i < replicates; i++) {
			newArray[i] = cells - array[i]; 
			second[i] = array[i] - newArray[i];
		}
		return second;
	}

	/**
	 * Runs replications of cellsWithLabels. Returns an array of integers. 
	 * 
	 * For example: run(4,1,5) with 6 replications could return [5, 5, 5, 4, 5, 3]
	 * 
	 * [5, 5, 5, 4, 5, 3] means that 5 cells are labeled for the 1st, 2nd, 3rd, and 5th cell. 
	 * Cells number 4 and cells number 6 have repeated barcodes.  
	 * 
	 * @param array
	 * @return
	 */
	public static int[] run(int barcodeLength, int min, int max) {
		int[] stats = new int[replicates];
		for (int i = 0; i < replicates; i++) {
			stats[i] = cellsWithLabels(cellsBarcoded(barcodeLength, min, max));
		}
		return stats;
	}

	/**
	 * Returns the number of cells with labels
	 * 
	 * For example: if we have 5 cells which have been barcoded s.t. the array is 
	 * [3 2 , 1 5 , 3 2 , 3 3 , 7 2 ], then cellsWithLabels(array) = 4
	 * 
	 * Why? Because one of the labels is not unique.
	 * 
	 * @param array
	 * @return
	 */
	public static int cellsWithLabels(String[] array) {
		List input = Arrays.asList(array);
		HashSet<String> check = new HashSet<String>(input);
		return check.size();
	}

	/**
	 * Returns an array with cell that have been barcoded. Each cell of the array
	 * represents a literal cell.
	 * 
	 * For example: cellsBarcoded(2,1,8) with 5 cells will return [3 2 , 1 5 , 4 3 , 3 3 , 7 2 ]
	 * 
	 * @param barcode
	 * @return
	 */
	public static String[] cellsBarcoded(int barcodeLength, int min, int max) {
		for (int i = 0; i < cells; i++) {
			array[i] = barcode(barcodeLength, min, max);
		}
		return array;
	}

	/**
	 * Generates a barcode of hgRNAs. The min/max represent the number of hgRNAs.
	 * 
	 * For example: min = 1 and max = 4, there will be 4 hgRNAs (hgRNA1, hgRNA2,
	 * hgRNA3, and hgRNA4) total to build the barcode from.
	 * 
	 * barcode(3,1,8) could return 6 7 3 
	 * barcode(10,1,2) could return 2 2 1 1 1 2 2 1 2 1
	 * 
	 * 
	 * @param hgRNAs
	 * @param min
	 * @param max
	 * @return
	 */
	public static String barcode(int barcodeLength, int min, int max) {
		String barcode = "";
		for (int i = 0; i < barcodeLength; i++) {
			barcode = barcode + random(min, max) + " ";
		}
		return barcode;
	}

	/**
	 * Generates a random int
	 * 
	 * For example: random(1,7) could return 5
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int random(int min, int max) {
		return RANDOM.nextInt(max) + min;
	}

}
