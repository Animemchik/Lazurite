package com.kingmang.lazurite.libraries.gforms;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.Lzr.LzrString;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.*;

public class Dialog implements Function {

    @Override
    public Value execute(Value... args) {
        final String v = JOptionPane.showInputDialog(args[0].asString());
        return new LzrString(v == null ? "0" : v);
    }
}
