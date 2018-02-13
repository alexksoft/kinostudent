package com.ks.db.hibernate;
// Generated 05-Oct-2017 17:05:02 by Hibernate Tools 5.2.5.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ks.enums.CategoryType;

/**
 * Category generated by hbm2java
 */
@Entity
@Table(name = "category", catalog = "pr")
public class Category implements java.io.Serializable {

	private Integer id;
	private Category category;
	private String name;
	private CategoryType catType;
	private Boolean isDeleted;
	private Set<Project> projects = new HashSet<Project>(0);
	private Set<Event> events = new HashSet<Event>(0);
	private Set<Category> categories = new HashSet<Category>(0);

	public Category() {
	}

	public Category(String name, CategoryType catType) {
		this.name = name;
		this.catType = catType;
	}

	public Category(Category category, String name, CategoryType catType, Boolean isDeleted, Set<Project> projects,
			Set<Event> events, Set<Category> categories) {
		this.category = category;
		this.name = name;
		this.catType = catType;
		this.isDeleted = isDeleted;
		this.projects = projects;
		this.events = events;
		this.categories = categories;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "name", nullable = false, length = 65535)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "cat_type", nullable = false, length = 100)
	@Enumerated(EnumType.STRING)
	public CategoryType getCatType() {
		return this.catType;
	}

	public void setCatType(CategoryType catType) {
		this.catType = catType;
	}

	@Column(name = "is_deleted")
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Event> getEvents() {
		return this.events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

}
