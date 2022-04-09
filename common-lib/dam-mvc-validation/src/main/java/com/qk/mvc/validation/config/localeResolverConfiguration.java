package com.qk.mvc.validation.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class localeResolverConfiguration {
  //  @Bean
  //  @ConditionalOnMissingBean(Validator.class)
  //  public Validator getValidator() {
  //    final var validator = new LocalValidatorFactoryBean();
  //    validator.setValidationMessageSource(getMessageSource());
  //    return validator;
  //  }
  //
  //  @Bean
  //  @ConditionalOnMissingBean(ResourceBundleMessageSource.class)
  //  public ResourceBundleMessageSource getMessageSource() {
  //    var resourceBundleMessageSource = new ResourceBundleMessageSource();
  //    resourceBundleMessageSource.setDefaultEncoding("UTF-8");
  //    resourceBundleMessageSource.setBasenames("i18n/messages");
  //    return resourceBundleMessageSource;
  //  }
  //  /**
  //   * 设置默认语言
  //   *
  //   * @return LocaleResolver
  //   */
  //  @Bean
  //  @ConditionalOnMissingBean(LocaleResolver.class)
  //  public LocaleResolver localeResolver() {
  //    var acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
  //    acceptHeaderLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
  //    return acceptHeaderLocaleResolver;
  //  }
}
