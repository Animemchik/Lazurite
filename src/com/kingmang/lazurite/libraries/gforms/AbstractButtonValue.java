package com.kingmang.lazurite.libraries.gforms;


import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.Lzr.LzrFunction;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.utils.ValueUtils;

import javax.swing.AbstractButton;

public class AbstractButtonValue extends JComponentValue {

    final AbstractButton abstractButton;

    public AbstractButtonValue(int functionsCount, AbstractButton abstractButton) {
        super(functionsCount + 2, abstractButton);
        this.abstractButton = abstractButton;
        init();
    }

    private void init() {
        set("onClick", new LzrFunction(this::addActionListener));
        set("addActionListener", new LzrFunction(this::addActionListener));
        set("onChange", new LzrFunction(this::addChangeListener));
        set("addChangeListener", new LzrFunction(this::addChangeListener));
        set("doClick", Converters.intOptToVoid(abstractButton::doClick, abstractButton::doClick));
        set("getActionCommand", Converters.voidToString(abstractButton::getActionCommand));
        set("getDisplayedMnemonicIndex", Converters.voidToInt(abstractButton::getDisplayedMnemonicIndex));
        set("getHideActionText", Converters.voidToBoolean(abstractButton::getHideActionText));
        set("getHorizontalAlignment", Converters.voidToInt(abstractButton::getHorizontalAlignment));
        set("getHorizontalTextPosition", Converters.voidToInt(abstractButton::getHorizontalTextPosition));
        set("getIconTextGap", Converters.voidToInt(abstractButton::getIconTextGap));
        set("getText", Converters.voidToString(abstractButton::getText));
        set("getVerticalAlignment", Converters.voidToInt(abstractButton::getVerticalAlignment));
        set("getVerticalTextPosition", Converters.voidToInt(abstractButton::getVerticalTextPosition));
        set("isSelected", Converters.voidToBoolean(abstractButton::isSelected));
        set("setActionCommand", Converters.stringToVoid(abstractButton::setActionCommand));
        set("setHorizontalAlignment", Converters.intToVoid(abstractButton::setHorizontalAlignment));
        set("setHorizontalTextPosition", Converters.intToVoid(abstractButton::setHorizontalTextPosition));
        set("setSelected", Converters.booleanToVoid(abstractButton::setSelected));
        set("setText", Converters.stringToVoid(abstractButton::setText));
        set("setVerticalAlignment", Converters.intToVoid(abstractButton::setVerticalAlignment));
        set("setVerticalTextPosition", Converters.intToVoid(abstractButton::setVerticalTextPosition));
    }

    private Value addActionListener(Value[] args) {
        Arguments.check(1, args.length);
        final Function action = com.kingmang.lazurite.utils.ValueUtils.consumeFunction(args[0], 0);
        abstractButton.addActionListener(e -> action.execute());
        return LzrNumber.ZERO;
    }
    
    private Value addChangeListener(Value[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        abstractButton.addChangeListener(e -> action.execute());
        return LzrNumber.ZERO;
    }
}