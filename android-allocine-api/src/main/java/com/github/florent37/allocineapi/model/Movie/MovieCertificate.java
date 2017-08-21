package com.github.florent37.allocineapi.model.Movie;

import com.github.florent37.allocineapi.model.ModelObject;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.io.Serializable;

public class MovieCertificate implements Serializable {

    @Expose
    private ModelObject certificate;

    public ModelObject getCertificate() {
        return certificate;
    }

    public void setCertificate(ModelObject certificate) {
        this.certificate = certificate;
    }

    @Override
    public String toString() {
        return "MovieCertificate{" +
                "certificate=" + certificate +
                '}';
    }
}
