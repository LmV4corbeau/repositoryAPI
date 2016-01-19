/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.Implemented;

import org.toschu.repositoryapi.api.Repository;
import org.toschu.repositoryapi.api.Identity;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toschu.repositoryapi.api.helpers.json.JSONPrivatekeySeriazlizer;
import org.toschu.repositoryapi.api.helpers.json.JSONPublickeySeriazlizer;
import org.toschu.repositoryapi.api.helpers.json.JSONRepositoryFileFilter;

/**
 *
 * @author corbeau
 * @param <T>
 */
public class JSONRepository<T extends Identity> implements Repository<T> {

    protected File targetFolder;
    protected Class<T> type;
    protected static Logger logger
            = LoggerFactory.getLogger(JSONRepository.class.getSimpleName());
    protected JSONRepositoryFileFilter fileFilter;
    protected ObjectMapper objectMapper;

    public JSONRepository(File targetFolder, Class<T> type) {
        this.targetFolder = targetFolder;
        if (!this.targetFolder.exists()) {
            this.targetFolder.mkdirs();
        }
        this.type = type;
        this.fileFilter = new JSONRepositoryFileFilter(type);
        this.objectMapper = getObjectMapper();

    }

    @Override
    public Set<T> get() {
        Set<T> result = new HashSet<>();
        if (targetFolder != null) {
            for (File currentChildren : targetFolder.listFiles(this.fileFilter)) {
                if (currentChildren.getName()
                        .endsWith(type.getClass().getSimpleName())) {
                    try {
                        ObjectMapper objectMapper = getObjectMapper();
                        T entity = objectMapper.readValue(currentChildren, type);
                        result.add(entity);
                    } catch (IOException exception) {
                        logger.error("Can't load " + currentChildren.getName(), exception);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void persist(T entity) {
        if (targetFolder != null) {
            File entityFile
                    = new File(targetFolder,
                            createFilename(entity)
                    );
            try {
                if (!entityFile.exists()) {
                    entityFile.createNewFile();
                }
                ObjectMapper objectMapper = getObjectMapper();
                objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(entityFile, entity);
            } catch (IOException exception) {
                logger.error("Can't save " + entity, exception);
            }
        }
    }

    @Override
    public void remove(T entity) {
        if (targetFolder != null) {
            String entityFileName = createFilename(entity);
            Set<File> deletes = new HashSet<>();
            for (File currentFile : targetFolder.listFiles()) {
                if (currentFile.getName().toLowerCase().equals(entityFileName)) {
                    currentFile.delete();
                }
            }
        }
    }

    public T parseJSONFileToObject(File jsonFile, Class<T> type) {
        T loaded = null;
        try {
            ObjectMapper objectMapper = getObjectMapper();
            loaded = objectMapper.readValue(jsonFile, type);
        } catch (IOException exception) {
            logger.error("Can't load " + jsonFile.getName(), exception);
        }
        return loaded;
    }

    public String createFilename(T entity) {
        return entity.getIdentitier() + "."
                + type.getSimpleName();
    }

    public ObjectMapper getObjectMapper() {
        if (this.objectMapper == null) {
            this.objectMapper = new ObjectMapper();
            SimpleModule privateKeyModule = new SimpleModule("PrivatKeyModule",
                    new Version(1, 0, 0, null));
            privateKeyModule.addSerializer(new JSONPrivatekeySeriazlizer());
            SimpleModule publicKeyModule = new SimpleModule("PrivatKeyModule",
                    new Version(1, 0, 0, null));
            publicKeyModule.addSerializer(new JSONPublickeySeriazlizer());
            objectMapper.registerModule(publicKeyModule);
            objectMapper.registerModule(privateKeyModule);

        }
        return this.objectMapper;
    }
}
