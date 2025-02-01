//Name: Caden-Gamer14 
//Date: 02/01/2025 
//File: CalculatorV2.java 

//Adds java imports 
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*; 
import java.util.Stack; 

//Creates the class CalculatorV2 
public class CalculatorV2 { 
   
   //Creates the necessary components of a Swing GUI with JFrame, JTextField, and JPanel 
   private JFrame frame; 
   private JTextField textField; 
   JPanel panel; 
   
   //The main method 
   public static void main(String[] args) { 
   
      EventQueue.invokeLater( 
         () -> {
            try { 
            
               CalculatorV2 window = new CalculatorV2();
               window.frame.setVisible(true); 
               
            } catch (Exception e) { 
            
               e.printStackTrace(); 
               
            } 
         }); 
   } 
   
   //Creates a constructor in order to run the initialize method 
   public CalculatorV2() { 
   
      initialize(); 
      
   }
   
   //Creates the initialize method to run the Swing GUI of my own customization 
   private void initialize() { 
   
      frame = new JFrame(); 
      frame.setBounds(100, 100, 550, 700); 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      frame.getContentPane().setLayout(null); 
   
      textField = new JTextField(); 
      textField.setEditable(false); 
      textField.setBounds(44, 11, 446, 76); 
      frame.getContentPane().add(textField); 
      textField.setColumns(10); 
      
      panel = new JPanel(); 
      panel.setBounds(50, 100, 436, 300); 
      panel.setLayout(new GridLayout(4, 4, 10, 10)); 
      
      //Runs the createButtons() method in order to create the buttons on the calculator GUI 
      createButtons();
   }
   
   //Creates the createButtons method so that the buttons on the calculator GUI are placed in the correct order 
   private void createButtons() { 
   
      String[] buttonLabelVar = { 
      
         "C", "(-)", ".", "/", 
         "7", "8", "9", "*", 
         "4", "5", "6", "-", 
         "1", "2", "3", "+", 
         "0", "(", ")", "=", 
         "sin", "cos", "tan", "ln" 
         
         }; 
      
      //Creates the variables xPositionVar, yPositionVar, widthVar, and heightVar of type int in order to create the necessary dimensions of each button 
      int xPositionVar = 72, yPositionVar = 123, widthVar = 89, heightVar = 70;
      
      //Creates a for loop in order to create each button with the correct clicking functionality 
      for (String labelVar : buttonLabelVar) { 
      
         JButton buttonVar = new JButton(labelVar); 
         
         buttonVar.setBounds(xPositionVar, yPositionVar, widthVar, heightVar); 
         
         buttonVar.addActionListener(new ButtonClickListener()); 
         
         frame.getContentPane().add(buttonVar); 
         
         //Increments the widthVar variable 
         xPositionVar += widthVar; 
         
         //Creates an if statement to move to the next row of the calculator 
         if (xPositionVar > 369) { 
         
            xPositionVar = 72; 
            
            yPositionVar += heightVar; 
            
         } 
      } 
   } 
   
   //Creates the ButtonClickListener method which is private and implements the ActionListener 
   private class ButtonClickListener implements ActionListener { 
   
      @Override 
      public void actionPerformed(ActionEvent e) { 
         
         //Creates the variable actionVar of type String to take the necessary commands 
         String actionVar = e.getActionCommand(); 
         
         //Creates a switch statement in order to perform certain comamnds 
         switch (actionVar) { 
            
            //Uses the textField.setText to assign the calculator to nothing when the C button is selected 
            case "C": 
               textField.setText(""); 
               break; 
            
            //Uses the calculateResultFunction method to find the final result that the user inserts 
            case "=": 
               calculateResultFunction(); 
               break; 
            
            //Uses the handleNegativeSign method to perform calculations with the "(-)" attached to it 
            case "(-)": 
               handleNegativeSign(); 
               break; 
            
            //Sin, cos, and tan are intentionally left as nothing here (they are implemented further in the code) 
            case "sin": 
            
            case "cos": 
            
            case "tan": 
            
            //Uses a combination of try catch and if else if else statements in order to perform ln, using the sin, cos, and tan methods to find the correct output when a user inserts ln 
            case "ln": 
            
               try { 
                  
                  //Creates the variable inputVar of type double in order to receive the correct input 
                  double inputVar = lookThroughExpression(textField.getText()); 
                  
                  //Uses an if statement to apply the correct sin calculations 
                  if (actionVar.equals("sin")) { 
                  
                     textField.setText(String.valueOf(Math.sin(inputVar))); 
                  
                  //Uses an else if statement to apply the correct cos calculations 
                  } else if (actionVar.equals("cos")) { 
                  
                     textField.setText(String.valueOf(Math.cos(inputVar))); 
                  
                  //Uses an else if statement to apply the correct tan calculations 
                  } else if (actionVar.equals("tan")) { 
                  
                     textField.setText(String.valueOf(Math.tan(inputVar))); 
                  
                  //Finally performs the ln function 
                  } else if (actionVar.equals("ln")) { 
                  
                     if (inputVar > 0) { 
                     
                        textField.setText(String.valueOf(Math.log(inputVar))); 
                        
                     } else { 
                     
                        textField.setText("Error d00d!"); 
                        
                     } 
                  } 
               
               //Uses a catch statement in case of an error    
               } catch (Exception ex) { 
               
                  textField.setText("Error d00d!"); 
                  
               } 
               
               break; 
            
            //Adds a default case 
            default: 
               textField.setText(textField.getText() + actionVar); 
               break; 
         }
      }
   }
   
   //Creates the calculateResultFunction method in order to find the correct input based on the user input 
   private void calculateResultFunction() { 
      
      //Uses a try catch statement to find the result 
      try { 
      
         String userInputExpressionVar = textField.getText(); 
         
         //Creates the variable resultVar of type double and assigns it to the lookThroughExpression method 
         double resultVar = lookThroughExpression(userInputExpressionVar); 
         
         textField.setText(String.valueOf(resultVar)); 
         
      } catch (Exception e) { 
      
         textField.setText("Error d00d!"); 
         
      }
   }
   
   //Creates the lookThroughExpression method of type double, throwing an exception and returning the evaluatePostFix method 
   private double lookThroughExpression(String expression) throws Exception { 
   
      return lookThroughPostfix(convertToPostfix(tokenizeValues(expression))); 
      
   } 

   //Creates the tokenizeValues method of type String[] in order to input the correct expression with the correct operators as well 
   private String[] tokenizeValues(String userInputExpressionVar) { 
      
      //Assigns userInputExpressionVar to the replaceAll function for necessary user input 
      userInputExpressionVar = userInputExpressionVar.replaceAll("(?<![\\d\\.])-(?=[\\d])", " -"); 
      
      //Uses the replaceAll method in order for the negative symbol to be used properly in the calculator 
      userInputExpressionVar = userInputExpressionVar.replaceAll("(?<=\\d)-", " -"); 
      
      //Returns the userInputExpressionVar 
      return userInputExpressionVar.replaceAll("\\s+", "").split("(?<=[-+*/()])|(?=[-+*/()])"); 
      
   } 

   //Creates the convertToPostfix method of type String[] in order to convert the infix expression to postfix 
   private String[] convertToPostfix(String[] tokensVar) throws Exception { 
      
      //Creates a new stack object called operatorStackVar
      Stack<String> operatorStackVar = new Stack<>(); 
      
      //Creates a new StringBuilder object called outputVar
      StringBuilder outputVar = new StringBuilder(); 
      
      //Creates a for loop in order to go through each token for correct placement 
      for (String token : tokensVar) { 
         
         //Creates an if statement in order to append the tokens correctly 
         if (isValidNumber(token)) { 
         
            outputVar.append(token).append(" "); 
         
         //Creates an else if statement in order for the correct operations to work with the ( parenthesis 
         } else if (token.equals("(")) { 
         
            operatorStackVar.push(token); 
         
         //Creates an else if statement in order for the correct operations to work with the ) parenthesis    
         } else if (token.equals(")")) { 
         
            while (!operatorStackVar.isEmpty() && !operatorStackVar.peek().equals("(")) { 
            
               outputVar.append(operatorStackVar.pop()).append(" "); 
               
            } 
            
            operatorStackVar.pop(); 
         
         //Creates an else statement in order to append the results together 
         } else { 
         
            while (!operatorStackVar.isEmpty() && pemdasOrderOfOperations(token) <= pemdasOrderOfOperations(operatorStackVar.peek())) { 
            
               outputVar.append(operatorStackVar.pop()).append(" "); 
               
            } 
            
            //Pushes the output 
            operatorStackVar.push(token); 
            
         } 
      } 
      
      //Creates a while loop in order to pop the last operators from the entire stack 
      while (!operatorStackVar.isEmpty()) { 
      
         outputVar.append(operatorStackVar.pop()).append(" "); 
         
      } 
      
      //Returns the output result 
      return outputVar.toString().trim().split(" "); 
      
   } 

   //Creates the lookThroughPostfix method in order to fix the postFix when it makes the changes 
   private double lookThroughPostfix(String[] tokensVar) throws Exception { 
      
      //Creates a new Stack object called valueStack 
      Stack<Double> valueStack = new Stack<>(); 
      
      //Creates a for loop in order to perform correct evaluations 
      for (String token : tokensVar) { 
         
         //Creates an if statement in order to see if the number inserted is a valid token 
         if (isValidNumber(token)) { 
         
            valueStack.push(Double.parseDouble(token)); 
         
         //Creates an else statement in order to see if the stack is empty or not 
         } else { 
            
            double b = valueStack.pop(); 
            
            double a = valueStack.isEmpty() ? 0 : valueStack.pop(); 
            
            valueStack.push(correctOperation(a, b, token)); 
            
         } 
      } 
      
      //Returns the valueStack variable 
      return valueStack.pop(); 
      
   }
   
   //Creates the isValidNumber method of type boolean in order to see if the String inserted is indeed a number 
   private boolean isValidNumber(String token) { 
      
      //Creates a try statement for further evaluations 
      try { 
      
         Double.parseDouble(token); 
         
         return true; 
      
      //Catches the exception 
      } catch (NumberFormatException e) { 
      
         return false; 
         
      } 
   } 

   //Creates the pemdasOrderOfOperations method in order to return the correct pemdasOrderOfOperations of operators the user inserts 
   private int pemdasOrderOfOperations(String operator) { 
      
      //Creates a switch statement for the operator 
      switch (operator) { 
      
         case "+": case "-": 
         
            return 1; 
            
         case "*": case "/": 
         
            return 2; 
         
         //Creates a default case as well 
         default: 
         
            return 0; 
            
      }
   }
   
   //Creates the correctOperation method of type double in order to correctly apply the operation of the operands inserted 
   private double correctOperation(double a, double b, String operator) throws Exception { 
      
      //Creates a switch statement for the operator 
      switch (operator) { 
         
         //Returns correct outputs for addition 
         case "+": 
         
            return a + b; 
         
         //Returns correct outputs for subtraction 
         case "-": 
         
            return a - b; 
         
         //Returns correct outputs for multiplication 
         case "*": 
         
            return a * b; 
         
         //Returns correct outputs for division 
         case "/": 
         
            if (b == 0) { 
            
               throw new Exception("Division by zero"); 
               
            } 
            
            return a / b; 
         
         //Creates a default case 
         default: 
         
            throw new Exception("Error d00d!"); 
            
      } 
   } 
   
   //Creates the handleNegativeSign method in order to help with the "(-)" symbol expressions 
   private void handleNegativeSign() { 
   
      String text = textField.getText(); 
      
      //Creates an if statement to determine if the textField is empty or not and to set it to "(-)" 
      if (text.isEmpty()) { 
         
         textField.setText("-"); 
      
      //Creates an else if statement in order to see if there is a negative and a negative to make it positive 
      } else if (text.startsWith("-")) { 
         
         textField.setText(text.substring(1)); 
      
      //Creates an else statement in order to make the inserted number negative 
      } else { 
         
         textField.setText("-" + text); 
         
      } 
   } 
} 