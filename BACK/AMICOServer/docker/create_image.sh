#!/bin/bash

ngFE_dir=$(dirname $(pwd))/ngFrontEnd
amicourses_dir=$(dirname $(pwd))/BACK/AMICOServer
jar_dir=$amicourses_dir/target

#Create ngFrontEnd prod
docker run -it --rm --name amicourses-angular -v "$ngFE_dir":/otp/amicourses -w /otp/amicourses teracy/angular-cli ng build --base-href /new/

#Move angular files to amicoServer
rm -rf $amicourses_dir/src/main/resources/static/new/*
cp -rf $ngFE_dir/dist/* $amicourses_dir/src/main/resources/static/new

#Create jar amicourses
docker run -it --rm --name amicourses -v "$amicourses_dir":/usr/src/mymaven -w /usr/src/mymaven maven mvn package -DskipTests



#Move jar to actual directory
mv $jar_dir/AMICOServer-0.0.1-SNAPSHOT.jar .
cp -rf $amicourses_dir/files .

#Create image 
docker build -t mesteban96/amico_angular_spring .
