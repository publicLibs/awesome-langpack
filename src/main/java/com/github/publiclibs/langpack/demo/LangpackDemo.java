
package com.github.publiclibs.langpack.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.github.publiclibs.langpack.Langpack;

public class LangpackDemo {
	public static void main(final String[] args) throws IOException {
		// demodata
		final var path = Paths.get("LangPack");
		if (!Files.exists(path)) {
			Files.createFile(path);
			final var demodata = new StringBuilder();
			demodata.append("HELLO=ru=привет").append('\n');
			demodata.append("HELLO=en=hello").append('\n');
			demodata.append("HELLO=de=Hallo").append('\n');
			Files.writeString(path, demodata.toString(), StandardOpenOption.TRUNCATE_EXISTING);
		} // demodata

		final var key = "HELLO";
		final var localJvmLangData = Langpack.getData(key);// jvm (if not exist -->fallbackLang)
		final var dataStringForExistLang = Langpack.getData(key, "de");// custom lang
		final var dataStringForNoExistLang = Langpack.getData(key, "it");// custom lang no exist -.> fallbackLang
		System.out.println(localJvmLangData);
		System.out.println(dataStringForExistLang);
		System.out.println(dataStringForNoExistLang);
	}
}
