package tech.willeei.admin.catalogo.application.category.update;

import io.vavr.control.Either;
import tech.willeei.admin.catalogo.application.UseCase;
import tech.willeei.admin.catalogo.domain.validation.handler.Notification;

public abstract class UpdateCategoryUseCase
        extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
