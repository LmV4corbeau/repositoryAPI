/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.helpers;

import java.io.File;
import java.io.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toschu.repositoryapi.api.Identity;

/**
 *
 * @author toschu
 * @param <T>
 */
public class JSONRepositoryFileFilter<T extends Identity> implements FileFilter {

    protected Class<T> type;
    protected static Logger logger
            = LoggerFactory.getLogger(JSONRepositoryFileFilter.class.getSimpleName());

    public JSONRepositoryFileFilter(Class<T> type) {
        this.type = type;
    }

    @Override
    public boolean accept(File pathname) {
        return pathname.getName().endsWith("." + type.getSimpleName());
    }

}
