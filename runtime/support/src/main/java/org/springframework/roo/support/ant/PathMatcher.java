package org.springframework.roo.support.ant;

import java.util.Map;

/**
 * Strategy interface for <code>String</code>-based path matching.
 * <p>
 * The default implementation is {@link AntPathMatcher}, supporting the
 * Ant-style pattern syntax.
 * 
 * @author Juergen Hoeller
 * @since 1.2.0
 * @see AntPathMatcher
 */
public interface PathMatcher {

  /**
   * Given a pattern and a full path, determine the pattern-mapped part.
   * <p>
   * This method is supposed to find out which part of the path is matched
   * dynamically through an actual pattern, that is, it strips off a
   * statically defined leading path from the given full path, returning only
   * the actually pattern-matched part of the path.
   * <p>
   * For example: For "myroot/*.html" as pattern and "myroot/myfile.html" as
   * full path, this method should return "myfile.html". The detailed
   * determination rules are specified to this PathMatcher's matching
   * strategy.
   * <p>
   * A simple implementation may return the given full path as-is in case of
   * an actual pattern, and the empty String in case of the pattern not
   * containing any dynamic parts (i.e. the <code>pattern</code> parameter
   * being a static path that wouldn't qualify as an actual {@link #isPattern
   * pattern}). A sophisticated implementation will differentiate between the
   * static parts and the dynamic parts of the given path pattern.
   * 
   * @param pattern the path pattern
   * @param path the full path to introspect
   * @return the pattern-mapped part of the given <code>path</code> (never
   *         <code>null</code>)
   */
  String extractPathWithinPattern(String pattern, String path);

  /**
   * Given a pattern and a full path, extract the URI template variables. URI
   * template variables are expressed through curly brackets ('{' and '}').
   * <p>
   * For example: For pattern "/hotels/{hotel}" and path "/hotels/1", this
   * method will return a map containing "hotel"->"1".
   * 
   * @param pattern the path pattern, possibly containing URI templates
   * @param path the full path to extract template variables from
   * @return a map, containing variable names as keys; variables values as
   *         values
   */
  Map<String, String> extractUriTemplateVariables(String pattern, String path);

  /**
   * Does the given <code>path</code> represent a pattern that can be matched
   * by an implementation of this interface?
   * <p>
   * If the return value is <code>false</code>, then the {@link #match} method
   * does not have to be used because direct equality comparisons on the
   * static path Strings will lead to the same result.
   * 
   * @param path the path String to check
   * @return <code>true</code> if the given <code>path</code> represents a
   *         pattern
   */
  boolean isPattern(String path);

  /**
   * Match the given <code>path</code> against the given <code>pattern</code>,
   * according to this PathMatcher's matching strategy.
   * 
   * @param pattern the pattern to match against
   * @param path the path String to test
   * @return <code>true</code> if the supplied <code>path</code> matched,
   *         <code>false</code> if it didn't
   */
  boolean match(String pattern, String path);

  /**
   * Match the given <code>path</code> against the corresponding part of the
   * given <code>pattern</code>, according to this PathMatcher's matching
   * strategy.
   * <p>
   * Determines whether the pattern at least matches as far as the given base
   * path goes, assuming that a full path may then match as well.
   * 
   * @param pattern the pattern to match against
   * @param path the path String to test
   * @return <code>true</code> if the supplied <code>path</code> matched,
   *         <code>false</code> if it didn't
   */
  boolean matchStart(String pattern, String path);
}