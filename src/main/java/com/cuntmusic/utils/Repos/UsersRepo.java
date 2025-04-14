package com.cuntmusic.utils.Repos;

import com.cuntmusic.utils.tables.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends CrudRepository<Users, Long> {
    
}