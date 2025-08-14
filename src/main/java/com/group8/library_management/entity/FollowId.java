package com.group8.library_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class FollowId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1899809096751212180L;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "target_id", nullable = false)
    private Integer targetId;

    @Column(name = "target_type", nullable = false, length = 20)
    private String targetType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FollowId entity = (FollowId) o;
        return Objects.equals(this.targetId, entity.targetId) &&
                Objects.equals(this.targetType, entity.targetType) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetId, targetType, userId);
    }
}
