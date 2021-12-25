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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserOperation {
    private final MBeanServerConnection mBeanServerConnection;
    private final ObjectName domainBean;

    @Value("${com.mvg.sky.james.common-password}")
    private String commonPassword;

    public UserOperation(JamesConnector jamesConnector) throws IOException, MalformedObjectNameException {
        mBeanServerConnection = jamesConnector.getMBeanServerConnection();
        domainBean = new ObjectName(getObjectName());
    }

    public String getObjectName() {
        return "org.apache.james:type=component,name=usersrepository";
    }

    public void addUser(String username) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {username, commonPassword};
        String[] signature = {String.class.getName(), String.class.getName()};
        mBeanServerConnection.invoke(domainBean, "addUser", params, signature);
    }

    public void setPassword(String username, String password) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {username, password};
        String[] signature = {String.class.getName(), String.class.getName()};
        mBeanServerConnection.invoke(domainBean, "setPassword", params, signature);
    }

    public boolean verifyExists(String username) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {username};
        String[] signature = {String.class.getName()};
        return (boolean) mBeanServerConnection.invoke(domainBean, "verifyExists", params, signature);
    }

    public long countUsers() throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {};
        String[] signature = {};
        return (long) mBeanServerConnection.invoke(domainBean, "countUsers", params, signature);
    }

    public void deleteUser(String username) throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {username};
        String[] signature = {String.class.getName()};
        mBeanServerConnection.invoke(domainBean, "deleteUser", params, signature);
    }

    public List<?> listAllUsers() throws ReflectionException, InstanceNotFoundException, MBeanException, IOException {
        Object[] params = {};
        String[] signature = {};
        return (List<?>) mBeanServerConnection.invoke(domainBean, "listAllUsers", params, signature);
    }

}
