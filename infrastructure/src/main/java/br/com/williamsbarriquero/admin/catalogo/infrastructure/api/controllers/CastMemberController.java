package br.com.williamsbarriquero.admin.catalogo.infrastructure.api.controllers;

import br.com.williamsbarriquero.admin.catalogo.application.castmember.create.CreateCastMemberCommand;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.retrieve.list.ListCastMembersUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.update.UpdateCastMemberCommand;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.SearchQuery;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.api.CastMemberAPI;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.models.CastMemberListResponse;
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
    private final DeleteCastMemberUseCase deleteCastMemberUseCase;
    private final ListCastMembersUseCase listCastMembersUseCase;

    public CastMemberController(
            final CreateCastMemberUseCase createCastMemberUseCase,
            final GetCastMemberByIdUseCase getCastMemberByIdUseCase,
            final UpdateCastMemberUseCase updateCastMemberUseCase,
            final DeleteCastMemberUseCase deleteCastMemberUseCase,
            final ListCastMembersUseCase listCastMembersUseCase
    ) {
        this.createCastMemberUseCase = Objects.requireNonNull(createCastMemberUseCase);
        this.getCastMemberByIdUseCase = Objects.requireNonNull(getCastMemberByIdUseCase);
        this.updateCastMemberUseCase = Objects.requireNonNull(updateCastMemberUseCase);
        this.deleteCastMemberUseCase = Objects.requireNonNull(deleteCastMemberUseCase);
        this.listCastMembersUseCase = Objects.requireNonNull(listCastMembersUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateCastMemberRequest input) {
        final var aCommand = CreateCastMemberCommand.with(input.name(), input.type());

        final var output = this.createCastMemberUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/cast_members/" + output.id())).body(output);
    }

    @Override
    public Pagination<CastMemberListResponse> list(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return this.listCastMembersUseCase.execute(
                new SearchQuery(page, perPage, search, sort, direction)
        ).map(CastMemberPresenter::present);
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

    @Override
    public void deleteById(final String id) {
        this.deleteCastMemberUseCase.execute(id);
    }
}
