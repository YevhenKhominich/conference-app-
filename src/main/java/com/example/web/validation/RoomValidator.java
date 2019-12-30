package com.example.web.validation;

import com.example.data.entity.Conference;
import com.example.data.entity.Room;
import com.example.exception.ValidationException;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomValidator {

    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "Conference field parameter '%s' must be provided";

    public static void validate(Room room) throws ValidationException {
        validateNotEmptyProperty(room.getName(),"name");
        validateNotEmptyProperty(room.getFloor(),"floor");
        validateNotEmptyProperty(room.getMaxSeats(),"maxSeats");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }
}
