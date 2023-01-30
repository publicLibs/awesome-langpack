/**
 *
 */
package com.github.publiclibs.langpack.exceptions.input;

/**
 * @author freedom1b2830
 * @date 2023-января-15 09:56:20
 */
public class NullContainsArgumentLangException extends InputLangPackException {
	private static final long serialVersionUID = 3705885895313100614L;

	public NullContainsArgumentLangException(final String[] argumentsName) {
		super(genNameMessage(argumentsName));
	}
}
