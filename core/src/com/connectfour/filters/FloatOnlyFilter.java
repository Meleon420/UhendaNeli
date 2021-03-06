package com.connectfour.filters;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class FloatOnlyFilter implements TextField.TextFieldFilter {

    private final char[] accepted;

    public FloatOnlyFilter() {
        accepted = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    }

    @Override
    public boolean acceptChar(TextField textField, char c) {
        for (char a : accepted)
            if (a == c) return true;
        return false;
    }
}
