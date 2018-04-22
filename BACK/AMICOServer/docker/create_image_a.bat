$var = $(get-item ${PWD}).parent.FullName
$pathProjectAngular = $var + "..\ngFrontEnd"
$pathProject = $var
$pathJar = $pathProject + "\target"

#Create Angular
docker run -it --rm --name amicourses-angular -v ${pathProjectAngular}:/otp/amicourses -w /otp/amicourses teracy/angular-cli ng build --base-href /new/

#Move angular files to amicourses
rm ${pathProject}\src\main\resources\static\new\*
cp ${pathProjectAngular}\dist\* ${pathProject}\src\main\resources\static\new

#Create jar amicourses
docker run -it --rm --name amicourses -v ${pathProject}:/usr/src/mymaven -w /usr/src/mymaven maven mvn package

#Move jar to actual directory
cp ${pathProject}/files . 
mv ${pathJar}/AMICOServer-0.0.1-SNAPSHOT.jar .

#Create image
docker build -t mesteban96/amicoweb_angular .