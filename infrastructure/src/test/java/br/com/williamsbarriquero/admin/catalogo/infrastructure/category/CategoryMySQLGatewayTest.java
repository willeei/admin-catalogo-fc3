package br.com.williamsbarriquero.admin.catalogo.infrastructure.category;

import br.com.williamsbarriquero.admin.catalogo.infrastructure.MySQLGatewayTest;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.category.persistence.CategoryMySQLGateway;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testInjectedDependecies() {
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(categoryRepository);
    }
}
