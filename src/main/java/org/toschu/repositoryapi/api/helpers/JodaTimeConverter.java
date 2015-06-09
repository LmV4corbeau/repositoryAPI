/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.helpers;

import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

/**
 *
 * @author corbeau
 */
public class JodaTimeConverter extends TypeConverter implements SimpleValueConverter {

    public JodaTimeConverter() {
        super(DateTime.class);
    }

    @Override
    public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
        DateTime readed = null;
        if (fromDBObject == null) {
            return null;
        } else if(fromDBObject instanceof String){
            DateTimeFormatter dateTimeFormat
                    = ISODateTimeFormat.dateTimeNoMillis()
                    .withLocale(Locale.GERMANY);
            readed = dateTimeFormat.parseDateTime((String) fromDBObject);
        }
        return fromDBObject;
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        if (value == null) {
            return null;
        }

        if (!(value instanceof DateTime)) {
            throw new RuntimeException(
                    "Did not expect " + value.getClass().getName());
        }
        DateTimeFormatter dateTimeFormat
                = ISODateTimeFormat.dateTimeNoMillis()
                .withLocale(Locale.GERMANY);
        return dateTimeFormat.print(((DateTime) value));
    }
}
