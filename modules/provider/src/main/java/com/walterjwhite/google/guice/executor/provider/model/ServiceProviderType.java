package com.walterjwhite.google.guice.executor.provider.model;

import com.walterjwhite.datastore.api.model.entity.AbstractNamedEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// TODO: can this be replaced with an enum (service providers will likely need a supporting
// implementation in Java code)
// mark the default provider in the enum to eliminate a database call, the enum will store a
// ServiceProvider
@NoArgsConstructor
@Data
@ToString(doNotUseGetters = true)
// @PersistenceCapable
@Entity
public class ServiceProviderType extends AbstractNamedEntity {
  @Column protected String serviceInterfaceClassname;
  @ManyToOne @JoinColumn protected ServiceProvider defaultServiceProvider;

  @OneToMany protected List<ServiceProvider> serviceProviders = new ArrayList<>();

  public ServiceProviderType(
      String name,
      String description,
      String serviceInterfaceClassname,
      ServiceProvider defaultServiceProvider) {
    super(name, description);
    this.serviceInterfaceClassname = serviceInterfaceClassname;
    this.defaultServiceProvider = defaultServiceProvider;
  }
}
