package com.es.poc.main.document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Eraine Otayde
 *
 */
public class BaseDocument {

	private Long id;

	private transient String index;

	private transient String docType;

	public BaseDocument() {
		super();
	}

	public BaseDocument(String index, String docType) {
		super();
		this.index = index;
		this.docType = docType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String toJson() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
}
