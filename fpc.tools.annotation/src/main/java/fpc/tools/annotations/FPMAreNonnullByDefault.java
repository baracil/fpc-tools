package fpc.tools.annotations;

import jakarta.annotation.Nonnull;

import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Applies the {@link Nonnull} annotation to every class field unless overridden.
 */
@Documented
@Nonnull
@TypeQualifierDefault({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FPMAreNonnullByDefault
{
    // nothing to add
}