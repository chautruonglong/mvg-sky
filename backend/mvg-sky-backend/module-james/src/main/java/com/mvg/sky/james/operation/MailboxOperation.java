package com.mvg.sky.james.operation;

import com.mvg.sky.james.connector.JamesConnector;
import java.io.IOException;
import java.util.List;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import org.springframework.stereotype.Component;

@Component
public class MailboxOperation {
    private final MBeanServerConnection mBeanServerConnection;
    private final ObjectName domainBean;

    public MailboxOperation(JamesConnector jamesConnector) throws IOException, MalformedObjectNameException {
        mBeanServerConnection = jamesConnector.getMBeanServerConnection();
        domainBean = new ObjectName(getObjectName());
    }

    public String getObjectName() {
        return "org.apache.james:type=component,name=mailboxmanagerbean";
    }

    public List<?> listMailboxes(String username) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {username};
        String[] signature = {String.class.getName()};
        return (List<?>) mBeanServerConnection.invoke(domainBean, "listMailboxes", params, signature);
    }

    public void createMailbox(String namespace, String username, String name) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {namespace, username, name};
        String[] signature = {String.class.getName(), String.class.getName(), String.class.getName()};
        mBeanServerConnection.invoke(domainBean, "createMailbox", params, signature);
    }

    public void deleteMailbox(String namespace, String username, String name) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {namespace, username, name};
        String[] signature = {String.class.getName(), String.class.getName(), String.class.getName()};
        mBeanServerConnection.invoke(domainBean, "deleteMailbox", params, signature);
    }

    public void deleteMailboxes(String username) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {username};
        String[] signature = {String.class.getName()};
        mBeanServerConnection.invoke(domainBean, "deleteMailboxes", params, signature);
    }

    public void importEmlFileToMailbox(String namespace, String username, String name, String path) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {namespace, username, name, path};
        String[] signature = {String.class.getName(), String.class.getName(), String.class.getName(), String.class.getName()};
        mBeanServerConnection.invoke(domainBean, "importEmlFileToMailbox", params, signature);
    }
}
