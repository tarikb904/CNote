package com.example.xinus.generic;

class MaterialUpdateModel {
    String materialmodelclass;
    String typeOfdata,imageUrl,datadescription,chaptername,coursettle,coursecode,semister,year;
    public MaterialUpdateModel(String materialmodelclass) { this.materialmodelclass = materialmodelclass; }
    public MaterialUpdateModel() { }

    public MaterialUpdateModel(String typeOfdata, String imageUrl, String datadescription, String chaptername,
                               String coursettle, String coursecode, String semister, String year) {
        this.typeOfdata = typeOfdata;
        this.imageUrl = imageUrl;
        this.datadescription = datadescription;
        this.chaptername = chaptername;
        this.coursettle = coursettle;
        this.coursecode = coursecode;
        this.semister = semister;
        this.year = year;
    }

    public String getMaterialmodelclass() {
        return materialmodelclass;
    }

    public void setMaterialmodelclass(String materialmodelclass) {
        this.materialmodelclass = materialmodelclass;
    }

    public String getTypeOfdata() {
        return typeOfdata;
    }

    public void setTypeOfdata(String typeOfdata) {
        this.typeOfdata = typeOfdata;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDatadescription() {
        return datadescription;
    }

    public void setDatadescription(String datadescription) {
        this.datadescription = datadescription;
    }

    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

    public String getCoursettle() {
        return coursettle;
    }

    public void setCoursettle(String coursettle) {
        this.coursettle = coursettle;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getSemister() {
        return semister;
    }

    public void setSemister(String semister) {
        this.semister = semister;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
