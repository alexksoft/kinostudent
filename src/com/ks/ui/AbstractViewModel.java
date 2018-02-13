package com.ks.ui;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.Locales;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.ext.Disable;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

import com.ks.service.AdminService;

import java.text.DecimalFormat;
import java.util.*;

public abstract class AbstractViewModel {

	@WireVariable
	protected AdminService adminServiceImpl;

	public String dateFormat;
	public String numberFormat;

	public String getILabel(String labelKey){
		Session session = Sessions.getCurrent();
		Locale locale = (Locale) session.getAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE);
		Locales.setThreadLocal(locale);
		return Labels.getLabel(labelKey);
	}
	
	public String getILabel(String labelKey, String param){
		Session session = Sessions.getCurrent();
		Locale locale = (Locale) session.getAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE);
		Locales.setThreadLocal(locale);
		return Labels.getLabel(labelKey,new Object[]{param});
	}

	public String getILabel(String labelKey, String param1, String param2){
		Session session = Sessions.getCurrent();
		Locale locale = (Locale) session.getAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE);
		Locales.setThreadLocal(locale);
		return Labels.getLabel(labelKey,new Object[]{param1, param2});
	}

	public boolean isGermanCurrent(){
		return isCurrentLocale(Locale.GERMAN);
	} 

	public boolean isEnglishCurrent(){
		return isCurrentLocale(Locale.ENGLISH);
	}

	public boolean isRussianCurrent(){
		return isCurrentLocale(new Locale("ru"));
	}

	public boolean isCurrentLocale(Locale checkedLocale){
		Session session = Sessions.getCurrent();
		Locale locale = (Locale) session.getAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE);
		if(locale == null){
			locale = Locale.ENGLISH;
		}
		return locale.equals(checkedLocale);
	}



	private boolean checkIfContractListPath(String path) {
		return path.contains("contract_list.zul")
				|| path.contains("uncompleted_list.zul")
				|| path.contains("expiring_list.zul")
				|| path.contains("deleted_list.zul");
	}

	public Component openWindowAfterCancel(String path, Map<?,?> parameters, Center center){
    	Component component = center.getFirstChild();
		if(component instanceof Window){
			Events.postEvent("onCancel", component, null);
		}
    	center.getChildren().clear();
		return Executions.createComponents(path, center, parameters);
    }
    
    public String getCurrentLocale(){
		Session session = Sessions.getCurrent();
		Locale locale = (Locale) session.getAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE);
		if(locale == null){
			locale = Locale.ENGLISH;
		}
		String lang = locale.getLanguage();
		switch (lang) {
			case "de":
				return "de_de";
			case "ru":
				return "ru_RU";
			default:
				return "en_US";
		}
    }
    public Locale getCurrentLocale2(){
		Session session = Sessions.getCurrent();
		Locale locale = (Locale) session.getAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE);
		return locale;
    }
    
	public String getDateFormat() {
		String currentLocale = getCurrentLocale();
		if(currentLocale.equals("en_US")){
			return "MM/dd/yyyy";
		}else{
			return "dd.MM.yyyy";
		}
	}

	public String getDateTimeFormat() {
		String currentLocale = getCurrentLocale();
		if(currentLocale.equals("en_US")){
			return "MM/dd/yyyy HH:mm";
		}else{
			return "dd.MM.yyyy HH:mm";
		}
	}

	public String getNumberFormat() {
		//Format must be the same for all locales!!!!
			return "#,##0.00";
	}
    
    public void unsetConstraint(InputElement comp){
    	comp.setConstraint("");
    	comp.clearErrorMessage(); 
    	comp.getText();
    }

    /**
     * Override this method in view-model classes to change java internationalization labels.   
     * (for labels added as pure-Java objects) 
     **/
	public void changeJavaILabels() {
		// TODO Auto-generated method stub
	}
	
	public String getPopupWidth(){
		return "190px";
	}
	
	protected Center getCenter(){
		return null;
	}
	
	public void cancel(){
		Map<?,?> parameters = new HashMap<>();
		openWindowAfterCancel("/WEB-INF/pages/contract_list.zul", parameters, getCenter());
	}
	
	@GlobalCommand
	public Window buttonWindow(Component c) {
		if(c != null && c.getParent() != null) {
			if(c.getParent() instanceof Window) {
				return (Window) c.getParent();
			}
			else {
				return buttonWindow(c.getParent());
			}	
		}
		else {
			return null;
		}
	}
	
	@GlobalCommand
	public void checkCasButtonStates(Component c) {
		for(Component cp : c.getChildren()) {
			if(cp instanceof Toolbarbutton) {
				Toolbarbutton tbtn = (Toolbarbutton) cp;
				if (tbtn.getImage() != null) {
					if(tbtn.getImage().equals("images/cas_no_link24.png")) {
                        tbtn.setImage("images/cas_link24.png");
                    }
                    else if(tbtn.getImage().equals("images/cas_no_select24.png")) {
                        tbtn.setImage("images/cas_select24.png");
                    }
                    else if(tbtn.getImage().equals("images/cas_no_edit24.png")) {
                        tbtn.setImage("images/cas_edit24.png");
                    }
				}
			}
			checkCasButtonStates(cp);
		}
	}
	

    public void disableRec(Component c){
        if (c != null) {
            for (Component cp : c.getChildren()) {
                if(cp instanceof Button) {
                    cp.setVisible(false);
                }
                if(cp instanceof Toolbarbutton) {
                    Toolbarbutton tbtn = (Toolbarbutton) cp;
					if (tbtn.getImage() != null) {
						if(tbtn.getImage().equals("images/cas_link24.png") || tbtn.getImage().equals("images/cas_no_link24.png")) {
                            // link button should always be visible
                            tbtn.setVisible(true);
                            continue;
                        }
                        else if(tbtn.getImage().equals("images/memoicon16.png")) {
                            // Note button should always be visible
                            tbtn.setVisible(true);
                            continue;
                        }
                        else {
                            tbtn.setVisible(false);
                        }
					}
				}
                if (cp instanceof InputElement) {
                    ((InputElement) cp).setReadonly(true);
                }
                if (cp instanceof Datebox){
                    ((Datebox) cp).setReadonly(false);
                    ((Datebox) cp).setSclass("casElementOpenContractBlur");
                    Clients.evalJavaScript("disableComboBandInputs()");
                    Clients.evalJavaScript("disableOtherInputs()");
                }

                if (cp instanceof Combobox){
                    ((Combobox) cp).setReadonly(false);
                    ((Combobox) cp).setSclass("casElementOpenContractBlur");
                    Clients.evalJavaScript("disableComboBandInputs()");
                    Clients.evalJavaScript("disableOtherInputs()");
                }

                if (cp instanceof Bandbox){
                    ((Bandbox) cp).setReadonly(false);
                    ((Bandbox) cp).setSclass("casElementOpenContractBlur");
                    Clients.evalJavaScript("disableComboBandInputs()");
                    Clients.evalJavaScript("disableOtherInputs()");
                }

                if (cp instanceof Doublespinner){
                    ((Doublespinner) cp).setReadonly(false);
                    ((Doublespinner) cp).setSclass("casElementOpenContractBlur");
                    Clients.evalJavaScript("disableComboBandInputs()");
                    Clients.evalJavaScript("disableOtherInputs()");
                }

                if (cp instanceof Spinner){
                    ((Spinner) cp).setReadonly(false);
                    ((Spinner) cp).setSclass("casElementOpenContractBlur");
                    Clients.evalJavaScript("disableComboBandInputs()");
                    Clients.evalJavaScript("disableOtherInputs()");
                }
                disableRec(cp);
            }
        }
    }
	
	public void enable(Component c) {
		if(c != null) {
			if(c instanceof Disable) {
				((Disable) c).setDisabled(false);
			}
			for (Component cp : c.getChildren()) {
				if(cp instanceof Button) {
					((Button) cp).setVisible(true);
				}
				if(cp instanceof Toolbarbutton) {
					((Toolbarbutton) cp).setVisible(true);
				}
				enable(cp);
			}
		}
	}
	
	@Command
	public void lostFocus(@BindingParam("comp") Component comp){
//		InputElement ie = (InputElement)comp;
	}
	
	public String getCountryByCode(String countryCode){
		 Locale obj = new Locale("", countryCode);
		 return obj.getDisplayCountry(getCurrentLocale2());
	}
	
	protected String getCountryByCodeInLang(String countryCode, String langCode){
		 Locale obj = new Locale("", countryCode);
		 Locale lang = new Locale("", langCode);
		 return obj.getDisplayCountry(lang);
	}

	
	public String fileTypeImage(String type) {
		switch (type) {
			case "doc":
				return "/images/doc.gif";
			case "docx":
				return "/images/docx.jpg";
			case "xls":
				return "/images/xls.jpg";
			case "xlsx":
				return "/images/xlsx.jpg";
			case "pdf":
				return "/images/pdf.jpg";
			case "png":
				return "/images/png.jpg";
			case "jpg":
			case "jpeg":
				return "/images/jpg.png";
			case "tiff":
				return "/images/tiff.png";
			case "gif":
				return "/images/gif.jpg";
			default:
				return "/images/undefined.png";
		}
		
	}
	
	//http://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
	public String readableFileSize(long size) {
	    if(size <= 0) return "0";
	    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
	public String readableFileType(String type) {
		switch (type) {
			case "doc":
			case "docx":
				return "MS Word Document";
			case "xls":
			case "xlsx":
				return "MS Excel";
			default:
				return type.toUpperCase();
		}
		
	}

	public String readableFileName(String fileName) {
		if(fileName.length() > 20){
			return fileName.substring(20);
		}else{
			return fileName;
		}
	}

	public String getConcatenated(String str1, String str2){
		return str1 + str2;
	}
		
	public String getLeasingComponentNumber(String stepName){
		if(stepName.length() >= 11){
			return stepName.substring(stepName.indexOf("Leasinggut")+11);
		}else{
			return "";
		}
	}

	private Column currentSortColumn;

	/**
	 * we can only get the column on OpenEvent event for custom menupopup
	 * @param event
	 */
	@Command
	public void openColumnMenu(@ContextParam(ContextType.TRIGGER_EVENT) OpenEvent event) {
		Component ref = event.getReference();
		if(ref != null){
			currentSortColumn = (Column)ref;
		}
	}
	
    public Component openWindow(String path, Map<?,?> parameters, Center center){
		//run onClose event of detached window to remove open threads, etc. before closing
		Component component = center.getFirstChild();
		if(component instanceof Window){
			Events.postEvent("onClose", component, null);
		}
		center.getChildren().clear();
		Component comp = Executions.createComponents(path, center, parameters);
		return comp;
    }
}
