package com.cmpe277.birdie.data;

public class BirdsObserved {

            private String speciesCode; //"calqua",
            private String comName;     //:"California Quail",
            private String sciName;     //:"Callipepla californica",
            private String locId;       //"L5679380",
            private String locName;     //:"Rancho San Antonio--Hill Trail",
            private String obsDt;       //"2022-05-05 08:51",
            private int howMany;     //2,
            private double lat;         //37.331076,
            private double lng;         //-122.0932293,
            private boolean obsValid;    //true,
            private boolean obsReviewed; //false,
            private String locationPrivate;//false,
            private String subId;       //S109011317"

            public String getComName() {
                return comName;
            }

            public void setComName(String comName) {
                this.comName = comName;
            }

            public String getSciName() {
                return sciName;
            }

            public void setSciName(String sciName) {
                this.sciName = sciName;
            }

            public String getLocId() {
                return locId;
            }

            public void setLocId(String locId) {
                this.locId = locId;
            }

            public String getLocName() {
                return locName;
            }

            public void setLocName(String locName) {
                this.locName = locName;
            }

            public String getObsDt() {
                return obsDt;
            }

            public void setObsDt(String obsDt) {
                this.obsDt = obsDt;
            }

            public int getHowMany() {
                return howMany;
            }

            public void setHowMany(int howMany) {
                this.howMany = howMany;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public boolean isObsValid() {
                return obsValid;
            }

            public void setObsValid(boolean obsValid) {
                this.obsValid = obsValid;
            }

            public boolean isObsReviewed() {
                return obsReviewed;
            }

            public void setObsReviewed(boolean obsReviewed) {
                this.obsReviewed = obsReviewed;
            }

            public String getLocationPrivate() {
                return locationPrivate;
            }

            public void setLocationPrivate(String locationPrivate) {
                this.locationPrivate = locationPrivate;
            }

            public String getSubId() {
                return subId;
            }

            public void setSubId(String subId) {
                this.subId = subId;
            }

            public String getSpeciesCode() {
                return speciesCode;
            }

            public void setSpeciesCode(String speciesCode) {
                this.speciesCode = speciesCode;
            }
}
