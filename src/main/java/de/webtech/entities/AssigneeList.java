package de.webtech.entities;

import java.util.Set;

public class AssigneeList {

    public AssigneeList(Set<String> names) {
        this.names = names;
    }

    public AssigneeList() {
    }

    private Set<String> names;

    public Set<String> getNames() {
        return names;
    }

    public void setNames(Set<String> names) {
        this.names = names;
    }
}
