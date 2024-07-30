package br.com.williamsbarriquero.admin.catalogo.domain.castmember;

import br.com.williamsbarriquero.admin.catalogo.domain.AggregateRoot;
import br.com.williamsbarriquero.admin.catalogo.domain.exceptions.NotificationException;
import br.com.williamsbarriquero.admin.catalogo.domain.utils.InstantUtils;
import br.com.williamsbarriquero.admin.catalogo.domain.validation.ValidationHandler;
import br.com.williamsbarriquero.admin.catalogo.domain.validation.handler.Notification;

import java.time.Instant;

public class CastMember extends AggregateRoot<CastMemberID> {

    private String name;
    private CastMemberType type;
    private Instant createdAt;
    private Instant updatedAt;

    public CastMember(
            final CastMemberID anId,
            final String aName,
            final CastMemberType aType,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
    ) {
        super(anId);
        this.name = aName;
        this.type = aType;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
        selfValidate();
    }

    public static CastMember newMember(final String aName, final CastMemberType aType) {
        final var anId = CastMemberID.unique();
        final var now = InstantUtils.now();
        return new CastMember(anId, aName, aType, now, now);
    }

    public static CastMember with(
            final CastMemberID anId,
            final String aName,
            final CastMemberType aType,
            final Instant aCreatedAt,
            final Instant aUpdatedAt
    ) {
        return new CastMember(anId, aName, aType, aCreatedAt, aUpdatedAt);
    }

    public static CastMember with(final CastMember aMember) {
        return new CastMember(
                aMember.id,
                aMember.name,
                aMember.type,
                aMember.createdAt,
                aMember.updatedAt
        );
    }

    public CastMember update(final String aName, final CastMemberType aType) {
        this.name = aName;
        this.type = aType;
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    public String getName() {
        return name;
    }

    public CastMemberType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void validate(final ValidationHandler aHandler) {
        new CastMemberValidator(this, aHandler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Aggregate CastMember", notification);
        }
    }
}
