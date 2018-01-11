package ie.gmit.sw;
/**
 * Shingle Class 
 * this class implements a shingle object which is a small section of a document.
 * @author Uamhan Mac Fhearghusa
 * @version 1.0
 *
 */
public class Shingle {

	
	private int hashWord;
	
	private int documentId;
	
	/**
	 * constructor for shingle object
	 * @param word is the hash value of a section of text passed into a shingle object.
	 * @param documentId is the id of the document this hash value came from.
	 */
	public Shingle(int word,int documentId) {
		
		this.hashWord=word;
		this.documentId=documentId;
	}

	/**
	 * getter for hashWord
	 * @return hashWord.
	 */
	public int getHashWord() {
		return hashWord;
	}

	/**
	 * setter for hashWord
	 * @param word sets this objects hashWord to word
	 */
	public void setHashWord(int word) {
		this.hashWord = word;
	}

	/**
	 * getter for DocumentId
	 * @return documentId
	 */
	public int getDocumentId() {
		return documentId;
	}

	/**
	 * setter for DocumentId
	 * @param documentId sets this objects documentId to documentId
	 */
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	
}
