$amicourses_dir = $(get-item ${PWD}).parent.FullName
$ngFE_dir = $amicourses_dir + "..\..\..\ngFrontEnd"
$jar_dir = $amicourses_dir + "\target"

#Create ngFrontEnd production
Write-Output "DIR $amicourses_dir"
docker run -it --rm --name amicourses_ng -v ${ngFE_dir}:/otp/amicourses -w /otp/amicourses teracy/angular-cli ng build --base-href /new/

#Move angular files to amicoServer
rm ${amicourses_dir}\src\main\resources\static\new\* -Recurse
cp ${ngFE_dir}\dist\*  -Recurse ${amicourses_dir}\src\main\resources\static\new -Force

#Create jar amicourses
docker run -it --rm --name amicourses -v ${amicourses_dir}:/usr/src/mymaven -w /usr/src/mymaven maven mvn clean package 

#Move jar to actual directory
mv ${jar_dir}/AMICOServer-0.0.1-SNAPSHOT.jar . -Force
cp -Recurse $amicourses_dir/files . -Force

#Create image
docker build -t mesteban96/amico_angular_spring .
