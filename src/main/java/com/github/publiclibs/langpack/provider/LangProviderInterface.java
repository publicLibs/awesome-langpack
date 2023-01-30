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
	/**
	 * получаем данные из провайдера по ключу key и языку lang
	 *
	 * @param key  ключ
	 * @param lang язык
	 * @return значение ключ->язык
	 */
	Optional<String> getData(String key, String lang);

	void init();

	void reportNoExist(String key, String lang);

	void resetData();
}
