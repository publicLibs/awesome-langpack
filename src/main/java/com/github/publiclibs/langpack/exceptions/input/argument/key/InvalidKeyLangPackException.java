/**
 *
 */
package com.github.publiclibs.langpack.exceptions.input.argument.key;

import com.github.publiclibs.langpack.exceptions.input.argument.InvalidArgumentLangPackException;

/**
 * @author freedom1b2830
 * @date 2023-января-15 10:11:30
 */
public class InvalidKeyLangPackException extends InvalidArgumentLangPackException {
	public static void checkKey(final String key) {
		try {
			checkString("key", key);
		} catch (final Exception e) {
			throw new InvalidKeyLangPackException(e);
		}
	}

	/**
	 * @param e
	 */
	public InvalidKeyLangPackException(final Exception e) {
		super(e);
	}

	public InvalidKeyLangPackException(final String[] argumentsName) {
		super(argumentsName);
	}

}
