package com.example.demo.enums;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    BACKEND("Back-end"),
    FRONTEND("Front-end"),
    DATA("Data");

    private String value;

    private Category(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue(){
        return value;
    }

    @JsonCreator
    public static Category fromString(String value) {
        for (Category category : Category.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category: " + value);
    }

    @Override
    public String toString() {
        return "Category{" +
                "value='" + value + '\'' +
                '}';
    }
}
