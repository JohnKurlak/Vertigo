import java.util.Stack;

/**
 * This class parses Postfix expressions.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class ExpressionParser
{
	/**
	 * This method evaluates a parameterized function of t.
	 * 
	 * @param function		The function to evaluate
	 * @param t				The parameter t
	 * @return				Returns the function evaluated at t
	 */
	public static int evaluate(String function, int t)
	{
		Stack<Double> expression = new Stack<Double>();
		String[] parts = function.split(",");
		
		for (String element : parts)
		{
			// Add each part of the Postfix expression onto the stack
			if (!element.equals("+") && !element.equals("-") && !element
				.equals("*") && !element.equals("/") && !element.equals("^") &&
				!element.equals("cos") && !element.equals("sin") && !element
				.equals("log"))
			{
				expression.push(ExpressionParser.fix(element, t));
			}
			// Evaluate the logarithm
			else if (element.equals("log"))
			{
				double number = expression.pop();
				
				expression.push(Math.log(number));
			}
			// Evaluate the cosine or sine
			else if (element.equals("cos") || element.equals("sin"))
			{
				double number = expression.pop();
				
				if (element.equals("cos"))
				{
					expression.push(Math.cos(Math.toRadians(number)));
				}
				else
				{
					expression.push(Math.sin(Math.toRadians(number)));
				}
			}
			// Evaluate an arithmetic operation
			else
			{
				double secondValue = expression.pop();
				double firstValue = expression.pop();
				
				if (element.equals("+"))
				{				
					expression.push(firstValue + secondValue);
				}
				else if (element.equals("*"))
				{				
					expression.push(firstValue * secondValue);
				}
				else if (element.equals("^"))
				{				
					expression.push(Math.pow(firstValue, secondValue));
				}
				else if (element.equals("-"))
				{				
					expression.push(firstValue - secondValue);
				}
				else if (element.equals("/"))
				{				
					expression.push(firstValue / secondValue);
				}
			}
		}
		
		return (int) expression.pop().doubleValue();
	}
	
	/**
	 * Replace constants with their equivalents.
	 * 
	 * @param value		The expression to fix
	 * @param t			The value of the parameter, t
	 * @return			Returns the expression in terms of numbers and not
	 * 					constants
	 */
	public static double fix(String value, double t)
	{
		String val = value;
		val = val.replace("t", t + "");
		val = val.replace("e", Math.E + "");
		val = val.replace("pi", Math.PI + "");
		
		return Double.parseDouble(val);
	}
}