package com.ks.db.hibernate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "gramma_rules")
public class GrammaRules {
    private Integer id;
    private String title;
    private String text;

    public GrammaRules() {
    }

    public GrammaRules(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "title", length = 500)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "text", length = 500)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (o instanceof GrammaRules) {
            GrammaRules that = (GrammaRules) o;
            EqualsBuilder eb = new EqualsBuilder();
            eb.append(this.getId(), that.getId());
            eb.append(this.getTitle(), that.getTitle());
            eb.append(this.getText(), that.getText());
            return eb.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.getId());
        hcb.append(this.getTitle());
        hcb.append(this.getText());
        return hcb.hashCode();
    }
}
