package com.ead.authuser.models;

import com.ead.authuser.utils.ObjectMapperUtils;

public abstract class JsonAbstract {

    @Override
    public String toString() {
        return ObjectMapperUtils.writeObjectInJson(this);
    }

}
