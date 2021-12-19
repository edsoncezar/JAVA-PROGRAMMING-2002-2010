//------------
// Lab5.java
//------------

//import java.lang.String;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;

//Holds the nodes of the trie
class TrieNode{
	private String suffix, letter = "";
	private TrieNode[] children;
	
	public TrieNode(){
		suffix = null;
		children  = new TrieNode[26];
	}//constructor
	
	public String getLetter(){
		return letter;
	}// method getLetter
	
	public void setLetter(String current){
		letter = current;
	}// method setLetter
	
	public String getSuffix(){
		// Returns suffix
		return suffix;
	}// method getSuffix
	
	public void setSuffix(String temp){
		// Sets suffix to particular String
		suffix = temp;
	}// method getSuffix
	
	public TrieNode getChild(int index){
		// Returns reference to child at certain location
		return children[index];
	}// method getChild
	
	public void setChild(TrieNode temp,int index){
		// Set one of the child references to a particular TrieNode
		children[index] = temp;
		
	}// method setChild
}// class TrieNode

class TrieDictionary implements TrieInterface{
	private TrieNode root = new TrieNode();
	private int numAccess;
	private int numWords;
	private String curWord =" ";
	protected int index = 0;
	
	public TrieDictionary(){
	}// constructor
	
	// Inserts a word into the trie
	public void addWord(String word){
		addWord(root, word);
		
	}// method insert
	
	// Inserts a word into the trie
	public void addWord(TrieNode temp, String word){
		int whichchild;		
		// You will get to an interior node that finishes your word.
		
		// You will hit a leaf node. 
		if(temp.getSuffix() != null){
			// You will find that the word already exists in the trie. If the word is a duplicate, we don't need to make any changes. 
			if(temp.getSuffix().compareTo(word) == 0){
				// The first problem is obviously the easiest one. As we traverse the trie, we will find a node that represents the word 
				// that we are trying to add. Since a trie does not allow duplicate words, we don't need to make any changes to the trie. 
			}// IF SUFFIX EQUALS WORD					
			else{
				// The fourth problem is somewhat more complicated. If you hit a leaf node, that node could already have a suffix in it.
				// Since you need to ensure that longer words only finish at a leaf node, and you got here because your data belongs in this subtree, 
				// you'll have to move the suffix that is already there out of the way (unless the suffix is an empty string - 
				// then you don't have to worry about it). That means you figure out what branch to take on the old data, create a node for it, 
				// and put it there. Then you continue trying to add your data where it belongs. Every time you collide with a suffix, 
				// you make a new child of the current node where the existing suffix goes, put the child in the correct place, 
				// and retry adding the new word to the trie until you hit one of the base cases given above. 
				
				if(temp.getSuffix().length()==0){
					whichchild = word.charAt(0) -'a';
					if(temp.getChild(whichchild) == null){
						TrieNode newtemp = new TrieNode();
						String letter = word.substring(0, 1);
						newtemp.setLetter(letter);
						temp.setChild(newtemp, whichchild);
						newtemp.setSuffix(word.substring(1));
					}//if
					else{
						addWord(temp.getChild(whichchild), word.substring(1));
					}// else
				}// IF SUFFIX IS AN EMPTY STRING
				else{
					String tempstr = temp.getSuffix();
					//-------------------------------
					if(word.length() == 0)
						temp.setSuffix("");
					else
						temp.setSuffix(null);
					//-------------------------------
					whichchild = tempstr.charAt(0)-'a';
					TrieNode node = new TrieNode();
					node.setLetter(tempstr.substring(0,1));
					temp.setChild(node, whichchild);
					
					//------------------------------------------
					if(tempstr.length() == 1)
						node.setSuffix("");
					else
						node.setSuffix(tempstr.substring(1));
					//------------------------------------------
					
					if(word.length() != 0)
						addWord(temp, word);
				}// ELSE SUFFIX ISN'T EMPTY
			}// IF SUFFIX IS NOT NULL
		}// else
		else{ //ElSe
			// You will run out of places to go at an interior node (you'll need to move down a branch that has no nodes) 
			if(word.length()==0)
				temp.setSuffix("");
			else{
				whichchild = word.charAt(0) -'a';
				if(temp.getChild(whichchild) == null){
					// The third problem is really the basis case for your recursive method. If you get to a point where you can 
					// no longer proceed, you create a child in the appropriate branch and that is where the word ends.
					// If necessary, set the suffix in that node to the part of the word that's left. Done. 
					
					TrieNode newtemp = new TrieNode();
					String letter = word.substring(0, 1);
					newtemp.setLetter(letter);
					temp.setChild(newtemp, whichchild);
					
					//------------------------------------------
					if(word.length() == 1)
						newtemp.setSuffix("");
					else
						newtemp.setSuffix(word.substring(1));
					//------------------------------------------
				}// IF CHILD IS NULL
				else{
					if(temp.getChild(whichchild) != null){
						whichchild = word.charAt(0)-'a';
						addWord(temp.getChild(whichchild), word.substring(1));
					}// if
				}// else
			}// else
		}// ElSe
	}// method insert	
	
	// Searches to see if the word is in the dictionary (trie)
	public boolean lookup(String tempword){
		numWords++;
		return lookup(tempword, root);
		// When we reach the final node, the word is only valid if the String is not empty string
		// If we reach null then the string does not exist
		// Count the number of words passed in
	}// method lookup
	
	public boolean lookup(String tempword, TrieNode temp){
		numAccess++;
		int path;
		// You get the word, take the first letter, and head down that path.
		// Get the second letter, and head down that path. Do this until you 
		// can't go any further on the correct path. If the node you're in 
		// has the same suffix as the letters you have left in the word you're 
		// finding, then you've found the word. If it doesn't, then your search 
		// word is not in the trie. 
		// Count the number of accesses to trie nodes
		
		//-----------------Good--------------------- 
		if(temp.getSuffix() != null){
			if(temp.getSuffix().compareTo(tempword)==0){
				return true; 
			}//if
			else{
				// If we can go down the next letter path then continue the lookup
				if(tempword.length() == 0)
					return false;
				path = tempword.charAt(0)-'a';
				if(temp.getChild(path) != null){
					return lookup(tempword.substring(1), temp.getChild(path));
				}// if
				else{
					return false;
				} // else
			}// else
		}// if
		else{ // Suffix is null
			if(tempword.length() == 0)
				return false;
			path = tempword.charAt(0)-'a';
			if(temp.getChild(path) == null){
				return false;
			}// if
			else{
				return lookup(tempword.substring(1), temp.getChild(path));
			}// else
		}// else 		
		//------------------------------------------------ 
	}// method lookup
	
	// Writes the dictionary to a file
	public void dumpToFile(String filename) throws java.io.IOException{
		TrieNode temp = root;
		//Reads in filename and opens that file
		//Calls other dumpToFile method
		
		PrintWriter writer = new PrintWriter( new BufferedWriter
			( new FileWriter( filename ) ) );
		dumpToFile(writer, curWord, temp);	
		writer.close();
		System.out.println("Created: trieoutputfile1.txt (a file of the words in the dictionary in sorted order) ");		
	}// method dumpToFile
	
	public void dumpToFile(PrintWriter w, String curstring, TrieNode temp){
		// In order to calculate the words that form part of a trie, we begin the recursion with an empty string
		// We can then recursively build up words from the tree by concatenating letters to the string.
		// Each time we reach a String that is not null, we will output the current value of the string.
		// Thus, there will be a new version of the String for each recursive call.
		// Write Trie to the file: trieoutputfile1.txt
		if(temp == null)
			return;
		if(temp.getSuffix() != null){
			if(temp.getSuffix().length() == 0){
				curstring = curstring + temp.getLetter() + temp.getSuffix();
				w.println(curstring);
				curstring = curstring.substring(0, curstring.length() -1);
			}// if
			else{
				curstring = curstring + temp.getLetter() + temp.getSuffix();
				w.println(curstring);
			}//else
		}// if
		
		for(int i = 0; i < 26; i++){
			if(temp.getChild(i) != null){
				dumpToFile(w, curstring.concat(temp.getLetter()), temp.getChild(i));
			}// if
		}//for
	}// method dumpToFile
	
	// Clears the trie
	public void clear(){
		root = null;
		numAccess = 0;
		// Sets the trie root to null
	}// method clear
	
	public TrieNode getRoot(){
		// Returns the root of the tree
		return root;
	}// method getRoot
	
	// Reads in the words from "dictionary" file and creates a trie with those words
	public TrieDictionary readInDictionary(String filename) throws IOException{
		TrieDictionary tempTrie = new TrieDictionary();
		
		BufferedReader reader = new BufferedReader( new FileReader( filename ) );
		String line = reader.readLine();
		
		// Read in one word at a time from file.
		// Pass each word into the insert method
		while(line.trim().length() == 0)
			line = reader.readLine();
		
		while(line != null){
			if(line.trim().length() == 0)
				line = reader.readLine();
			if(line == null)
				break;
			line = line.toLowerCase();
			line=line.trim();
			if(line.trim().length() != 0)
				addWord(line);
			
			line = reader.readLine();			
		}//while
		// Return a TrieDictionary
		return tempTrie; 
	}// method readInDictionary
	
	// Read in the text file and spell check it
	public void readInTextFile(String filename) throws IOException{
		
		PrintWriter writer = new PrintWriter( new BufferedWriter
			( new FileWriter( "wordsnotfound.txt" ) ) );
		
		String line2;
		int curLine=1;
		String sentence = "";
		boolean isInSentence =false;
		BufferedReader reader = new BufferedReader( new FileReader( filename ) );
		String line = reader.readLine();
		while(line.trim().length() == 0)
			line = reader.readLine();
		
		while(line != null){
			
			line = line.toLowerCase();
			String delimiters = "";
			// use every character in the normal character set that is not a lower case letter as a delimiter
			for (int i = 0; i < 256; i++) {
				if ((i - ((int)'a') < 0) || (i - ((int)'z') > 0)) {
					delimiters += (char)i;
				} // if
			} // for
			
			// Use a StringTokenizer to cut the lines in the file into words
			StringTokenizer strtok = new StringTokenizer(line, delimiters, false);
			String token;
			if(line.trim().length() !=0){
				while (strtok.hasMoreTokens()) {
					token = strtok.nextToken();
					token = token.trim();
					// Pass each word into search to see if it is in the dictionary
					if(!lookup(token)){
						// The word isn't in the dictionary
						
						// Cut the lines in the file into words
						StringTokenizer strtok2 = new StringTokenizer(sentence, delimiters, false);
						String token2;
						
						// If the word is not there then write it to the file: wordsnotfound.txt
						// If words appear twice on same line then only write it once to the file
						while (strtok2.hasMoreTokens()) {
							token2 = strtok2.nextToken();
							token2 = token2.trim();
							if(token2.compareTo(token) == 0)
								isInSentence = true;
						}// while strtok2
						if(!isInSentence)
							sentence = sentence + token + " ";
					}//if lookup is false
					
				} // while StringTokenizer1
				
				writer.println("Line "+curLine+": "+sentence);
				isInSentence = false;
				sentence = "";
				curLine++; 	// Keep track of which line I'm reading from.
				
			}// if line is not empty != 0
			line= reader.readLine();
			if(line == null)
				break;
			if(line.trim().length() ==0 && line != null)
				line = reader.readLine();
			if(line == null)
				break;
			
		}//while: line != null
		writer.close();
		System.out.println("Created: wordsnotfound.txt (a file of the results of the spell check) ");
	}// method readInTextFile
	
	public int numberOfAccesses(){
		//Returns number of accesses to trie nodes
		return numAccess; // This line can be deleted
	}// method numberOfAccesses
	
	public int numberWords(){
		// Returns the number of words spell checked
		return numWords; // This line can deleted 
	}// method numberWords
	
}// class TrieDictionary

public class Lab5{
	protected String[] args;
	protected TrieDictionary project;
	
	// Main method
	// Calls run method
	public static void main(String[] args) throws java.io.IOException{
		Lab5 program = new Lab5(args);
		program.run();
	}//main method	
	
	public Lab5(String[] args){
		this.args = args;
	}// constructor
	
	// Calls other methods to make program run
	public void run() throws IOException{
		
		String dictionaryfile = args[0]; // 1.Name of dictionary file  
		String textfilename = args[1]; // 2.Name of the text input file to be spell checked.
		
		project = new TrieDictionary(); 
		project.readInDictionary(dictionaryfile);
		
		System.out.println("Read in dictionary file: "+dictionaryfile);
		
		project.dumpToFile("trieoutputfile1.txt");
		
		System.out.println("Read in input file: "+textfilename);
		
		project.readInTextFile(textfilename);
		
		System.out.println("Total number of words processed: "+project.numberWords());
		System.out.println("Total number of nodes accessed: "+project.numberOfAccesses());
		System.out.println("Finished.");
		
	}// method run	
}// class Lab5
