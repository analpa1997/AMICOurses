
#!/bin/sh

ngFE_dir=$(cd "$(dirname "$0")"; cd "../../../ngFrontEnd" ; pwd)
amicourses_dir=$(dirname $(pwd))
jar_dir=$amicourses_dir/target

echo Dir: $ngFE_dir
#Create ngFrontEnd prod
docker run -it --rm --name amicourses-a -v "$ngFE_dir":/otp/amicourses -w /otp/amicourses teracy/angular-cli ng build --base-href /new/

#Move angular files to amicoServer
rm -R $amicourses_dir/src/main/resources/static/new/*
cp -R $ngFE_dir/dist/ $amicourses_dir/src/main/resources/static/new

#Create jar amicourses
docker run -it --rm --name amicourses -v "$amicourses_dir":/usr/src/mymaven -w /usr/src/mymaven maven mvn package -DskipTests

#Move jar to actual directory
mv $jar_dir/AMICOServer-0.0.1-SNAPSHOT.jar .
cp -rf $amicourses_dir/files .

#Create image 
docker build -t mesteban96/amico_angular_spring .