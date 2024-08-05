package br.com.williamsbarriquero.admin.catalogo.application;

import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMember;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberType;
import com.github.javafaker.Faker;

public final class Fixture {

    private static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static final class CastMember {

        public static CastMemberType type() {
            return FAKER.options().option(CastMemberType.values());
        }
    }
}
