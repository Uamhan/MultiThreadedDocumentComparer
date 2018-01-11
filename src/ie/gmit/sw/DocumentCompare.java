package ie.gmit.sw;


import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * DocumentCompare class contains the main method for this package.
 * 
 * @author Uamhan Mac Fhearghusa
 * @version 1.0
 */
public class DocumentCompare {

	/**
	 * <p>
	 * main method
	 * </p>
	 * <p>
	 * contains main menu loop with user choice variable that takes user input to select which action to do using a switch statement
	 * </p>
	 * <p>
	 * choice 1: add file path A
	 * </p>
	 * <p>
	 * choice 2: add file path B
	 * </p>
	 * <p>
	 * choice 3: Select shingle word size
	 * </p>
	 * <p>
	 * choice 4: Calculate jaccard index
	 * </p>
	 * to calculate the jaccard index we create three threads 2 producer threads one for each document and 1 consumer thread
	 * these create a map of has values for documents A and B we find the size of the intersection of these two sets AnB
	 * and calculate the jaccard index using AnB / (a + b - Anb)
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException{
		
		
		String docPathA = "";
		
		String docPathB = "";
		
		int shingleLength = 0;
		
		int choice;
		
		boolean exit = false;
		
		Map<Integer,List<Integer>> map = new ConcurrentHashMap<Integer,List<Integer>>();
		
		Scanner sc = new Scanner(System.in);
		
		BlockingQueue<Shingle> queue= new LinkedBlockingQueue<Shingle>(100);
		
		while(!exit){
		
			System.out.println("Document A path = "+docPathA);
			System.out.println("Document B path = "+docPathB);
			System.out.println("Shingle Length = "+shingleLength);
			
			System.out.println("Enter 1 to Set A Path");
			System.out.println("Enter 2 to Set B Path");
			System.out.println("Enter 3 to Set Shingle Length");
			System.out.println("Enter 4 to calculate document similarity");
			choice = Integer.parseInt(sc.nextLine());
			
			switch(choice)
			{
			//set Path A
			case 1:
				System.out.println("Enter Document A path");
				docPathA=sc.nextLine();
				break;
			//set Path B
			case 2:
				System.out.println("Enter Document B path");
				docPathB=sc.nextLine();
				break;
			//set Shingle Length
			case 3: 
				System.out.println("Enter Amount of words per Shingle");
				shingleLength=Integer.parseInt(sc.nextLine());
				break;
			//calculate similarity
			case 4:
				if(!docPathA.equals(""))
				{
					if(!docPathB.equals(""))
					{
						if(shingleLength!=0)
						{
							Consumer c = new Consumer(queue,300,4);
							Producer p1= new Producer(docPathA,0,queue);
							Producer p2= new Producer(docPathB,1,queue);
							
							Thread t1 = new Thread(p1);
							Thread t2 = new Thread(p2);
							Thread t3 = new Thread(c);
							
							t1.start();
							t2.start();
							t3.start();
							
							System.out.println("Calculating...");
							Thread.sleep(5000);
							
							
							
							map = c.getMap();
							
							
							List<Integer> a = map.get(0);
							List<Integer> b = map.get(1);
							List<Integer> AnB = map.get(0);
							
							AnB.retainAll(b);
							
							double JaccardIndex = (double)AnB.size() / ((double)a.size() + (double)b.size() - (double)AnB.size());							
							
							System.out.println("");
							System.out.println("Jaccard Index = " + JaccardIndex);
							System.out.println("similarity in % = " + JaccardIndex*100);
							
							
							exit = true;
							sc.close();
							break;
							
						}
						else
						{
							System.out.println("please ensure Shingle Length is set");
						}
					}
					else
					{
						System.out.println("please ensure Document B path is set");
					}
				}
				else
				{
					System.out.println("please ensure Document A path is set");
				}
				
				
				break;
			
			default:
				System.out.println("Please Enter Valid Value");
				break;			
			}
		}	
	}
}
