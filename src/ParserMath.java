public class ParserMath
{
    private String exp;
    private int position = 0;


    public double parse(String exp)
    {
        this.exp = exp;
        position = 0;
        double result = expression(term(pow (factor())));
        return result;
    }

    private double factor()
    {
        char charExp = getChar();
        int min = 0;

        if(Character.isDigit(charExp) || (charExp == '-' && min == 0))

        {
            return getValue(charExp);

        }
        min++;
        if (charExp == '(')
        {
            ParserMath p = new ParserMath();
            return p.parse(getExpression());
        }
        return 0;
    }

    private double pow (double left)
    {
        char ch = getChar();

        if (ch != '^')
        {
            position --;
            return left;
        }
        double right = factor();
        return Math.pow(left, right);
    }

    private  double term (double left)
    {
        char ch = getChar();
        if (ch != '*' && ch != '/')
        {
            position --;
            return left;
        }
        double right = pow(factor());
        if (ch == '*')
        {
            return term(left * right);
        }
        if (right == 0)
        {
            System.err.println("Error");
            return 0;
        }
        return term(left / right);
    }

    private double expression (double left)
    {
        char ch = getChar();
        if (ch != '+' && ch != '-')
        {
            position --;
            return left;
        }
        double right  = term(pow (factor()));
        if (ch == '+')
        {
            return expression(left+right);
        }

        return expression(left - right);

    }

    //Âîçâðàùàåò âûðàæåíèå äî çàêðûâàþùåéñÿ ñêîáêè

    private String getExpression()
    {
        String charStringNew = "";
        int opened = 0;
        while (true)
        {
            char charExp = getChar();
            if (charExp == '(')
            {
                opened++;
            }
            if (charExp == ')')
            {
                if (opened !=0)
                    opened--;

                else
                    break;
            }
            charStringNew += Character.toString(charExp);
        }

        return charStringNew;

    }

    //âîçâðàùàåì ÷èñëà, íà÷èíàþùåãîñÿ ñ ch
    private double getValue(char charExp)
    {
        String charString;
        charString = Character.toString(charExp);

        while (true)
        {
            charExp = getChar();
            if (Character.isDigit(charExp) || charExp == '.')
            {
                charString += Character.toString(charExp);
            } else
            {
                break;
            }
        }
        position--;
        return Double.parseDouble(charString);
    }



    //âîçâðàò ñëåäóþùåãî ñèìâîëà
    private char getChar()
    {
        if (position<exp.length()) {

            char charExp = exp.charAt(position);
            position++;
            return charExp;
        }
        return '\0';
    }
}