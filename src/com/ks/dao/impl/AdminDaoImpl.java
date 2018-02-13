package com.ks.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.ks.dao.AdminDao;
import com.ks.db.hibernate.Category;
import com.ks.db.hibernate.Event;
import com.ks.db.hibernate.FacebookPlace;
import com.ks.db.hibernate.Project;
import com.ks.db.hibernate.SiteUrl;
import com.ks.ui.events.FilterParameters;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.Map.Entry;

@Component
public class AdminDaoImpl extends BaseDaoImpl implements AdminDao {
	private static final Logger logger = LogManager.getLogger();
	
	private <T> TypedQuery<T> createSimpleQuery(Class<T> clazz, HashMap<String, Object> args) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		List<Predicate> predicates = new ArrayList<>();
		if(args != null && args.entrySet() != null) {
			for(Entry<String, Object> e : args.entrySet()) {
				String[] properties = e.getKey().split("[.]");
				if (properties.length > 1) {
					predicates.add(builder.equal(root.get(properties[0]).get(properties[1]), e.getValue()));
				} else {
					predicates.add(builder.equal(root.get(e.getKey()), e.getValue()));
				}
			}
			query.select(root).where(predicates.toArray(new Predicate[]{}));
		}
		query.distinct(true);
		return session.createQuery(query);
	}
	
	public <T> Object getItemById(Class<T> clazz, Integer id) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		query.select(root).where(builder.equal(root.get("id"), id));
		try{
			return session.createQuery(query).getSingleResult();
		} catch(Exception e){
			return null;
		}
	}

	public Category getCategoryByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Category> query = builder.createQuery(Category.class);
		Root<Category> root = query.from(Category.class);
		query.select(root).where(builder.equal(root.get("name"), name));
		try {
			return session.createQuery(query).getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	@Override
	public boolean doesEventExist(String link) {
		if(link.equals("http://dede.com.ua/kyiv/Art-vecherinka-Sladkii-Kapkeik/")) {
			int i=0;
			i++;
		}
		Event event = getEventByLink(link);
		if(event == null) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public Event getEventByLink(String link) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Event> query = builder.createQuery(Event.class);
		Root<Event> root = query.from(Event.class);
		query.select(root).where(builder.equal(root.get("link"), link));
		Event event = null;
		try {
			event = session.createQuery(query).getSingleResult();
		} catch(NoResultException ex) {
			event = null;
		}catch(org.hibernate.NonUniqueResultException e) {
			event = null;
		}catch(org.springframework.dao.IncorrectResultSizeDataAccessException s) {
			event = null;
		}
		return event;
	}
	
	public List<SiteUrl> getSiteUrlList(Boolean active){
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<SiteUrl> query = builder.createQuery(SiteUrl.class);
		Root<SiteUrl> root = query.from(SiteUrl.class);
		if(active != null ) {
			if(active) {
				query.select(root).where(builder.equal(root.get("isDeleted"), false));
			}else {
				query.select(root).where(builder.equal(root.get("isDeleted"), true));
			}
		}
		return session.createQuery(query).getResultList();		
	}
	
	public SiteUrl getSiteUrlByUrl(String url) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<SiteUrl> query = builder.createQuery(SiteUrl.class);
		Root<SiteUrl> root = query.from(SiteUrl.class);
		query.select(root).where(builder.equal(root.get("url"), url));
		SiteUrl siteUrl = null;
		try {
			siteUrl = session.createQuery(query).getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}catch(org.hibernate.NonUniqueResultException e) {
			return null;
		}catch(org.springframework.dao.IncorrectResultSizeDataAccessException s) {
			return null;
		}
		return siteUrl;
	}
	
	public List<Event> getEventList(Boolean active, String orderBy){
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Event> query = builder.createQuery(Event.class);
		Root<Event> root = query.from(Event.class);
		if(active != null ) {
			if(active) {
				query.select(root).where(builder.equal(root.get("isDeleted"), false));
			}else {
				query.select(root).where(builder.equal(root.get("isDeleted"), true));
			}
		}
		if(orderBy != null && orderBy.length() > 0) {
			query.orderBy(builder.asc(root. get(orderBy)));
		}else {
			query.orderBy(builder.desc(root. get("rank")));
		}
		return session.createQuery(query).getResultList();		
	}
	
	public List<Category> getCategories(Boolean active){
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Category> query = builder.createQuery(Category.class);
		Root<Category> root = query.from(Category.class);
		if(active != null ) {
			if(active) {
				query.select(root).where(builder.equal(root.get("isDeleted"), false));
			}else {
				query.select(root).where(builder.equal(root.get("isDeleted"), true));
			}
		}
		return session.createQuery(query).getResultList();		
	}
	
	public List<Event> getEventListByFilter(Boolean active, FilterParameters parameters){
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Event> query = builder.createQuery(Event.class);
		Root<Event> root = query.from(Event.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
//		if(active != null ) {
//			if(active) {
//				predicates.add(builder.equal(root.get("isDeleted"), false));
////				query.select(root).where(builder.equal(root.get("isDeleted"), false));
//			}else {
//				predicates.add(builder.equal(root.get("isDeleted"), true));
////				query.select(root).where(builder.equal(root.get("isDeleted"), true));
//			}
//		}
		
		predicates.add(builder.like(root.get("shortText"), "%" + parameters.getTextString() + " " + "%"));
		predicates.add(builder.like(root.get("title"), "%" + parameters.getTextString() + " " + "%"));
		
		Predicate[] predicatesArray = new Predicate[predicates.size()];
		Predicate disjunction = builder.or(predicates.toArray(predicatesArray));
		if(active != null ) {
			if(active) {
//				predicates.add(builder.equal(root.get("isDeleted"), false));
//				query.select(root).where(builder.equal(root.get("isDeleted"), false));
				
				query.where(disjunction, builder.equal(root.get("isDeleted"), false));
			}else {
//				predicates.add(builder.equal(root.get("isDeleted"), true));
//				query.select(root).where(builder.equal(root.get("isDeleted"), true));
				
				query.where(disjunction, builder.equal(root.get("isDeleted"), true));
			}
		}else {
			query.where(disjunction);
		}
		
		return session.createQuery(query).getResultList();	
	}
	
	public FacebookPlace getPlaceByFacebookPlaceID(String placeId, String facebookURL) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<FacebookPlace> query = builder.createQuery(FacebookPlace.class);
		Root<FacebookPlace> root = query.from(FacebookPlace.class);
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(builder.equal(root.get("facebookId"), placeId));
		if(facebookURL != null && facebookURL.endsWith("/")) {
			facebookURL = facebookURL.substring(0, facebookURL.length() - 1);
			predicates.add(builder.equal(root.get("facebookId"), facebookURL));
		}
		Predicate[] predicatesArray = new Predicate[predicates.size()];
		Predicate conjunction = builder.or(predicates.toArray(predicatesArray));
		query.select(root).where(conjunction);
		
//		query.select(root).where(builder.equal(root.get("facebookId"), placeId));
		List<FacebookPlace> fplaces = null;
		try {
			fplaces = session.createQuery(query).list();
		} catch(NoResultException ex) {
			return null;
		}catch(org.hibernate.NonUniqueResultException e) {
			return null;
		}catch(org.springframework.dao.IncorrectResultSizeDataAccessException s) {
			return null;
		}
		//If both found then id by url has higher priority and it must be returned
		if(fplaces != null && fplaces.size()>0) {
			FacebookPlace retPlace = null;
			for(FacebookPlace place : fplaces) {
				if(place.getFacebookId().equals(facebookURL)) {
					retPlace = place;
				}
			}
			if(retPlace != null) {
				return retPlace;
			}else {
				return fplaces.get(0);
			}
		}else {
			return null;
		}
	}
	
	public List<FacebookPlace> getFacebookPlaceListByFilter(Boolean active, Boolean manuallyAdded){
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<FacebookPlace> query = builder.createQuery(FacebookPlace.class);
		Root<FacebookPlace> root = query.from(FacebookPlace.class);
		List<Predicate> predicates = new ArrayList<>();
		if(active != null) {
			predicates.add(builder.equal(root.get("isDeleted"), !active));
		}
		if(manuallyAdded != null) {
			predicates.add(builder.equal(root.get("manuallyAdded"), manuallyAdded));
		}
		

//		predicates.add(builder.like(root.get("shortText"), "%" + parameters.getTextString() + " " + "%"));
//		predicates.add(builder.like(root.get("title"), "%" + parameters.getTextString() + " " + "%"));
		
//		Predicate[] predicatesArray = new Predicate[predicates.size()];
//		Predicate disjunction = builder.or(predicates.toArray(predicatesArray));
//		if(active != null ) {
//			if(active) {
////				predicates.add(builder.equal(root.get("isDeleted"), false));
////				query.select(root).where(builder.equal(root.get("isDeleted"), false));
//				
//				query.where(disjunction, builder.equal(root.get("isDeleted"), false));
//			}else {
////				predicates.add(builder.equal(root.get("isDeleted"), true));
////				query.select(root).where(builder.equal(root.get("isDeleted"), true));
//				
//				query.where(disjunction, builder.equal(root.get("isDeleted"), true));
//			}
//		}else {
//			query.where(disjunction);
//		}

		Predicate[] predicatesArray = new Predicate[predicates.size()];
		Predicate conjunction = builder.and(predicates.toArray(predicatesArray));
		query.where(conjunction);
		
		return session.createQuery(query).getResultList();	
	}
	
	public List<Project> getProjectList(Boolean active, String orderBy){
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Project> query = builder.createQuery(Project.class);
		Root<Project> root = query.from(Project.class);
		if(active != null ) {
			if(active) {
				query.select(root).where(builder.equal(root.get("isDeleted"), false));
			}else {
				query.select(root).where(builder.equal(root.get("isDeleted"), true));
			}
		}
		if(orderBy != null && orderBy.length() > 0) {
			query.orderBy(builder.asc(root. get(orderBy)));
		}else {
			query.orderBy(builder.desc(root. get("rank")));
		}
		return session.createQuery(query).getResultList();		
	}
}
