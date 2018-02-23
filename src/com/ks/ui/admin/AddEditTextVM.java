package com.ks.ui.admin;

import com.ks.db.hibernate.Text;
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
public class AddEditTextVM extends AbstractViewModel {

    @Wire
    protected Window addEditWindow;

    @Wire
    protected Caption caption;

    private Text text;

    @Init
    public void init() {
        if (Executions.getCurrent().getArg().containsKey("object")) {
            text = (Text) Executions.getCurrent().getArg().get("object");
        } else {
            text = new Text();
        }
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Selectors.wireComponents(view, this, false);
        if (text.getId() == null) {
            caption.setLabel("Add text");
        } else {
            caption.setLabel("Edit text");
        }
    }

    public void saveAfterCheck() {
        adminServiceImpl.save(text);
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

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
