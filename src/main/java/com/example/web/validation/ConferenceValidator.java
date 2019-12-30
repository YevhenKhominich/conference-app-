package com.example.web.validation;

import com.example.data.entity.Conference;
import com.example.exception.ValidationException;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConferenceValidator {

    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "Conference field parameter '%s' must be provided";
    private static final String REGEX_EXCEPTION_MESSAGE = "Conference field parameter '%s' must match these parameters: '%s'";

   // private static String VALID_YEAR_REGEX = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";

    public static void validate(Conference conference) throws ValidationException {
        validateNotEmptyProperty(conference.getName(), "name");
        System.out.println(conference.getDate());
//        validateWithRegularExpression(conference.getDate(), VALID_YEAR_REGEX, "date", "Year must consist of four digits");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }
//
//    private static void validateWithRegularExpression(Object value, String regex, String propertyName, String exceptionMessage) {
//
//        Matcher matcher = Pattern.compile(regex).matcher(String.valueOf(value));
//        if (!matcher.matches()) {
//            throw new ValidationException(String.format(REGEX_EXCEPTION_MESSAGE, propertyName, exceptionMessage));
//        }
//
//    }
}
