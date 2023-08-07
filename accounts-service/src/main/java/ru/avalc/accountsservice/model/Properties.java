package ru.avalc.accountsservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author Alexei Valchuk, 07.08.2023, email: a.valchukav@gmail.com
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Properties {
    private String msg;
    private String buildVersion;
    private Map<String, String> mailDetails;
}
