package com.ks.service;

import java.util.List;

import com.ks.db.hibernate.Category;
import com.ks.db.hibernate.Event;
import com.ks.db.hibernate.FacebookPlace;
import com.ks.db.hibernate.Project;
import com.ks.db.hibernate.SiteUrl;
import com.ks.ui.events.FilterParameters;

public interface AdminService extends BaseService{

	public Object getItemById(Class clazz, Integer id);
	public Category getCategoryByName(String name);
	public boolean doesEventExist(String link);
	public Event getEventByLink(String link) ;
	
	public List<SiteUrl> getSiteUrlList(Boolean active);
	
	public SiteUrl getSiteUrlByUrl(String url);
	
	public List<Event> getEventList(Boolean active, String orderBy);
	
	public List<Category> getCategories(Boolean active);
	
	public List<Event> getEventListByFilter(Boolean active, FilterParameters parameters);
	
	public FacebookPlace getPlaceByFacebookPlaceID(String placeId, String facebookURL);
	
	public List<FacebookPlace> getFacebookPlaceListByFilter(Boolean active, Boolean manuallyAdded);
	
	//Projects
	public List<Project> getProjectList(Boolean active, String orderBy);
}
