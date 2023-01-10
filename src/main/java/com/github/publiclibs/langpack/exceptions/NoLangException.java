/**
 *
 */
package com.github.publiclibs.langpack.exceptions;

/**
 * @author user_dev_new
 *
 */
public class NoLangException extends LangPackException {

	private static final long serialVersionUID = 2241754058380255415L;

	/**
	 * @param key
	 * @param lang
	 */
	public NoLangException(final String key, final String lang) {
		super(key, lang);
	}

}
