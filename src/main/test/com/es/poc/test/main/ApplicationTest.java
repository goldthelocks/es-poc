package com.es.poc.test.main;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.update.UpdateResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.es.poc.main.constants.ESConstants;
import com.es.poc.main.document.PetDocument;
import com.es.poc.main.model.PetSearchResult;
import com.es.poc.main.model.SearchParam;
import com.es.poc.main.service.IndexService;
import com.es.poc.main.service.PetService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Eraine Otayde
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-context.xml")
public class ApplicationTest {

	@Autowired
	private IndexService indexService;		

	@Autowired
	private PetService petService;

	public void mustUpdate() {
		PetDocument doc3 = new PetDocument(ESConstants.PET_INDEX, ESConstants.DOCUMENT);
		doc3.setId((long)3);
		doc3.setName("popoy");
		doc3.setAge((long)3);
		doc3.setCategory("dog");
		
		UpdateResponse response = petService.updatePet(doc3);
		System.out.println(response.getResult());
	}
	
	@Test
	public void mustSearchByCategory() throws JsonParseException, JsonMappingException, IOException {
		SearchParam param = new SearchParam();
		param.setCategory("dog");
		
		List<PetSearchResult> results = petService.findPets(param);
		
		for (PetSearchResult result : results) {
			System.out.println(result.toString());
		}
	}
	
	public void mustSearchById() throws JsonParseException, JsonMappingException, IOException {
		SearchParam param = new SearchParam();
		param.setId((long) 1);
		
		List<PetSearchResult> results = petService.findPets(param);
		
		for (PetSearchResult result : results) {
			System.out.println(result.toString());
		}
	}
	
	public void mustIndex() {
		PetDocument doc1 = new PetDocument(ESConstants.PET_INDEX, ESConstants.DOCUMENT);
		doc1.setId((long)1);
		doc1.setName("akai");
		doc1.setAge((long)1);
		doc1.setCategory("cat");
		
		PetDocument doc2 = new PetDocument(ESConstants.PET_INDEX, ESConstants.DOCUMENT);
		doc2.setId((long)2);
		doc2.setName("basha");
		doc2.setAge((long)1);
		doc2.setCategory("dog");
		
		PetDocument doc3 = new PetDocument(ESConstants.PET_INDEX, ESConstants.DOCUMENT);
		doc3.setId((long)3);
		doc3.setName("popoy");
		doc3.setAge((long)2);
		doc3.setCategory("dog");
		
		petService.indexPet(doc2);
		petService.indexPet(doc3);		
	}
	
	public void mustHaveNoIndex() {
		Assert.assertFalse(indexService.isIndexExisting("meow"));
	}

	public void mustHaveIndex() {
		Assert.assertTrue(indexService.isIndexExisting("pets"));
	}
	
}
