package com.ks.ui.admin;

import com.ks.db.hibernate.Word;
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
public class WordListVM extends AbstractViewModel {

    private ListModelList<Word> word;
    protected String addEditZulPath, labelPart;

    @Init
    public void init() {
        addEditZulPath = "/WEB-INF/pages/add_edit_word.zul";
        refresh();
    }

    @GlobalCommand
    @NotifyChange("word")
    public void refresh() {
        word = new ListModelList<>(adminServiceImpl.get(Word.class));
        BindUtils.postNotifyChange(null, null, this, "word");
    }

    @Command
    public void add() {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Function", "Add");
        Executions.createComponents(addEditZulPath, null, map);
    }

    @Command
    public void edit(@BindingParam("object") final Word object) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("object", object);
        Executions.createComponents(addEditZulPath, null, map);
    }

    @Command
    public void delete(@BindingParam("object") final Word object) {
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

    void confirmDelete(Word object) {
        try {
            adminServiceImpl.delete(object);
        } catch (ConstraintViolationException | DataIntegrityViolationException cve) {
            Clients.showNotification("Помилка видалення даних", "error",
                    null, "middle_center", 8000, true);
        }
        refresh();
    }

    public ListModelList<Word> getWord() {
        return word;
    }

    public void setWord(ListModelList<Word> rules) {
        this.word = word;
    }

}
