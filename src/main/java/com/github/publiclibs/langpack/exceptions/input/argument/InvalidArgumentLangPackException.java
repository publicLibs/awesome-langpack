/**
 *
 * Для связи со мной используйте почту freedom1b2830@gmail.com
 * Ключ pgp 4388DF6D2D19DA0BD7BB0FBBDBA96F466835877C
 * был отправлен на hkps://keyserver.ubuntu.com
 * также в https://raw.githubusercontent.com/freedom1b2830/freedom1b2830/main/data/pgp.4388DF6D2D19DA0BD7BB0FBBDBA96F466835877C.pub
 */
package com.github.publiclibs.langpack.exceptions.input.argument;

import com.github.publiclibs.langpack.exceptions.input.InputLangPackException;

/**
 * @author freedom1b2830
 * @date 2023-января-11 19:40:30
 */
public class InvalidArgumentLangPackException extends InputLangPackException {
	private static final long serialVersionUID = 5966375797017385317L;

	/**
	 * @param e
	 */
	public InvalidArgumentLangPackException(final Exception e) {
		super(e);
	}

	/**
	 *
	 * @see #genNameMessage(String[])
	 *
	 */
	public InvalidArgumentLangPackException(final String[] argumentsName) {
		super(genNameMessage(argumentsName));
	}

}
