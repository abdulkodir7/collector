package com.itransition.coursework.search.index;

import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.itransition.coursework.util.Constants.THREAD_NUMBER;

@Transactional
@Component
@RequiredArgsConstructor
public class Indexer {

    private final EntityManager entityManager;

    public void indexPersistedData(String indexClassName) throws com.itransition.coursework.search.index.IndexException {

        try {
            SearchSession searchSession = Search.session(entityManager);

            Class<?> classToIndex = Class.forName(indexClassName);
            MassIndexer indexer =
                    searchSession
                            .massIndexer(classToIndex)
                            .threadsToLoadObjects(THREAD_NUMBER);

            indexer.startAndWait();
        } catch (ClassNotFoundException e) {
            throw new com.itransition.coursework.search.index.IndexException("Invalid class " + indexClassName, e);
        } catch (InterruptedException e) {
            throw new com.itransition.coursework.search.index.IndexException("Index Interrupted", e);
        }
    }
}
