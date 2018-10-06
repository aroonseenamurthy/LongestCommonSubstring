package com.example.rest.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;

import com.example.business.LongestCommonSubstring;
import com.example.misc.ErrorCode;
import com.example.misc.ErrorMessage;
import com.example.pojo.ErrorResponse;
import com.example.pojo.LcsFactory;
import com.example.pojo.Word;
import com.example.pojo.WordsFactory;

@Path("/")
public class LCSResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response processLCS(WordsFactory words) {
		Object responseAfterValidation = getResponseAfterValidation(words);
		if (responseAfterValidation != null) {
			return Response.status(Status.BAD_REQUEST).entity(responseAfterValidation).build();
		} else {
			return Response.status(Status.OK).entity(runLCS(words)).build();
		}
	}

	private Object runLCS(WordsFactory words) {
		List<String> stringListOfWords = convertWordsListToStringList(words.getWords());
		List<String> processedList = new LongestCommonSubstring().process(stringListOfWords);
		Collections.sort(processedList);//alphabetical order
		LcsFactory lcsFactory = recreateLCSFactoryWithProcessedList(processedList); 
		return lcsFactory;
	}

	private LcsFactory recreateLCSFactoryWithProcessedList(
			List<String> processedList) {
		LcsFactory lcsFactory = new LcsFactory();
		List<Word> wordList = new ArrayList<Word>();
		for ( String processedString : processedList) {
			Word word  = new Word();
			word.setValue(processedString);
			wordList.add(word);
		}
		lcsFactory.setLcs(wordList);
		return lcsFactory;
	}

	private List<String> convertWordsListToStringList(List<Word> wordList) {
		List <String> wordListInString = new ArrayList<String>();
		for (Word word : wordList) {
			wordListInString.add(word.getValue());
		}
		return wordListInString;
	}

	private Object getResponseAfterValidation(WordsFactory words) {
		ValidationType validWordsPayload = isValidWordsPayload(words);
		if (ValidationType.FAIL_ON_EMPTY_WORDS_COLLECTION == validWordsPayload ) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorCode(ErrorCode.JSON_WORDS_PAYLOAD_EMPTY_COLLECTION);
			errorResponse.setErrorMessage(ErrorMessage.JSON_WORDS_PAYLOAD_COLLECTION_EMPTY);
			return errorResponse;
		} else if ( ValidationType.FAIL_ON_EMPTY_OR_NULL_WORD_WITHIN_COLLECTION == validWordsPayload) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorCode(ErrorCode.JSON_WORDS_PAYLOAD_WITHIN_COLLECTION_EMPTY_OR_NULL);
			errorResponse.setErrorMessage(ErrorMessage.JSON_WORDS_PAYLOAD_WITHIN_COLLECTION_EMPTY_OR_NULL);
			return errorResponse;
		} else if ( ValidationType.COLLECTION_IS_NOT_A_SET == validWordsPayload){
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setErrorCode(ErrorCode.JSON_WORDS_PAYLOAD_IS_NOT_A_SET);
			errorResponse.setErrorMessage(ErrorMessage.JSON_WORDS_PAYLOAD_IS_NOT_A_SET);
			return errorResponse;
		} 
		return null;
	}

	private ValidationType isValidWordsPayload(WordsFactory words) {
		if (CollectionUtils.isEmpty(words.getWords())) {
			return ValidationType.FAIL_ON_EMPTY_WORDS_COLLECTION;
		} else if ( isOneOfTheWordsNullOrEmpty(words)){
			return ValidationType.FAIL_ON_EMPTY_OR_NULL_WORD_WITHIN_COLLECTION;
		} else if ( isTheCollectionsNotASet(words.getWords())) {
			return ValidationType.COLLECTION_IS_NOT_A_SET;
		}
		return ValidationType.PASS;
	}

	private boolean isTheCollectionsNotASet(List<Word> words) {
		Set <String> wordColl = new HashSet<String>();
		for (Word word : words) {
			String value = word.getValue();
			wordColl.add(value);
		}
		if ( wordColl.size() != words.size()) {
			return true;
		}
		return false;
	}

	private boolean isOneOfTheWordsNullOrEmpty(WordsFactory words) {
		List<Word> wordsColl = words.getWords();
		for (Word word : wordsColl) {
			if (word.getValue() == null || word.getValue().equals("")) {
				return true;
			}
		}
		return false;
	}
}
