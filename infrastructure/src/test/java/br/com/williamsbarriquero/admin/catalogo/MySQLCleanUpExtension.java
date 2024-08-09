package br.com.williamsbarriquero.admin.catalogo;

import java.util.Collection;
import java.util.List;

import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.williamsbarriquero.admin.catalogo.infrastructure.category.persistence.CategoryRepository;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.persistence.GenreRepository;

class MySQLCleanUpExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext context) {
        final var appContext = SpringExtension.getApplicationContext(context);

        cleanUp(List.of(
                appContext.getBean(CastMemberRepository.class),
                appContext.getBean(GenreRepository.class),
                appContext.getBean(CategoryRepository.class)
        ));
    }

    @SuppressWarnings("rawtypes")
    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}
