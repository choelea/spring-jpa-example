# Examples of spring jpa usage

# Setup
## 生成Metamodel
在eclipse的Java Compiler(右键点击项目->属性)里增加了一个名为Annotation Processing的选项，用于配置各种各样的注解处理。打开Annotation Processing的配置界面，勾选“Enable Annotation Processing”，配置代码生成的目录，然后，切换到下面的“Factory Path”配置界面，点击Add Jars，将hibernate-jpamodelgen的jar包引入，点击Apply，即可:

# Test
 - Run Spring Boot application
 - nav to http://localhost:8080/products?caseNo=I0101