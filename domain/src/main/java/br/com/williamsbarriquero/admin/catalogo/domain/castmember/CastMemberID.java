package br.com.williamsbarriquero.admin.catalogo.domain.castmember;

import java.util.Objects;

import br.com.williamsbarriquero.admin.catalogo.domain.Identifier;
import br.com.williamsbarriquero.admin.catalogo.domain.utils.IdUtils;

public class CastMemberID extends Identifier {

    private final String value;

    private CastMemberID(final String anId) {
        Objects.requireNonNull(anId);
        this.value = anId;
    }

    public static CastMemberID unique() {
        return CastMemberID.from(IdUtils.uuid());
    }

    public static CastMemberID from(final String anId) {
        return new CastMemberID(anId);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CastMemberID that = (CastMemberID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
