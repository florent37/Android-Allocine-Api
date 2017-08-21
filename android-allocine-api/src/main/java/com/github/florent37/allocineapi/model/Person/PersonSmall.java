package com.github.florent37.allocineapi.model.Person;

import com.github.florent37.allocineapi.model.ModelObject;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class PersonSmall extends Person {

    @Expose
    private String name;

    @Expose
    private List<ModelObject> activity = new ArrayList<ModelObject>();

    public List<ModelObject> getActivity() {
        return activity;
    }

    public void setActivity(List<ModelObject> activity) {
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return name;
    }

    @Override
    public String toString() {
        return "PersonSmall{" +
                "name=" + name +
                "activity=" + activity +
                super.toString() + "}";
    }

}
