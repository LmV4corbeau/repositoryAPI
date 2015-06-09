/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.Implemented;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.toschu.repositoryapi.api.Repository;
import org.toschu.repositoryapi.api.Identity;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateException;
import org.toschu.repositoryapi.api.helpers.JodaTimeConverter;

/**
 *
 * @author corbeau
 * @param <T>
 */
public class MongoDBMorphia<T extends Identity> implements Repository<T> {

    protected static Logger logger
            = Logger.getLogger(MongoDBMorphia.class.getSimpleName());
    protected Class<T> type;
    protected MongoClient mongoClient;
    protected final Morphia morphia;
    protected final Datastore datastore;

    /**
     * for more Configuration like Mongodbcluster or Morpiha with own Mapper
     *
     * @param type
     * @param mongoClient
     * @param morphia
     * @param databaseName
     */
    public MongoDBMorphia(Class<T> type, MongoClient mongoClient, Morphia morphia, String databaseName) {
        this.type = type;
        this.mongoClient = mongoClient;
        this.morphia = morphia;
        this.morphia.getMapper().getConverters().addConverter(new JodaTimeConverter());
        this.datastore = morphia.createDatastore(this.mongoClient, databaseName);
        datastore.ensureIndexes();
    }

    /**
     *
     * @param type
     * @param packageName Morphia mappingbase and databaseName
     * @param dbHost
     * @param dbPort
     * @throws UnknownHostException
     */
    public MongoDBMorphia(Class<T> type, String packageName,
            String dbHost, Integer dbPort) throws UnknownHostException {
        this.type = type;
        this.mongoClient = new MongoClient(dbHost, dbPort);
        this.morphia = new Morphia().mapPackage(packageName);
        this.morphia.getMapper().getConverters().addConverter(new JodaTimeConverter());
        this.datastore = morphia.createDatastore(this.mongoClient, packageName);
        datastore.ensureIndexes();
    }

    /**
     *
     * @param type
     * @param packageName
     * @param address
     */
    public MongoDBMorphia(Class<T> type, String packageName, ServerAddress address) {
        this.type = type;
        this.morphia = new Morphia().mapPackage(packageName);
        this.morphia.getMapper().getConverters().addConverter(new JodaTimeConverter());
        this.mongoClient = new MongoClient(address);
        String databaseName = packageName.replaceAll("\\.", "");
        this.datastore = morphia.createDatastore(this.mongoClient, databaseName);
        datastore.ensureIndexes();
    }

    /**
     * localHost database
     *
     * @param type
     * @param packageName
     * @throws UnknownHostException
     */
    public MongoDBMorphia(Class<T> type, String packageName) throws UnknownHostException {
        this.type = type;
        this.mongoClient = new MongoClient();
        this.morphia = new Morphia().mapPackage(packageName);
        this.morphia.getMapper().getConverters().addConverter(new JodaTimeConverter());
        String databaseName = packageName.replaceAll("\\.", "");
        this.datastore = morphia.createDatastore(this.mongoClient, databaseName);
        datastore.ensureIndexes();
    }

    /**
     * loacal host database with own database name
     *
     * @param type
     * @param packageName
     * @param databaseName
     * @throws UnknownHostException
     */
    public MongoDBMorphia(Class<T> type, String packageName, String databaseName) throws UnknownHostException {
        this.type = type;
        this.mongoClient = new MongoClient();
        this.morphia = new Morphia().mapPackage(packageName);
        this.morphia.getMapper().getConverters().addConverter(new JodaTimeConverter());
        this.datastore = morphia.createDatastore(this.mongoClient, databaseName);
        datastore.ensureIndexes();
    }

    /**
     * Multiple Servers for database with own database name
     *
     * @param type
     * @param packageName
     * @param databaseName
     * @param servers
     * @throws UnknownHostException
     */
    public MongoDBMorphia(Class<T> type, String packageName, String databaseName, List<ServerAddress> servers) throws UnknownHostException {
        this.type = type;
        this.mongoClient = new MongoClient(servers);
        this.morphia = new Morphia().mapPackage(packageName);
        this.morphia.getMapper().getConverters().addConverter(new JodaTimeConverter());
        this.datastore = morphia.createDatastore(this.mongoClient, databaseName);
        datastore.ensureIndexes();
    }

    /**
     * Searchs all Entities of T
     *
     * @return a Set of entities
     */
    @Override
    public Set<T> get() {
        Query<T> dbresult = this.datastore.find(type);
        Set<T> result = new HashSet<>(dbresult.asList());
        return result;
    }

    /**
     * Persist an Entity of T, if the entity is already in the database, it will
     * be updatet
     *
     * @param entity
     */
    @Override
    public void persist(T entity) {
        logger.info("persist single");
        logger.info(entity);
        Set<T> elements = get();
        boolean merged = false;
        for (T currentElement : elements) {
            if (currentElement.equals(entity)) {
                try {
                    logger.info("merged");
                    merged = true;
                    this.datastore.merge(entity);
                } catch (UpdateException ue) {
                    if (!ue.getMessage().toLowerCase()
                            .equals("Nothing updated".toLowerCase())) {
                        throw ue;
                    }
                } catch (Exception e) {
                    throw e;
                }
            } else {
            }
        }
        if (!merged) {
            logger.info("new Entity");
            this.datastore.save(entity);
        }
    }

    /**
     * removes all entities, they are equals to the given
     *
     * @param entity
     */
    @Override
    public void remove(T entity) {
        this.datastore.delete(entity);
    }

}
