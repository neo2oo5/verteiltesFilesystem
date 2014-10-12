cd /
cd verteiltesFilesystem\origin\verteiltesFilesystem\verteiltesFilesystem_origin\
md dist\System
copy System dist\System
cd dist
java -jar "verteiltesFilesystem_org.jar"
ping /n 6 localhost >nul 
