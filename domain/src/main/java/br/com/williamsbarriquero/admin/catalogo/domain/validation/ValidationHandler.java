package br.com.williamsbarriquero.admin.catalogo.domain.validation;

import java.util.List;
import java.util.Optional;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler anHandler);

    <T> T validate(Validation<T> aValidation);

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            Optional<Error> first = getErrors().stream().findFirst();
            return first.orElse(null);
        } else {
            return null;
        }
    }

    List<Error> getErrors();

    interface Validation<T> {

        T validate();
    }
}
