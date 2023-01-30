package com.github.publiclibs.langpack.exceptions.result;

public class NoKeyException extends RuntimeException {

	private static final long serialVersionUID = -6028575506563811892L;

	public NoKeyException() {
		super();
	}

	/**
	 * @param message
	 */
	public NoKeyException(final String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoKeyException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoKeyException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param cause
	 */
	public NoKeyException(final Throwable cause) {
		super(cause);
	}

}
