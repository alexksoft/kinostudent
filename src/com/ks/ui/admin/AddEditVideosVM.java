package com.ks.ui.admin;

import com.ks.db.hibernate.Videos;
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
public class AddEditVideosVM extends AbstractViewModel {

    @Wire
    protected Window addEditWindow;

    @Wire
    protected Caption caption;

    private Videos videos;

    @Init
    public void init() {
        if (Executions.getCurrent().getArg().containsKey("object")) {
            videos = (Videos) Executions.getCurrent().getArg().get("object");
        } else {
            videos = new Videos();
        }
    }

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws ClassNotFoundException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
        Selectors.wireComponents(view, this, false);
        if (videos.getId() == null) {
            caption.setLabel("Add videos");
        } else {
            caption.setLabel("Edit videos");
        }
    }

    public void saveAfterCheck() {
        adminServiceImpl.save(videos);
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

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }
}
