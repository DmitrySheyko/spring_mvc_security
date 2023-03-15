package org.example.web.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegexValidation implements ConstraintValidator<RegexValidationAnnotation, String> {
    private static final String ALL_NUMBERS_REGEX = "^\\d+$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s != null && !s.isEmpty()) {
            int countOfSlash = 0;
            int firstSlashIndex = -1;
            int secondSlashIndex = -1;
            String[] stringArray;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '/') {
                    countOfSlash++;
                    if (firstSlashIndex == -1) {
                        firstSlashIndex = i;
                    } else {
                        secondSlashIndex = i;
                    }
                }
            }
            if (countOfSlash == 2 && firstSlashIndex != 0 && firstSlashIndex != s.length() - 1 && secondSlashIndex != s.length() - 1) {
                stringArray = s.split("/");
                return stringArray[2].matches(ALL_NUMBERS_REGEX) || stringArray[2].equals("*");
            }
        }
        return false;
    }
}
