package de.fb.arduino_sandbox.annotations;

import java.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Designates a class as a "java Swing view" component.
 * 
 * @author ibragim
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SwingView {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * 
     * @return the suggested component name, if any.
     */
    String value() default "";

}
