package com.es.poc.main.service;

/**
 * @author Eraine Otayde
 *
 */
public interface IndexService {

	boolean isIndexExisting(String indexName);
		
	void createIndex(String indexName);
}
