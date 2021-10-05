package com.example.bigdaddy.repository;


import com.example.bigdaddy.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UserFile, Long> {
}
