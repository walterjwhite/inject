// package com.walterjwhite.infrastructure.inject.cli.cdi;
//
// import com.walterjwhite.inject.cdi.CDIInjectorService;
// import lombok.Getter;
//
/// **
// * Discovered through reflections API and instantiated by CLIApplicationHelper, then initializes
// * Guice.
// */
// @Getter
// public class CDICLIApplication extends AbstractCLIApplication {
//
//  public CDICLIApplication() {
//    super(new CDIInjectorService());
//  }
//
//  @Override
//  protected void initialize() {
//    // nothing additional required here
//  }
//
//  //  protected void run(){
//  //      // create application scoped beans
//  //      // seContainer.getBeanManager().COMMAND_LINE_MODULE.doRun(arguments);
//  //
//  //      // static lookup
//  //      CLIApplication cliApplication = CDI.current().select(CLIApplication.class).get();
//  //      BeanAttributes beanAttributes = null; // new ImmutableBeanAttributes();
//  //      final Class beanClass = Integer.class;
//  //      InjectionTargetFactory injectionTargetFactory =
//  //              CDI.current().getBeanManager().getInjectionTargetFactory(null);
//  //
//  //      CDI.current().getBeanManager().createBean(beanAttributes, beanClass,
//  //              injectionTargetFactory);
//  //  }
// }
