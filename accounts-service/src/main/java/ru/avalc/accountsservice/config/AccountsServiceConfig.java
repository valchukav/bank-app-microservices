package ru.avalc.accountsservice.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author Alexei Valchuk, 07.08.2023, email: a.valchukav@gmail.com
 */

@Configuration
@ConfigurationProperties(prefix = "accounts-service")
@Getter
@Setter
@ToString
public class AccountsServiceConfig {

    private String msg;
    private String buildVersion;
    private Map<String, String> mailDetails;
}
