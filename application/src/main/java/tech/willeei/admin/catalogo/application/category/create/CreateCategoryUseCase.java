package tech.willeei.admin.catalogo.application.category.create;

import tech.willeei.admin.catalogo.application.UseCase;
import tech.willeei.admin.catalogo.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends
        UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {

}
