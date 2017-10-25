package com.es.poc.main.document;

/**
 * @author Eraine Otayde
 *
 */
public class PetDocument extends BaseDocument {

	private String name;
	private String category;
	private Long age;

	public PetDocument() {}
	
	public PetDocument(String index, String docType) {
		super(index, docType);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

}
