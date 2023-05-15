package com.smart.agriculture.enums.DiseaseMenu;

public enum category {
    category("父节点",0);

    category(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    private String type;
    private Integer code;
}
