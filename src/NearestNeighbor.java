/* Programming Fundamentals
 * Name: Nhu-Chi Ha
 * PROGRAMMING ASSIGNMENT 3
 */
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class NearestNeighbor 
{
	public static void main(String[] args) throws IOException
	{	
		// Introduction
		System.out.println("Programming Fundamentals");
		System.out.println("Name: Nhu-Chi Ha");
		System.out.println("PROGRAMMING ASSIGNMENT 3");
		//Declare arrays
		double[][] trainingAttributes = new double[75][4];
		String[] trainingLabels = new String[75];
		double[][] testAttributes = new double[75][4];
		String[] testLabels = new String[75];
		
		//Declare variables for two files; 
		String inputTrainingFile, inputTestingFile;
		//Scanner for user input and reading Training file	
		Scanner inputTraining = new Scanner(System.in);
		System.out.print("Enter the name of the training file: ");
		inputTrainingFile = inputTraining.nextLine();
		//Scanner for user input and reading testing file	
		Scanner inputTesting = new Scanner(System.in);
		System.out.print("Enter the name of the testing file: ");
		inputTestingFile = inputTesting.nextLine();
		System.out.println("EX#: TRUE LABEL, PREDICTED LABEL");
		
		// Step 02. Initialize our arrays with data from external file
		InitializeData("C:\\Users\\nhuch\\Documents\\GitHub\\Project_3\\src\\" + inputTrainingFile, trainingAttributes, trainingLabels);
		InitializeData("C:\\Users\\nhuch\\Documents\\GitHub\\Project_3\\src\\" + inputTestingFile, testAttributes, testLabels);
		
		// Step 03. Classified the test sample
		String[] results = ClassifyIris(trainingAttributes,trainingLabels,testAttributes);
		
		// Step 04. Evaluate the accuracy of this model
		CompareClassification(results, testLabels);
		
		System.out.println("");		
	}
	
	public static void CompareClassification(String[] predictedClass, String[] actualClass)
	{
		double correctCount = 0;
		for(int i = 0; i < actualClass.length; i++)
		{
			String actualValue = actualClass[i];
			String predictedValue = predictedClass[i];
			
			if (actualValue.equals(predictedValue))
				correctCount++;
			
			// Print values
			System.out.println(i + 1 + ": " + actualClass[i] + " " + predictedClass[i]);
		}	
		double accuracy = correctCount/actualClass.length;
		System.out.println("Accuracy: " + accuracy);
	}
	
	public static String[] ClassifyIris(double[][] trainingArray, String[] trainingClass, double[][] testingArray)
	{
		String[] results = new String[75];
		
		for (int i = 0; i < testingArray.length; i++)
		{
			// find the nearest neighbor 
			String currentClass = "";
			double minDistance = 0;
			double sly = 0, swy = 0, ply = 0, pwy = 0;
			
			sly = testingArray[i][0];
			swy = testingArray[i][1];
			ply = testingArray[i][2];
			pwy = testingArray[i][3];
			
			for (int j = 0; j < trainingArray.length; j++)
			{
				// training attributes
				double slx = 0, swx = 0, plx = 0, pwx = 0;
				slx = trainingArray[j][0];
				swx = trainingArray[j][1];
				plx = trainingArray[j][2];
				pwx = trainingArray[j][3];
				
				// calculate the nearest neighbor and obtain its class
				double currentDistance = 0;
				currentDistance = calcDistance(slx, swx, plx, pwx, sly, swy, ply, pwy);
				
				if (minDistance > currentDistance || minDistance == 0)
				{
					minDistance = currentDistance;
					currentClass = trainingClass[j];
				}
			}
			
			// store the prediction into an array
			results[i] = currentClass;
		}	
		return results;
	}
	
	public static void InitializeData(String fileName, double[][] attributeArray, String[] classArray)
	{
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = "";
			int row = 0;
			
			while(((line = reader.readLine()) != null))
			{
				String[] attrList = line.split(",");
				// get the column values for training sample
				for(int col = 0; col < attrList.length - 1; col ++)
				{
					String number = attrList[col];
					attributeArray[row][col] = Double.parseDouble(number);
				}
				// get the column value for training label
				classArray[row] = attrList[4];
				row++;
			}
			
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}	
	}
	
	public static double calcDistance(double slx, double swx, double plx, double pwx,
									  double sly, double swy, double ply, double pwy)
	{
		double dist = 0; 
		dist = Math.sqrt(Math.pow((slx-sly),2) + Math.pow((swx - swy),2) + Math.pow((plx - ply),2) + + Math.pow((pwx - pwy),2));
		return dist;
	}
}
