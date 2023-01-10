/**
 *
 */
package com.github.publiclibs.langpack.debugpackage;

import java.io.IOException;

import com.code_intelligence.jazzer.Jazzer;

/**
 * @author user_dev_new
 *
 */
public class JazzerDebug {
	public static void main(final String[] args) throws IOException, InterruptedException {

//		Jazzer.main(new String[] { "--autofuzz=com.github.publiclibs.langpack.Langpack::getData"
//
//				,
//				"--autofuzz_ignore=com.github.publiclibs.langpack.exceptions.NoDataException,com.github.publiclibs.langpack.exceptions.LangPackException"
//
//		});

		Jazzer.main(new String[] { "--autofuzz=com.github.publiclibs.langpack.Langpack::getData"

				,
				"--autofuzz_ignore=com.github.publiclibs.langpack.exceptions.NoDataException,com.github.publiclibs.langpack.exceptions.LangPackException,java.nio.file.InvalidPathException"

		});

	}
}
