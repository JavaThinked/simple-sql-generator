package com.javathinked.reflection.model.generic;

import com.javathinked.reflection.model.enums.Gender;
import lombok.Data;

@Data
public abstract class Person {

    public static final String FIELD_ID = "id";
    public static final String FIELD_FIRST_NAME = "firstname";
    public static final String FIELD_LAST_NAME = "lastname";
    public static final String FIELD_TELEPHONE = "telephone";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_GENDER = "gender";
    public static final String FIELD_ADDRESS = "address";

    protected long id;
    protected String firstName;
    protected String lastName;
    protected String telephone;
    protected String email;
    protected Gender gender;
    protected String address;

}
