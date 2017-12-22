# could be busybox (which is lighter) but than much more work to get dependecies right
FROM ubuntu:14.04 

RUN apt-get -y update
RUN apt-get -y install curl
RUN apt-get -y install vim
RUN apt-get -y install telnet
RUN apt-get -y install software-properties-common

# JAVA
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

ENV PATH $PATH:$JAVA_HOME/bin
# RUN curl -s --insecure \
#  --header "Cookie: oraclelicense=accept-securebackup-cookie;" ${JAVA_ARCHIVE} \
#  | tar -xz -C /usr/local/ && ln -s $JAVA_HOME /usr/local/java 

# assumes next are provided with --build-arg
ARG app_version
ENV app_name angelos-sample-app-assembly-${app_version}.jar
# We don't want to provide default values in Dockerfile since it could be a bad practice
# ARG http_interface
# ARG http_port

ADD target/scala-2.11/${app_name} .

# expose the port the server runs on
# EXPOSE ${http_port}

# Has to execute it this way, otherwise won't substitute the env var
CMD ["/bin/bash", "-c", "java -Dhttp.port=${http_port} -Dhttp.interface=${http_interface} -jar ${app_name}"]

