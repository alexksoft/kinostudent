package com.ks.ui.admin;

import com.ks.db.hibernate.GrammaRules;
import com.ks.ui.AbstractViewModel;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import java.util.HashMap;

@AfterCompose(superclass = true)
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RulesListVM extends AbstractViewModel {

    private ListModelList<GrammaRules> rules;
    protected String addEditZulPath, labelPart;

    @Init
    public void init() {
        addEditZulPath = "/WEB-INF/pages/add_edit_rules.zul";
        refresh();
    }

    @GlobalCommand
    @NotifyChange("rules")
    public void refresh() {
        rules = new ListModelList<>(adminServiceImpl.get(GrammaRules.class));
        BindUtils.postNotifyChange(null, null, this, "rules");
    }

    @Command
    public void add() {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Function", "Add");
        Executions.createComponents(addEditZulPath, null, map);
    }

    @Command
    public void edit(@BindingParam("object") final GrammaRules object) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("object", object);
        Executions.createComponents(addEditZulPath, null, map);
    }

    @Command
    public void delete(@BindingParam("object") final GrammaRules object) {
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

    void confirmDelete(GrammaRules object) {
        try {
            adminServiceImpl.delete(object);
        } catch (ConstraintViolationException | DataIntegrityViolationException cve) {
            Clients.showNotification("Помилка видалення даних", "error",
                    null, "middle_center", 8000, true);
        }
        refresh();
    }

    public ListModelList<GrammaRules> getRules() {
        return rules;
    }

    public void setRules(ListModelList<GrammaRules> rules) {
        this.rules = rules;
    }

}
