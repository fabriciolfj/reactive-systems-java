package com.github.fabriciolfj.message;

import com.github.fabriciolfj.entity.PersonEntity;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Processing {

    @Incoming("upload")
    @Outgoing("database")
    public PersonEntity validate(final PersonEntity entity) {
        System.out.println("Item recebido");
        if (entity.age <= 0) {
            throw new IllegalArgumentException("Invalid age");
        }

        //entity.name = capitalize(entity.name);
        return entity;
    }

    public static String capitalize(final String name) {
        char[] chars = name.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])){
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i])) {
                found = false;
            }
        }

        return String.valueOf(chars);
    }
}
