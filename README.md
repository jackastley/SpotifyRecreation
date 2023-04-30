# Spotify console application
## A CRUD Java command line application which emulates the way Spotify clients manage thier music library

### Dependencies
- Ensure MySQL JDBC driver is added to classpath
- MYSQL should be running on localhost

### Set-up
- Create a database in MySQL called music_library_app
- Run db/config.sql in your MySQL command line
- Seed the database tables: Songs, Users and UserPlaylists with data
- Ensure a "DBPWRD" environment variable is set indicating the localhost database password
- Compile Main.java

### Run
- Run java Spotify in command line
- Login with username seeded in database
- Use any password
- Use commands indicated in the command line
