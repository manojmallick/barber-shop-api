package com.ing.barber.shop.api.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * The type Mongo config.
 */
@Configuration
public class MongoConfig {

  /**
   * Mapping mongo converter mapping mongo converter.
   *
   * @param factory     the factory
   * @param context     the context
   * @param beanFactory the bean factory
   * @return the mapping mongo converter
   */
  @Bean
  public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory,
      MongoMappingContext context, BeanFactory beanFactory) {
    DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
    MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
    try {
      mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
    } catch (NoSuchBeanDefinitionException ignore) {
    }
    // to remove  _class to mongo collection
    mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    return mappingConverter;
  }
}
 
