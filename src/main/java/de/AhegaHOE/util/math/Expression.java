package de.AhegaHOE.util.math;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class Expression {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.###", DecimalFormatSymbols.getInstance(Locale.GERMAN));

    private static final String[] TO_REPLACE = new String[]{"pi", "e", "ans"};

    private static final String[] REPLACER = new String[]{String.valueOf(Math.PI), String.valueOf(Math.E), "0"};

    private static double lastResult;

    static {
        DECIMAL_FORMAT.setMaximumFractionDigits(5);
    }

    private double result = Double.NaN;

    private String expression;

    private int pos = -1;

    private int ch;

    public Expression(String expression) {
        this.expression = expression;
    }

    public String parse() {
        if (Double.isNaN(this.result))
            evaluate();
        return DECIMAL_FORMAT.format(this.result);
    }

    public double evaluate() {
        replaceVariables();
        nextChar();
        double x = parseExpression();
        if (this.pos < this.expression.length())
            throw new ExpressionException("Unerwartetes Zeichen: " + (char) this.ch);
        this.result = x;
        lastResult = x;
        return x;
    }

    private void replaceVariables() {
        REPLACER[2] = String.valueOf(lastResult);
        this.expression = StringUtils.replaceEach(this.expression, TO_REPLACE, REPLACER);
    }

    private double parseExpression() throws ExpressionException {
        double x = parseTerm();
        while (true) {
            for (; eat(43); x += parseTerm()) ;
            if (eat(45)) {
                x -= parseTerm();
                continue;
            }
            return x;
        }
    }

    private double parseTerm() throws ExpressionException {
        double x = parseFactor();
        while (true) {
            for (; eat(42); x *= parseFactor()) ;
            if (eat(47)) {
                x /= parseFactor();
                continue;
            }
            return x;
        }
    }

    private double parseFactor() throws ExpressionException {
        double x;
        if (eat(43))
            return parseFactor();
        if (eat(45))
            return -parseFactor();
        int startPos = this.pos;
        if (eat(40)) {
            x = parseExpression();
            eat(41);
        } else {
            if ((this.ch >= 48 && this.ch <= 57) || this.ch == 46)
                while (true) {
                    if ((this.ch >= 48 && this.ch <= 57) || this.ch == 46) {
                        nextChar();
                        continue;
                    }
                    x = Double.parseDouble(this.expression.substring(startPos, this.pos));
                    if (eat(94))
                        x = Math.pow(x, parseFactor());
                    return x;
                }
            if (this.ch >= 97 && this.ch <= 122) {
                for (; this.ch >= 97 && this.ch <= 122; nextChar()) ;
                String func = this.expression.substring(startPos, this.pos);
                x = parseFactor();
                switch (func) {
                    case "sqrt":
                        x = Math.sqrt(x);
                        break;
                    case "sin":
                        x = Math.sin(Math.toRadians(x));
                        break;
                    case "cos":
                        x = Math.cos(Math.toRadians(x));
                        break;
                    case "tan":
                        x = Math.tan(Math.toRadians(x));
                        break;
                    default:
                        throw new ExpressionException("Unbekannte Funktion: " + func);
                }
            } else {
                char wrongChar;
                if (this.ch == -1) {
                    wrongChar = this.expression.charAt(this.expression.length() - 1);
                } else {
                    wrongChar = (char) this.ch;
                }
                throw new ExpressionException("Unerwartetes Zeichen: " + wrongChar);
            }
        }
        if (eat(94))
            x = Math.pow(x, parseFactor());
        return x;
    }

    private void nextChar() {
        this.ch = (++this.pos < this.expression.length()) ? this.expression.charAt(this.pos) : -1;
    }

    private boolean eat(int charToEat) {
        for (; this.ch == 32; nextChar()) ;
        if (this.ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    public static class ExpressionException extends ArithmeticException {
        ExpressionException(String message) {
            super(message);
        }
    }

}