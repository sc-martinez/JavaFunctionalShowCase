package showcase;

import java.util.function.Function;
import showcase.PersonValidator.ValidationResult;
import static showcase.PersonValidator.ValidationResult.*;


public interface PersonValidator extends Function<Person, ValidationResult> {

    static PersonValidator isAgeValid() {
        return person -> person.getAge() > 18 && person.getAge() <= 120 ? SUCCESS : AGE_NOT_VALID;
    }

    static PersonValidator isRelativesValid(){
        return person -> person.getRelatives() != null ? SUCCESS : RELATIVES_VALID;
    }

    static PersonValidator isNameValid(){
        return person -> !person.getName().contains("@") ? SUCCESS : NAME_NOT_VALID;
    }

    default PersonValidator and (PersonValidator other){
        return person -> {
            ValidationResult result = this.apply(person);
            return result.equals(SUCCESS) ? other.apply(person) : result;
        };
    }


    enum ValidationResult{
        SUCCESS,
        AGE_NOT_VALID,
        NAME_NOT_VALID,
        RELATIVES_VALID
    }
}
