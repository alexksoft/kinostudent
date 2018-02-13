package com.ks.ui.login;

import java.util.Locale;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

import com.ks.ui.AbstractViewModel;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Login extends AbstractViewModel implements EventListener<Event> {

	@Wire("#username")
	private Textbox username;

	@Wire("#password")
	private Textbox password;

	@Wire
	private Button loginBtn;

	@Wire
	private Grid loginGrid;
	
	@Init
	public void init() {
        Session session = Sessions.getCurrent();
        Locale prefer_locale = Locale.getDefault();
		// if default system language isn't supported, choose some locale from available
		if (prefer_locale.getLanguage().equals(new Locale("de").getLanguage()))
			prefer_locale = Locale.GERMAN;
		else
			prefer_locale = Locale.ENGLISH;
		session.setAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE, prefer_locale);
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command("loginCheck")
	public void loginCheck() throws WrongValueException {
			Sessions.getCurrent().setAttribute("authenticated", "already_authenticated");
			Executions.sendRedirect("main.zul");
	}
	
	@Command
    public void onLangChanged (@BindingParam("lang") String lang){
		Session session = Sessions.getCurrent();
		Locale preferLocale = null;
		if (lang.equals("English")) {
			preferLocale = Locale.ENGLISH;
		} else if (lang.equals("Russian")) {
			preferLocale = new Locale("ru");
		} else {
			preferLocale = Locale.GERMAN;
		}
		session.setAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE, preferLocale);
		BindUtils.postNotifyChange(null, null, this, "getILabel");
	}

	@Override
	public void onEvent(Event event) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
