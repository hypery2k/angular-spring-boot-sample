# ubuntu base image
FROM java:8
# prepare env
ENV DEBIAN_FRONTEND noninteractive
RUN dpkg-divert --local --rename --add /sbin/initctl && ln -sf /bin/true /sbin/initctl

# use google DNS
RUN echo 'nameserver 8.8.8.8' > /etc/resolv.conf; echo 'nameserver 8.8.4.4' >> /etc/resolv.conf ;

# tools
RUN \
  apt-get clean && \
  apt-get update && \
  apt-get -f install -y vim openssh-server net-tools bzip2 mysql-server unzip nginx wget

# Adding files
ADD bootstrap.sh /usr/bin/
ADD prepareMySQL.sh /tmp/
ADD ${project.build.finalName}.jar /tmp/${project.build.finalName}.jar
RUN chmod +x /tmp/prepareMySQL.sh
RUN chmod +x /usr/bin/bootstrap.sh

# prepare mysql
RUN /tmp/prepareMySQL.sh NGSPRING ngspring password

EXPOSE 40080

# print versions
RUN java -version

ENTRYPOINT ["bootstrap.sh"]