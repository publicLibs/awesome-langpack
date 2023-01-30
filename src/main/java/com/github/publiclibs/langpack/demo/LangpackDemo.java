
package com.github.publiclibs.langpack.demo;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.github.publiclibs.langpack.Langpack;
import com.github.publiclibs.langpack.provider.file.DefaultLangProvider;
import com.github.publiclibs.langpack.provider.file.JarResourcesLangProvider;

public class LangpackDemo {
	public static void main(final String[] args) throws IOException {
		// demodata
		final Path path = Paths.get("LangPack");
		if (!Files.exists(path)) {
			Files.createFile(path);
			final StringBuilder demodata = new StringBuilder();
			demodata.append("HELLO=ru=привет").append('\n');
			demodata.append("HELLO=en=hello").append('\n');
			demodata.append("HELLO=de=Hallo").append('\n');
			Files.write(path, demodata.toString().getBytes(UTF_8), StandardOpenOption.TRUNCATE_EXISTING);
		} // demodata

		// setup
		// if any (at least one) provider is not registered, DefaultLangProvider will be
		// registered
		// see private Langpack#getDataPriv(String,String)
		// Registering your language provider
		Langpack.getInstance().registerPrivider(new JarResourcesLangProvider());
		Langpack.getInstance().registerPrivider(new DefaultLangProvider());
		// setup

		final String key = "HELLO";
		final String localJvmLangData = Langpack.getData(key);// jvm (if not exist -->fallbackLang)
		final String dataStringForExistLang = Langpack.getData(key, "de");// custom lang
		final String dataStringForNoExistLang = Langpack.getData(key, "it");// custom lang no exist -.> fallbackLang
		System.out.println(localJvmLangData);
		System.out.println(dataStringForExistLang);
		System.out.println(dataStringForNoExistLang);

		final String helpString = Langpack.getData("LANGPACK_HELP");// custom lang no exist -.> fallbackLang
		System.out.println(helpString);

		final String helpString2 = Langpack.getData("");// custom lang no exist -.> fallbackLang
		System.out.println(helpString2);

	}
}
