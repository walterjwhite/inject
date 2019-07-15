package com.walterjwhite.infrastructure.inject.core.model;

import com.walterjwhite.datastore.api.model.entity.AbstractEntity;
import com.walterjwhite.property.api.property.ApplicationEnvironment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ApplicationIdentifier extends AbstractEntity {
  @NonNull
  @Column(updatable = false, nullable = false)
  protected ApplicationEnvironment applicationTargetEnvironment;

  @NonNull
  @Column(updatable = false, nullable = false)
  protected ApplicationEnvironment applicationEnvironment;

  @NonNull
  @Column(updatable = false, nullable = false)
  protected String applicationName;

  @NonNull
  @Column(updatable = false, nullable = false)
  protected String applicationVersion;

  @NonNull
  @Column(updatable = false, nullable = false)
  protected String scmVersion;

  @OneToMany(mappedBy = "applicationIdentifier")
  protected List<ApplicationSession> applicationSessions = new ArrayList<>();

  public String toString() {
    return String.format(
        "ApplicationIdentifier(%s-%s-%s (%s/%s))",
        applicationName,
        applicationVersion,
        scmVersion,
        applicationTargetEnvironment,
        applicationEnvironment);
  }
}
