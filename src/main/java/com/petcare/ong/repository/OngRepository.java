package com.petcare.ong.repository;

import com.petcare.ong.model.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepository extends JpaRepository<Ong, Long> {
    Ong findByEmail(String email);
}
