package com.cmpe277.birdie.data;

public class BirdsTaxonomy {

    private String imageUrl;
    private String scientificName;
    private String commonName;
    private String speciesCode;
    private String category;
    private String taxonOrder;
    private String comNameCodes;
    private String scientificNameCodes;
    private String bandingCodes;
    private String order;
    private String familyComName;
    private String familySciName;
    private String reportAs;
    private String extinct;
    private String extinctYear;
    private String familyCode;

    @Override
    public String toString() {
        return "BirdsTaxonomy{" +
                "scientificName='" + scientificName + '\'' +
                ", commanName='" + commonName + '\'' +
                ", speciesCode='" + speciesCode + '\'' +
                ", category='" + category + '\'' +
                ", taxonOrder='" + taxonOrder + '\'' +
                ", comNameCodes='" + comNameCodes + '\'' +
                ", scientificNameCodes='" + scientificNameCodes + '\'' +
                ", bandingCodes='" + bandingCodes + '\'' +
                ", order='" + order + '\'' +
                ", familyComName='" + familyComName + '\'' +
                ", familySciName='" + familySciName + '\'' +
                ", reportAs='" + reportAs + '\'' +
                ", extinct='" + extinct + '\'' +
                ", extinctYear='" + extinctYear + '\'' +
                ", familyCode='" + familyCode + '\'' +
                '}';
    }

    public BirdsTaxonomy(String scientificName, String commanName, String speciesCode, String category,
                         String taxonOrder, String comNameCodes, String scientificNameCodes, String bandingCodes,
                         String order, String familyComName, String familySciName, String reportAs, String extinct,
                         String extinctYear, String familyCode) {
        this.scientificName = scientificName;
        this.commonName = commanName;
        this.speciesCode = speciesCode;
        this.category = category;
        this.taxonOrder = taxonOrder;
        this.comNameCodes = comNameCodes;
        this.scientificNameCodes = scientificNameCodes;
        this.bandingCodes = bandingCodes;
        this.order = order;
        this.familyComName = familyComName;
        this.familySciName = familySciName;
        this.reportAs = reportAs;
        this.extinct = extinct;
        this.extinctYear = extinctYear;
        this.familyCode = familyCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getSpeciesCode() {
        return speciesCode;
    }

    public String getCategory() {
        return category;
    }

    public String getTaxonOrder() {
        return taxonOrder;
    }

    public String getComNameCodes() {
        return comNameCodes;
    }

    public String getScientificNameCodes() {
        return scientificNameCodes;
    }

    public String getBandingCodes() {
        return bandingCodes;
    }

    public String getOrder() {
        return order;
    }

    public String getFamilyComName() {
        return familyComName;
    }

    public String getFamilySciName() {
        return familySciName;
    }

    public String getReportAs() {
        return reportAs;
    }

    public String isExtinct() {
        return extinct;
    }

    public String getExtinctYear() {
        return extinctYear;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public String getExtinct() {
        return extinct;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public void setCommonName(String commanName) {
        this.commonName = commanName;
    }

    public void setSpeciesCode(String speciesCode) {
        this.speciesCode = speciesCode;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTaxonOrder(String taxonOrder) {
        this.taxonOrder = taxonOrder;
    }

    public void setComNameCodes(String comNameCodes) {
        this.comNameCodes = comNameCodes;
    }

    public void setScientificNameCodes(String scientificNameCodes) {
        this.scientificNameCodes = scientificNameCodes;
    }

    public void setBandingCodes(String bandingCodes) {
        this.bandingCodes = bandingCodes;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setFamilyComName(String familyComName) {
        this.familyComName = familyComName;
    }

    public void setFamilySciName(String familySciName) {
        this.familySciName = familySciName;
    }

    public void setReportAs(String reportAs) {
        this.reportAs = reportAs;
    }

    public void setExtinct(String extinct) {
        this.extinct = extinct;
    }

    public void setExtinctYear(String extinctYear) {
        this.extinctYear = extinctYear;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }
}
