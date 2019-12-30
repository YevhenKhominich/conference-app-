package com.example.web.validation;

import com.example.data.entity.Member;
import com.example.exception.ValidationException;
import org.springframework.util.StringUtils;

public class MemberValidator {

    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "Member field parameter '%s' must be provided";
    private static final String AGE_EXCEPTION_MESSAGE = "Member age '%d' must be in range 6 to 150";

    public static void validate(Member member) throws ValidationException {
        validateNotEmptyProperty(member.getFirstName(), "firstName");
        validateNotEmptyProperty(member.getLastName(), "lastName");
        validateNotEmptyProperty(member.getAge(), "age");
        validateAge(member.getAge());
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }

    private static void validateAge(int age) {
        if (age <= 0 || age >= 150) {
            throw new ValidationException(String.format(AGE_EXCEPTION_MESSAGE));
        }
    }
}
