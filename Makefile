build:
	-mkdir bin
	javac -sourcepath src -d bin src/Graviton.java
	echo "fps=70" > Data/settings.dat
clean:
	-rm -rf bin Release
	-rm -rf Data/*.dat
run:
	java -cp bin Graviton
jar: clean build
	cd bin; jar cfe Graviton.jar Graviton *
package: jar
	mkdir Release
	cp bin/Graviton.jar Release/Graviton.jar
	cp -R Data Release/Data
	cp -R levels Release/levels
	zip -r Graviton Release
	rm -rf Release
