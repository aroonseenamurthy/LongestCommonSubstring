package com.example.test;

import java.util.ArrayList;
import java.util.List;

import com.example.pojo.Word;
import com.example.pojo.WordsFactory;

public class PayloadGenerator {

	public static WordsFactory getWellFormedJsonPayload() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();

		Word word = new Word();
		word.setValue("comcast");
		collection.add(word);

		Word word1 = new Word();
		word1.setValue("comcastic");
		collection.add(word1);

		Word word2 = new Word();
		word2.setValue("broadcaster");
		collection.add(word2);

		Word word3 = new Word();
		word3.setValue("communicate");
		collection.add(word3);

		words.setWords(collection);
		return words;
	}
	
	public static WordsFactory getSimpleJsonPayloadWithOneLongestSubstring() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();

		Word word = new Word();
		word.setValue("comcast");
		collection.add(word);

		Word word1 = new Word();
		word1.setValue("comcastic");
		collection.add(word1);

		Word word2 = new Word();
		word2.setValue("broadcaster");
		collection.add(word2);

		words.setWords(collection);
		return words;
	}

	public static WordsFactory getEmptyWordsCollectionJsonPayload() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();
		words.setWords(collection);
		return words;
	}
	
	
	public static WordsFactory getEmptyWordWithinJsonPayload() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();

		Word word = new Word();
		word.setValue("comcast");
		collection.add(word);

		Word word1 = new Word();
		word1.setValue("");
		collection.add(word1);

		words.setWords(collection);
		return words;
	}
	
	public static WordsFactory getNullWordWithinJsonPayload() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();

		Word word = new Word();
		word.setValue("comcast");
		collection.add(word);

		Word word1 = new Word();
		word1.setValue(null);
		collection.add(word1);

		words.setWords(collection);
		return words;
	}
	
	public static WordsFactory insertDuplicatesWordWithinJsonPayload() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();

		Word word = new Word();
		word.setValue("comcast");
		collection.add(word);

		Word word1 = new Word();
		word1.setValue("comcast");
		collection.add(word1);

		Word word2 = new Word();
		word2.setValue("comcastic");
		collection.add(word1);

		
		words.setWords(collection);
		return words;
	}

	public static Object getComplexJsonPayloadWithOneLongestSubstring() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();

		Word word = new Word();
		word.setValue("trampoline");
		collection.add(word);

		Word word1 = new Word();
		word1.setValue("cramped");
		collection.add(word1);

		Word word2 = new Word();
		word2.setValue("trampled");
		collection.add(word2);

		Word word3 = new Word();
		word3.setValue("rampaged");
		collection.add(word3);
		
		Word word4 = new Word();
		word4.setValue("skateboardramp");
		collection.add(word4);
		
		words.setWords(collection);
		return words;
	}

	public static Object getComplexJsonPayloadWithNoLongestSubstring() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();

		Word word = new Word();
		word.setValue("trampoline");
		collection.add(word);

		Word word1 = new Word();
		word1.setValue("cramped");
		collection.add(word1);

		Word word2 = new Word();
		word2.setValue("trampled");
		collection.add(word2);

		Word word3 = new Word();
		word3.setValue("rampaged");
		collection.add(word3);
		
		Word word4 = new Word();
		word4.setValue("skateboard");
		collection.add(word4);
		
		Word word5 = new Word();
		word5.setValue("wxyz");
		collection.add(word5);
		
		
		words.setWords(collection);
		return words;
	}

	public static Object getComplexJsonPayloadWithTwoLongestSubstring() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();

		Word word = new Word();
		word.setValue("compxlast");
		collection.add(word);

		Word word1 = new Word();
		word1.setValue("lastercomp");
		collection.add(word1);

		Word word2 = new Word();
		word2.setValue("rolllastincomputer");
		collection.add(word2);
		
		
		words.setWords(collection);
		return words;	}

	public static Object getComplexJsonPayloadWithThreeLongestSubstring() {
		List<Word> collection = new ArrayList<Word>();
		WordsFactory words = new WordsFactory();

		Word word = new Word();
		word.setValue("costlyematicincity");
		collection.add(word);

		Word word1 = new Word();
		word1.setValue("incitymathematicforcostly");
		collection.add(word1);

		Word word2 = new Word();
		word2.setValue("cosematiciscostlyincity");
		collection.add(word2);
		
		
		words.setWords(collection);
		return words;	}

}
