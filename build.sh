# run using:
# sudo ./build.sh

# compile class under test
javac -cp .:src:lib/junit4.8.1/junit-4.8.1.jar src/JHangman.java

# compile test class
javac -cp .:src:test:lib/junit4.8.1/junit-4.8.1.jar test/JHangmanTest.java

# run test
java -cp .:src:test:lib/junit4.8.1/junit-4.8.1.jar org.junit.runner.JUnitCore JHangmanTest
