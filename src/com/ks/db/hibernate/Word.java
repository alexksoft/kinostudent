package com.ks.db.hibernate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "word")
public class Word {
    private Integer id;
    private String word;
    private String translation1;
    private String translation2;
    private String translation3;
    private String translation4;
    private String description;

    public Word() {
    }

    public Word(Integer id, String word, String translation1, String translation2, String translation3, String translation4, String description) {
        this.id = id;
        this.word = word;
        this.translation1 = translation1;
        this.translation2 = translation2;
        this.translation3 = translation3;
        this.translation4 = translation4;
        this.description = description;
    }
    public Word( String word, String translation1, String translation2, String translation3, String translation4, String description) {

        this.word = word;
        this.translation1 = translation1;
        this.translation2 = translation2;
        this.translation3 = translation3;
        this.translation4 = translation4;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "word", length = 100)
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Column(name = "translation1", length = 100)
    public String getTranslation1() {
        return translation1;
    }

    public void setTranslation1(String translation1) {
        this.translation1 = translation1;
    }
    @Column(name = "translation2", length = 100)
    public String getTranslation2() {
        return translation2;
    }

    public void setTranslation2(String translation2) {
        this.translation2 = translation2;
    }
    @Column(name = "translation3", length = 100)
    public String getTranslation3() {
        return translation3;
    }

    public void setTranslation3(String translation3) {
        this.translation3 = translation3;
    }
    @Column(name = "translation4", length = 100)
    public String getTranslation4() {
        return translation4;
    }

    public void setTranslation4(String translation4) {
        this.translation4 = translation4;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (o instanceof Word) {
            Word that = (Word) o;
            EqualsBuilder eb = new EqualsBuilder();
            eb.append(this.getId(), that.getId());
            eb.append(this.getWord(), that.getWord());
            eb.append(this.getTranslation1(), that.getTranslation1());
            eb.append(this.getTranslation2(), that.getTranslation2());
            eb.append(this.getTranslation3(), that.getTranslation3());
            eb.append(this.getTranslation4(), that.getTranslation4());
            eb.append(this.getDescription(),that.getDescription());
            return eb.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.getId());
        hcb.append(this.getTranslation1());
        hcb.append(this.getTranslation2());
        hcb.append(this.getTranslation3());
        hcb.append(this.getTranslation4());
        hcb.append(this.getDescription());
        return hcb.hashCode();
    }
}
