package com.es.poc.main.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.DocWriteRequest.OpType;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.poc.main.constants.ESConstants;
import com.es.poc.main.document.PetDocument;
import com.es.poc.main.model.PetSearchResult;
import com.es.poc.main.model.SearchParam;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Eraine Otayde
 *
 */
@Service
public class PetServiceImpl implements PetService {

	private static final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

	@Autowired
	private Client client;

	public IndexResponse indexPet(PetDocument document) {
		logger.info("indexing {}", document.getId());

		IndexRequestBuilder request = client.prepareIndex(document.getIndex(), document.getDocType(),
				document.getId().toString());
		request.setSource(document.toJson(), XContentType.JSON);
		request.setOpType(OpType.INDEX);

		IndexResponse response = request.execute().actionGet();
		logger.info("index response: {}", response);

		return response;
	}

	public UpdateResponse updatePet(PetDocument document) {
		UpdateRequestBuilder request = client.prepareUpdate(document.getIndex(), document.getDocType(),
				document.getId().toString());
		request.setDoc(document.toJson(), XContentType.JSON);
		
		UpdateResponse response = request.execute().actionGet();
		
		return response;
	}
	
	public DeleteResponse deletePet(Long id) {
		DeleteRequestBuilder request = client.prepareDelete(ESConstants.PET_INDEX, ESConstants.DOCUMENT, String.valueOf(id));
		
		DeleteResponse response = request.execute().actionGet();		
		
		return response;		
	}

	public List<PetSearchResult> findPets(SearchParam searchParam)
			throws JsonParseException, JsonMappingException, IOException {
		List<PetSearchResult> petSearchResult = new ArrayList<PetSearchResult>();
		BoolQueryBuilder builder = QueryBuilders.boolQuery();

		buildSearchQuery(builder, searchParam);

		SearchRequestBuilder request = client.prepareSearch(ESConstants.PET_INDEX).setTypes(ESConstants.DOCUMENT)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(builder);

		SearchResponse response = request.execute().actionGet();

		SearchHit[] hits = response.getHits().getHits();

		if (hits.length > 0) {
			ObjectMapper mapper = new ObjectMapper();
			String result;

			for (SearchHit hit : hits) {
				result = hit.getSourceAsString();
				PetSearchResult searchResult = mapper.readValue(result, PetSearchResult.class);
				petSearchResult.add(searchResult);
			}
		}
		
		return petSearchResult;
	}

	private void buildSearchQuery(BoolQueryBuilder boolQuery, SearchParam searchParam) {
		if (searchParam.getId() != null) {
			boolQuery.filter(QueryBuilders.termQuery("id", searchParam.getId()));
		}

		if (searchParam.getName() != null) {
			boolQuery.filter(QueryBuilders.termQuery("name", searchParam.getName()));
		}

		if (searchParam.getCategory() != null) {
			boolQuery.filter(QueryBuilders.termQuery("category", searchParam.getCategory()));
		}
	}

}
