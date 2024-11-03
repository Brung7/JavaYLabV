package by.vladimir.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private final List<Error> errorList = new ArrayList<>();

    public void add(Error error){
        this.errorList.add(error);
    }

    public boolean isValid(){
        return errorList.isEmpty();
    }

}