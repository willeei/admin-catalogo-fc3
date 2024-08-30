package br.com.williamsbarriquero.admin.catalogo.infrastructure.api.controllers;

import br.com.williamsbarriquero.admin.catalogo.application.castmember.create.CreateCastMemberCommand;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.update.UpdateCastMemberCommand;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.api.CastMemberAPI;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.models.UpdateCastMemberRequest;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.presenter.CastMemberPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CastMemberController implements CastMemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;
    private final GetCastMemberByIdUseCase getCastMemberByIdUseCase;
    private final UpdateCastMemberUseCase updateCastMemberUseCase;

    public CastMemberController(
            final CreateCastMemberUseCase createCastMemberUseCase,
            final GetCastMemberByIdUseCase getCastMemberByIdUseCase,
            final UpdateCastMemberUseCase updateCastMemberUseCase
    ) {
        this.createCastMemberUseCase = Objects.requireNonNull(createCastMemberUseCase);
        this.getCastMemberByIdUseCase = Objects.requireNonNull(getCastMemberByIdUseCase);
        this.updateCastMemberUseCase = Objects.requireNonNull(updateCastMemberUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateCastMemberRequest input) {
        final var aCommand = CreateCastMemberCommand.with(input.name(), input.type());

        final var output = this.createCastMemberUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/cast_members/" + output.id())).body(output);
    }

    @Override
    public CastMemberResponse getById(final String id) {
        return CastMemberPresenter.present(this.getCastMemberByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCastMemberRequest aBody) {
        final var aCommand =
                UpdateCastMemberCommand.with(id, aBody.name(), aBody.type());

        final var output = this.updateCastMemberUseCase.execute(aCommand);

        return ResponseEntity.ok(output);
    }
}
