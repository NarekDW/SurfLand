package com.sp.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Accessors(chain = true)
// When spring will see this annotation he will call Special Convertor for XML
// from JAXB (Java API XML Binding)
@XmlRootElement // It's necessary if we want to return data in XML format
public class User {

    @Size(min = 3, max = 15, message = "Имя пользователя")
    String firstName;
    String lastName;
    int age;
}
