package com.smart.agriculture.enums.DiseaseMenu;

public enum menuType {
    category("类别",0),plant("植物",1);

    menuType(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    private String type;
    private Integer code;
}
