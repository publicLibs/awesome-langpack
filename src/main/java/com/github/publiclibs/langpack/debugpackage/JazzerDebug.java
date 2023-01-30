/**
 *
 */
package com.github.publiclibs.langpack.debugpackage;

import java.io.IOException;

import com.code_intelligence.jazzer.Jazzer;
import com.github.publiclibs.langpack.exceptions.input.argument.key.InvalidKeyLangPackException;
import com.github.publiclibs.langpack.exceptions.input.argument.lang.InvalidLangCodeLangPackException;

/**
 * @author user_dev_new
 *
 */
public class JazzerDebug {
	public static void main(final String[] args) throws IOException, InterruptedException {
		Jazzer.main(new String[] {

				"--autofuzz=com.github.publiclibs.langpack.Langpack::getData",

				"--keep_going=200",

				"-detect_leaks=1",

				// "--autofuzz_ignore=com.github.publiclibs.langpack.exceptions.NoDataException,com.github.publiclibs.langpack.exceptions.LangPackException,java.nio.file.InvalidPathException"
				"--autofuzz_ignore=" + InvalidKeyLangPackException.class.getName() + ","
						+ InvalidLangCodeLangPackException.class.getName()

		});

	}
}
