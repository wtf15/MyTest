package com.example.demo.jmxdemo;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.*;

public class JMXMain {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException,
        InstanceAlreadyExistsException, MBeanRegistrationException, IOException {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName on = new ObjectName("com.example.demo.jmxdemo.Mechine:type=mechine");
        MechineMBean mechineMBean = new Mechine();
        beanServer.registerMBean(mechineMBean, on);
        System.in.read();
    }
}
