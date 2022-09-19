package org.week2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void readProperty() {
        //Real file
        assertEquals("edu.whu.MyClass",
                Main.readProperty("/myapp.properties"));
        assertEquals("edu.whu.NoClass",
                Main.readProperty("/test1.properties"));
        //empty string
        assertThrows(Main.ReadPropertyException.class, ()->{
            Main.readProperty("");
        });
        //no such file
        assertThrows(Main.ReadPropertyException.class, ()->{
            Main.readProperty("/no.such.app.properties");
        });
        //no such property called bootstrapClass
        assertThrows(Main.ReadPropertyException.class, ()->{
            Main.readProperty("/test2.properties");
        });
    }

    @Test
    void executeAnnotation() {
        //true MyClass
        assertEquals(2022,
                Main.executeAnnotation("edu.whu.MyClass"));
        //wrong number of arguments
        assertThrows(Main.ExecuteAnnotationException.class, ()->{
            Main.executeAnnotation("edu.whu.Test1Class");
        });
        //no such class
        assertThrows(Main.ExecuteAnnotationException.class, ()->{
            Main.executeAnnotation("edu.whu.NoClass");
        });
        //no such constructor
        assertThrows(Main.ExecuteAnnotationException.class, ()->{
            Main.executeAnnotation("edu.whu.Test2Class");
        });
        //no such type return value
        assertThrows(Main.ExecuteAnnotationException.class, ()->{
            Main.executeAnnotation("edu.whu.Test3Class");
        });
    }
}
