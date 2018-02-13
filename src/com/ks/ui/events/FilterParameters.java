package com.ks.ui.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.ks.db.hibernate.Category;
import com.ks.enums.Language;
import com.ks.enums.PaymentLevel;

public class FilterParameters {

	String textString;
	
	ArrayList<Category> selectedCategories = new ArrayList<Category>();
	
	ArrayList<String> selectedPhrases = new ArrayList<String>();
	
	ArrayList<PaymentLevel> selectedPaymentLevels = new ArrayList<PaymentLevel>();
	
	ArrayList<Language> selectedLanguage = new ArrayList<Language>();

	String from;
	String to;
	
	public FilterParameters() {
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void addCategory(Category category){
		selectedCategories.add(category);
	}
	
	public void removeCategory(Category category){
		for (Iterator iterator = selectedCategories.iterator(); iterator.hasNext();) {
			Category existingCategory = (Category) iterator.next();
			if(existingCategory.getId() == category.getId()){
//				selectedCategories.remove(existingCategory);
				iterator.remove();
			}
		}
	}

	public void addPhrase(String phrase){
		selectedPhrases.add(phrase);
	}
	
	public void removePhrase(String phrase){
		for (Iterator iterator = selectedPhrases.iterator(); iterator.hasNext();) {
			String existingPhrase = (String) iterator.next();
			if(existingPhrase.equals(phrase)){
//				selectedPhrases.remove(existingPhrase);
				iterator.remove();
			}
		}
	}

	public void addPaymantLevel(PaymentLevel paymentLevel){
		selectedPaymentLevels.add(paymentLevel);
	}
	
	public void removePaymentLevel(PaymentLevel paymentLevel){
		for (Iterator iterator = selectedPaymentLevels.iterator(); iterator.hasNext();) {
			PaymentLevel existingPaymentLevel = (PaymentLevel) iterator.next();
			if(existingPaymentLevel.equals(paymentLevel)){
				iterator.remove();
			}
		}
	}

	public void addLanguage(Language language){
		selectedLanguage.add(language);
	}
	
	public void removeLanguage(Language language){
		for (Iterator iterator = selectedLanguage.iterator(); iterator.hasNext();) {
			Language existingLanguage = (Language) iterator.next();
			if(existingLanguage.equals(language)){
				iterator.remove();
			}
		}
	}


	public ArrayList<Category> getSelectedCategories() {
		return selectedCategories;
	}

	public ArrayList<String> getSelectedPhrases() {
		return selectedPhrases;
	}

	public ArrayList<PaymentLevel> getSelectedPaymentLevels() {
		return selectedPaymentLevels;
	}

	public ArrayList<Language> getSelectedLanguage() {
		return selectedLanguage;
	}

	public String getTextString() {
		return textString;
	}

	public void setTextString(String textString) {
		this.textString = textString;
	}

}
