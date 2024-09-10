package br.com.williamsbarriquero.admin.catalogo.domain.category;

import java.util.Objects;

import br.com.williamsbarriquero.admin.catalogo.domain.Identifier;
import br.com.williamsbarriquero.admin.catalogo.domain.utils.IdUtils;

public class CategoryID extends Identifier {

    private final String value;

    private CategoryID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static CategoryID unique() {
        return CategoryID.from(IdUtils.uuid());
    }

    public static CategoryID from(final String anId) {
        return new CategoryID(anId);
    }

    public static CategoryID from() {
        return new CategoryID(IdUtils.uuid());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoryID that = (CategoryID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
