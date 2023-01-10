/**
 *
 */
package com.github.publiclibs.langpack.provider.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.github.publiclibs.langpack.exceptions.LangPackException;
import com.github.publiclibs.langpack.exceptions.NoDataException;
import com.github.publiclibs.langpack.exceptions.NoKeyException;
import com.github.publiclibs.langpack.exceptions.NoLangException;
import com.github.publiclibs.langpack.provider.LangProvider;

/**
 * @author user_dev_new
 *
 */
public class DefaultLangProvider extends LangProvider {

	public static final Path DEFAULTPROVIDER_PATH = Paths.get("defaultLangProviderDir");

	/**
	 * @param file
	 * @throws IOException
	 */
	private static void fixFile(Path path) throws IOException {

		if (path == null) {
			throw new LangPackException(null, null);
		}
		path = path.normalize();

		if (!Files.exists(path)) {
			final var parentFile = path.getParent();
			if (parentFile != null) {
				Files.createDirectories(parentFile);
			}
			Files.createFile(path);
		}
	}

	/**
	 * @param string
	 * @param line
	 */
	public static void logWrong(final String string, final String line) {
		System.err.println(String.format("wrong %s for %s", string, line));
	}

	public static String sanitizeFilePath(String name) {
		if (name == null) {
			throw new LangPackException("#FILENAME", name);
		}
		if (name.contains(File.separator)) {
			throw new LangPackException("#FILENAME", name);
		}
		name = name.replaceAll("[.]{2}", "");
		if (name.isEmpty()) {
			throw new LangPackException("#FILENAME", name);
		}
		for (final char symbol : name.toCharArray()) {
			if (symbol == '\u0000') {
				throw new LangPackException("#FILENAME null ", name);
			}
			final var symbolStr = Character.toString(symbol);
			if (!symbolStr.matches("[-.a-zA-Z_0-9]+")) {
				throw new LangPackException("#FILENAME matches ", name);
			}
		}
		return name;

	}

	// key->lang:value
	private final ConcurrentHashMap<String, ConcurrentHashMap<String, String>> data = new ConcurrentHashMap<>();

	private final Path inputFile;

	private final Path reportFile = Paths.get("LangPack-report.txt");

	public DefaultLangProvider() throws IOException {
		inputFile = Paths.get("LangPack");
		fixFile(inputFile);
	}

	public DefaultLangProvider(String fileNameInDir) throws IOException {
		fileNameInDir = sanitizeFilePath(fileNameInDir);

		inputFile = Paths.get(DEFAULTPROVIDER_PATH.toString(), fileNameInDir);
		fixFile(inputFile);
	}

	public @Override Optional<String> getData(final String key, final String lang) {
		try {
			if (!data.containsKey(key)) {
				throw new NoKeyException(key);
			}
			final var langs = data.get(key);
			if (langs == null || langs.isEmpty()) {
				throw new NoLangException(key, lang);
			}
			final var value = langs.get(lang);
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
		try {
			Files.readAllLines(inputFile, StandardCharsets.UTF_8).stream().filter(line -> {
				return !line.isEmpty() && line.contains("=");
			}).forEachOrdered((final String line) -> {
				final var pairs = line.split("=", 3);
				final var key = pairs[0];
				if (key == null || key.isEmpty()) {
					logWrong("key", line);
					return;
				}
				final var lang = pairs[1];
				if (lang == null || lang.isEmpty()) {
					logWrong("lang", line);
					return;
				}
				final var val = pairs[2];
				if (val == null || val.isEmpty()) {
					logWrong("val", line);
					return;
				}
				final var langs = data.computeIfAbsent(key, t1 -> new ConcurrentHashMap<>());
				langs.putIfAbsent(lang, val);
			});

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public @Override void reportNoExist(final String key, final String lang) {
		try {
			if (!Files.exists(reportFile)) {
				Files.createFile(reportFile);
			}
			final var dataMSG = String.format("%s=%s=VALUE%n", key, lang);
			Files.writeString(reportFile, dataMSG, StandardOpenOption.APPEND);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 */

	public @Override void resetData() {
		data.clear();
		init();
	}

}
