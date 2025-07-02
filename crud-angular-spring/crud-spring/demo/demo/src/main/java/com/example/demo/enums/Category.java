package com.example.demo.enums;

public enum Category {
    BACKEND("Back-end"), FRONTEND("Front-end"), DATA("Data");

    private String value;


    private Category(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString() {
        return "Category{" +
                "value='" + value + '\'' +
                '}';
    }
}
