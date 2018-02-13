package com.ks.ui.admin;

import java.util.HashMap;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.ks.db.hibernate.User;
import com.ks.ui.AbstractViewModel;

@AfterCompose(superclass = true)
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserListVM extends AbstractViewModel{
	
	private ListModelList<User> users;
	protected String addEditZulPath, labelPart;
	
	@Init
	public void init() {
		addEditZulPath = "/WEB-INF/pages/add_edit_user.zul";
		refresh();
	}

	@GlobalCommand
	@NotifyChange("users")
	public void refresh() {
		users = new ListModelList<>(adminServiceImpl.get(User.class));
		BindUtils.postNotifyChange(null, null, this, "users");
	}
	
	@Command
	public void add() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Function", "Add");
		Executions.createComponents(addEditZulPath, null, map);
	}

	@Command
	public void edit(@BindingParam("object") final User object) {
		final HashMap<String, Object> map = new HashMap<>();
		map.put("object", object);
		Executions.createComponents(addEditZulPath, null, map);
	}

	@Command	
	public void delete(@BindingParam("object") final User object) {
		Messagebox.show("Ви дійсно хочете видалити запис","Видалення запису", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event evt) throws InterruptedException {
						evt.stopPropagation();
						if (Messagebox.ON_YES.equals(evt.getName())) {
							confirmDelete(object);
						}
					}

				});
	}

	void confirmDelete(User object) {
		try {
			adminServiceImpl.delete(object);
		} catch (ConstraintViolationException | DataIntegrityViolationException cve) {
			Clients.showNotification("Помилка видалення даних", "error",
					null, "middle_center", 8000, true);
		}
		refresh();
	}

	public ListModelList<User> getUsers() {
		return users;
	}

	public void setUsers(ListModelList<User> users) {
		this.users = users;
	}

}
