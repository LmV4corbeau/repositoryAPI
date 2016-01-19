/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.helpers;


import org.joda.time.Period;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

/**
 *
 * @author corbeau
 */
public class JodaPeriodConverter
        extends TypeConverter implements SimpleValueConverter {

    public JodaPeriodConverter() {
        super(Period.class);
    }

    @Override
    public Object decode(Class targetClass, Object fromDBObject,
            MappedField optionalExtraInfo) throws MappingException {
        if (fromDBObject == null) {
            return null;
        }

        if (fromDBObject instanceof Period) {
            Period period = (Period) fromDBObject;
            
            return null;
        }

        throw new RuntimeException(
                "Did not expect " + fromDBObject.getClass().getName());
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        return null;
    }
}
