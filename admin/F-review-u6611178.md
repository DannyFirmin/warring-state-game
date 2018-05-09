Reviewer: Danny Feng (u6611178)
Component: Task 5 - isMoveLegal
Author: Chi Ben (u6555078)

Review Comments:
Overview: My teammate rewrote this function using a better method. In the previous version of task5, 
we re-coded the board layout to integer and operated the placement string. For example, the author 
used for loop to find the zhangyi and locationchar in string. This is good. But when it comes to 
the "if it is the furthest character of the state" determination, it failed to give the correct result.
The potential bug was hard to locate because the code was vague, complicated and even a little bit mess.
(e.g. create and initialize a lot of variable for some reasons but haven't been used at all). 
The latest version of task 5 remains the method of finding zhangyi and locationchar in plancement string,
but implement a two-dimensional array to store the board layout. The reason why I think this method is 
better is that it is very close to the real board layout, we don't need to covert the board to the integer 
to do the calculation. Instead, just put the character onto the two-dimensional array.

Improvement Suggestion
1. WarringStatesGame.java:162-299 Use proper capitalization for all the class and method, but 
some of the capitalization of the variables are in a mess. e.g. two words variables name "nofurther"
should be "noFurther". What's more, variable "c" is not a good name, but it has a good comment to 
explain so it is ok.

2. WarringStatesGame.java:165, 182 variable b is declared but never used. It should be removed.

3. Forget to consider the situation that zhangyi move to his current location. This should return false.
I fixed this now.

4. WarringStatesGame.java:175 Didn't decomposition well. Since the two-dimensional array is heavily used
later. I put this to a separate class called board and call it in this task.