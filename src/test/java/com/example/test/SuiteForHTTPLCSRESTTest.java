package com.example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.example.misc.ErrorCode;
import com.example.misc.ErrorMessage;
import com.example.pojo.ErrorResponse;
import com.example.pojo.LcsFactory;
import com.example.pojo.Word;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class SuiteForHTTPLCSRESTTest {
	
    private WebResource webResource = null;
	
    @Before
	public void setup(){
       this.webResource = getWebResource("http://localhost:8080/lcs");
	}

	private WebResource getWebResource(String path) {
		ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        return client.resource(path);
	}
    
    @After
    public void tearDown(){
    	this.webResource = null;
    }
     
	@Test
	public void testWhenRequestPayloadsMimeTypeCannotBeHandled() { //Negative input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/xml").post(ClientResponse.class, null);
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),response.getStatus());
        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertEquals(ErrorCode.MALFORMED_REQUEST,errorResponse.getErrorCode());
        assertEquals(ErrorMessage.MALFORMED_REQUEST,errorResponse.getErrorMessage());
	}
	@Test
	public void testWhenRequestPayloadIsNull() { //Negative input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, null);
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),response.getStatus());
        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertEquals(ErrorCode.JSON_INPUT_NULL_ERROR,errorResponse.getErrorCode());
        assertEquals(ErrorMessage.JSON_INPUT_NULL_FAILURE,errorResponse.getErrorMessage());
	}

	@Test
	public void testWhenRequestPayloadIsStringABC() { //Negative input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, "ABC");
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),response.getStatus());
        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertEquals(ErrorCode.JSON_PARSE_FAILURE,errorResponse.getErrorCode());
        assertEquals(ErrorMessage.JSON_PARSE_FAILURE,errorResponse.getErrorMessage());
	}
	
	@Test
	public void testWhenRequestPayloadIsDummy() { //Negative input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, new Dummy());
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(),response.getStatus());
        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertEquals(ErrorCode.JSON_PARSE_FAILURE,errorResponse.getErrorCode());
        assertEquals(ErrorMessage.JSON_PARSE_FAILURE,errorResponse.getErrorMessage());
	}
	
	@Test
	public void testWhenRequestPayloadHasEmptyWordsCollection() { //Negative input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.getEmptyWordsCollectionJsonPayload());
		assertEquals(Status.BAD_REQUEST.getStatusCode(),response.getStatus());
        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertEquals(ErrorCode.JSON_WORDS_PAYLOAD_EMPTY_COLLECTION,errorResponse.getErrorCode());
        assertEquals(ErrorMessage.JSON_WORDS_PAYLOAD_COLLECTION_EMPTY,errorResponse.getErrorMessage());

 	}
	
	@Test
	public void testWhenRequestPayloadHasEmptyWordWithinCollection() { //Negative input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.getEmptyWordWithinJsonPayload());
		assertEquals(Status.BAD_REQUEST.getStatusCode(),response.getStatus());
        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertEquals(ErrorCode.JSON_WORDS_PAYLOAD_WITHIN_COLLECTION_EMPTY_OR_NULL,errorResponse.getErrorCode());
        assertEquals(ErrorMessage.JSON_WORDS_PAYLOAD_WITHIN_COLLECTION_EMPTY_OR_NULL,errorResponse.getErrorMessage());

 	}
	
	@Test
	public void testWhenRequestPayloadHasNullWordWithinCollection() { //Negative input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.getNullWordWithinJsonPayload());
		assertEquals(Status.BAD_REQUEST.getStatusCode(),response.getStatus());
        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertEquals(ErrorCode.JSON_WORDS_PAYLOAD_WITHIN_COLLECTION_EMPTY_OR_NULL,errorResponse.getErrorCode());
        assertEquals(ErrorMessage.JSON_WORDS_PAYLOAD_WITHIN_COLLECTION_EMPTY_OR_NULL,errorResponse.getErrorMessage());

 	}
	
	@Test
	public void testWhenRequestPayloadHasDuplicateEntries() { //Negative input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.insertDuplicatesWordWithinJsonPayload());
		assertEquals(Status.BAD_REQUEST.getStatusCode(),response.getStatus());
        ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
        assertEquals(ErrorCode.JSON_WORDS_PAYLOAD_IS_NOT_A_SET,errorResponse.getErrorCode());
        assertEquals(ErrorMessage.JSON_WORDS_PAYLOAD_IS_NOT_A_SET,errorResponse.getErrorMessage());

 	}
	
	@Test
	public void tesSimpleLCSRequestWithOneLongestSubstring() { //Postive input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.getSimpleJsonPayloadWithOneLongestSubstring());
		assertEquals(Status.OK.getStatusCode(),response.getStatus());
		LcsFactory lcsFactory = response.getEntity(LcsFactory.class);
		List<Word> words = lcsFactory.getLcs();
		assertNotNull(words);
		assertTrue(words.size() > 0 );
		assertEquals("cast",words.get(0).getValue());

	}
	
	@Test
	public void tesComplexLCSRequestWithOneLongestSubstring() { //Postive input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.getComplexJsonPayloadWithOneLongestSubstring());
		assertEquals(Status.OK.getStatusCode(),response.getStatus());
		LcsFactory lcsFactory = response.getEntity(LcsFactory.class);
		List<Word> words = lcsFactory.getLcs();
		assertNotNull(words);
		assertTrue(words.size() > 0 );
		assertEquals("ramp",words.get(0).getValue());

	}
	
	@Test
	public void tesComplexLCSRequestWithTwoLongestSubstringInAlphabeticalOrder() { //Postive input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.getComplexJsonPayloadWithTwoLongestSubstring());
		assertEquals(Status.OK.getStatusCode(),response.getStatus());
		LcsFactory lcsFactory = response.getEntity(LcsFactory.class);
		List<Word> words = lcsFactory.getLcs();
		assertNotNull(words);
		assertEquals(words.size(),2 );
		assertEquals("comp",words.get(0).getValue());
		assertEquals("last",words.get(1).getValue());
	}

	@Test
	public void tesComplexLCSRequestWithThreeLongestSubstringInAlphabeticalOrder() { //Postive input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.getComplexJsonPayloadWithThreeLongestSubstring());
		assertEquals(Status.OK.getStatusCode(),response.getStatus());
		LcsFactory lcsFactory = response.getEntity(LcsFactory.class);
		List<Word> words = lcsFactory.getLcs();
		assertNotNull(words);
		assertEquals(words.size(),3 );
		assertEquals("costly",words.get(0).getValue());
		assertEquals("ematic",words.get(1).getValue());
		assertEquals("incity",words.get(2).getValue());	
	}
	
	
	@Test
	public void tesComplexLCSRequestWithNoLongestSubstring() { //Postive input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.getComplexJsonPayloadWithNoLongestSubstring());
		assertEquals(Status.OK.getStatusCode(),response.getStatus());
		LcsFactory lcsFactory = response.getEntity(LcsFactory.class);
		List<Word> words = lcsFactory.getLcs();
		assertNotNull(words);
		assertTrue(words.size() == 0);

	}
	
	@Test
	public void testWhenRequestPayloadIsWellFormed() { //Postive input scenario 
		ClientResponse response = webResource.accept("application/json")
				.type("application/json").post(ClientResponse.class, PayloadGenerator.getWellFormedJsonPayload());
		assertEquals(Status.OK.getStatusCode(),response.getStatus());
	}

}
