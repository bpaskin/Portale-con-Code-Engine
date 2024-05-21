package com.ibm.example.codeengine.beans;

import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuoteRequest implements Serializable {
   
    private String categoryLabel = "category";
    private String category;
    
    public String getCategoryLabel() {
        return categoryLabel;
    }
    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
}
