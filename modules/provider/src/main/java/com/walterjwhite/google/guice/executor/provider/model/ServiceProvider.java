package com.walterjwhite.google.guice.executor.provider.model;

import com.walterjwhite.datastore.api.model.entity.AbstractNamedEntity;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true)
@NoArgsConstructor
// @PersistenceCapable
@Entity
public class ServiceProvider extends AbstractNamedEntity {
  @Column protected String serviceClassname;
  @ManyToOne @JoinColumn protected ServiceProviderType serviceProviderType;
  //    @Column
  //    protected boolean defaultProvider;

  public ServiceProvider(
      String name,
      String description,
      String serviceClassname,
      ServiceProviderType serviceProviderType) {
    super(name, description);
    this.serviceClassname = serviceClassname;
    this.serviceProviderType = serviceProviderType;
  }
}
