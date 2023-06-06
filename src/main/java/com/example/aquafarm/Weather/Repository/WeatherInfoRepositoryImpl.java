package com.example.aquafarm.Weather.Repository;

import com.example.aquafarm.Weather.Domain.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
    public class WeatherInfoRepositoryImpl implements WeatherInfoRepository {

    private final EntityManager entityManager;

    @Autowired
    public WeatherInfoRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public WeatherInfo findByAddress(String address) {
        TypedQuery<WeatherInfo> query = entityManager.createQuery("SELECT w FROM WeatherInfo w WHERE w.address = :address", WeatherInfo.class);
        query.setParameter("address", address);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public WeatherInfo findByAddressAndTime(String address, LocalDate time) {
        String query = "SELECT w FROM WeatherInfo w WHERE w.address = :address AND w.time = :time";
        return entityManager.createQuery(query, WeatherInfo.class)
                .setParameter("address", address)
                .setParameter("time", time)
                .getSingleResult();
    }

    @Override
    public List<WeatherInfo> findByLocationXAndLocationY(Double locationX, Double locationY) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WeatherInfo> query = cb.createQuery(WeatherInfo.class);
        Root<WeatherInfo> root = query.from(WeatherInfo.class);
        query.select(root)
                .where(cb.equal(root.get("locationX"), locationX),
                        cb.equal(root.get("locationY"), locationY));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<WeatherInfo> findByTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WeatherInfo> query = cb.createQuery(WeatherInfo.class);
        Root<WeatherInfo> root = query.from(WeatherInfo.class);
        query.select(root)
                .where(cb.between(root.get("time"), startTime, endTime));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<WeatherInfo> findFirstByOrderByTimeDesc() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<WeatherInfo> query = cb.createQuery(WeatherInfo.class);
        Root<WeatherInfo> root = query.from(WeatherInfo.class);
        query.select(root)
                .orderBy(cb.desc(root.get("time")));
        return entityManager.createQuery(query)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<WeatherInfo> findByAquafarmId(int aquafarmId) {
        String jpql = "SELECT w FROM WeatherInfo w WHERE w.aquafarm.aquafarmId = :aquafarmId";
        TypedQuery<WeatherInfo> query = entityManager.createQuery(jpql, WeatherInfo.class);
        query.setParameter("aquafarmId", aquafarmId);
        return query.getResultList().stream().findFirst();
    }


    @Override
    public List<WeatherInfo> findAll() {
        // Custom implementation
        // Use entityManager to write and execute the query
        // ...
        return null;
    }

    @Override
    public List<WeatherInfo> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<WeatherInfo> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<WeatherInfo> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(WeatherInfo entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends WeatherInfo> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends WeatherInfo> S save(S entity) {
        return null;
    }

    @Override
    public <S extends WeatherInfo> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<WeatherInfo> findById(Integer weatherId) {
        WeatherInfo weatherInfo = entityManager.find(WeatherInfo.class, weatherId);
        return Optional.ofNullable(weatherInfo);
    }


    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends WeatherInfo> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends WeatherInfo> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<WeatherInfo> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public WeatherInfo getOne(Integer integer) {
        return null;
    }

    @Override
    public WeatherInfo getById(Integer integer) {
        return null;
    }

    @Override
    public WeatherInfo getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends WeatherInfo> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends WeatherInfo> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends WeatherInfo> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends WeatherInfo> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WeatherInfo> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends WeatherInfo> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends WeatherInfo, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }


}

