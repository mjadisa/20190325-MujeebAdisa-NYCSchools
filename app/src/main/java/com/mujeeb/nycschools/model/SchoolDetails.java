package com.mujeeb.nycschools.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolDetails {

    @SerializedName("dbn")
    @Expose
    private String dbn;
    @SerializedName("num_of_sat_test_takers")
    @Expose
    private String numOfSatTestTakers;
    @SerializedName("sat_critical_reading_avg_score")
    @Expose
    private String satCriticalReadingAvgScore;
    @SerializedName("sat_math_avg_score")
    @Expose
    private String satMathAvgScore;
    @SerializedName("sat_writing_avg_score")
    @Expose
    private String satWritingAvgScore;
    @SerializedName("school_name")
    @Expose
    private String schoolName;

    public String getDbn() {
        return dbn;
    }

    public void setDbn(String dbn) {
        this.dbn = dbn;
    }

    public String getNumOfSatTestTakers() {
        return numOfSatTestTakers;
    }

    public void setNumOfSatTestTakers(String numOfSatTestTakers) {
        this.numOfSatTestTakers = numOfSatTestTakers;
    }

    public String getSatCriticalReadingAvgScore() {
        return satCriticalReadingAvgScore;
    }

    public void setSatCriticalReadingAvgScore(String satCriticalReadingAvgScore) {
        this.satCriticalReadingAvgScore = satCriticalReadingAvgScore;
    }

    public String getSatMathAvgScore() {
        return satMathAvgScore;
    }

    public void setSatMathAvgScore(String satMathAvgScore) {
        this.satMathAvgScore = satMathAvgScore;
    }

    public String getSatWritingAvgScore() {
        return satWritingAvgScore;
    }

    public void setSatWritingAvgScore(String satWritingAvgScore) {
        this.satWritingAvgScore = satWritingAvgScore;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

}