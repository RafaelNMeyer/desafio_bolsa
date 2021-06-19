package com.example.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestBody {
    @XmlElement public String name;
    @XmlElement public int serial;
    @XmlElement public String validate;
    @XmlElement public String inDateTime;
    @XmlElement public String finalDateTime;
}
