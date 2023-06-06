/*
 * COMP 352
 * Assignment 1
 * Summer 2021
 * 
 * James Partsafas 40170301
 * Christina Darstbanian 40097340
 */

public class PartB {

	static int currentDate = 20210521;
	static int seniorAge = 650000;

	public static void main(String[] args) {

		//Test data. Comment out and replace with file call if necessary
		String[] pName = {"Linda", "Sam", "Roger", "Alfred", "Roberto", "Melissa", "Brian", "Thomas", "Leslie", "Maria", "Joe"};
		String[] pDOB = {"1-1-2003", "24-2-1940", "11-12-1995", "31-3-1980", "29-6-1950", "25-7-1945", "15-7-2002", "20-7-2004", "27-4-1990", "9-5-1941", "1-1-1900"};
		
		System.out.println("First test the rearrangeParticipantsPartB method:");
		int numSeniors = rearrangeParticipantsPartB(pName, pDOB, pName.length);
		
		//Print results of rearrangeParticipants
		for (int i = 0; i < pName.length; i++) {
			System.out.println(pName[i] + "\t" + pDOB[i]);
		}
		System.out.println("\nThere are " + numSeniors + " seniors in this list.");
		
		
		System.out.println("\nProgram is ending.\nGoodbye.");

	}
	
	public static int rearrangeParticipantsPartB(String[] pName, String[] pDOB, int n) {
		
		//Special cases
		if (pName.length != pDOB.length || n == 0) {
			return 0;
		}
		if (n == 1) { //Only one element in array to consider
			int seniorCount = 0;
			for (int i = 0; i < pName.length; i++) {
				if (getAge(pDOB[i]) >= seniorAge)
					seniorCount++;
				else
					break;
			}
			return seniorCount;
		}
		
		//General case
		int tempIndex;
		int current;
		String tempString;
		int starting = pName.length - n;
		int tempAge;
		boolean leftIsSenior = false;
		boolean rightIsSenior = false;
		
		tempIndex = starting;
		current = getAge(pDOB[starting]);
		
		//Check if first person is senior
		if (current >= seniorAge) 
			leftIsSenior = true;
		
		//Left participant is a non-senior
		if (!leftIsSenior) {
			for (int j = starting + 1; j < pName.length; j++) {
				tempAge = getAge(pDOB[j]);
				if (tempAge >= seniorAge) {
					//Swap positions when senior is found
					tempString = pName[starting];
					pName[starting] = pName[j];
					pName[j] = tempString;
					tempString = pDOB[starting];
					pDOB[starting] = pDOB[j];
					pDOB[j] = tempString;
					
					leftIsSenior = true;
					current = tempAge;
					
					break;
				}
			}
		} 
		
		//Main loop when a senior is found
		if (leftIsSenior) {
			for (int j = starting + 1; j < pName.length; j++) {			
				
				rightIsSenior = false;
				tempAge = getAge(pDOB[j]);
				if (tempAge >= seniorAge) 
					rightIsSenior = true;

				if (tempAge > current && rightIsSenior) {
					tempIndex = j;
					current = tempAge;
				}
				
			}
		}
		
		
		//Only juniors are in the loop
		else {
			for (int j = starting + 1; j < pName.length; j++) {			
				
				rightIsSenior = false;
				tempAge = getAge(pDOB[j]);
				if (tempAge >= seniorAge) 
					rightIsSenior = true;
				
				if (tempAge < current && !rightIsSenior) {
					tempIndex = j;
					current = tempAge;
				}
				
			}
		}
		
		
		//Swap positions
		if (tempIndex != starting) {
			tempString = pName[starting];
			pName[starting] = pName[tempIndex];
			pName[tempIndex] = tempString;
			tempString = pDOB[starting];
			pDOB[starting] = pDOB[tempIndex];
			pDOB[tempIndex] = tempString;
		}
				
		//Recursive call
		return rearrangeParticipantsPartB(pName, pDOB, n-1);
		
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
