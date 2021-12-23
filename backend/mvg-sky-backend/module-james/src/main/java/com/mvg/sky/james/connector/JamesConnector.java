package com.mvg.sky.james.connector;

import java.io.IOException;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JamesConnector {
    private final JMXConnector jmxConnector;

    public JamesConnector(@Value("${com.mvg.sky.james.url}") final String jamesUrl) throws IOException {
        JMXServiceURL jmxServiceURL = new JMXServiceURL(jamesUrl);
        jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, null);
    }

    public MBeanServerConnection getMBeanServerConnection() throws IOException {
        return jmxConnector.getMBeanServerConnection();
    }
}
