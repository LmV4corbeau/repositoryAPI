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
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
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
        this.type = type;
        this.fileFilter = new JSONRepositoryFileFilter(type);
    }

    @Override
    public Set<T> get() {
        Set<T> result = new HashSet<>();
        if (targetFolder != null) {
            for (File currentChildren : targetFolder.listFiles(this.fileFilter)) {

            }
        }
        return result;
    }

    @Override
    public void persist(T entity) {
        if (targetFolder != null) {
            File entityFile
                    = new File(targetFolder,
                            entity.getIdentitier() + "."
                            + entity.getClass().getSimpleName());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(entityFile, entity);
            } catch (IOException exception) {
                logger.error("Can't load " + entity, exception);
            }
        }
    }

    @Override
    public void remove(T entity) {
        if (targetFolder != null) {
            String entityFileName
                    = entity.getIdentitier() + "." + entity.getClass().getSimpleName();
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

}
