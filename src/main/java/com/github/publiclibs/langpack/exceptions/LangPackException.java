/**
 *
 */
package com.github.publiclibs.langpack.exceptions;

/**
 * @author user_dev_new
 *
 */
public class LangPackException extends RuntimeException {
	private static final long serialVersionUID = -9106091792148276045L;
	public final String key;
	public final String lang;

	public LangPackException(final String key, final String lang) {
		super(key + "-" + lang);
		this.key = key;
		this.lang = lang;
	}
}
