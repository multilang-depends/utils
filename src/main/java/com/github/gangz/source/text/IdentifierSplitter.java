package com.github.gangz.source.text;


import java.util.ArrayList;
import java.util.List;

/**
 * Split identifiers 
 * e.g. 
 * T -> T
 * Hello_World -> Hello, World
 * HelloWorld -> Hello, World (Camel case)
 * DNAReader -> DNA Reader (keep lasted Captial character)
 * readDNA -> read DNA 
 * =======
 * support predefined words
 * JUnit4Runner -> JUnit4 Runner (if JUnit4 is predefined)
 * @author Gang ZHANG
 *
 */
public class IdentifierSplitter {

	ArrayList<String> predefinedWords;
	public IdentifierSplitter(){
		predefinedWords = new ArrayList<String>();
	}
	
	/**
	 * Add pre-defined words, each word with 1 invocation
	 * @param predefineWord the word
	 */
	public void addPredefinedWord(String predefineWord) {
		predefinedWords.add(predefineWord);
	}
	
	/**
	 * split the given identifier
	 * @param identifier 
	 * @return a string list, each one is a word
	 */
	public List<String> split(String identifier) {
		List<String> result = new ArrayList<String>();
		String converted=camelCaseConvert(identifier);
		String[] splitted = converted.split("_");
		add_non_empty_words_to_array(result, splitted);
		return result;
	}

	private String camelCaseConvert(String input) {
		char[] charlist = input.toCharArray();
		String result = new String();
		for (int i=0;i<charlist.length;i++){
			int preDefinedWordLength = matchPredefinedWord(input,i);
			if (preDefinedWordLength!=0){
				result += input.substring(i,i+preDefinedWordLength) + "_";
				i=i+preDefinedWordLength;
			}
			char ch = charlist[i];
			if (Character.isUpperCase(ch))
			{
				if (previousWordIsLowerCase(charlist, i)||
					 !nextWordIsUpperCase(charlist, i))
					result += "_";
			}
			result = result+charlist[i];
		}
		return result;
	}
	private int matchPredefinedWord(String input, int position) {
		for (String predefinedWord:predefinedWords){
			if (position+predefinedWord.length()>=input.length()) return 0;
			if (input.substring(position,predefinedWord.length()+position).equals(predefinedWord))
				return predefinedWord.length();
		}
		return 0;
	}
	private boolean previousWordIsLowerCase(char[] charlist, int i) {
		if (i==0) return false;
		return Character.isLowerCase(charlist[i-1]);
	}

	private boolean nextWordIsUpperCase(char[] charlist, int i) {
		if (i>=charlist.length-1) return  true;
		return !Character.isLowerCase(charlist[i+1]);
	}

	private void add_non_empty_words_to_array(List<String> array,String[] words) {
		for(String s:words){
			if (s.length()>0)
			{
				array.add(s);
			}
		}
	}



}
