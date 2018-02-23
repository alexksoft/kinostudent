package com.ks.db.hibernate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "text")
public class Text {
    private Integer id;
    private String type;
    private String text;

    public Text() {
    }

    public Text(Integer id, String type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }

    public Text(String type, String text) {
        this.type = type;
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

    @Column(name = "type", length = 100)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (o instanceof Text) {
            Text that = (Text) o;
            EqualsBuilder eb = new EqualsBuilder();
            eb.append(this.getId(), that.getId());
            eb.append(this.getType(), that.getType());
            eb.append(this.getText(), that.getText());
            return eb.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.getId());
        hcb.append(this.getType());
        hcb.append(this.getText());
        return hcb.hashCode();
    }
}
