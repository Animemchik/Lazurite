package com.kingmang.lazurite.parser.AST.Expressions;

import com.kingmang.lazurite.parser.AST.Node;
import com.kingmang.lazurite.runtime.LzrValue;

public interface Expression extends Node {
    LzrValue eval();
}
