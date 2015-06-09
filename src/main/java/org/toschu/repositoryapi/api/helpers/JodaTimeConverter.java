/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.helpers;

import java.util.Date;
import org.joda.time.DateTime;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

/**
 *
 * @author Eemuli
 * @link{https://github.com/Eemuli}
 */
public class JodaTimeConverter extends TypeConverter implements SimpleValueConverter {

    public JodaTimeConverter() {
        super(DateTime.class);
    }

    @Override
    public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
        if (fromDBObject == null) {
            return null;
        }

        if (fromDBObject instanceof Date) {
            Date d = (Date) fromDBObject;
            return new DateTime(d.getTime());
        }

        throw new RuntimeException(
                "Did not expect " + fromDBObject.getClass().getName());
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

        DateTime dateTime = (DateTime) value;
        return dateTime.toDate();
    }
}
