# Boggle
## Boggle Proof Of Concept

## Assumptions:
* Square board to allow for inverse matrix

## Dictionary
* JSON format
* [Source](http://blog.dotnetframework.org/2013/05/30/english-dictionary-api-served-with-mongodb/)

## Libraries Used
* https://github.com/douglascrockford/JSON-java

## Tree's ported from my own C++ implementations
* [My C++ Trees](https://github.com/jidol/tree)

## Optimizations
* Thread used to read in dictionary by half
* Thread read solution is possible with semaphore add in
* Also dictionary is already in alpha order
* Only store dictionary words that would fit within the board