/**
 *
 */
package com.github.publiclibs.langpack.exceptions.input;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.publiclibs.langpack.exceptions.LangPackException;
import com.github.publiclibs.langpack.exceptions.input.argument.InvalidArgumentLangPackException;
import com.github.publiclibs.langpack.exceptions.input.argument.key.InvalidKeyLangPackException;
import com.github.publiclibs.langpack.exceptions.input.argument.lang.InvalidLangCodeLangPackException;

/**
 * @author freedom1b2830
 * @date 2023-января-15 09:53:16
 */
public class InputLangPackException extends LangPackException {
	private static final long serialVersionUID = -4618210794069517887L;

	public static void checkString(final String argName, final String argument) {
		if (argument == null || argument.isEmpty()) {
			if (argName.equalsIgnoreCase("key")) {
				throw new InvalidKeyLangPackException(new String[] { "key" });
			} else if (argName.equalsIgnoreCase("lang")) {
				throw new InvalidLangCodeLangPackException(new String[] { "lang" });
			} else {
				throw new InvalidLangCodeLangPackException(new String[] { "WTTTF" });
			}
		}
		if (!argument.matches("[a-zA-Z_0-9]{2,}")) {
			if (argName.equalsIgnoreCase("key")) {
				throw new InvalidKeyLangPackException(new String[] { "key" });
			}
			if (argName.equalsIgnoreCase("lang")) {
				throw new InvalidLangCodeLangPackException(new String[] { "lang" });
			}
		}
		for (final char charTest : argument.toCharArray()) {
			if (charTest == '\0') {
				throw new NullContainsArgumentLangException(new String[] { argName });
			}
		}
	}

	public static void checkStringArNoEmpty(final String argName, final String[] target) {
		if (target == null || target.length == 0) {
			throw new InvalidArgumentLangPackException(new String[] { argName });
		}
		for (int i = 0; i < target.length; i++) {
			final String source = target[i];
			if (source == null) {
				throw new InvalidArgumentLangPackException(new String[] {
						String.format("in %s element with index %s is null", argName, Integer.valueOf(i)) });
			}
		}
	}

	public static CopyOnWriteArrayList<String> checkStringArNoEmptyAndRetAsList(final String argName,
			final String[] target) {
		checkStringArNoEmpty(argName, target);
		final CopyOnWriteArrayList<String> tmpList = new CopyOnWriteArrayList<>();
		Arrays.asList(target).forEach((final String source) -> {
			if (!tmpList.addIfAbsent(source)) {
				throw new LangPackException(String.format("source %s already exist", source));
			}
		});
		return tmpList;
	}

	/**
	 *
	 * @param argumentsName список проблемных аргументов
	 * @return Invalid Arguments: [argumentsName[0], ... , argumentsName[n]]
	 */
	public static String genNameMessage(final String[] argumentsName) {
		final StringBuilder builder = new StringBuilder();
		final Iterator<String> nameIterator = Arrays.asList(argumentsName).iterator();
		builder.append("Invalid Argument");
		if (argumentsName.length > 0) {
			builder.append("s");
		}
		builder.append(": [");
		while (nameIterator.hasNext()) {
			final String name = nameIterator.next();
			builder.append(name);
			if (nameIterator.hasNext()) {
				builder.append(", ");
			}
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @param e
	 */
	public InputLangPackException(final Exception e) {
		super(e);
	}

	/**
	 * @param message
	 */
	public InputLangPackException(final String message) {
		super(message);
	}

	public InputLangPackException(final String key, final String lang) {
		super(key, lang);
	}
}
