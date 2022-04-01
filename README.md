# Examples of spring jpa usage

# Setup
## 生成Metamodel
在eclipse的Java Compiler(右键点击项目->属性)里增加了一个名为Annotation Processing的选项，用于配置各种各样的注解处理。打开Annotation Processing的配置界面，勾选“Enable Annotation Processing”，配置代码生成的目录，然后，切换到下面的“Factory Path”配置界面，点击Add Jars，将hibernate-jpamodelgen的jar包引入，点击Apply，即可:

## run 

`./mvnw spring-boot:run`

指定active profile可以使用 `./mvnw spring-boot:run -Dspring-boot.run.profiles=joe`

如果需要指定JVM参数，最好打jar包执行： `java -Xmx2g -jar target/spring-jpa-example-0.0.1-SNAPSHOT.jar --spring.profiles.active=joe`

打包命令： `mvn package -Dmaven.test.skip=true `
# Test
 - Run Spring Boot application
 - nav to http://localhost:8080/products?caseNo=I0101