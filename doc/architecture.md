# Jailcrawl architecture

## Four parts, with the MVP (minimal value product)

#### 1. Roster

  - This is the managerment of all jail links
  - For MVP, it will be spreadsheet on shared G-drive
  
#### 2. Scrape

  - 1,000 or more classes in Java. That is too much, and we are thinking of
  an improvement. 
  - For MVP, do just 2 classes and two jails.


#### 3. Parser

  - We would like people to be able to contribute parsers in the future
  - For MVP, try to do a parser from a provided script, like
    
    go my-script-name 
    python myscript-name
    
#### 4. Data science

  - We want something flexible like NoSQL 
  - For MVP, just load the parse results into some simple db    
  