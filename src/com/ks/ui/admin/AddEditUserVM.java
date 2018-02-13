package com.ks.ui.admin;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Window;

import com.ks.db.hibernate.User;
import com.ks.ui.AbstractViewModel;
import com.ks.util.UIUtilities;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddEditUserVM extends AbstractViewModel {

	@Wire
	protected Window addEditWindow;

	@Wire
	protected Caption caption;

	private User user;

	@Init
	public void init() {
		if (Executions.getCurrent().getArg().containsKey("object")) {
			user = (User) Executions.getCurrent().getArg().get("object");
		} else {
			user = new User();
		}
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Selectors.wireComponents(view, this, false);
		if (user.getId() == null) {
			caption.setLabel("Add User");
		} else {
			caption.setLabel("Edit User");
		}
	}
	
	public void saveAfterCheck() {
		adminServiceImpl.save(user);
		BindUtils.postGlobalCommand(null, null, "refresh", null);
	}

	@Command
	public void save() {
		if (UIUtilities.check(addEditWindow)) {
			user.setSha2Password(user.getPassword());
			saveAfterCheck();
			BindUtils.postGlobalCommand(null, null, "refresh", null);
			addEditWindow.detach();
		} else {
			Clients.showNotification("Form has error. Please correct values and then try to save again.", "error",
					null, "", 8000, true);
		}
	}

	@Command
	public void cancel() {
		BindUtils.postGlobalCommand(null, null, "refresh", null);
		addEditWindow.detach();
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User place) {
		this.user = place;
	}
}
