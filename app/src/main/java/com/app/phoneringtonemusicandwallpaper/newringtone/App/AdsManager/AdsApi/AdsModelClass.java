package com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.AdsApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsModelClass {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("myadtype")
    @Expose
    private String myadtype;
    @SerializedName("finter")
    @Expose
    private String finter;
    @SerializedName("fnative")
    @Expose
    private String fnative;
    @SerializedName("fbanner")
    @Expose
    private String fbanner;
    @SerializedName("gnative")
    @Expose
    private String gnative;
    @SerializedName("gbanner")
    @Expose
    private String gbanner;
    @SerializedName("ginter")
    @Expose
    private String ginter;
    @SerializedName("other")
    @Expose
    private String other;
    @SerializedName("other1")
    @Expose
    private String other1;
    @SerializedName("counter")
    @Expose
    private Integer counter;
    @SerializedName("increment")
    @Expose
    private Integer increment;
    @SerializedName("adbanner1")
    @Expose
    private String adbanner1;
    @SerializedName("adbanner2")
    @Expose
    private String adbanner2;
    @SerializedName("package")
    @Expose
    private String _package;

    public AdsModelClass(Integer id, String myadtype, String finter, String fnative, String fbanner, String gnative, String gbanner, String ginter, String other, String other1, Integer counter, Integer increment, String adbanner1, String adbanner2, String _package) {
        this.id = id;
        this.myadtype = myadtype;
        this.finter = finter;
        this.fnative = fnative;
        this.fbanner = fbanner;
        this.gnative = gnative;
        this.gbanner = gbanner;
        this.ginter = ginter;
        this.other = other;
        this.other1 = other1;
        this.counter = counter;
        this.increment = increment;
        this.adbanner1 = adbanner1;
        this.adbanner2 = adbanner2;
        this._package = _package;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMyadtype() {
        return myadtype;
    }

    public void setMyadtype(String myadtype) {
        this.myadtype = myadtype;
    }

    public String getFinter() {
        return finter;
    }

    public void setFinter(String finter) {
        this.finter = finter;
    }

    public String getFnative() {
        return fnative;
    }

    public void setFnative(String fnative) {
        this.fnative = fnative;
    }

    public String getFbanner() {
        return fbanner;
    }

    public void setFbanner(String fbanner) {
        this.fbanner = fbanner;
    }

    public String getGnative() {
        return gnative;
    }

    public void setGnative(String gnative) {
        this.gnative = gnative;
    }

    public String getGbanner() {
        return gbanner;
    }

    public void setGbanner(String gbanner) {
        this.gbanner = gbanner;
    }

    public String getGinter() {
        return ginter;
    }

    public void setGinter(String ginter) {
        this.ginter = ginter;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOther1() {
        return other1;
    }

    public void setOther1(String other1) {
        this.other1 = other1;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public String getAdbanner1() {
        return adbanner1;
    }

    public void setAdbanner1(String adbanner1) {
        this.adbanner1 = adbanner1;
    }

    public String getAdbanner2() {
        return adbanner2;
    }

    public void setAdbanner2(String adbanner2) {
        this.adbanner2 = adbanner2;
    }

    public String getPackage() {
        return _package;
    }

    public void setPackage(String _package) {
        this._package = _package;
    }

}
