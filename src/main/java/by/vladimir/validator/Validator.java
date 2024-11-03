package by.vladimir.validator;

public interface Validator <T>{
    ValidationResult isValid (T object);
}