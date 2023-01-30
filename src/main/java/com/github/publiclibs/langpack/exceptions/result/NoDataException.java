/**
 *
 */
package com.github.publiclibs.langpack.exceptions.result;

import com.github.publiclibs.langpack.exceptions.LangPackException;

/**
 * @author user_dev_new
 *
 */
public class NoDataException extends LangPackException {

	private static final long serialVersionUID = 2163082881064723106L;

	/**
	 * @param key
	 * @param lang
	 */
	public NoDataException(final String key, final String lang) {
		super(key, lang);
	}

}
