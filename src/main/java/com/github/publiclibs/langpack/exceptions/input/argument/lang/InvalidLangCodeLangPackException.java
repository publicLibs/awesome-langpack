/**
 *
 */
package com.github.publiclibs.langpack.exceptions.input.argument.lang;

import com.github.publiclibs.langpack.exceptions.input.argument.InvalidArgumentLangPackException;

/**
 * @author freedom1b2830
 * @date 2023-января-15 10:11:57
 */
public class InvalidLangCodeLangPackException extends InvalidArgumentLangPackException {

	public InvalidLangCodeLangPackException(final String[] argumentsName) {
		super(argumentsName);
	}

}
