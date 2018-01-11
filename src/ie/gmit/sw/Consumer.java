package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * this class implements a consumer object this object takes shingles for the blocking queue and using a minHash algorithm gets
 * the smallest k hash values for these shingles which will be used to compute the jaccard index of the documents
 * @author Uamhan Mac Fhearghusa
 * @version 1.0
 */
public class Consumer implements Runnable{
	
	private BlockingQueue<Shingle> q; 	
	private int k;						
	private int[] minhashes;			
	private ConcurrentMap<Integer,List<Integer>> map = new ConcurrentHashMap<>();
	private ExecutorService pool;
	
	/**
	 * constructor for object Consumer calling the method init() at the end to instantiate the properties of this object
	 * 
	 * @param q is the blocking queue which this object takes shingles from
	 * @param k is the number of shingles we are going to use for the minHash algorithm
	 * @param poolSize is the pool size of threads to be executed upon
	 * 
	 */
	public Consumer(BlockingQueue<Shingle> q,int k,int poolSize){
	
		this.q=q;
		this.k=k;
		this.pool=Executors.newFixedThreadPool(poolSize);
		
		init();
		
	}
	/**
	 * Initialises the properties of this object
	 * Creating k random values for minHashes
	 * and initialises the array lists list 1 and list 2 filling them with k max value integers it then adds these lists to the ConcurrentHashMap.
	 */
	public void init() {
		Random random = new Random();
		minhashes =  new int[k];
		
		for(int i=0;i<minhashes.length;i++) {
			minhashes[i]=random.nextInt();
		}
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		
		for(int i=0;i<k;i++)
		{
			list1.add(Integer.MAX_VALUE);
			list2.add(Integer.MAX_VALUE);
		}
		map.put(0,list1);
		map.put(1,list2);
	}
	/**
	 * getter for the map propertie.
	 * @return returns the ConcurrentHashMap
	 */
	public Map<Integer,List<Integer>> getMap(){
		return this.map;
	}
	/**
	 * takes a shingle from the blocking queue checks to see if this shingle has a valid value or if the end of the document has been reached
	 * then executes a new runnable from the thread pool this new runnable implements the minHash algorithm it gets a list from the hashmap
	 * and generates k different hash algorithms and checks them against the list always replacing the list value with the smallest it then
	 * places the list back on the map.
	 */
	public void run() {
		int documentCount = 2;
		
		while (documentCount > 0) 
		{
			
			try {
				Shingle s = q.take();
				//signifies end of document
				if(s.getHashWord() != 48)
				{
					pool.execute(new Runnable() {
						
						@Override
						public  void  run() {
							
							List<Integer>list=map.get(s.getDocumentId());
							
							for(int i=0;i<minhashes.length;i++)
							{
								int value = s.getHashWord() ^ minhashes[i];
								
									if(list.get(i)>value) 
									{
										list.set(i, value);
									}	
							}

						map.put(s.getDocumentId(), list);
						
						
						}
						});
				
				}
				else 
				{
					documentCount--;
				}
			}
			catch(Exception e)
			{
				System.out.println("error: "+e);
			}
		}
		}
	
}