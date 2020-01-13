FROM adoptopenjdk/maven-openjdk11:latest

RUN apt-get update
RUN apt-get -yq clean
RUN apt-get install wget
RUN apt-get update
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN dpkg -i google-chrome-stable_current_amd64.deb; apt-get -fy install

RUN groupadd -g 985 rhsvc && \
    useradd -m -r -u 985 -g rhsvc rhsvc

ENV M2_HOME=/home/rhsvc/.m2
ENV DISPLAY=:99
ENV CHROME_BIN=/usr/bin/google-chrome
RUN mkdir -p /home/rhsvc/.m2/repository
COPY . /home/rhsvc/census-fs-cucumber
RUN chown -R rhsvc:rhsvc /home/rhsvc/census-fs-cucumber
COPY .maven.settings.xml /home/rhsvc/.m2/settings.xml
WORKDIR /home/rhsvc/census-fs-cucumber
USER rhsvc

CMD Xvfb :99 -screen 0 1280x1024x24
CMD [ "mvn", "verify", "-Dmaven.repo.local=m2/repository"]
