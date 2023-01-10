/**
 *
 */
package com.github.publiclibs.langpack.provider;

import java.util.Optional;

/**
 * @author user_dev_new
 *
 */
public interface LangProviderInterface {

	Optional<String> getData(String key, String lang);

	void init();

	void reportNoExist(String key, String lang);

	void resetData();
}
