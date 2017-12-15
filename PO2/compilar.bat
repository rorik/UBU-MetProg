@echo off
javac -encoding utf8 ^
-cp ./lib/* ^
-d ./bin ^
./src/juego/control/* ^
./src/juego/modelo/* ^
./src/juego/textui/* ^
./src/juego/util/*
copy .\rsc\log4j.properties .\bin\log4j.properties