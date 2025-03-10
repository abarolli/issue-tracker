package io.onicodes.issue_tracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Issue {

    @Id
    private @GeneratedValue Long id;
    @NotBlank
    private String title;

    public Issue() {}

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
