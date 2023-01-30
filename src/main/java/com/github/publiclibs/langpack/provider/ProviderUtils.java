/**
 *
 */
package com.github.publiclibs.langpack.provider;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author user_dev_new
 *
 */
public final class ProviderUtils {
	public static void logWrong(final String string, final String line) {
		System.err.println(String.format("wrong %s for [%s]", string, line));
	}

	public static void parseLine(final ConcurrentHashMap<String, ConcurrentHashMap<String, String>> storage,
			final String line) {
		if (line.trim().trim().isEmpty()) {
			logWrong("empty", line);
			return;
		}

		final String[] pairs = line.split("=", 3);
		final String key = pairs[0];
		if (key == null || key.isEmpty()) {
			logWrong("key", line);
			return;
		}
		final String lang = pairs[1];
		if (lang == null || lang.isEmpty()) {
			logWrong("lang", line);
			return;
		}
		final String val = pairs[2];
		if (val == null || val.isEmpty()) {
			logWrong("val", line);
			return;
		}
		final ConcurrentHashMap<String, String> langs = storage.computeIfAbsent(key, t1 -> new ConcurrentHashMap<>());
		langs.putIfAbsent(lang, val);
	}

	private ProviderUtils() {
	}

}
