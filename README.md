# KayJam code executor
[![CircleCI](https://circleci.com/gh/KayJamLang/core.svg?style=svg)](https://circleci.com/gh/KayJamLang/executor)

Basic code executor for KayJam Core

## How to use
1. Add the library depending on:

    Maven:
    ```xml
    <dependency>
      <groupId>com.github.kayjamlang</groupId>
      <artifactId>executor</artifactId>
      <version>version</version>
    </dependency>
    ```
    
    Gradle:
    ```groovy
    implementation group: 'com.github.kayjamlang', name: 'core', version: 'version'
    ```

2. Call the method of the Executor class, 
   which will return the result of the code execution in Object:
   ```java
       class Main {
           public static void main(String[] args){
               String code = "{some code}";
               Object object = new Executor()
                   .execute(code);
           }
       }   
   ```
