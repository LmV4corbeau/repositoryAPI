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
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toschu.repositoryapi.api.helpers.JSONRepositoryFileFilter;

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

    public JSONRepository(File targetFolder, Class<T> type) {
        this.targetFolder = targetFolder;
        if (!this.targetFolder.exists()) {
            this.targetFolder.mkdirs();
        }
        this.type = type;
        this.fileFilter = new JSONRepositoryFileFilter(type);
    }

    @Override
    public Set<T> get() {
        Set<T> result = new HashSet<>();
        if (targetFolder != null) {
            for (File currentChildren : targetFolder.listFiles(this.fileFilter)) {
                if (currentChildren.getName()
                        .endsWith(type.getClass().getSimpleName())) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
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
                ObjectMapper objectMapper = new ObjectMapper();
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
            ObjectMapper objectMapper = new ObjectMapper();
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
}
