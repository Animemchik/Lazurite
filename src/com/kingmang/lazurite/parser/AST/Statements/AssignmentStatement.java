package com.kingmang.lazurite.parser.AST.Statements;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.parser.AST.Expressions.Expression;
import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Lzr.LzrString;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

public final class AssignmentStatement implements Statement {

    private final String variable;
    private final Expression expression;
    private final int mode;

    public AssignmentStatement(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
        this.mode = 0;
    }
    public AssignmentStatement(String variable, Expression expression, int mode) {
        this.variable = variable;
        this.expression = expression;
        this.mode = mode;
    }

    @Override
    public void execute() {
        if (mode == 0){
            final Value result = expression.eval();
            Variables.set(variable, result);
        }
        else {
            try{
                // Is number
                Integer.parseInt(Variables.get(variable).asString());
                // =========
                if (mode == 1){
                    final Value result = expression.eval();
                    Variables.set(variable, LzrNumber.of(Variables.get(variable).asInt() + result.asInt()));
                }
                else if(mode == 2){
                    final Value result = expression.eval();
                    Variables.set(variable, LzrNumber.of(Variables.get(variable).asInt() - result.asInt()));
                }
                else if(mode == 3){
                    final Value result = expression.eval();
                    Variables.set(variable, LzrNumber.of(Variables.get(variable).asInt() * result.asInt()));
                }
                else if(mode == 4){
                    final Value result = expression.eval();
                    Variables.set(variable, LzrNumber.of(Variables.get(variable).asInt() / result.asInt()));
                }
            }
            catch (Exception ex){
                if (mode == 1){
                    final Value result = expression.eval();
                    Variables.set(variable, new LzrString(Variables.get(variable).toString() + result.toString()));
                }
                else{
                    throw new LZRException("TypeError", "non-applicable operation to string");
                }
            }
        }
    }

    public String getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }

    public int getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variable, expression);
    }

    @Override
    public void accept(Visitor visitor) {

    }

    @Override
    public <R, T> R accept(ResultVisitor<R, T> visitor, T input) {
        return null;
    }
}