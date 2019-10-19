# Sample spring mvc application to demonstrate jmx micrometer metrics


# Dependencies for JMX and Micrometer for non spring boot applications

```
	<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-registry-jmx</artifactId>
			<version>1.3.0</version>
		</dependency>
		<dependency>
			<groupId>io.micrometer</groupId>
			<artifactId>micrometer-core</artifactId>
			<version>1.3.0</version>
		</dependency>
```


# JMX and micrometer integration

```
// Below method binds the jvm memory metrics to micrometer

MeterRegistry registry = jvmMicroMeterJmxMonitoring();
		new JvmMemoryMetrics().bindTo(registry);
	
	// Below method creates the registry
		
	public  MeterRegistry jvmMicroMeterJmxMonitoring() {
		// Pick a monitoring system here to use in your samples.
		return new JmxMeterRegistry(JmxConfig.DEFAULT, Clock.SYSTEM, HierarchicalNameMapper.DEFAULT);
}

```


# Enable tomcat level jmx metrics (out of the box). This can be integrated with monitoring tools like datadog to troubleshoot applications
```
<role rolename="manager-jmx"/>
<role rolename="manager-gui"/>
<user username="tomcat" password="tomcat" roles="manager-gui,manager-jmx"/>

Adding above lines to tomcat-users.xml gives visibility to jmx metrics

 add manager-jmx to view jmx proxy 
 http://localhost:8080/manager/jmxproxy

```

# Deploy to pcf

 cf push monolithic-springmvc -p ./target/monolithic-springmvc.war
 
 # Remotely Monitor Java Applications Deployed on PCF-via-JMX
 
 ```
 cf set-env monolithic-springmvc JBP_CONFIG_DEBUG '{enabled: true}'
 
 cf restage monolithic-springmvc
 
 cf ssh -N -T -L 5000:localhost:5000 monolithic-springmvc
 ```
 
 # Enable ssh into pcf
 
 ```
 cf ssh-enabled monolithic-springmvc
 cf ssh monolithic-springmvc


 ```