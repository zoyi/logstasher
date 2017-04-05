package com.zoyi.logstasher.util.annotation.processor;


import com.zoyi.logstasher.util.StringUtil;
import com.zoyi.logstasher.util.annotation.Stasher;

/**
 * Created by lou on 2017-04-04 16:03
 */
public class StasherProcessor extends AnnotationProcessor<Stasher> {
  public static final StasherProcessor STASHER_PROCESSOR = new StasherProcessor();

  private StasherProcessor() {
    super();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void addClass(final Class<?> klass) {
    final Stasher stasher = klass.getAnnotation((Class<Stasher>)getGenericsClassType(0));
    final String name = stasher.name().getName();
    StringUtil.isNotNullOrEmptyOrElseEachThen(name,
                                       name1 -> put(name1, klass),
                                       name2 -> put(klass.getSimpleName(), klass));
  }
}