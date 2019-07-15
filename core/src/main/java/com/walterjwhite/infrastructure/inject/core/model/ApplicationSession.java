package com.walterjwhite.infrastructure.inject.core.model;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@Entity
public class ApplicationSession extends AbstractEntity {
  @NonNull
  @ManyToOne(optional = false)
  @JoinColumn(nullable = false, updatable = false)
  protected ApplicationIdentifier applicationIdentifier;

  //  @NonNull
  //  @ManyToOne(optional = false)
  //  @JoinColumn(nullable = false, updatable = false)
  //  protected Node node;
  @NonNull
  @Column(nullable = false, updatable = false)
  protected String nodeId;

  @NonNull
  @Column(updatable = false)
  protected LocalDateTime startDateTime;

  @EqualsAndHashCode.Exclude
  @Column(insertable = false)
  protected LocalDateTime endDateTime;

  @Override
  public String toString() {
    return "ApplicationSession("
        + applicationIdentifier
        + " on "
        + nodeId
        + " started @ "
        + startDateTime
        + ')';
  }
}
