package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * this class implements a Producer object this object takes input from a document and formats this input
 * into Shingle objects it then adds this shingle to a blocking queue.
 * 
 * @author Uamhan Mac Fhearghusa 
 * @version 1.0
 */
public class Producer implements Runnable{
	
	private String filePath;
	private int documentId;
	private BlockingQueue<Shingle> queue;
	/**
	 * Constructor for producer class
	 * @param path this is the file path of the document to be formated into shingles.
	 * @param documentId the document id of the document to be formated into shingles.
	 * @param queue is the blocking que these shingles will be added to.
	 */
	public Producer (String path,int documentId,BlockingQueue<Shingle> queue){
		
		this.filePath=path;
		this.documentId=documentId;
		this.queue=queue;
		
	}
	
	/**
	 * reads the document at the specified file path line by line. converts each line to lower case and splits the string into an array of words
	 * this word array is the iterated over by a foreach loop that creates a shingle and adds it to the blocking queue
	 */
	public void run() {
		try {
			FileReader input = new FileReader(filePath);
			BufferedReader read = new BufferedReader(input);
			String line = null;
		
			while ( (line = read.readLine()) != null) {
				if(line.length()>0)
				{
					line=line.toLowerCase();
					String[] words = line.split(" ");
					for(String word : words) {
						Shingle tempShingle = new Shingle(word.hashCode(),documentId);
						queue.put(tempShingle);
					}
				}
			}
			input.close();
			read.close();
		}
		catch(Exception e) {
			System.out.println("error: "+e);
		}
		
	}
}


