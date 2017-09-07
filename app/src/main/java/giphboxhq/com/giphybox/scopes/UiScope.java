package giphboxhq.com.giphybox.scopes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Owner on 2017-09-07.
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UiScope {
}

