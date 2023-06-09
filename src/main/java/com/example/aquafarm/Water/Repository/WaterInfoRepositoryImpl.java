package com.example.aquafarm.Water.Repository;

import com.example.aquafarm.Aquafarm.Domain.AquafarmInfo;
import com.example.aquafarm.Aquafarm.Repository.AquafarmInfoRepository;
import com.example.aquafarm.Water.Domain.WaterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class WaterInfoRepositoryImpl implements WaterInfoRepository{
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private AquafarmInfoRepository aquafarmInfoRepository;

    @Override
    public List<WaterInfo> findAllByDateBetween(int aquafarm_id, Timestamp start, Timestamp end) {
        AquafarmInfo aquafarmInfo = aquafarmInfoRepository.findById(aquafarm_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid aquafarm_id: " + aquafarm_id));

        return entityManager.createQuery(
                        "SELECT w FROM WaterInfo w WHERE w.aquafarm_info = :aquafarmInfo AND w.date >= :start AND w.date <= :end", WaterInfo.class)
                .setParameter("aquafarmInfo", aquafarmInfo)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    @Override
    public List<WaterInfo> findAll() {
        return null;
    }

    @Override
    public List<WaterInfo> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<WaterInfo> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<WaterInfo> findAllById(Iterable<Integer> integers) {
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
    public void delete(WaterInfo entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends WaterInfo> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends WaterInfo> S save(S entity) {
        return null;
    }

    @Override
    public <S extends WaterInfo> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<WaterInfo> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends WaterInfo> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends WaterInfo> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<WaterInfo> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public WaterInfo getOne(Integer integer) {
        return null;
    }

    @Override
    public WaterInfo getById(Integer integer) {
        return null;
    }

    @Override
    public WaterInfo getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends WaterInfo> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends WaterInfo> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends WaterInfo> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends WaterInfo> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WaterInfo> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends WaterInfo> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends WaterInfo, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
