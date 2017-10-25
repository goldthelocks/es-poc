package com.es.poc.main.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;

import com.es.poc.main.document.PetDocument;
import com.es.poc.main.model.PetSearchResult;
import com.es.poc.main.model.SearchParam;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Eraine Otayde
 *
 */
public interface PetService {

	IndexResponse indexPet(PetDocument document);
	
	UpdateResponse updatePet(PetDocument document);
	
	DeleteResponse deletePet(Long id);

	List<PetSearchResult> findPets(SearchParam searchParam) throws JsonParseException, JsonMappingException, IOException;
	
}
