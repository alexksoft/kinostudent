package com.ks.ui.admin;

import com.ks.db.hibernate.GrammaRules;
import com.ks.ui.AbstractViewModel;
import com.ks.util.UIUtilities;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Window;

import java.lang.reflect.InvocationTargetException;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AddEditRulesVM extends AbstractViewModel {

    @Wire
    protected Window addEditWindow;

    @Wire
    protected Caption caption;

    private GrammaRules rules;

    @Init
    public void init() {
        if (Executions.getCurrent().getArg().containsKey("object")) {
            rules = (GrammaRules) Executions.getCurrent().getArg().get("object");
        } else {
            rules = new GrammaRules();
        }
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Selectors.wireComponents(view, this, false);
        if (rules.getId() == null) {
            caption.setLabel("Add rules");
        } else {
            caption.setLabel("Edit rules");
        }
    }

    public void saveAfterCheck() {
        adminServiceImpl.save(rules);
        BindUtils.postGlobalCommand(null, null, "refresh", null);
    }

    @Command
    public void save() {
        if (UIUtilities.check(addEditWindow)) {
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

    public GrammaRules getRules() {
        return rules;
    }

    public void setRules(GrammaRules rules) {
        this.rules = rules;
    }
}
