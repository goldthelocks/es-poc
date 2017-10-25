package com.es.poc.main.model;

/**
 * @author Eraine Otayde
 *
 */
public class PetSearchResult {

	private Long id;
	private String name;
	private String category;
	private Long age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "PetSearchResult [id=" + id + ", name=" + name + ", category=" + category + ", age=" + age + "]";
	}

}
