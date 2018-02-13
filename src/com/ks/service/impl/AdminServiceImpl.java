package com.ks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ks.dao.AdminDao;
import com.ks.db.hibernate.Category;
import com.ks.db.hibernate.Event;
import com.ks.db.hibernate.FacebookPlace;
import com.ks.db.hibernate.Project;
import com.ks.db.hibernate.SiteUrl;
import com.ks.service.AdminService;
import com.ks.ui.events.FilterParameters;

@Service
public class AdminServiceImpl extends BaseServiceImpl implements AdminService  {
	
	@Autowired
	AdminDao adminDaoImpl;
	
	@Override
	@Transactional(readOnly = true)
	public Object getItemById(Class clazz, Integer id){
		return adminDaoImpl.getItemById(clazz, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Category getCategoryByName(String name) {
		return adminDaoImpl.getCategoryByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean doesEventExist(String link) {
		return adminDaoImpl.doesEventExist(link);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Event getEventByLink(String link) {
		return adminDaoImpl.getEventByLink(link);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<SiteUrl> getSiteUrlList(Boolean active){
		return adminDaoImpl.getSiteUrlList(active);
	}
	
	@Override
	@Transactional(readOnly = true)
	public SiteUrl getSiteUrlByUrl(String url) {
		return adminDaoImpl.getSiteUrlByUrl(url);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Event> getEventList(Boolean active, String orderBy){
		return adminDaoImpl.getEventList(active, orderBy);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Category> getCategories(Boolean active){
		return adminDaoImpl.getCategories(active);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Event> getEventListByFilter(Boolean active, FilterParameters parameters){
		return adminDaoImpl.getEventListByFilter(active, parameters);
	}
	
	@Override
	@Transactional(readOnly = true)
	public FacebookPlace getPlaceByFacebookPlaceID(String placeId, String facebookURL) {
		return adminDaoImpl.getPlaceByFacebookPlaceID(placeId, facebookURL);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<FacebookPlace> getFacebookPlaceListByFilter(Boolean active, Boolean manuallyAdded){
		return adminDaoImpl.getFacebookPlaceListByFilter(active, manuallyAdded);		
	}
	
	//Projects
	@Override
	@Transactional(readOnly = true)
	public List<Project> getProjectList(Boolean active, String orderBy){
		return adminDaoImpl.getProjectList(active, orderBy);
	}
}

