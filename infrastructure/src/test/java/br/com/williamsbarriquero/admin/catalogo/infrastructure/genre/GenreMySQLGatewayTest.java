package br.com.williamsbarriquero.admin.catalogo.infrastructure.genre;

import br.com.williamsbarriquero.admin.catalogo.MySQLGatewayTest;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.category.persistence.CategoryMySQLGateway;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.persistence.GenreMySQLGateway;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
class GenreMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryGateway;

    @Autowired
    private GenreMySQLGateway genreGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void testDependenciesInjected() {
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(genreGateway);
        Assertions.assertNotNull(genreRepository);
    }
}
