package de.juli.phaseten.exeption;

public class NoPermissionExeption extends RuntimeException {
	public NoPermissionExeption(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;
}
