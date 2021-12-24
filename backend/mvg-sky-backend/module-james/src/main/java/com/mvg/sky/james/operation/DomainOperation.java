package com.mvg.sky.james.operation;

import com.mvg.sky.james.connector.JamesConnector;
import java.io.IOException;
import java.util.List;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.springframework.stereotype.Component;

@Component
public class DomainOperation {
    private final MBeanServerConnection mBeanServerConnection;
    private final ObjectName domainBean;

    public DomainOperation(JamesConnector jamesConnector) throws IOException, MalformedObjectNameException {
        mBeanServerConnection = jamesConnector.getMBeanServerConnection();
        domainBean = new ObjectName(getObjectName());
    }

    public String getObjectName() {
        return "org.apache.james:type=component,name=domainlist";
    }

    public List<?> listDomains() throws ReflectionException, InstanceNotFoundException, MBeanException, IOException, AttributeNotFoundException {
        return (List<?>) mBeanServerConnection.getAttribute(domainBean, "Domains");
    }

    public void addDomain(String domain) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {domain};
        String[] signature = {String.class.getName()};
        mBeanServerConnection.invoke(domainBean, "addDomain", params, signature);
    }

    public void removeDomain(String domain) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {domain};
        String[] signature = {String.class.getName()};
        mBeanServerConnection.invoke(domainBean, "removeDomain", params, signature);
    }

    public boolean containsDomain(String domain) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {domain};
        String[] signature = {String.class.getName()};
        return (boolean) mBeanServerConnection.invoke(domainBean, "containsDomain", params, signature);
    }
}
