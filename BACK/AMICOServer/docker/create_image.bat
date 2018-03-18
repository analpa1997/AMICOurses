@echo off

cd ..
echo Cleaning folder and packaging app
call mvnw clean package

echo building docker image
call docker build -f docker/Dockerfile -t mesteban96/amicoweb .
echo Done