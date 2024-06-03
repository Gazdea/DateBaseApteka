FROM tomcat:latest
LABEL authors="mikha"
COPY ./target/DateBase.war /usr/local/tomcat/webapps/
ENTRYPOINT ["top", "-b"]