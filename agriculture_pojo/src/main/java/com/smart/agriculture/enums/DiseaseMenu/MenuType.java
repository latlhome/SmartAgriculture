package com.smart.agriculture.enums.DiseaseMenu;

public enum MenuType {
    CATEGORY("类别",0),PLANT("植物",1);

    MenuType(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }


    public Integer getCode() {
        return code;
    }


    private String type;
    private Integer code;
}
