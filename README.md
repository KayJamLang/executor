# KayJam code executor
[![CircleCI](https://circleci.com/gh/KayJamLang/executor.svg?style=svg)](https://circleci.com/gh/KayJamLang/executor)

Basic code executor for KayJam Core

## How to use
1. Add the library depending on:

    Maven:
    ```xml
    <dependency>
      <groupId>com.github.kayjamlang</groupId>
      <artifactId>executor</artifactId>
      <version>0.1.3.21</version>
    </dependency>
    ```
    
    Gradle:
    ```groovy
    implementation group: 'com.github.kayjamlang', name: 'executor', version: '0.1.3.21'
    ```

2. Call the method of the Executor class, 
   which will return the result of the code execution in Object:
   ```java
       class Main {
           public static void main(String[] args){
               String code = "println(\"Hello, World\");";
               Executor executor = new Executor();
               
               //Default library
               executor.addLibrary(new MainLibrary());
               executor.execute(code);
           }
       }   
   ```
## Additionally
[Library catalog for KayJam Executor](https://github.com/KayJamLang/executor/blob/main/libs.md)
