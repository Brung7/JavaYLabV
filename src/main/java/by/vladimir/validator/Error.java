package by.vladimir.validator;

import lombok.*;

@Value(staticConstructor = "of")
public class Error {
    String code;
     String message;
}
