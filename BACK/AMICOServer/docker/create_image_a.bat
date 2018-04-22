#Create angular-cli
docker run -it --rm --name amicourses-angular -v "/Users/Miguel/Documents/GitHub/AMICOurses/ngFrontEnd":/otp/back -w /otp/back teracy/angular-cli ng build --base-href /new/

#Move angular files to Back
rm /Users/Miguel/Documents/GitHub/AMICOurses/BACK/AMICOServer/src/main/resources/static/new/*
cp - /Users/Miguel/Documents/GitHub/AMICOurses/ngFrontEnd/dist/* /Users/Miguel/Documents/GitHub/AMICOurses/BACK/AMICOServer/src/main/resources/static/new


docker run -it --rm --name back -v "/Users/Miguel/Documents/GitHub/AMICOurses/BACK/AMICOServer":/usr/src/mymaven -w /usr/src/mymaven maven mvn package -DskipTests

#Move jar to actual directory
mv /Users/Miguel/Documents/GitHub/AMICOurses/BACK/AMICOServer/target/AMICOServer-0.0.1-SNAPSHOT.jar .
cp /Users/Miguel/Documents/GitHub/AMICOurses/BACK/AMICOServer/files .

#Create image 
docker build -t mesteban96/amico_angular .