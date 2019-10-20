# ReportServer

## Develop build status

![develop build status](https://travis-ci.com/robnn/reportserver.svg?token=1gPHpTfqK7tHXxSEHjKq&amp;branch=develop)

## Master build status

![develop build status](https://travis-ci.com/robnn/reportserver.svg?token=1gPHpTfqK7tHXxSEHjKq&amp;branch=master)

## Development frontend server

Run `ng serve` in `reportserver-ui` folder to start a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Development backend server

Install postgressql9:

`sudo apt-get install postgresql-9.6`

Install pgadmin 3 or 4

For 3:

`sudo apt-get install pgadmin3`

Start Pgadmin and create a database on the local running postgres server, with the name `report_server`.

Create a folder to hold the installed JDBC drivers, then set the `jdbc.drivers.folder` property in the config.properties file.

CD into project directory and run:

`mvn spring-boot:run`