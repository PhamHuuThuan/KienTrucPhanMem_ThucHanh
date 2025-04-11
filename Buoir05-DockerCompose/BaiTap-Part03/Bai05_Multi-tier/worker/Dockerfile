FROM openjdk:11
WORKDIR /app

# Tải Jedis từ Maven Central
ADD https://repo1.maven.org/maven2/redis/clients/jedis/4.4.3/jedis-4.4.3.jar jedis.jar

# Copy code
COPY Worker.java .

# Biên dịch với classpath chỉ định thư viện Jedis
RUN javac -cp jedis.jar Worker.java

# Chạy chương trình Java có dùng classpath
CMD ["java", "-cp", ".:jedis.jar", "Worker"]
