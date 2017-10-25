package com.es.poc.main.service;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Eraine Otayde
 *
 */
@Service
public class IndexServiceImpl implements IndexService {

	@Autowired
	private Client client;
	
	public boolean isIndexExisting(String indexName) {
		return client.admin().indices().prepareExists(indexName).execute().actionGet().isExists();
	}

	public void createIndex(String indexName) {
		CreateIndexRequestBuilder request = client.admin().indices().prepareCreate(indexName);
		request.execute();
	}

}
