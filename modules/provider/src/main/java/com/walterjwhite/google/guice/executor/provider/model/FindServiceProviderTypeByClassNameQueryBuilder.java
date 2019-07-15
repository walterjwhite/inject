// package com.walterjwhite.google.guice.executor.provider.model;
//
// import com.walterjwhite.datastore.modules.jpa.JpaRepository;
// import javax.persistence.EntityManager;
// import javax.persistence.criteria.CriteriaBuilder;
//
// public class ServiceProviderTypeEntityRepository
//    extends JpaRepository<ServiceProviderType> {
//  public ServiceProviderTypeEntityRepository(
//      EntityManager entityManager,
//      CriteriaBuilder criteriaBuilder,
//      Class<ServiceProviderType> serviceProviderTypeClass) {
//    super(entityManager, serviceProviderTypeClass);
//  }
//
//  public ServiceProviderType findByServiceProviderTypeClassName(
//      final String serviceProviderTypeClassName) {
//    //    final CriteriaQueryConfiguration<ServiceProviderType> jobCriteriaQueryConfiguration =
//    //        getCriteriaQuery();
//    //
//    //    Predicate condition =
//    //        criteriaBuilder.equal(
//    //            jobCriteriaQueryConfiguration
//    //                .getRoot()
//    //                .get(ServiceProviderType_.serviceInterfaceClassname),
//    //            serviceProviderTypeClassName);
//    //    jobCriteriaQueryConfiguration.getCriteriaQuery().where(condition);
//    //
//    //    return entityManager
//    //        .createQuery(jobCriteriaQueryConfiguration.getCriteriaQuery())
//    //        .getSingleResult();
//
//    return null;
//  }
// }
