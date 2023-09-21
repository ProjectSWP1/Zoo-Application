package com.thezookaycompany.zookayproject.model.api;

import com.thezookaycompany.zookayproject.model.enums.GenderStatus;

public class Gender {

    private GenderStatus genderStatus;

    public enum GenderStatus {
        MALE,
        FEMALE,
        UNDEFINED;
    }

    public GenderStatus getGenderStatus() {
        return genderStatus;
    }

    public void setGenderStatus(GenderStatus genderStatus) {
        this.genderStatus = genderStatus;
    }

    public boolean isGender() {
        if(getGenderStatus() == GenderStatus.MALE) {
            return true;
        } else if(getGenderStatus() == GenderStatus.FEMALE) {
            return true;
        } else return getGenderStatus() == GenderStatus.UNDEFINED;
    }

    // Co the xay dung them api o day
}
