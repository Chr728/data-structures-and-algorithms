/*
 * COMP 352
 * Assignment 1
 * Summer 2021
 * 
 * James Partsafas 40170301
 * Christina Darstbanian 40097340
 */


public class AgeHandler {

	static int currentDate = 20210521;
	static int seniorAge = 650000;
	
	public static void main(String[] args) {
		
		System.out.println("**************************************************************************");
		System.out.println("   Welcome to assignment 1 by James Partsafas and Christina Darstbanian");
		System.out.println("**************************************************************************\n");
		
		//Test data. Comment out and replace with file call if necessary
		String[] pName = {"Linda", "Sam", "Roger", "Alfred", "Roberto", "Melissa", "Brian", "Thomas", "Leslie", "Maria", "Joe"};
		String[] pDOB = {"1-1-2003", "24-2-1940", "11-12-1995", "31-3-1980", "29-6-1950", "25-7-1945", "15-7-2002", "20-7-2004", "27-4-1990", "9-5-1941", "1-1-1900"};
		
		System.out.println("First test the rearrangeParticipants method:");
		int numSeniors = rearrangeParticipants(pName, pDOB, pName.length);
		
		//Print results of rearrangeParticipants
		for (int i = 0; i < pName.length; i++) {
			System.out.println(pName[i] + "\t" + pDOB[i]);
		}
		System.out.println("\nThere are " + numSeniors + " seniors in this list.");
		
		
		System.out.println("\nProgram is ending.\nGoodbye.");
	}
	
	//Rearrange participants using variant of merge sort
	public static int rearrangeParticipants(String[] pName, String[] pDOB, int n) {
		
		//Special cases. Abort.
		if (pName.length != pDOB.length || n == 0) //Mismatched lengths or empty arrays
			return -1;
		if (n == 1) { //Only one person in array
			int age = getAge(pDOB[0]);
			return (age >= seniorAge) ? 1 : 0; //Check if the single person in the array is a senior
		}
		
		//Special cases did not pass. Now use the general case.
		int mid = n/2;
		String[] leftName = new String[mid];
		String[] leftDate = new String[mid];
		String[] rightName = new String[n-mid];
		String[] rightDate = new String[n-mid];
		
		for (int i = 0; i < mid; i++) {
			leftName[i] = pName[i];
			leftDate[i] = pDOB[i];
		}
		for (int i = mid; i < n; i++) {
			rightName[i-mid] = pName[i];
			rightDate[i-mid] = pDOB[i];
		}
		
		//Create tree structure recursively
		rearrangeParticipants(leftName, leftDate, mid);
		rearrangeParticipants(rightName, rightDate, n-mid);
		
		//call mergeSeniorParticipants on every part of the created tree
		int seniorCount = mergeSeniorParticipants(pName, pDOB, leftName, leftDate, rightName, rightDate, mid, n-mid); //Goes through list ordering seniors
		
		return seniorCount;
	}
	
	//Works with rearrangeParticipants to reorder the passed arrays
	public static int mergeSeniorParticipants(
			String[] pName, String[] pDOB, String[] leftName, String[] leftDate, 
			String[] rightName, String[] rightDate, int left, int right) 
	{		
		//i traverses left lists, j the right ones, and k the final sorted one
		int i = 0, j = 0, k = 0;
		
		int seniorCount = 0;
		
		//Move through left and right lists and reorder the original as you go
		int leftAge, rightAge;
		boolean leftIsSenior, rightIsSenior;
		
		while (i < left && j < right) {
			
			leftAge = getAge(leftDate[i]);
			rightAge = getAge(rightDate[j]);
			leftIsSenior = rightIsSenior = false;
			if (leftAge >= seniorAge)
				leftIsSenior = true;
			if (rightAge >= seniorAge)
				rightIsSenior = true;
			
			
			//Check for seniors
			if (leftIsSenior && !rightIsSenior) { //left is senior, right is not
				pName[k] = leftName[i];
				pDOB[k] = leftDate[i];
				i++;
				k++;
				seniorCount++;
			}
			else if (rightIsSenior && !leftIsSenior) { //right is senior, left is not
				pName[k] = rightName[j];
				pDOB[k] = rightDate[j];
				k++;
				j++;
				seniorCount++;
			}
			//Both left and right are seniors
			else if (rightIsSenior && leftIsSenior) {
				seniorCount++;
				if (leftAge >= rightAge) {
					pName[k] = leftName[i];
					pDOB[k] = leftDate[i];
					i++;
					k++;
				}
				else {
					pName[k] = rightName[j];
					pDOB[k] = rightDate[j];
					k++;
					j++;
				}
			}
			//Both must be juniors
			else {
				if (leftAge <= rightAge) {
					pName[k] = leftName[i];
					pDOB[k] = leftDate[i];
					i++;
					k++;
				}
				else {
					pName[k] = rightName[j];
					pDOB[k] = rightDate[j];
					k++;
					j++;
				}
			}
		}
		
		//One of these two while loops will run depending on if the left or right list was exhausted first and the other one isn't done with yet
		while (i < left) {
			
			if (getAge(leftDate[i]) >= seniorAge)
				seniorCount++;
			pName[k] = leftName[i];
			pDOB[k] = leftDate[i];
			k++;
			i++;
		}
		while (j < right) {
			if (getAge(rightDate[j]) >= seniorAge)
				seniorCount++;
			pName[k] = rightName[j];
			pDOB[k] = rightDate[j];
			k++;
			j++;
		}
		
		return seniorCount;
	}
	
	
	/*Helper methods
	 * 
	 * 
	 */
	
	//Method to get age when passed a String containing the date of birth.
	public static int getAge(String dob) {
		String[] stringDate = dob.split("-");

		//Add extra zero if necessary to day and month for consistent formatting
		if (stringDate[0].length() == 1)
			stringDate[0] = "0" + stringDate[0];
		if (stringDate[1].length() == 1)
			stringDate[1] = "0" + stringDate[1];
		
		int date = Integer.parseInt(stringDate[2] + stringDate[1] + stringDate[0]); //Combine date into format yyyyMMdd and convert to an integer
		int age = currentDate - date; //age is in a nonstandard format. Somebody exactly 65 years old will have an assigned age of 650000
		return age;
	}

}

