## Code Review

Reviewed by: Ta-Wei Chen, u7546144

Reviewing code written by: Ziling Ruan, u7505884

Component:  <FIXME: Task #4>
From
https://gitlab.cecs.anu.edu.au/u7546144/comp1110-ass2/-/blob/main/src/comp1110/ass2/CatanDice.java#L85
To
https://gitlab.cecs.anu.edu.au/u7546144/comp1110-ass2/-/blob/main/src/comp1110/ass2/CatanDice.java#L109

### Comments:
  - _**Q1: What are the best features of this code?**_<br>
    A1: Generally speaking, Josie uses "switch" as the main skeleton of her code, and I think it's a pretty elegant way.

  - _**Q2、Is the code well-documented?**_<br>
    A2：YES, it starts at line 85 and ends at line 109 in class CantanDice. The code is well-documented, concise, highly readable, sufficient and complete.
  
  - _**Q3、Is the program decomposition (class and method structure) appropriate?**_<br>
    A3：YES, the program is appropriate. Let me explain it as follows.
    
    First, Josie uses “.split()” to split “build something”, “trade something” or”swap something something” into a new ArrayList<String>.
    
    Second, in order to deal with “build”, Josie wisely refers it back to isBoardStateWellFormed() which is already well-constructed.
    
    Last but not least, as for “trade” and “swap”, Josie not only cleverly uses “.parseInt()”, but also gracefully utilizes comparison operators to ensure the returning integer is between 0 (inclusive)~5 (inclusive).

  - _**Q4、Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?**_<br>
    A4：YES, it follows Java code conventions, and its style is consistent throughout.
  
  - _**Q5、If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.**_<br>
    A5：NO, I don’t suspect any error in the code.
