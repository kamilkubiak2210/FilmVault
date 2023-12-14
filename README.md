#F ilmVault

FilmVault is a program in which we will create a movie database, allowing anyone to register and vote for their favorite titles. However, the right to add new content will be reserved for editors and administrators.

The program has two profiles: "dev" - development, connected to the H2 database containing test data, and "prod" - production, connected to the MySQL database without test data.

To launch the production version, you must first establish a connection with the MySQL database - the information is provided in the "application-prod.yml" file. The ADMIN has the username "admin@example.com" and the password "adminPass."

In the development profile, to log in as ADMIN, use the username "admin@example.com" and the password "adminpass."
