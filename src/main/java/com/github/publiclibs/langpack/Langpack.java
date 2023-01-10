/**
 *
 */
package com.github.publiclibs.langpack;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import com.github.publiclibs.langpack.exceptions.LangPackException;
import com.github.publiclibs.langpack.exceptions.NoDataException;
import com.github.publiclibs.langpack.provider.LangProviderInterface;
import com.github.publiclibs.langpack.provider.file.DefaultLangProvider;

/**
 * Предоставляет текст по ключу для определенного языка
 *
 *
 * @see #getInstance()
 *
 * @author user_dev_new
 *
 */
public final class Langpack {

	private static final Langpack instance = new Langpack();

	private static final String JVMLANG = Locale.getDefault().getLanguage();

	private static final String FALLBACK = "en";

	public static String getData(final String key) {
		return getInstance().getDataPriv(key);
	}

	public static String getData(final String key, final String lang) {

		if (key == null || key.isEmpty()) {
			throw new LangPackException(key, lang);
		}
		if (lang == null || lang.isEmpty()) {
			throw new LangPackException(key, lang);
		}

		return getInstance().getDataPriv(key, lang);
	}

	public static Langpack getInstance() {
		return instance;
	}

	private final CopyOnWriteArrayList<LangProviderInterface> providers = new CopyOnWriteArrayList<>();

	private Langpack() {
	}

	private String getDataPriv(final String key) {
		try {
			final var data = getDataPriv(key, JVMLANG);
			if (data != null) {
				return data;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return getDataPriv(key, FALLBACK);
	}

	private String getDataPriv(final String key, final String lang) {
		if (providers.isEmpty()) {
			try {
				registerPrivider(new DefaultLangProvider());
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		final var ok = providers.parallelStream().map((final var provider) -> provider.getData(key, lang))
				.filter(Optional::isPresent).map(Optional::get).findFirst();
		if (ok.isPresent()) {
			return ok.get();
		}
		if (lang.equals(FALLBACK)) {
			throw new NoDataException(key, lang);
		}
		return getDataPriv(key, FALLBACK);
	}

	public void registerPrivider(final LangProviderInterface langProvider) {
		langProvider.init();
		providers.addIfAbsent(langProvider);
	}

	public void removeAllProviders() {
		providers.clear();
	}

	public void resetData() {
		providers.parallelStream().forEachOrdered(LangProviderInterface::resetData);
	}

}
