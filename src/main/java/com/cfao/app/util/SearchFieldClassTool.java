package com.cfao.app.util;

import javafx.css.PseudoClass;
import javafx.scene.Node;

/**
 * Created by JP on 6/11/2017.
 */
public class SearchFieldClassTool {
    private static final PseudoClass CLASS_FAIL = PseudoClass.getPseudoClass("fail");

    private SearchFieldClassTool() {

    }

    public static void updateStateClass(final Node node, final boolean isFail) {
        node.pseudoClassStateChanged(CLASS_FAIL, isFail);
    }
}
