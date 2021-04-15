# Microservices
This has details to building microservices from scratch

# Port Details  
##(will be updated as and when new services are added)  
### Spring Cloud Config Server
server.port=9100


### Spring Cloud Eureka Server
server.port=9200 (prod)  
server.port=9210 (test)  
server.port=9220 (dev)  

### Spring Cloud API Gateway  
server.port=9000 (prod)  
server.port=9010 (test)  
server.port=9020 (dev)  

# Step 1: Build the cloud config server (for centralized configuration management)

a. Include the following dependency in the pom.xml 
```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

b. Add the following in the application.properties file  
spring.application.name=cloud-config-server  
server.port=9100  

c. Create a local config folder called git-config-repo and save this file as <application_name>.properties  
eg. login-service.properties  

d. Now go to this folder in command prompt and create a local git reposistory. This is where all the configurations will reside. The following is commands that need to be run for this.
cd git-config-repo  
git init  
git add .  
git commit -m "Initial commit"  

e. The final configuration is to tie this git config folder in the config server property file. This can be done by add the following line in the application.properties file.  
spring.cloud.config.server.git.uri=file:///Users/balaji/eclipse-workspace/Microservices/git-config-repo  

f. Please note that in my case I pushed all my config files into the centralized github and pulled them up during the application startup rather than maintaing it locally.  
For this instead of the above line I did the below configuration after pushing my config files to github  
spring.cloud.config.server.git.uri=https://github.com/balaji1974/microservices  
spring.cloud.config.server.git.searchPaths=git-config-repo  
spring.cloud.config.server.git.default-label=main  
(the above line depends on your branch name in github - avoiding master :))   

Add the user name and password as below in case of private repository  
spring.cloud.config.server.git.username=   
spring.cloud.config.server.git.password=   


f. Add the following annotation in the main class to make spring aware that this is a configuration server 
@EnableConfigServer

g. Start the server and use the browser to go to the following url:  
http://localhost:9100/login-service/default  
If a json respone with the config file content is displayed then everything is working fine.  

With this the config server is ready to pick the configuration files from the central git repo.  

# Step 2: Connecting the client services to the config server.

a. Include the following dependency in the pom.xml 

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

b. In the application.properties file enter the application name. This name should macth the name given in the cloud config property file.  
spring.application.name=login-service

c. Finally connect the client to the config server with the following line.  
spring.config.import=optional:configserver:http://localhost:9100  

With this the client will be able to fetch all the configurations from the central config server.  


# Step 3: Adding multiple enviroment properties to the config server   
Make the copy of the properties file that as created earlier and create files for dev and test enviroments as  
login-service-dev.properties  
login-service-test.properties  
Make changes to this file as per your local settings  

Excute the following command in git for these files.  
git add .  
git commit -m "Added new enviroment files"  

If the repository is located in github push these files.  

Now restart the config server and check if the files load properly with the following url:  
http://localhost:9100/login-service/dev  
http://localhost:9100/login-service/test  

Now to configure the client for the different enviroments we need to add the below line of code in the application.properties file of the client service.   
spring.profiles.active=test  

If the above does not work [in some versions of spring because of bug] we need to add the below line also. For me it works and the below line is not needed.  
spring.cloud.config.profile=test  

Thats it and all profiles are set and reading now from the central config server after client restart  


# Step 4: Adding basic security to connect to Spring Config Server (This will be later changed to OAuth2.0 when we integrate an OAuth Server)  

### a. Spring config server by default comes with basic security and all we need to add is the following 2 lines in the application.properties file.   
spring.security.user.name=balaji  
spring.security.user.password=balaji  

b. Now the clients can connect to the server using the same user id and password. It can be enabled on the client side by adding the following 2 lines:  
spring.cloud.config.username=balaji  
spring.cloud.config.password=balaji  

But in 99% of the use cases this might not be so useful as the password is open and anyone can peek into it.  

c. So we need to have some basic encryption for this password.  

For this to happen we need have "Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files" in our JVM   
I did not have this with me so, I went to oracle and downloaded it. Next I installed the 2 jar files that came with it, in my JDK_HOME/lib/security/ folder  

d. Next step is to encrypt my password. For this from command line I ran the following script after starting my config server.  
curl localhost:9100/encrypt -d balajibalaji -> Where 'balajibalaji' is my secrect key.  

I will now get an encrypted password which I can copy and paste it in the config server properties file like below:
spring.cloud.config.password={cipher}936fbc53d891780736d0470380c49beb43e73aee0fed5a639f1a220e3e2ba999  

Please note that {cipher} is ammended before the password which tells Springs that my password is encrypted.  

Also the secrect I used must be specified to Spring for it to use in encrypt/decrypt of passwords. This is done by added it in the following way:  
encrypt.key=balajibalaji  

e. So my final application.properties file for the config server would be:  

encrypt.key=balajibalaji  

spring.security.user.name=balaji  
spring.security.user.password={cipher}ccae6044dc0fd0a13e3e459298b7ff5bdf4ed56ee9a204779fb0e108b022af33  


### f. On the client side the configuration is fairly straigh forward:  


encrypt.key=balajibalaji  
spring.cloud.config.username=balaji
spring.cloud.config.password={cipher}ccae6044dc0fd0a13e3e459298b7ff5bdf4ed56ee9a204779fb0e108b022af33  

This security is not tamper proof but atleast it will not expose my passwords to the outside would when I push my config server or the microservice to some other third party location.  


# Step 5: Adding Discovery and Naming Server (Eureka) into my microservice ecosystem

a. Include the following two dependencies for config client and eureka server in the pom.xml 

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>

<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

b. Add the following annotation in the main class to make spring aware that this is a eureka server  
@EnableEurekaServer  

c. Connect Eureka to the config client with the following properties:  
spring.application.name=eureka-naming-server  
spring.config.import=optional:configserver:http://localhost:9100  

encrypt.key=pA5hGk9SUN87  
spring.cloud.config.username=balaji  
spring.cloud.config.password={cipher}1a0de898f66611ff768031fb288db89146a249989996ae638f6431c437d62d5f  

spring.profiles.active=test  

d. Now as usual create 3 config files as shown below in the local github repository of the config client, add the below configurations to it and show below and commit it as before.  
eureka-naming-server.properties  
eureka-naming-server-test.properties  
eureka-naming-server-dev.properties  

The contents of the file are sampled below:  
spring.application.name=eureka-naming-server  
server.port=9200  

eureka.client.register-with-eureka=false  
eureka.client.fetch-registry=false  
eureka.server.maxThreadsForPeerReplication=0


e. Now start up Eureka and see if it is picking the configuration from the config files. In our case it must pick from the test configuration file as we had given spring.profiles.active=test  

Thats it. Eureka server is up and running.  


# Step 6: Connecting the client services to my discovery and naming server (Eureka)  

a. Include the following dependency for eureka client in the pom.xml  

```xml  
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

b. Add the following annotation in the main class to make spring aware that this is a eureka client  
@EnableEurekaClient  

c. Connect the client services with the Eureka server by adding the following properties:  
eureka.client.serviceUrl.defaultZone=http://localhost:9200/eureka  

Of course this file will be in the git repo and hence it has to be committed.  

d. Restart the config server first, then the Eureka and then the client.  
Now check if the client is connected with Eureka by going to the following URL:  (since I have enabled test profile)
http://localhost:9210/  


# Step 7: Adding basic security on the Spring Cloud Eureka Server (This will be later changed)  
a. Include the following dependency for security in the pom.xml  

```xml  
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
``` 

b. Add the following basic in-memory (to be changed later) configuration class. 

@Configuration  
@EnableWebSecurity  
@Order(1)   
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {  

&nbsp;&nbsp;&nbsp;@Autowired  
&nbsp;&nbsp;&nbsp;public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;String password = passwordEncoder().encode("randompwd");  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;auth.inMemoryAuthentication().withUser("balaji")  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.password(password).roles("SYSTEM");  
&nbsp;&nbsp;&nbsp;}  

&nbsp;&nbsp;&nbsp;@Override  
&nbsp;&nbsp;&nbsp;protected void configure(HttpSecurity http) throws Exception {  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http.sessionManagement()  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.and().requestMatchers().antMatchers("/eureka/**")   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.and().authorizeRequests().antMatchers("/eureka/**")  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.hasRole("SYSTEM").anyRequest().denyAll().and()  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.httpBasic().and().csrf().disable();  
&nbsp;&nbsp;&nbsp;}   
   
&nbsp;&nbsp;&nbsp;@Bean  
&nbsp;&nbsp;&nbsp;public BCryptPasswordEncoder passwordEncoder() {   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return new BCryptPasswordEncoder();  
&nbsp;&nbsp;&nbsp;}  
}  

With this the server is secure.  


# Step 8: Changing the client configuration to connect to Eureka   

a. On the client property file modify the following line:  

eureka.client.serviceUrl.defaultZone=http://localhost:9210/eureka  

to   

eureka.client.serviceUrl.defaultZone=http://balaji:randompwd@localhost:9210/eureka   

Thats all and the client is now ready to connect to Eureka after restart.   

# Step 9: Creating the API Gateway  
a. The dependencies that are needed for the api-gateway are - Cloud config client for fetching the centralized configuration, Eureka client for registering with discovery services and Spring cloud Gatway server.  
Add then as follows  

```xml 
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-config</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
``` 

b. Check if the dependences are downloaded and if the server is starting successfully after this  

c. Add the following configuration properties in the API-Gateway propeties file that is stored in the github and commit it  
(As usual create 3 properties file for each enivornment and add this into all these 3 properties file)  

spring.cloud.gateway.discovery.locator.enabled=true  
spring.cloud.gateway.discovery.locator.lower-case-service-id=true   

This will allow the api gateway to discover other services that are registered in Eureka and this will also allow other microservices to be called using the API gateway URL   

As a next step our login microservice was called using the below URL  
http://localhost:8110/login  

Now it can be accessed using the API gateway URL as follows:   
http://localhost:9010/login-service/login  



