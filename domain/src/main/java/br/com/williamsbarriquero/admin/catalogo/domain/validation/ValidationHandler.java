package br.com.williamsbarriquero.admin.catalogo.domain.validation;

import java.util.List;
import java.util.Optional;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler anHandler);

    ValidationHandler validate(Validation aValidation);

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            Optional<Error> first = getErrors().stream().findFirst();
            return first.orElse(null);
        } else return null;
    }

    List<Error> getErrors();

    interface Validation {

        void validate();
    }
}

