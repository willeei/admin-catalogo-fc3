package br.com.williamsbarriquero.admin.catalogo.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.AbstractEnvironment;

class MainTest {

    @Test
    void testMain() {
        String property = System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "test-integration");
        Assertions.assertNull(property);
        Main.main(new String[]{});
    }
}
