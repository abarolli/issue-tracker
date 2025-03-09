package io.onicodes.issue_tracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Issue {

    @Id
    private @GeneratedValue Long id;
    private String title;

    public Issue(String title) {
        this.title = title;
    }


    public Long getId() {
        return this.id;
    }

    
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Issue{id=" + "title=" + this.title + "}";
    }
}
