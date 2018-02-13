package com.ks.util;

import java.util.List;
import java.util.logging.Level;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.impl.InputElement;

public class UIUtilities {
	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("");

	public static boolean check(Component component) {
		boolean valid = true;

		if (!checkIsValid(component))
			valid = false;

		List<Component> children = component.getChildren();
		for (Component each : children) {
			if (!check(each))
				valid = false;
		}
		return valid;
	}

	private static boolean checkIsValid(Component component) {
		boolean valid = true;

		if (component instanceof InputElement) {
			if (!((InputElement) component).isValid()) {
				String text = "";
				// Force show errorMessage
				// ((InputElement) component).focus();
				try {
					text = ((InputElement) component).getText();
				} catch (WrongValueException e) {
					valid = false;
					logger.log(Level.FINER, e.getMessage(), e);
				}

			}
		}
		return valid;
	}

}
