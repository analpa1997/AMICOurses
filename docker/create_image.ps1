$directory = $(get-item ${PWD}).parent.FullName
$ngFE_dir = $directory + "\ngFrontEnd"
$amicourses_dir = $directory + "\BACK\AMICOServer"
$jar_dir = $amicourses_dir + "\target"

#Create ngFrontEnd production
docker run -it --rm --name amicourses_ng -v ${ngFE_dir}:/otp/amicourses -w /otp/amicourses teracy/angular-cli ng build --base-href /new/

#Move angular files to amicoServer
rm ${amicourses_dir}\src\main\resources\static\new\* -Recourse
cp ${ngFE_dir}\dist\*  -Recourse ${amicourses_dir}\src\main\resources\static\new

#Create jar amicourses
docker run -it --rm --name amicourses -v ${amicourses_dir}:/usr/src/mymaven -w /usr/src/mymaven maven mvn clean package 

#Move jar to actual directory
mv ${jar_dir}/AMICOServer-0.0.1-SNAPSHOT.jar . -Force
cp -rf $amicourses_dir/files . -Force

#Create image
docker build -t mesteban96/amico_angular_spring .
