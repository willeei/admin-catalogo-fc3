package tech.willeei.admin.catalogo.application.category.update;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

import java.util.Objects;
import java.util.function.Supplier;

import io.vavr.control.Either;
import tech.willeei.admin.catalogo.domain.category.Category;
import tech.willeei.admin.catalogo.domain.category.CategoryGateway;
import tech.willeei.admin.catalogo.domain.category.CategoryID;
import tech.willeei.admin.catalogo.domain.exceptions.DomainException;
import tech.willeei.admin.catalogo.domain.exceptions.NotFoundException;
import tech.willeei.admin.catalogo.domain.validation.handler.Notification;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(anId)
                .orElseThrow(notFound(anId));

        final var notification = Notification.create();
        aCategory
                .update(aName, aDescription, isActive)
                .validate(notification);

        return notification.hasError() ? Left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }
}
