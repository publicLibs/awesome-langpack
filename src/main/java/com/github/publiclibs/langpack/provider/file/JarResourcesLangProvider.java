/**
 *
 */
package com.github.publiclibs.langpack.provider.file;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.github.publicLibs.freedom1b2830.awesomeio.IoUtils;
import com.github.publicLibs.freedom1b2830.awesomeio.ResourcesIoUtils;
import com.github.publiclibs.langpack.exceptions.LangPackException;
import com.github.publiclibs.langpack.exceptions.input.argument.InvalidArgumentLangPackException;
import com.github.publiclibs.langpack.exceptions.result.NoDataException;
import com.github.publiclibs.langpack.exceptions.result.NoKeyException;
import com.github.publiclibs.langpack.exceptions.result.NoLangException;
import com.github.publiclibs.langpack.provider.LangProvider;
import com.github.publiclibs.langpack.provider.ProviderUtils;

/**
 * @author user_dev_new
 *
 */
public class JarResourcesLangProvider extends LangProvider {
	private final String[] sources;
	private final ConcurrentHashMap<String, ConcurrentHashMap<String, String>> data = new ConcurrentHashMap<>();

	/**
	 *
	 */
	public JarResourcesLangProvider() {
		final String[] sources2 = new String[] { "LangPackInResourcesHelp", "LangPackInResourcesHelpFallback" };
		checkSources(sources2);
		sources = sources2;
	}

	/**
	 * Создает провайдер с вашими источниками в текущем classpath
	 *
	 * @param sources источники для поиска
	 */
	public JarResourcesLangProvider(final String[] sources) {
		checkSources(sources);
		this.sources = sources.clone();
	}

	/**
	 * Проверяет список источников на уникальность,также на то что эти источники
	 * существуют
	 *
	 *
	 * @param sourcesInput список источников
	 */
	private void checkSources(final String[] sourcesInput) throws InvalidArgumentLangPackException {
		InvalidArgumentLangPackException.checkStringArNoEmpty("sourcesInput", sourcesInput);
		InvalidArgumentLangPackException.checkStringArNoEmptyAndRetAsList("sourcesInput", sourcesInput).parallelStream()
				.forEachOrdered((final String source) -> {
					try (InputStream resource = ResourcesIoUtils.readResource(getClass(), source)) {
						// NoSuchElementException from ResourcesIoUtils.readResource
					} catch (NoSuchElementException | IOException e) {
						throw new LangPackException(e);
					}
				});
	}

	public @Override Optional<String> getData(final String key, final String lang) {
		try {
			if (!data.containsKey(key)) {
				throw new NoKeyException(key);
			}
			final ConcurrentHashMap<String, String> langs = data.get(key);
			if (langs == null || langs.isEmpty()) {
				throw new NoLangException(key, lang);
			}
			final String value = langs.get(lang);
			if (value == null) {
				throw new NoDataException(key, lang);
			}
			return Optional.of(value);
		} catch (final Exception e) {
			reportNoExist(key, lang);
		}
		return Optional.empty();
	}

	public @Override void init() {
		Arrays.stream(sources).map((final String source) -> {
			byte[] bytes;
			try (InputStream resource = ResourcesIoUtils.readResource(getClass(), source)) {
				bytes = IoUtils.isToBytes(resource);
			} catch (NoSuchElementException | IOException e) {
				throw new LangPackException(e);
			}
			return new String(bytes, UTF_8);
		}).forEachOrdered(sourcesData -> {
			final String[] datas = sourcesData.split("\n");
			for (final String line : datas) {
				ProviderUtils.parseLine(data, line);
			}
		});
	}

	public @Override void reportNoExist(final String key, final String lang) {
		// игнорируем
	}

	public @Override void resetData() {
		data.clear();
		init();
	}

}
