package com.zoyi.logstasher.util.annotation.processor;


import com.zoyi.logstasher.util.StringUtil;
import com.zoyi.logstasher.util.annotation.Stasher;

/**
 * @author Loustler(Dongyeon Lee)
 * @since 2017-04-04
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

    if (StringUtil.isNotNullOrEmpty(name))
      put(name, klass);
    else
      put(klass.getSimpleName(), klass);
  }
}
