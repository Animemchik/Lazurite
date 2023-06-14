package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.runtime.FunctionValue;
import com.kingmang.lazurite.lib.Functions;


public final class DPointExpression extends InterruptableNode implements Expression {

    public final String name;

    public DPointExpression(String name) {
        this.name = name;
    }

    @Override
    public FunctionValue eval() {
        super.interruptionCheck();
        return new FunctionValue(Functions.get(name));
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T t) {
        return visitor.visit(this, t);
    }

    @Override
    public String toString() {
        return "::" + name;
    }
}