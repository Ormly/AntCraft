# antdefense
### git quick guide
<b>First time ever?</b>

1. Install git!
2. Clone the repository
```
git clone https://github.com/Ormly/AntCraft.git
```
3. enter the working directory
```
cd AntCraft
```

<b>Want to start to write some code?</b>
1. Make sure you have the most recent version of the code
```
git pull
```
2. Create your own branch
```
git checkout -b feature/new_branch_desctiptive_name
```
3. Write code like crazy

<b>Ready to share with the world?</b>
1. Commit your work
```
git commit -m -a "short comment describing what changed since the last commit"
```
* commit is local, you may do that as often as you wish. Usualy when you're finished writing some part of the feature, and you're happy   with it but the feature is not yet done.

2. Push to remote branch
```
git push origin feature/new_branch_desctiptive_name
```
this will create a new branch on the server with your *commited* work

3. When you're ready, open a pull request on github requesting to merge your branch into master.

<b>Ready to work on the next feature?</b>

Make sure you're back on the master branch, before making another new one
````
git checkout master
````

### Using Logging
The `Logging` class provides a global logging solution.

One time application configuration:
```java
import utilities.logging.AbstractLogger;
import utilities.logging.ConsoleLoggerFactory;
import utilities.logging.FileLoggerFactory;
import utilities.logging.Logging;

public static void main(String[] args)
{
    Logging.setLoggerFactory(new FileLoggerFactory(Constants.LOG_FILE_PATH));    
}
```

Usage anywhere
```java
Logging.getLogger(CurrentClass.class.getName()).info("information message");
```
A logger may also be stored in a class member for continuous usage throughout the class.
```java
private AbstractLogger logger;

public ObjectClass(){
    this.logger = Logging.getLogger(ObjectClass.class.getName());
}
...

this.logger.debug("x:" + obj.x);

...
this.logger.error("Unable to add new object with id: " + obj.id);
```
Example log entries
```text
2019-33-15 10:33:10::utilities.logging.Logging::INFO::Started logging..
2019-33-15 10:33:10::main.Main::INFO::Starting Game
```
