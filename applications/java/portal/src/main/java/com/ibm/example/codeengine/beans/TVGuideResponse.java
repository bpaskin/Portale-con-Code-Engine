package com.ibm.example.codeengine.beans;

import java.io.Serializable;
import java.util.Collection;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TVGuideResponse implements Serializable {
    private Collection<TVGuideChannel> program;

    public Collection<TVGuideChannel> getProgram() {
        return program;
    }

    public void setProgram(Collection<TVGuideChannel> program) {
        this.program = program;
    }
    
}
