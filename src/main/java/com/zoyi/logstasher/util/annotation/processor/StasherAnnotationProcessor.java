package com.zoyi.logstasher.util.annotation.processor;


import com.zoyi.logstasher.util.StringUtil;
import com.zoyi.logstasher.util.annotation.Stasher;

/**
 * Created by lou on 2017-04-04 16:03
 */
public class StasherAnnotationProcessor extends AnnotationProcessor<Stasher> {
  public static final StasherAnnotationProcessor STASHER_PROCESSOR = new StasherAnnotationProcessor();

  private StasherAnnotationProcessor() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void addClass(final Class<?> klass) {
    final Stasher stasher = klass.getAnnotation((Class<Stasher>) getGenericsClassType(0));
    final String name = stasher.name().getName();
    StringUtil.ifNotNullOrEmptyThen(name,
                                       name1 -> put(name1, klass),
                                       name2 -> put(klass.getSimpleName(), klass));
  }
}
