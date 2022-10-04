## Code Review

### Reviewed by:<br>
Haoxiang Wang, u7544188

### Reviewing code written by:<br>
Ta-Wei Chen, u7546144

### Component:
1. [Dice Class](https://gitlab.cecs.anu.edu.au/u7546144/comp1110-ass2/-/blob/main/src/comp1110/ass2/Dice.java#L5) and [Task 6](https://gitlab.cecs.anu.edu.au/u7546144/comp1110-ass2/-/blob/main/src/comp1110/ass2/CatanDice.java#L127) in CatanDice
2. Method [Swap](https://gitlab.cecs.anu.edu.au/u7546144/comp1110-ass2/-/blob/main/src/comp1110/ass2/Knight.java#L52) in Knight class

### Comments 

Q1. What are the best features of this code?

&emsp; In Dice class, Ta-Wei used Random class to generate random results, which showed his ability to import predefined classes. Correspondingly, he instantiated this class in task 5 and called the method defined in Dice class to realize the function of task5. This needs compiler to be familiar to basic concepts of objected-oriented programming. And after regarding method Swap in Knight class, I can see that Ta-Wei has good comprehension in loop. And the only flaw I can detect at my level is that "switch" seems to be more suitable here. By the way, we can simplify the code by extracting common condition and return "switch statement" directly.

Q2. Is the code well-documented?

&emsp; Yes, the code was well-documented. We can see their function basic logic quickly after checking JavaDoc.

Q3. Is the program decomposition (class and method structure) appropriate?

&emsp; Yes, a separated Dice class will be helpful when we need to work on JavaFX part. And method Swap has been classified as a part of knight class.

Q4. Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?

&emsp; Yes, all methods and variables are properly named and their style are consistent throughout. I believed all codes here follow Java code conventions.

Q5. If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.

&emsp; No, I think all codes perform well.