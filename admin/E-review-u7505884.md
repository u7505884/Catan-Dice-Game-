## Code Review

Reviewed by: Ziling Ruan, u7505884

Reviewing code written by: Haoxiang Wang u7544188

Component: [Board](https://gitlab.cecs.anu.edu.au/u7546144/comp1110-ass2/-/blob/main/src/comp1110/ass2/Board.java#L1-220) 


Comments:


_Q1: What are the best features of this code?_

A1: Initialize the board including creating all roads, settlements, cities and knights, and check whether each type of building was built when the previous one was built by creating a set HashMap corresponding to the key.


_Q2、Is the code well-documented?_

A2：Yes, each method and definition coded by Haoxiang is well-documented.


_Q3、Is the program decomposition (class and method structure) appropriate?_

A3：Yes, the plan is appropriate. Let me explain as follows.

Firstly, Haoxiang uses HashMap to build a collection, and inserts the key and value mapping relationship into the HashMap through the put method to initialize the board, including creating all roads, settlements, cities and knights.

Secondly, each building can only be built after the previous one is completed. For example, to build R0, you must first build R1. Haoxiang wisely determines whether we can build the current structure by building constraints。

_Q4、Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?_

A4：YES, it follows Java code conventions, and its style is consistent throughout.


_Q5、If you suspect an error in the code, suggest a particular situation in which the program will nolyt function correctly._

A5：NO.


