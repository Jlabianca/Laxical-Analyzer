package LaxicalAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class laxicalAnalyzer
{
    public static final int LETTER=0;
    public static final int DIGIT=1;
    public static final int UNKNOWN=99;
    public static final int INT_LIT=10;
    public static final int IDENT=11;
    public static final int ASSIGN_OP=20;
    public static final int ADD_OP=21;
    public static final int SUB_OP=22;
    public static final int MULT_OP=23;
    public static final int DIV_OP=24;
    public static final int LEFT_PAREN=25;
    public static final int RIGHT_PAREN=26;

    public static List<String> lexemes=new ArrayList<String>();
    public static List<Integer> tokens=new ArrayList<Integer>();
    public static int nextToken=0;
    public static int index=0;

    /**
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        try {
            File bookData = new File("data.txt");
            Scanner readFile = new Scanner(bookData);
            while (readFile.hasNextLine()) {
                String information = readFile.nextLine();
                laxicalAnalyzer(information);
                System.out.print("Next token is: "+tokens.get(0));
                System.out.println("\tNext lexeme is: "+lexemes.get(0));
                nextToken=tokens.get(0);
                expr();
            }
            readFile.close();
            System.out.println("Testing finished for Lexical Analyzer.\n");
        } catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("File not present!!");
        }
    }
    /**
     *
     * @param inputExpression
     */
    public static void laxicalAnalyzer(String inputExpression )
    {

        String letter="";
        String DIGIT="";
        for(int i=0; i<inputExpression.length(); i++)
        {
            while( (inputExpression.charAt(i)>= 'a' &&inputExpression.charAt(i) <= 'z') || (inputExpression.charAt(i) >= 'A' && inputExpression.charAt(i) <= 'Z'))
            {
                letter=letter+inputExpression.charAt(i);
                i=i+1;
                if(i==inputExpression.length())
                    break;
            }
            if(!letter.isEmpty())
            {
                lexemes.add(letter);
                tokens.add(IDENT);
                letter="";
            }
            if(i==inputExpression.length())
                break;
            Boolean flag = Character.isDigit(inputExpression.charAt(i));
            while(flag)
            {
                DIGIT=DIGIT+inputExpression.charAt(i);
                i=i+1;

                if(i==inputExpression.length())
                    break;
                flag = Character.isDigit(inputExpression.charAt(i));
            }
            if( !DIGIT.isEmpty())
            {
                lexemes.add(DIGIT);
                tokens.add(INT_LIT);
                DIGIT="";
            }
            if(inputExpression.charAt(i)=='+')
            {
                String add="";
                add=add+inputExpression.charAt(i);
                lexemes.add(add);
                tokens.add(ADD_OP);
            }
            else if(inputExpression.charAt(i)=='-')
            {
                String sub="";
                sub=sub+inputExpression.charAt(i);
                lexemes.add(sub);
                tokens.add(ADD_OP);
            }
            else if (inputExpression.charAt(i)=='*')
            {
                String Mul="";
                Mul=Mul+inputExpression.charAt(i);
                lexemes.add(Mul);
                tokens.add(MULT_OP);
            }
            else if(inputExpression.charAt(i)=='/')
            {
                String DIV="";
                DIV=DIV+inputExpression.charAt(i);
                lexemes.add(DIV);
                tokens.add(DIV_OP);

            }
            else if(inputExpression.charAt(i)=='(')
            {
                String leftParen="";
                leftParen=leftParen+inputExpression.charAt(i);
                lexemes.add(leftParen);
                tokens.add(LEFT_PAREN);
            }
            else if(inputExpression.charAt(i)==')')
            {
                String rightParen="";
                rightParen=rightParen+inputExpression.charAt(i);
                lexemes.add(rightParen);
                tokens.add(RIGHT_PAREN);
            }
        }
        lexemes.add("EOF");
        tokens.add(-1);
    }

    /**
     *  looking for the expr
     */
    public static void expr()
    {
        System.out.println("Enter <expr>");
        term();
        while (nextToken == ADD_OP || nextToken == SUB_OP)
        {
            lex();
            term();
        }
        System.out.println("Exit <expr>");
    }

    /**
     * looking for the term
     */
    public static void term()
    {
        System.out.println("Enter <term>");
        factor();
        while (nextToken == MULT_OP || nextToken == DIV_OP)
        {
            lex();
            factor();
        }
        System.out.println("Exit <term>");
    }

    /**
     * looking for the factor
     */
    public static void factor()
    {
        System.out.println("Enter <factor>");

        if (nextToken == IDENT || nextToken == INT_LIT)
            lex();
        else
        {
            if (nextToken == LEFT_PAREN)
            {
                lex();
                expr();
                if (nextToken == RIGHT_PAREN)
                    lex();
                else
                    error();
            }
            else
                error();
        }
        System.out.println("Exit <factor>");
    }
    /**
     *  getting next token
     */
    public static void lex()
    {
        if(index<tokens.size()-1)
        {
            index=index+1;
            System.out.print("Next token is: "+tokens.get(index));
            System.out.println("\tNext lexeme is: "+lexemes.get(index));
            nextToken=tokens.get(index);
        }
    }
    /**
     *  show syntax error
     */
    public static void error()
    {
        System.out.println("There is syntax error");
    }

}