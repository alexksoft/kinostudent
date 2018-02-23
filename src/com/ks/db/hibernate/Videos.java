package com.ks.db.hibernate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "videos")
public class Videos {
    private Integer id;
    private String type;
    private String videoUrl;
    private String description;

    public Videos() {
    }

    public Videos(int id, String type, String videoUrl, String description ) {
        this.id = id;
        this.type = type;
        this.videoUrl = videoUrl;
        this.description = description;
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

    @Column(name = "video_url", length = 500)
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
        if (o instanceof Videos) {
            Videos that = (Videos) o;
            EqualsBuilder eb = new EqualsBuilder();
            eb.append(this.getId(), that.getId());
            eb.append(this.getType(), that.getType());
            eb.append(this.getVideoUrl(), that.getVideoUrl());
            eb.append(this.getDescription(),that.getDescription());
            return eb.isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.getId());
        hcb.append(this.getType());
        hcb.append(this.getVideoUrl());
        hcb.append(this.getDescription());
        return hcb.hashCode();
    }
}
