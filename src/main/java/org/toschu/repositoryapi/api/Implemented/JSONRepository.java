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
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author corbeau
 */
public class JSONRepository<T extends Identity> implements Repository<T> {

    protected File targetFolder;
    protected Class<T> type;
    protected static Logger logger
            = Logger.getLogger(JpaRepository.class.getSimpleName());

    public JSONRepository(File targetFolder, Class<T> type) {
        this.targetFolder = targetFolder;
        this.type = type;
    }

    @Override
    public Set<T> get() {
        Set<T> result = new HashSet<>();
        if (targetFolder != null) {
            for (File currentChildren : targetFolder.listFiles()) {
                if (currentChildren.getName().endsWith(type.getSimpleName())) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        T loaded = objectMapper.readValue(currentChildren,
                                type);
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
                            entity.getIdentitier() + "."
                            + entity.getClass().getSimpleName());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(entityFile, type);
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

}
