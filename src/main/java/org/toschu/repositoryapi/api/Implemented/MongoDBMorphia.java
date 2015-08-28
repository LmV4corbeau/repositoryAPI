/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.toschu.repositoryapi.api.Implemented;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;
import org.toschu.repositoryapi.api.Repository;
import org.toschu.repositoryapi.api.Identity;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.toschu.repositoryapi.api.helpers.JodaTimeConverter;

/**
 *
 * @author corbeau
 * @param <T>
 */
public class MongoDBMorphia<T extends Identity> implements Repository<T> {

    protected static Logger logger
            = LoggerFactory.getLogger(MongoDBMorphia.class.getSimpleName());
    protected Class<T> type;
    protected MongoClient mongoClient;
    protected Morphia morphia;
    protected String databaseName;

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
        this.databaseName = databaseName;
        morphia.getMapper().getOptions().setStoreEmpties(true);
        morphia.getMapper().getOptions().setStoreNulls(true);
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
        morphia.getMapper().getOptions().setStoreEmpties(true);
        morphia.getMapper().getOptions().setStoreNulls(true);
        this.databaseName = packageName;
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
        morphia.getMapper().getOptions().setStoreEmpties(true);
        morphia.getMapper().getOptions().setStoreNulls(true);
        this.mongoClient = new MongoClient(address);
        String tmpdatabaseName = packageName.replaceAll("\\.", "");
        this.databaseName = tmpdatabaseName;
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
        morphia.getMapper().getOptions().setStoreEmpties(true);
        morphia.getMapper().getOptions().setStoreNulls(true);
        String tmpdatabaseName = packageName.replaceAll("\\.", "");
        this.databaseName = tmpdatabaseName;
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
        morphia.getMapper().getOptions().setStoreEmpties(true);
        morphia.getMapper().getOptions().setStoreNulls(true);
        this.databaseName = databaseName;
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
        morphia.getMapper().getOptions().setStoreEmpties(true);
        morphia.getMapper().getOptions().setStoreNulls(true);
        this.databaseName = databaseName;

    }

    protected Datastore createDataStore(String databaseName) {
        Datastore datastore = morphia.createDatastore(this.mongoClient, databaseName);
        datastore.ensureIndexes();
        return datastore;
    }

    /**
     * Searchs all Entities of T
     *
     * @return a Set of entities
     */
    @Override
    public Set<T> get() {
        logger.info("Database communication");
        Datastore datastore = createDataStore(databaseName);
        Query<T> dbresult = datastore.find(type);
        Set<T> result = new HashSet<>(dbresult.asList());

        return result;
    }

    @Override
    public void persist(Collection<T> entities) {
        logger.info("persist mulktiple");
        logger.info("Entities:\t", entities);
        Set<T> elements = get();
        entities.stream().forEach((currentEntity) -> {
            this.mergeEntity(currentEntity, elements);
        });
    }

    /**
     * Persist an Entity of T, if the entity is already in the database, it will
     * remove the old and add a the new own
     *
     * @param entity
     */
    @Override
    public void persist(T entity) {
        logger.info("persist single");
        logger.info("Entities:\t", entity);
        Set<T> elements = get();
        this.mergeEntity(entity, elements);
    }

    /**
     * searchs existing Entity and replace them with the new own
     *
     * @param entity
     * @param elements
     */
    private void mergeEntity(T entity, Set<T> elements) {
        boolean merged = false;
        Datastore datastore = createDataStore(databaseName);
        for (T currentElement : elements) {
            if (currentElement.equals(entity)) {
                try {
                    logger.info("merging");
                    merged = true;
                    WriteResult delete = datastore.delete(currentElement);
                    logger.info(delete.toString());
                    Key<T> save = datastore.save(entity);
                    logger.info("Savequery", save);
                    logger.info("merged:\t" + merged);
                } catch (UpdateException ue) {
                    if (!ue.getMessage().toLowerCase()
                            .equals("Nothing updated".toLowerCase())) {
                        throw ue;
                    }
                }
            } else {
            }
        }
        if (!merged) {
            logger.info("new Entity");
            datastore.save(entity);
        }
    }

    /**
     * removes all entities, they are equals to the given
     *
     * @param entity
     */
    @Override
    public void remove(T entity) {
        Datastore datastore = createDataStore(databaseName);
        datastore.delete(entity);
    }

    public void closeConnection() {
        this.mongoClient.close();
    }

}
