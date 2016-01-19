/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.helpers.json;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Base64;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

/**
 *
 * @author toschu
 */
public class JSONPrivatekeySeriazlizer extends SerializerBase<PrivateKey> {

    public JSONPrivatekeySeriazlizer() {
        super(PrivateKey.class, true);
    }

    @Override
    public void serialize(PrivateKey value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        String privatekey
                = Base64.getEncoder().encodeToString(value.getEncoded());
        jgen.writeStringField("PrivateKey", privatekey);
    }

}
