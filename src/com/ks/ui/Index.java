package com.ks.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Center;
import org.zkoss.zul.Include;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Window;

import com.ks.service.AdminService;
import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class Index extends AbstractViewModel implements EventListener<Event> {

	@WireVariable
	AdminService adminServiceImpl;

	@Wire
	private Menuitem en, de;

	@Wire
	private Include content;

	@Wire
	private Center center;

	@Wire
	private Window outerIndexWindow;

	private boolean windowVisible;
	static JsonMapper jsonMapper = new DefaultJsonMapper();

	@Init
	public void init() {
		String auth = ((String) Sessions.getCurrent().getAttribute("authenticated"));
		if (auth != null && auth.equals("already_authenticated")) {
			BindUtils.postGlobalCommand(null, null, "makeVisible", null);
		} else {
			Executions.sendRedirect("/index.zul");
		}
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Selectors.wireComponents(view, this, false);

	}
	
	@Command
	public void showWindow(@BindingParam("path") String path) {
		Map<?, ?> parameters = new HashMap<>();
		openWindow(path, parameters, center);
	}

	@Override
	public void onEvent(Event event) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
