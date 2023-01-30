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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.github.publiclibs.langpack.exceptions.LangPackException;
import com.github.publiclibs.langpack.exceptions.input.InputLangPackException;
import com.github.publiclibs.langpack.exceptions.input.NullContainsArgumentLangException;
import com.github.publiclibs.langpack.exceptions.input.argument.InvalidArgumentLangPackException;
import com.github.publiclibs.langpack.exceptions.result.NoDataException;
import com.github.publiclibs.langpack.exceptions.result.NoKeyException;
import com.github.publiclibs.langpack.exceptions.result.NoLangException;
import com.github.publiclibs.langpack.provider.LangProvider;
import com.github.publiclibs.langpack.provider.ProviderUtils;

/**
 *
 * @author freedom1b2830
 * @date 2023-января-12 03:18:21
 */
public class DefaultLangProvider extends LangProvider {

	public static final Path DEFAULTPROVIDER_PATH = Paths.get("defaultLangProviderDir");

	/**
	 *
	 * @param path
	 * @throws IOException
	 */
	private static void fixFile(Path path) throws IOException {
		InputLangPackException.checkString("path", path.toString());
		path = path.normalize();

		if (!Files.exists(path)) {
			final Path parentFile = path.getParent();
			if (parentFile != null) {
				Files.createDirectories(parentFile);
			}
			Files.createFile(path);
		}
	}

	public static String sanitizeFilePath(final String name) {
		if (name == null || name.isEmpty()) {
			throw new InvalidArgumentLangPackException(new String[] { "name" });
		}

		if (name.contains(File.separator)) {
			throw new LangPackException("#FILENAME-path-trav-separator", name);
		}
		if (name.contains("..")) {
			throw new LangPackException("#FILENAME-path-trav-dot", name);
		}

		for (final char symbol : name.toCharArray()) {
			if (symbol == '\u0000') {
				throw new NullContainsArgumentLangException(new String[] { "name" });
			}
			if (!Character.toString(symbol).matches("[-.a-zA-Z_0-9]+")) {
				throw new InvalidArgumentLangPackException(new String[] { "name" });
			}
		}
		return name;
	}

	// key->lang:value
	private final ConcurrentHashMap<String, ConcurrentHashMap<String, String>> data = new ConcurrentHashMap<>();

	private final Path inputFile;

	private final Path reportFile = Paths.get("LangPack-report.txt");

	public DefaultLangProvider() throws IOException {
		inputFile = Paths.get(sanitizeFilePath("LangPack"));
		fixFile(inputFile);
	}

	public DefaultLangProvider(String fileNameInDir) throws IOException {
		fileNameInDir = sanitizeFilePath(fileNameInDir);

		inputFile = Paths.get(DEFAULTPROVIDER_PATH.toString(), fileNameInDir);
		fixFile(inputFile);
	}

	public @Override boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DefaultLangProvider other = (DefaultLangProvider) obj;
		return Objects.equals(inputFile, other.inputFile);
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

	@Override
	public int hashCode() {
		return Objects.hash(inputFile);
	}

	public @Override void init() {
		try {
			Files.readAllLines(inputFile, StandardCharsets.UTF_8).stream()
					.filter((final String line) -> !line.isEmpty() && line.contains("="))
					.forEachOrdered((final String line) -> ProviderUtils.parseLine(data, line));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public @Override void reportNoExist(final String key, final String lang) {
		try {
			if (!Files.exists(reportFile)) {
				Files.createFile(reportFile);
			}
			final String dataMSG = String.format("%s=%s=VALUE%n", key, lang) + "\n";
			Files.write(reportFile, dataMSG.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
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
